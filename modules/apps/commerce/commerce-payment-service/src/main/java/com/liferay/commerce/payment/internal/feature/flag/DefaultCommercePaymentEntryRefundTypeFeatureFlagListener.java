/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.feature.flag;

import com.liferay.commerce.payment.configuration.CommercePaymentEntryRefundTypeConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.IOException;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "featureFlagKey=COMMERCE-12754",
	service = FeatureFlagListener.class
)
public class DefaultCommercePaymentEntryRefundTypeFeatureFlagListener
	implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		if (!enabled) {
			return;
		}

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					StringBundler.concat(
						"(&(companyId=", companyId, ")(service.pid=",
						CommercePaymentEntryRefundTypeConfiguration.class.
							getName(),
						"*))"));

			if (!ArrayUtil.isEmpty(configurations)) {
				return;
			}

			_createFactoryConfiguration(
				_configurationAdmin.createFactoryConfiguration(
					CommercePaymentEntryRefundTypeConfiguration.class.getName(),
					StringPool.QUESTION),
				CommercePaymentEntryRefundTypeConfiguration.class.getName(),
				"damaged-in-transit", "Damaged in Transit", companyId);
			_createFactoryConfiguration(
				_configurationAdmin.createFactoryConfiguration(
					CommercePaymentEntryRefundTypeConfiguration.class.getName(),
					StringPool.QUESTION),
				CommercePaymentEntryRefundTypeConfiguration.class.getName(),
				"product-defect", "Product Defect", companyId);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _createFactoryConfiguration(
			Configuration configuration, String factoryPid, String key,
			String name, long companyId)
		throws IOException {

		configuration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				ConfigurationAdmin.SERVICE_FACTORYPID, factoryPid
			).put(
				"enabled", true
			).put(
				"key", key
			).put(
				"name",
				StringBundler.concat(
					"{", LocaleUtil.getDefault(), ":", name, "}")
			).put(
				"priority", "0"
			).put(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				companyId
			).put(
				"configuration.cleaner.ignore", "true"
			).build());
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}