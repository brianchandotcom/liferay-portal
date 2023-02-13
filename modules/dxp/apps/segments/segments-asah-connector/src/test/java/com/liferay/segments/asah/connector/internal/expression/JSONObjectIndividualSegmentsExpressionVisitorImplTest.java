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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringBundler;
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
public class JSONObjectIndividualSegmentsExpressionVisitorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAcceptMultipleFilterByCount() {
		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(
							StringBundler.concat(
								"activities.filterByCount(filter='",
								"(activityKey eq ",
								"''Page#pageViewed#545188693724480043'' and ",
								"day gt ''last24Hours'')',operator='ge',",
								"value=1)) and ",
								"((activities.filterByCount(filter='",
								"(activityKey eq ",
								"''Page#pageViewed#545188693724480041'' and ",
								"day lt ''2023-02-09'')',operator='ge',",
								"value=1)) or ",
								"(activities.filterByCount(filter='(",
								"activityKey eq ",
								"''Page#pageViewed#545188693724480037'' and ",
								"day gt ''2023-02-08'')',",
								"operator='ge',value=1")))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		JSONObject jsonObject = (JSONObject)expressionContext.accept(
			new JSONObjectIndividualSegmentsExpressionVisitorImpl());

		Assert.assertEquals(
			StringBundler.concat(
				"{\"groupId\":\"group_2\",\"items\":[",
				"{\"propertyName\":\"pageViewed\",",
				"\"assetId\":\"545188693724480043\",",
				"\"day\":{\"operatorName\":\"gt\",\"value\":\"last24Hours\"},",
				"\"operatorName\":\"ge\",\"value\":\"1\"},",
				"{\"groupId\":\"group_1\",\"items\":[",
				"{\"propertyName\":\"pageViewed\",",
				"\"assetId\":\"545188693724480041\",",
				"\"day\":{\"operatorName\":\"lt\",\"value\":\"2023-02-09\"},",
				"\"operatorName\":\"ge\",\"value\":\"1\"},",
				"{\"propertyName\":\"pageViewed\",",
				"\"assetId\":\"545188693724480037\",",
				"\"day\":{\"operatorName\":\"gt\",\"value\":\"2023-02-08\"},",
				"\"operatorName\":\"ge\",\"value\":\"1\"}],",
				"\"conjunctionName\":\"or\"}],",
				"\"conjunctionName\":\"and\"}"),
			jsonObject.toString());
	}

	@Test
	public void testAcceptSimpleFilterByCount() {
		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(
							StringBundler.concat(
								"activities.filterByCount(filter='",
								"(activityKey eq ",
								"''Page#pageViewed#545188693724480037'' and ",
								"day gt ''2023-02-07'')',",
								"operator='ge',value=1)")))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		JSONObject jsonObject = (JSONObject)expressionContext.accept(
			new JSONObjectIndividualSegmentsExpressionVisitorImpl());

		Assert.assertEquals("1", jsonObject.get("value"));
		Assert.assertEquals("545188693724480037", jsonObject.get("assetId"));
		Assert.assertEquals("ge", jsonObject.get("operatorName"));
		Assert.assertEquals("pageViewed", jsonObject.get("propertyName"));

		JSONObject dayJSONObject = jsonObject.getJSONObject("day");

		Assert.assertEquals("2023-02-07", dayJSONObject.get("value"));
		Assert.assertEquals("gt", dayJSONObject.get("operatorName"));
	}

}