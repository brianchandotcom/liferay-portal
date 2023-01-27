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

package com.liferay.segments.internal.criteria.mapper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.mapper.SegmentsCriteriaJSONObjectMapper;
import com.liferay.segments.internal.odata.ExpressionVisitorImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "segments.criteria.mapper.key=" + SegmentsCriteriaJSONObjectMapperImpl.KEY,
	service = SegmentsCriteriaJSONObjectMapper.class
)
public class SegmentsCriteriaJSONObjectMapperImpl
	implements SegmentsCriteriaJSONObjectMapper {

	public static final String KEY = "odata";

	@Override
	public JSONObject toJSONObject(
			Criteria criteria,
			SegmentsCriteriaContributor segmentsCriteriaContributor)
		throws Exception {

		Criteria.Criterion criterion = criteria.getCriterion(
			segmentsCriteriaContributor.getKey());

		return JSONUtil.put(
			"conjunctionName", _getCriterionConjunction(criterion)
		).put(
			"query", _getQueryJSONObject(criterion, segmentsCriteriaContributor)
		);
	}

	private String _getCriterionConjunction(Criteria.Criterion criterion) {
		if (criterion == null) {
			return StringPool.BLANK;
		}

		return criterion.getConjunction();
	}

	private JSONObject _getQueryJSONObject(
			Criteria.Criterion criterion,
			SegmentsCriteriaContributor segmentsCriteriaContributor)
		throws Exception {

		if (criterion == null) {
			return null;
		}

		String filterString = criterion.getFilterString();

		if (Validator.isNull(filterString)) {
			return null;
		}

		FilterParser filterParser = _filterParserProvider.provide(
			segmentsCriteriaContributor.getEntityModel());

		Expression expression = filterParser.parse(filterString);

		JSONObject jsonObject = (JSONObject)expression.accept(
			new ExpressionVisitorImpl(
				1, segmentsCriteriaContributor.getEntityModel()));

		if (Validator.isNull(jsonObject.getString("groupId"))) {
			jsonObject = JSONUtil.put(
				"conjunctionName",
				StringUtil.toLowerCase(
					String.valueOf(BinaryExpression.Operation.AND))
			).put(
				"groupId", "group_0"
			).put(
				"items", JSONUtil.putAll(jsonObject)
			);
		}

		return jsonObject;
	}

	@Reference
	private FilterParserProvider _filterParserProvider;

}