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

package com.liferay.segments.web.internal.odata;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.ListExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class ExpressionVisitorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testVisitBinaryExpressionOperationWithAndOperation()
		throws ExpressionVisitException {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			(JSONObject)_expressionVisitorImpl.visitBinaryExpressionOperation(
				BinaryExpression.Operation.AND,
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.EQ, entityFieldsMap.get("title"),
					"title1"),
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.LT, entityFieldsMap.get("id"),
					"2"));

		Assert.assertEquals("and", jsonObject.getString("conjunctionName"));
		Assert.assertEquals("group_1", jsonObject.getString("groupId"));

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"operatorName", "eq"
				).put(
					"propertyName", "title"
				).put(
					"value", "title1"
				),
				JSONUtil.put(
					"operatorName", "lt"
				).put(
					"propertyName", "id"
				).put(
					"value", "2"
				)
			).toString(),
			itemsJSONArray.toString());
	}

	@Test
	public void testVisitBinaryExpressionOperationWithEqualOperation()
		throws ExpressionVisitException {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			(JSONObject)_expressionVisitorImpl.visitBinaryExpressionOperation(
				BinaryExpression.Operation.EQ, entityFieldsMap.get("title"),
				"title1");

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName",
				StringUtil.toLowerCase(BinaryExpression.Operation.EQ.toString())
			).put(
				"propertyName", "title"
			).put(
				"value", "title1"
			).toJSONString(),
			jsonObject.toJSONString());
	}

	@Test
	public void testVisitListExpressionOperation()
		throws ExpressionVisitException {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		ListExpression listExpression = new ListExpression() {

			public <T> T accept(ExpressionVisitor<T> expressionVisitor)
				throws ExpressionVisitException {

				List<Object> objects = Arrays.asList("title1", "title2");

				return expressionVisitor.visitListExpressionOperation(
					Operation.IN, (T)entityFieldsMap.get("title"),
					(List<T>)objects);
			}

			@Override
			public Expression getLeftOperationExpression() {
				return null;
			}

			@Override
			public Operation getOperation() {
				return null;
			}

			@Override
			public List<Expression> getRightOperationExpressions() {
				return null;
			}

		};

		JSONObject jsonObject = (JSONObject)listExpression.accept(
			_expressionVisitorImpl);

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName",
				StringUtil.toLowerCase(ListExpression.Operation.IN.toString())
			).put(
				"propertyName", "title"
			).put(
				"value", JSONUtil.putAll("title1", "title2")
			).toJSONString(),
			jsonObject.toJSONString());
	}

	@Test
	public void testVisitMethodExpressionWithContains()
		throws ExpressionVisitException {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			(JSONObject)_expressionVisitorImpl.visitMethodExpression(
				Arrays.asList(entityFieldsMap.get("title"), "title1"),
				MethodExpression.Type.CONTAINS);

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName",
				StringUtil.toLowerCase(
					MethodExpression.Type.CONTAINS.toString())
			).put(
				"propertyName", "title"
			).put(
				"value", "title1"
			).toJSONString(),
			jsonObject.toJSONString());
	}

	@Test
	public void testVisitUnaryExpressionOperation()
		throws ExpressionVisitException {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		JSONObject jsonObject =
			_expressionVisitorImpl.visitUnaryExpressionOperation(
				UnaryExpression.Operation.NOT,
				_expressionVisitorImpl.visitBinaryExpressionOperation(
					BinaryExpression.Operation.GE, entityFieldsMap.get("id"),
					"4"));

		Assert.assertEquals(
			JSONUtil.put(
				"operatorName",
				StringUtil.toLowerCase(
					UnaryExpression.Operation.NOT + "-" +
						BinaryExpression.Operation.GE.toString())
			).put(
				"propertyName", "id"
			).put(
				"value", "4"
			).toJSONString(),
			jsonObject.toJSONString());
	}

	private static final EntityModel _entityModel = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return Stream.of(
				new IntegerEntityField("id", locale -> "id"),
				new StringEntityField("title", locale -> "title")
			).collect(
				Collectors.toMap(EntityField::getName, Function.identity())
			);
		}

		@Override
		public String getName() {
			return "SomeEntityName";
		}

	};

	private static final ExpressionVisitorImpl _expressionVisitorImpl =
		new ExpressionVisitorImpl(0, _entityModel);

}