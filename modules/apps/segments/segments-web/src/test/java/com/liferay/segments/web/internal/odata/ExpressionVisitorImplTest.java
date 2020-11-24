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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.MethodExpression;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class ExpressionVisitorImplTest {

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
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

	private static final EntityModel _entityModel = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return Stream.of(
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
		new ExpressionVisitorImpl(_entityModel);

}