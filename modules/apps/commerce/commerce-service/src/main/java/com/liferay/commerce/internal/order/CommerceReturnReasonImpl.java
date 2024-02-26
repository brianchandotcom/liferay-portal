/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order;

import com.liferay.commerce.configuration.CommerceReturnReasonConfiguration;
import com.liferay.commerce.order.CommerceReturnReason;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	configurationPid = "com.liferay.commerce.configuration.CommerceReturnReasonConfiguration",
	service = CommerceReturnReason.class
)
public class CommerceReturnReasonImpl implements CommerceReturnReason {

	@Override
	public String getKey() {
		return _commerceReturnReasonConfiguration.key();
	}

	@Override
	public String getName(Locale locale) {
		LocalizedValuesMap localizedValuesMap =
			_commerceReturnReasonConfiguration.name();

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
			_commerceReturnReasonConfiguration.name();

		return localizedValuesMap.getValues();
	}

	@Override
	public int getPriority() {
		return _commerceReturnReasonConfiguration.priority();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceReturnReasonConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceReturnReasonConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnReasonImpl.class);

	private volatile CommerceReturnReasonConfiguration
		_commerceReturnReasonConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}