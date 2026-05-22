/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.audiences;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.web.internal.odata.AudiencesExpressionVisitor;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesJSONObjectBuilder {

	public static JSONObject toAudienceJSONObject(
			FilterParserProvider filterParserProvider,
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

		FilterParser filterParser = filterParserProvider.provide(
			contextContributor.getEntityModel());

		Expression expression = filterParser.parse(filterString);

		JSONObject jsonObject = (JSONObject)expression.accept(
			new AudiencesExpressionVisitor(
				contextContributor.getEntityModel()));

		if (!jsonObject.has("rules")) {
			jsonObject = JSONUtil.put(
				"conjunction", "AND"
			).put(
				"rules", JSONUtil.putAll(jsonObject)
			);
		}

		return jsonObject.put(
			"id", segmentsEntry.getSegmentsEntryKey()
		).put(
			"retentionType", _getRetentionType(segmentsEntry.getSource())
		);
	}

	private static String _getRetentionType(String source) {
		int index = source.indexOf(':');

		if (index < 0) {
			return "SESSION";
		}

		return StringUtil.toUpperCase(source.substring(index + 1));
	}

	private AudiencesJSONObjectBuilder() {
	}

}