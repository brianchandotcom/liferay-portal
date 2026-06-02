/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.audiences;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntry;

import java.util.Map;

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

	private static JSONObject _toAudienceJSONObject(
		JSONObject queryJSONObject) {

		if (!queryJSONObject.has("items")) {
			String propertyName = queryJSONObject.getString("propertyName");

			return JSONUtil.put(
				"attribute",
				_attributeNames.getOrDefault(propertyName, propertyName)
			).put(
				"operator",
				StringUtil.replace(
					queryJSONObject.getString("operatorName"), '-', '_')
			).put(
				"value", queryJSONObject.getString("value")
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

	private static final Map<String, String> _attributeNames =
		HashMapBuilder.put(
			Context.BROWSER, "browser_name"
		).put(
			Context.LANGUAGE_ID, "language"
		).put(
			Context.LAST_SIGN_IN_DATE_TIME, "last_sign_in_date"
		).put(
			Context.LOCAL_DATE, "local_date"
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

}