/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.integration;

import com.liferay.commerce.payment.integration.CommercePaymentIntegration;
import com.liferay.commerce.payment.internal.configuration.FunctionCommercePaymentIntegrationConfiguration;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	configurationPid = "com.liferay.commerce.payment.internal.configuration.FunctionCommercePaymentIntegrationConfiguration",
	service = CommercePaymentIntegration.class
)
public class FunctionCommercePaymentIntegration
	implements CommercePaymentIntegration {

	@Override
	public CommercePaymentEntry authorize(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		_portalCatapult.launch(
			commercePaymentEntry.getCompanyId(), Http.Method.POST,
			_functionCommercePaymentIntegrationConfiguration.
				oAuth2ApplicationExternalReferenceCode(),
			_getPayloadJSONObject(commercePaymentEntry),
			_functionCommercePaymentIntegrationConfiguration.authorizePath(),
			commercePaymentEntry.getUserId());

		return commercePaymentEntry;
	}

	@Override
	public CommercePaymentEntry cancel(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		_portalCatapult.launch(
			commercePaymentEntry.getCompanyId(), Http.Method.POST,
			_functionCommercePaymentIntegrationConfiguration.
				oAuth2ApplicationExternalReferenceCode(),
			_getPayloadJSONObject(commercePaymentEntry),
			_functionCommercePaymentIntegrationConfiguration.cancelPath(),
			commercePaymentEntry.getUserId());

		return commercePaymentEntry;
	}

	@Override
	public CommercePaymentEntry capture(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		_portalCatapult.launch(
			commercePaymentEntry.getCompanyId(), Http.Method.POST,
			_functionCommercePaymentIntegrationConfiguration.
				oAuth2ApplicationExternalReferenceCode(),
			_getPayloadJSONObject(commercePaymentEntry),
			_functionCommercePaymentIntegrationConfiguration.capturePath(),
			commercePaymentEntry.getUserId());

		return commercePaymentEntry;
	}

	@Override
	public String getKey() {
		return _functionCommercePaymentIntegrationConfiguration.key();
	}

	@Override
	public int getType() {
		return _functionCommercePaymentIntegrationConfiguration.type();
	}

	@Override
	public CommercePaymentEntry refund(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		_portalCatapult.launch(
			commercePaymentEntry.getCompanyId(), Http.Method.POST,
			_functionCommercePaymentIntegrationConfiguration.
				oAuth2ApplicationExternalReferenceCode(),
			_getPayloadJSONObject(commercePaymentEntry),
			_functionCommercePaymentIntegrationConfiguration.refundPath(),
			commercePaymentEntry.getUserId());

		return commercePaymentEntry;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_functionCommercePaymentIntegrationConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionCommercePaymentIntegrationConfiguration.class,
				properties);
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		String key = getKey();

		if (key == null) {
			return;
		}

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			_commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRels(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			if (key.equals(
					commercePaymentMethodGroupRel.getPaymentIntegrationKey())) {

				_commercePaymentMethodGroupRelLocalService.
					deleteCommercePaymentMethodGroupRel(
						commercePaymentMethodGroupRel.
							getCommercePaymentMethodGroupRelId());
			}
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties)
		throws PortalException {

		_functionCommercePaymentIntegrationConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionCommercePaymentIntegrationConfiguration.class,
				properties);

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			_commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRels(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			String key = (String)properties.get("key");

			if (key.equals(
					commercePaymentMethodGroupRel.getPaymentIntegrationKey())) {

				_commercePaymentMethodGroupRelLocalService.
					deleteCommercePaymentMethodGroupRel(
						commercePaymentMethodGroupRel.
							getCommercePaymentMethodGroupRelId());
			}
		}
	}

	private JSONObject _getPayloadJSONObject(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			commercePaymentEntry.getClassPK());

		if (objectEntry == null) {
			throw new PortalException(
				"No object entry found with object entry ID " +
					commercePaymentEntry.getClassPK());
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				commercePaymentEntry.getCommerceChannelId());

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				fetchCommercePaymentMethodGroupRel(
					commerceChannel.getGroupId(), getKey());

		UnicodeProperties typeSettingsUnicodeProperties =
			commercePaymentMethodGroupRel.getTypeSettingsUnicodeProperties();

		JSONObject typeSettingsJSONObject = _jsonFactory.createJSONObject();

		typeSettingsUnicodeProperties.forEach(
			(key, value) -> typeSettingsJSONObject.put(key, value));

		return JSONUtil.put(
			"commercePaymentEntry",
			_jsonFactory.looseSerializeDeep(commercePaymentEntry)
		).put(
			"objectEntry", _jsonFactory.looseSerializeDeep(objectEntry)
		).put(
			"typeSettings", typeSettingsJSONObject
		);
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	private volatile FunctionCommercePaymentIntegrationConfiguration
		_functionCommercePaymentIntegrationConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private PortalCatapult _portalCatapult;

}