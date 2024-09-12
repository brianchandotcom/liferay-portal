/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.entry;

import com.liferay.commerce.payment.configuration.CommercePaymentEntryRefundTypeConfiguration;
import com.liferay.commerce.payment.entry.CommercePaymentEntryRefundType;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

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

		if (Validator.isNull(value)) {
			value = localizedValuesMap.getDefaultValue();
		}

		try {
			if (JSONUtil.isJSONObject(value)) {
				JSONObject jsonObject = _jsonFactory.createJSONObject(value);

				value = jsonObject.getString(_language.getLanguageId(locale));
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return value;
	}

	@Override
	public Map<Locale, String> getNameMap() {
		LocalizedValuesMap localizedValuesMap =
			_commercePaymentEntryRefundTypeConfiguration.name();

		Map<Locale, String> localizedValuesMapValues =
			localizedValuesMap.getValues();

		if (localizedValuesMapValues.isEmpty()) {
			localizedValuesMapValues.put(
				LocaleThreadLocal.getDefaultLocale(),
				localizedValuesMap.getDefaultValue());
		}

		return localizedValuesMapValues;
	}

	@Override
	public int getPriority() {
		return _commercePaymentEntryRefundTypeConfiguration.priority();
	}

	@Override
	public boolean isEnabled() {
		return _commercePaymentEntryRefundTypeConfiguration.enabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commercePaymentEntryRefundTypeConfiguration =
			ConfigurableUtil.createConfigurable(
				CommercePaymentEntryRefundTypeConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentEntryRefundTypeImpl.class);

	private volatile CommercePaymentEntryRefundTypeConfiguration
		_commercePaymentEntryRefundTypeConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}