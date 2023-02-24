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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionLexer;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionParser;
import com.liferay.segments.asah.connector.internal.expression.parser.test.util.IndividualSegmentsExpressionUtil;

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

		JSONObject jsonObject = (JSONObject)expressionContext.accept(
			new JSONObjectIndividualSegmentsExpressionVisitorImpl());

		Assert.assertEquals(
			JSONUtil.put(
				"conjunctionName", "and"
			).put(
				"groupId", "group_2"
			).put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"assetId", "545188693724480043"
					).put(
						"day",
						JSONUtil.put(
							"operatorName", "gt"
						).put(
							"value", "last24Hours"
						)
					).put(
						"operatorName", "ge"
					).put(
						"propertyName", "pageViewed"
					).put(
						"value", "1"
					),
					JSONUtil.put(
						"conjunctionName", "or"
					).put(
						"groupId", "group_1"
					).put(
						"items",
						JSONUtil.putAll(
							JSONUtil.put(
								"assetId", "545188693724480041"
							).put(
								"day",
								JSONUtil.put(
									"operatorName", "lt"
								).put(
									"value", "2023-02-09"
								)
							).put(
								"operatorName", "ge"
							).put(
								"propertyName", "pageViewed"
							).put(
								"value", "1"
							),
							JSONUtil.put(
								"assetId", "545188693724480037"
							).put(
								"day",
								JSONUtil.put(
									"operatorName", "gt"
								).put(
									"value", "2023-02-08"
								)
							).put(
								"operatorName", "ge"
							).put(
								"propertyName", "pageViewed"
							).put(
								"value", "1"
							))
					))
			).toString(),
			jsonObject.toString());
	}

	@Test
	public void testAcceptSimpleFilterByCount() {
		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(
							IndividualSegmentsExpressionUtil.getFilterByCount(
								IndividualSegmentsExpressionUtil.getFilter(
									"Page#pageViewed#545188693724480037", "gt",
									"2023-02-07"),
								"ge", 1)))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		JSONObject jsonObject = (JSONObject)expressionContext.accept(
			new JSONObjectIndividualSegmentsExpressionVisitorImpl());

		Assert.assertEquals(1, jsonObject.getInt("value"));
		Assert.assertEquals(545188693724480037L, jsonObject.getLong("assetId"));
		Assert.assertEquals("ge", jsonObject.get("operatorName"));
		Assert.assertEquals("pageViewed", jsonObject.get("propertyName"));

		JSONObject dayJSONObject = jsonObject.getJSONObject("day");

		Assert.assertEquals("2023-02-07", dayJSONObject.get("value"));
		Assert.assertEquals("gt", dayJSONObject.get("operatorName"));
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

		JSONObject jsonObject = (JSONObject)expressionContext.accept(
			new JSONObjectIndividualSegmentsExpressionVisitorImpl());

		Assert.assertEquals(1, jsonObject.getInt("value"));
		Assert.assertEquals(545188693724480037L, jsonObject.getLong("assetId"));
		Assert.assertEquals("ge", jsonObject.getString("operatorName"));
		Assert.assertEquals("true", jsonObject.getString("operatorNot"));
		Assert.assertEquals("pageViewed", jsonObject.get("propertyName"));

		JSONObject dayJSONObject = jsonObject.getJSONObject("day");

		Assert.assertEquals("2023-02-07", dayJSONObject.get("value"));
		Assert.assertEquals("gt", dayJSONObject.get("operatorName"));
	}

}