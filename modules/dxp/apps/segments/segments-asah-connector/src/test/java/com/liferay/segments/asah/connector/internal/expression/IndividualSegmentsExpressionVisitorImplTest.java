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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionLexer;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionParser;
import com.liferay.segments.asah.connector.internal.expression.parser.test.util.IndividualSegmentsExpressionUtil;
import com.liferay.segments.criteria.Criteria;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class IndividualSegmentsExpressionVisitorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAcceptMultipleFilterByCount() {
		String filter = StringBundler.concat(
			IndividualSegmentsExpressionUtil.getFilterByCount(
				IndividualSegmentsExpressionUtil.getFilter(
					"Page#pageViewed#545188693724480043", "gt", "last24Hours"),
				"ge", 1),
			" and (",
			IndividualSegmentsExpressionUtil.getFilterByCount(
				IndividualSegmentsExpressionUtil.getFilter(
					"Page#pageViewed#545188693724480041", "lt", "2023-02-09"),
				"ge", 1),
			" or ",
			IndividualSegmentsExpressionUtil.getFilterByCount(
				IndividualSegmentsExpressionUtil.getFilter(
					"Page#pageViewed#545188693724480037", "gt", "2023-02-08"),
				"ge", 1),
			")");

		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(filter))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		Criteria criteria = expressionContext.accept(
			new IndividualSegmentsExpressionVisitorImpl());

		Criteria.Criterion criterion = criteria.getCriterion("event");

		Assert.assertEquals("and", criterion.getConjunction());

		Assert.assertEquals(
			StringBundler.concat(
				StringPool.OPEN_PARENTHESIS,
				IndividualSegmentsExpressionUtil.getFilterByCount(
					IndividualSegmentsExpressionUtil.getFilter(
						"Page#pageViewed#545188693724480043", "gt",
						"last24Hours"),
					"ge", 1),
				") and ((",
				IndividualSegmentsExpressionUtil.getFilterByCount(
					IndividualSegmentsExpressionUtil.getFilter(
						"Page#pageViewed#545188693724480041", "lt",
						"2023-02-09"),
					"ge", 1),
				") or (",
				IndividualSegmentsExpressionUtil.getFilterByCount(
					IndividualSegmentsExpressionUtil.getFilter(
						"Page#pageViewed#545188693724480037", "gt",
						"2023-02-08"),
					"ge", 1),
				"))"),
			criterion.getFilterString());

		Assert.assertEquals(
			String.valueOf(Criteria.Type.ANALYTICS), criterion.getTypeValue());
	}

	@Test
	public void testAcceptSimpleFilterByCount() {
		String filter = IndividualSegmentsExpressionUtil.getFilterByCount(
			IndividualSegmentsExpressionUtil.getFilter(
				"Page#pageViewed#545188693724480037", "gt", "2023-02-07"),
			"ge", 1);

		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(filter))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		Criteria criteria = expressionContext.accept(
			new IndividualSegmentsExpressionVisitorImpl());

		Criteria.Criterion criterion = criteria.getCriterion("event");

		Assert.assertEquals("and", criterion.getConjunction());

		Assert.assertEquals(filter, criterion.getFilterString());

		Assert.assertEquals(
			String.valueOf(Criteria.Type.ANALYTICS), criterion.getTypeValue());
	}

	@Test
	public void testAcceptSimpleFilterByCountWithNotOperator() {
		String filter = IndividualSegmentsExpressionUtil.getFilterByCount(
			IndividualSegmentsExpressionUtil.getFilter(
				"Page#pageViewed#545188693724480037", "gt", "2023-02-07"),
			"ge", 1);

		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream("not " + filter))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		Criteria criteria = expressionContext.accept(
			new IndividualSegmentsExpressionVisitorImpl());

		Criteria.Criterion criterion = criteria.getCriterion("event");

		Assert.assertEquals("and", criterion.getConjunction());

		Assert.assertEquals(
			"not (" + filter + ")", criterion.getFilterString());

		Assert.assertEquals(
			String.valueOf(Criteria.Type.ANALYTICS), criterion.getTypeValue());
	}

}