/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.portal.instance.provisioning.internal.provider;

import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.headless.portal.instances.client.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.client.resource.v1_0.PortalInstanceResource;
import com.liferay.osb.portal.instance.provisioning.configuration.OSBPortalInstanceProvisioningConfiguration;
import com.liferay.osb.portal.instance.provisioning.constants.OSBPortalInstanceProvisioningCommerceOptions;
import com.liferay.osb.portal.instance.provisioning.constants.OSBPortalInstanceProvisioningPortalInstanceConstants;
import com.liferay.osb.portal.instance.provisioning.constants.OSBPortalInstanceProvisioningPortalInstanceStatus;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.osb.portal.instance.provisioning.configuration.OSBPortalInstanceProvisioningConfiguration",
	service = OSBPortalInstanceProvisioningProvider.class
)
public class OSBPortalInstanceProvisioningProvider {

	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void provisionPortalInstance(long commerceOrderId) throws Exception {
		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		if (commerceOrder == null) {
			return;
		}

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			_commerceSubscriptionEntryLocalService.
				fetchCommerceSubscriptionEntryByCommerceOrderItemId(
					commerceOrderItem.getCommerceOrderItemId());

		if ((commerceSubscriptionEntry == null) ||
			(commerceSubscriptionEntry.getSubscriptionStatus() !=
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_ACTIVE)) {

			return;
		}

		commerceSubscriptionEntry = _updateSubscriptionTypeSettingsProperties(
			commerceSubscriptionEntry,
			OSBPortalInstanceProvisioningPortalInstanceConstants.
				PORTAL_INSTANCE_STATUS,
			String.valueOf(
				OSBPortalInstanceProvisioningPortalInstanceStatus.IN_PROGRESS.
					getStatus()));

		try {
			PortalInstance portalInstance =
				_portalInstanceResource.postPortalInstance(
					_toPortalInstance(commerceOrderItem));

			_updateSubscriptionTypeSettingsProperties(
				commerceSubscriptionEntry,
				OSBPortalInstanceProvisioningPortalInstanceConstants.
					PORTAL_INSTANCE_STATUS,
				String.valueOf(
					OSBPortalInstanceProvisioningPortalInstanceStatus.ACTIVE.
						getStatus()),
				OSBPortalInstanceProvisioningPortalInstanceConstants.
					PORTAL_INSTANCE_VIRTUAL_HOSTNAME,
				portalInstance.getVirtualHost(),
				OSBPortalInstanceProvisioningPortalInstanceConstants.
					PORTAL_INSTANCE_WEB_ID,
				portalInstance.getPortalInstanceId());
		}
		catch (Exception exception) {
			_updateSubscriptionTypeSettingsProperties(
				commerceSubscriptionEntry,
				OSBPortalInstanceProvisioningPortalInstanceConstants.
					PORTAL_INSTANCE_STATUS,
				String.valueOf(
					OSBPortalInstanceProvisioningPortalInstanceStatus.FAILED.
						getStatus()));

			throw exception;
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		OSBPortalInstanceProvisioningConfiguration
			osbPortalInstanceProvisioningConfiguration =
				ConfigurableUtil.createConfigurable(
					OSBPortalInstanceProvisioningConfiguration.class,
					properties);

		PortalInstanceResource.Builder builder =
			PortalInstanceResource.builder();

		_portalInstanceResource = builder.authentication(
			osbPortalInstanceProvisioningConfiguration.remoteLogin(),
			osbPortalInstanceProvisioningConfiguration.remotePassword()
		).endpoint(
			osbPortalInstanceProvisioningConfiguration.remoteHost(),
			osbPortalInstanceProvisioningConfiguration.remotePort(),
			osbPortalInstanceProvisioningConfiguration.remoteSchema()
		).build();
	}

	private String _getDomain(CommerceOrderItem commerceOrderItem)
		throws Exception {

		List<KeyValuePair> keyValuePairs = _cpInstanceHelper.getKeyValuePairs(
			commerceOrderItem.getCPDefinitionId(), commerceOrderItem.getJson(),
			LocaleThreadLocal.getThemeDisplayLocale());

		Stream<KeyValuePair> keyValuePairsStream = keyValuePairs.stream();

		return keyValuePairsStream.filter(
			keyValuePair -> Objects.equals(
				OSBPortalInstanceProvisioningCommerceOptions.DOMAIN,
				keyValuePair.getKey())
		).map(
			KeyValuePair::getValue
		).findAny(
		).orElse(
			commerceOrderItem.getUserId() + ".com"
		);
	}

	private PortalInstance _toPortalInstance(
			CommerceOrderItem commerceOrderItem)
		throws Exception {

		String portalInstanceDomain = _getDomain(commerceOrderItem);

		CPInstance cpInstance = commerceOrderItem.getCPInstance();

		return new PortalInstance() {
			{
				setDomain(portalInstanceDomain);
				setPortalInstanceId(portalInstanceDomain);
				setSiteInitializerKey(cpInstance.getManufacturerPartNumber());
				setVirtualHost(portalInstanceDomain);
			}
		};
	}

	private CommerceSubscriptionEntry _updateSubscriptionTypeSettingsProperties(
		CommerceSubscriptionEntry commerceSubscriptionEntry, String... values) {

		UnicodeProperties subscriptionTypeSettingsUnicodeProperties =
			commerceSubscriptionEntry.getSubscriptionTypeSettingsProperties();

		for (int i = 0; i < values.length; i += 2) {
			subscriptionTypeSettingsUnicodeProperties.setProperty(
				values[i], values[i + 1]);
		}

		commerceSubscriptionEntry.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsUnicodeProperties);

		return _commerceSubscriptionEntryLocalService.
			updateCommerceSubscriptionEntry(commerceSubscriptionEntry);
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	private volatile PortalInstanceResource _portalInstanceResource;

}