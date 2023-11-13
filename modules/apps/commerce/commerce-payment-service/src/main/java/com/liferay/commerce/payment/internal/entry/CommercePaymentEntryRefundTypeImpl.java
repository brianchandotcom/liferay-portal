/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.entry;

import com.liferay.commerce.payment.configuration.CommercePaymentEntryRefundTypeConfiguration;
import com.liferay.commerce.payment.entry.CommercePaymentEntryRefundType;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.payment.configuration.CommercePaymentEntryRefundTypeConfiguration",
	service = CommercePaymentEntryRefundType.class
)
public class CommercePaymentEntryRefundTypeImpl
	implements CommercePaymentEntryRefundType {

	@Override
	public String getKey() {
		return _commercePaymentEntryRefundTypeConfiguration.key();
	}

	@Override
	public String getName(Locale locale) {
		LocalizedValuesMap localizedValuesMap =
			_commercePaymentEntryRefundTypeConfiguration.name();

		String value = localizedValuesMap.get(locale);

		if (Validator.isNotNull(value)) {
			return value;
		}

		return localizedValuesMap.getDefaultValue();
	}

	@Override
	public int getPriority() {
		return _commercePaymentEntryRefundTypeConfiguration.priority();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commercePaymentEntryRefundTypeConfiguration =
			ConfigurableUtil.createConfigurable(
				CommercePaymentEntryRefundTypeConfiguration.class, properties);
	}

	private volatile CommercePaymentEntryRefundTypeConfiguration
		_commercePaymentEntryRefundTypeConfiguration;

}