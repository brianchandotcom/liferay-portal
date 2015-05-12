/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.Iterator;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class LocalizedValueDDMFormFieldTypeSettingJSONConverter
	implements DDMFormFieldTypeSettingJSONConverter
		<LocalizedValue, JSONObject> {

	@Override
	public LocalizedValue fromJSON(String serializedSetting) {
		if (Validator.isNull(serializedSetting)) {
			return null;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				serializedSetting);

			LocalizedValue localizedValue = new LocalizedValue();

			Iterator<String> itr = jsonObject.keys();

			while (itr.hasNext()) {
				String languageId = itr.next();

				localizedValue.addString(
					LocaleUtil.fromLanguageId(languageId),
					jsonObject.getString(languageId));
			}

			return localizedValue;
		}
		catch (JSONException jsone) {
			_log.error(jsone);
		}

		return null;
	}

	@Override
	public JSONObject toJSON(Object setting) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		LocalizedValue localizedValue = (LocalizedValue)setting;

		for (Locale availableLocale : localizedValue.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				localizedValue.getString(availableLocale));
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocalizedValueDDMFormFieldTypeSettingJSONConverter.class);

}