/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.audiences;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.model.SegmentsEntry;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesJSONObjectBuilder {

	public static JSONObject toAudienceJSONObject(
			JSONFactory jsonFactory, SegmentsEntry segmentsEntry)
		throws Exception {

		String criteria = segmentsEntry.getCriteria();

		if (Validator.isNull(criteria)) {
			return null;
		}

		JSONObject audienceJSONObject = _toAudienceJSONObject(
			jsonFactory, jsonFactory.createJSONObject(criteria));

		if (!audienceJSONObject.has("rules")) {
			audienceJSONObject = JSONUtil.put(
				"conjunction", "AND"
			).put(
				"rules", JSONUtil.putAll(audienceJSONObject)
			);
		}

		return audienceJSONObject.put(
			"id", segmentsEntry.getSegmentsEntryKey()
		).put(
			"retentionType", _getRetentionType(segmentsEntry.getSource())
		);
	}

	private static String _getRetentionType(String source) {
		int index = source.indexOf(':');

		if (index < 0) {
			return "BROWSER";
		}

		return StringUtil.toUpperCase(source.substring(index + 1));
	}

	private static JSONObject _toAudienceJSONObject(
		JSONFactory jsonFactory, JSONObject queryJSONObject) {

		if (!queryJSONObject.has("items")) {
			String propertyName = queryJSONObject.getString("propertyName");

			return JSONUtil.put(
				"attribute",
				_attributeNames.getOrDefault(propertyName, propertyName)
			).put(
				"operation",
				StringUtil.replace(
					queryJSONObject.getString("operatorName"), '-', '_')
			).put(
				"value", queryJSONObject.getString("value")
			);
		}

		JSONArray rulesJSONArray = jsonFactory.createJSONArray();

		JSONArray itemsJSONArray = queryJSONObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			rulesJSONArray.put(
				_toAudienceJSONObject(
					jsonFactory, itemsJSONArray.getJSONObject(i)));
		}

		return JSONUtil.put(
			"conjunction",
			StringUtil.toUpperCase(queryJSONObject.getString("conjunctionName"))
		).put(
			"rules", rulesJSONArray
		);
	}

	private AudiencesJSONObjectBuilder() {
	}

	private static final Map<String, String> _attributeNames =
		HashMapBuilder.put(
			"browser", "browser_name"
		).put(
			"customContext/ipGeocoderCountry", "ip_geocoder_country"
		).put(
			"languageId", "language"
		).put(
			"lastSignInDateTime", "last_sign_in_date"
		).put(
			"localDate", "local_date"
		).put(
			"referrerURL", "referrer_url"
		).put(
			"requestParameters", "request_parameters"
		).put(
			"signedIn", "signed_in"
		).put(
			"userAgent", "user_agent"
		).build();

}