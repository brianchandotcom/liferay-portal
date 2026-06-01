/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.audiences;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesJSONObjectBuilder {

	public static JSONObject toAudienceJSONObject(
			JSONFactory jsonFactory,
			SegmentsCriteriaContributor contextContributor,
			SegmentsEntry segmentsEntry)
		throws Exception {

		Criteria criteria = CriteriaSerializer.deserialize(
			segmentsEntry.getCriteria());

		Criteria.Criterion criterion = criteria.getCriterion(
			contextContributor.getKey());

		if (criterion == null) {
			return null;
		}

		String filterString = criterion.getFilterString();

		if (Validator.isNull(filterString)) {
			return null;
		}

		JSONObject audienceJSONObject = _toAudienceJSONObject(
			jsonFactory, jsonFactory.createJSONObject(filterString));

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
			return JSONUtil.put(
				"attribute", queryJSONObject.getString("propertyName")
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

}