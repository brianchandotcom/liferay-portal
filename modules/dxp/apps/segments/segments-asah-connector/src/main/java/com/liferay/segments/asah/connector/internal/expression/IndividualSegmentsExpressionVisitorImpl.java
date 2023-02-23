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

package com.liferay.segments.asah.connector.internal.expression;

import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionBaseVisitor;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionParser;
import com.liferay.segments.criteria.Criteria;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

/**
 * @author Cristina González
 */
public class IndividualSegmentsExpressionVisitorImpl
	extends IndividualSegmentsExpressionBaseVisitor<Criteria> {

	@Override
	public Criteria visitAndExpression(
		@NotNull IndividualSegmentsExpressionParser.AndExpressionContext
			andExpressionContext) {

		Criteria criteria = visitChildren(
			(RuleNode)andExpressionContext.getChild(0));

		criteria.mergeCriteria(
			visitChildren((RuleNode)andExpressionContext.getChild(2)),
			Criteria.Conjunction.AND);

		return criteria;
	}

	@Override
	public Criteria visitChildren(@NotNull RuleNode ruleNode) {
		Criteria defaultResultCriteria = defaultResult();

		for (int i = 0; i < ruleNode.getChildCount(); i++) {
			if (!shouldVisitNextChild(ruleNode, defaultResultCriteria)) {
				break;
			}

			ParseTree parseTree = ruleNode.getChild(i);

			defaultResultCriteria = aggregateResult(
				defaultResultCriteria, parseTree.accept(this));
		}

		return defaultResultCriteria;
	}

	@Override
	public Criteria visitNotExpression(
		@NotNull IndividualSegmentsExpressionParser.NotExpressionContext
			notExpressionContext) {

		Criteria criteria = visitChildren(
			notExpressionContext.booleanUnaryExpression());

		Criteria.Criterion criterion = criteria.getCriterion(_KEY);

		Criteria resultCriteria = new Criteria();

		resultCriteria.addCriterion(
			_KEY, Criteria.Type.parse(criterion.getTypeValue()),
			"not (" + criterion.getFilterString() + ")",
			Criteria.Conjunction.parse(criterion.getConjunction()));

		return resultCriteria;
	}

	@Override
	public Criteria visitOrExpression(
		@NotNull IndividualSegmentsExpressionParser.OrExpressionContext
			orExpressionContext) {

		Criteria criteria = visitChildren(
			(RuleNode)orExpressionContext.getChild(0));

		criteria.mergeCriteria(
			visitChildren((RuleNode)orExpressionContext.getChild(2)),
			Criteria.Conjunction.OR);

		return criteria;
	}

	@Override
	public Criteria visitToFilterByCountExpression(
		@NotNull
			IndividualSegmentsExpressionParser.ToFilterByCountExpressionContext
				toFilterByCountExpressionContext) {

		IndividualSegmentsExpressionParser.FilterByCountExpressionContext
			filterByCountExpressionContext =
				(IndividualSegmentsExpressionParser.
					FilterByCountExpressionContext)
						toFilterByCountExpressionContext.getChild(0);

		Criteria criteria = new Criteria();

		criteria.addCriterion(
			_KEY, Criteria.Type.ANALYTICS,
			filterByCountExpressionContext.getText(), Criteria.Conjunction.AND);

		return criteria;
	}

	@Override
	protected Criteria aggregateResult(
		Criteria aggregateCriteria, Criteria nextResultCriteria) {

		if (aggregateCriteria == null) {
			return nextResultCriteria;
		}
		else if (nextResultCriteria == null) {
			return aggregateCriteria;
		}

		return aggregateCriteria;
	}

	@Override
	protected Criteria defaultResult() {
		return null;
	}

	private static final String _KEY = "event";

}