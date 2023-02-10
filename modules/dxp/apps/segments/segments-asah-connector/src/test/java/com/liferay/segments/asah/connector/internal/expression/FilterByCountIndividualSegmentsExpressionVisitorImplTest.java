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

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionLexer;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionParser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class FilterByCountIndividualSegmentsExpressionVisitorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAccept() {
		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(
							"activityKey eq " +
								"'Page#pageViewed#545188693724480037' and " +
									"day eq '2023-02-06'"))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		FilterByCountIndividualSegmentsExpressionVisitorImpl.FilterByCount
			filterByCount =
				(FilterByCountIndividualSegmentsExpressionVisitorImpl.
					FilterByCount)expressionContext.accept(
						new FilterByCountIndividualSegmentsExpressionVisitorImpl());

		FilterByCountIndividualSegmentsExpressionVisitorImpl.FilterByCount.Day
			day = filterByCount.getDay();

		Assert.assertEquals("eq", day.getOperator());
		Assert.assertEquals("2023-02-06", day.getValue());

		FilterByCountIndividualSegmentsExpressionVisitorImpl.FilterByCount.Event
			event = filterByCount.getEvent();

		Assert.assertEquals("pageViewed", event.getEvent());
		Assert.assertEquals("545188693724480037", event.getAssetId());
	}

	@Test
	public void testAcceptWithoutDay() {
		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(
							"activityKey eq " +
								"'Page#pageViewed#545188693724480037'"))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		FilterByCountIndividualSegmentsExpressionVisitorImpl.FilterByCount
			filterByCount =
				(FilterByCountIndividualSegmentsExpressionVisitorImpl.
					FilterByCount)expressionContext.accept(
						new FilterByCountIndividualSegmentsExpressionVisitorImpl());

		Assert.assertNull(filterByCount.getDay());

		FilterByCountIndividualSegmentsExpressionVisitorImpl.FilterByCount.Event
			event = filterByCount.getEvent();

		Assert.assertEquals("pageViewed", event.getEvent());
		Assert.assertEquals("545188693724480037", event.getAssetId());
	}

}