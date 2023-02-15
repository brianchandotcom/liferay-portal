/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.criteria.mapper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.segments.asah.connector.internal.expression.JSONObjectIndividualSegmentsExpressionVisitorImpl;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionLexer;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionParser;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.mapper.SegmentsCriteriaJSONObjectMapper;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * @author Cristina González
 */
public class SegmentsCriteriaJSONObjectMapperImpl
	implements SegmentsCriteriaJSONObjectMapper {

	@Override
	public JSONObject toJSONObject(
		Criteria criteria,
		SegmentsCriteriaContributor segmentsCriteriaContributor) {

		Criteria.Criterion criterion = segmentsCriteriaContributor.getCriterion(
			criteria);

		return JSONUtil.put(
			"conjunctionId", _getCriterionConjunction(criterion)
		).put(
			"propertyKey", segmentsCriteriaContributor.getKey()
		).put(
			"query", _getQueryJSONObject(criterion)
		);
	}

	private String _getCriterionConjunction(Criteria.Criterion criterion) {
		if (criterion == null) {
			return StringPool.BLANK;
		}

		return criterion.getConjunction();
	}

	private String _getCriterionFilterString(Criteria.Criterion criterion) {
		if (criterion == null) {
			return StringPool.BLANK;
		}

		return criterion.getFilterString();
	}

	private JSONObject _getQueryJSONObject(Criteria.Criterion criterion) {
		String criterionFilterString = _getCriterionFilterString(criterion);

		if (Validator.isNull(criterionFilterString)) {
			return null;
		}

		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(criterionFilterString))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		JSONObject jsonObject = (JSONObject)expressionContext.accept(
			new JSONObjectIndividualSegmentsExpressionVisitorImpl());

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

}