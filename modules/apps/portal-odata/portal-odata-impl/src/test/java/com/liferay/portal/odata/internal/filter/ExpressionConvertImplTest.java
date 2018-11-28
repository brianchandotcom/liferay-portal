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

package com.liferay.portal.odata.internal.filter;

import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.LambdaFunctionExpression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.internal.filter.expression.BinaryExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.CollectionPropertyExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LambdaFunctionExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LambdaVariableExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LiteralExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.MemberExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.PrimitivePropertyExpressionImpl;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * The type Expression convert impl test.
 *
 * @author Cristina González
 */
public class ExpressionConvertImplTest {

	@Before
	public void setUp() {
		PropsUtil.setProps(Mockito.mock(Props.class));

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			Mockito.mock(FastDateFormatFactory.class));
	}

	@Test
	public void testConvertBinaryExpressionWithEqOnPrimitiveField()
		throws ExpressionVisitException {

		BinaryExpression binaryExpression = new BinaryExpressionImpl(
			new MemberExpressionImpl(
				new PrimitivePropertyExpressionImpl("title")),
			BinaryExpression.Operation.EQ,
			new LiteralExpressionImpl("test", LiteralExpression.Type.STRING));

		TermFilter termFilter = (TermFilter)_expressionConvert.convert(
			binaryExpression, Locale.getDefault(), _entityModel);

		Assert.assertEquals("title", termFilter.getField());
		Assert.assertEquals("test", termFilter.getValue());
	}

	@Test
	public void testConvertMemberExpressionWithLambdaAnyEqOnCollectionField()
		throws ExpressionVisitException {

		MemberExpression memberExpression = new MemberExpressionImpl(
			new CollectionPropertyExpressionImpl(
				new PrimitivePropertyExpressionImpl("keywords"),
				new LambdaFunctionExpressionImpl(
					LambdaFunctionExpression.Type.ANY, "k",
					new BinaryExpressionImpl(
						new MemberExpressionImpl(
							new LambdaVariableExpressionImpl("k")),
						BinaryExpression.Operation.EQ,
						new LiteralExpressionImpl(
							"'keyword1'", LiteralExpression.Type.STRING)))));

		TermFilter termFilter = (TermFilter)_expressionConvert.convert(
			memberExpression, Locale.getDefault(), _entityModel);

		Assert.assertNotNull(termFilter);
		Assert.assertEquals("keywords.raw", termFilter.getField());
		Assert.assertEquals("keyword1", termFilter.getValue());
	}

	private static final EntityModel _entityModel = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return Stream.of(
				new StringEntityField("title", locale -> "title"),
				new CollectionEntityField(
					new StringEntityField("keywords", locale -> "keywords.raw"))
			).collect(
				Collectors.toMap(EntityField::getName, Function.identity())
			);
		}

		@Override
		public String getName() {
			return "SomeEntityName";
		}

	};

	private final ExpressionConvertImpl _expressionConvert =
		new ExpressionConvertImpl();

}