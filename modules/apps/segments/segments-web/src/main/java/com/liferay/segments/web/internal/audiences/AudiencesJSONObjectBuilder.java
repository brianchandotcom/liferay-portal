/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.audiences;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Map;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesJSONObjectBuilder {

	public static JSONObject toAudienceJSONObject(SegmentsEntry segmentsEntry)
		throws Exception {

		String criteria = segmentsEntry.getCriteria();

		if (Validator.isNull(criteria)) {
			return null;
		}

		JSONObject audienceJSONObject = _toAudienceJSONObject(
			JSONFactoryUtil.createJSONObject(criteria));

		return audienceJSONObject.put(
			"id", segmentsEntry.getSegmentsEntryKey()
		).put(
			"retentionType", "BROWSER"
		);
	}

	private static String _getOperator(String operatorName) {
		String operator = StringUtil.replace(operatorName, '-', '_');

		if (Objects.equals(operator, "contains")) {
			return "includes";
		}
		else if (Objects.equals(operator, "not_contains")) {
			return "not_includes";
		}

		return operator;
	}

	private static String _getValue(String propertyName, String value) {
		if (Objects.equals(propertyName, Context.LANGUAGE_ID)) {
			return LocaleUtil.toBCP47LanguageId(value);
		}
		else if (Objects.equals(propertyName, Context.LOCAL_DATE)) {
			try {
				LocalDate localDate = LocalDate.parse(
					value, _dateTimeFormatter);

				return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
			}
			catch (DateTimeParseException dateTimeParseException) {
				if (_log.isDebugEnabled()) {
					_log.debug(dateTimeParseException);
				}

				return value;
			}
		}

		return value;
	}

	private static JSONObject _toAudienceJSONObject(
		JSONObject queryJSONObject) {

		if (!queryJSONObject.has("items")) {
			String propertyName = queryJSONObject.getString("propertyName");

			return JSONUtil.put(
				"attribute",
				_attributeNames.getOrDefault(propertyName, propertyName)
			).put(
				"operator",
				_getOperator(queryJSONObject.getString("operatorName"))
			).put(
				"value",
				_getValue(propertyName, queryJSONObject.getString("value"))
			);
		}

		return JSONUtil.put(
			"conjunction",
			StringUtil.toUpperCase(queryJSONObject.getString("conjunctionName"))
		).put(
			"rules",
			() -> {
				JSONArray rulesJSONArray = JSONFactoryUtil.createJSONArray();

				JSONArray itemsJSONArray = queryJSONObject.getJSONArray(
					"items");

				for (int i = 0; i < itemsJSONArray.length(); i++) {
					rulesJSONArray.put(
						_toAudienceJSONObject(itemsJSONArray.getJSONObject(i)));
				}

				return rulesJSONArray;
			}
		);
	}

	private AudiencesJSONObjectBuilder() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AudiencesJSONObjectBuilder.class);

	private static final Map<String, String> _attributeNames =
		HashMapBuilder.put(
			Context.BROWSER, "browser_name"
		).put(
			Context.BROWSER_VERSION, "browser_version"
		).put(
			Context.LANGUAGE_ID, "language"
		).put(
			Context.LAST_SIGN_IN_DATE_TIME, "last_sign_in_date"
		).put(
			Context.LOCAL_DATE, "local_date"
		).put(
			Context.LOCAL_TIME, "local_time"
		).put(
			Context.REFERRER_URL, "referrer"
		).put(
			Context.REQUEST_PARAMETERS, "request_parameters"
		).put(
			Context.SIGNED_IN, "signed_in"
		).put(
			Context.USER_AGENT, "user_agent"
		).put(
			"customContext/ipGeocoderCountry", "ip_geocoder_country"
		).build();
	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("MM/dd/yyyy");

}