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

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.LambdaFunctionExpression;
import com.liferay.portal.odata.filter.expression.ListExpression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.internal.filter.expression.BinaryExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.CollectionPropertyExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LambdaFunctionExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LambdaVariableExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.ListExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LiteralExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.MemberExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.PrimitivePropertyExpressionImpl;
import com.liferay.portal.search.internal.query.NestedFieldQueryHelperImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * The type Expression convert impl test.
 *
 * @author Cristina González
 */
public class ExpressionConvertImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		PropsTestUtil.setProps(Collections.emptyMap());

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

		QueryFilter queryFilter = (QueryFilter)_expressionConvertImpl.convert(
			binaryExpression, LocaleUtil.getDefault(), _entityModel);

		TermQuery termQuery = (TermQuery)queryFilter.getQuery();

		QueryTerm queryTerm = termQuery.getQueryTerm();

		Assert.assertEquals("title", queryTerm.getField());
		Assert.assertEquals("test", queryTerm.getValue());
	}

	@Test
	public void testConvertListExpressionWithInOnPrimitiveField()
		throws ExpressionVisitException {

		ListExpression listExpression = new ListExpressionImpl(
			new MemberExpressionImpl(
				new PrimitivePropertyExpressionImpl("title")),
			ListExpression.Operation.IN,
			Collections.singletonList(
				new LiteralExpressionImpl(
					"test", LiteralExpression.Type.STRING)));

		QueryFilter queryFilter = (QueryFilter)_expressionConvertImpl.convert(
			listExpression, LocaleUtil.getDefault(), _entityModel);

		BooleanQuery booleanQuery = (BooleanQuery)queryFilter.getQuery();

		List<BooleanClause<Query>> booleanClauses = booleanQuery.clauses();

		Assert.assertEquals(
			booleanClauses.toString(), 1, booleanClauses.size());

		BooleanClause<Query> booleanClause = booleanClauses.get(0);

		TermQuery termQuery = (TermQuery)booleanClause.getClause();

		QueryTerm queryTerm = termQuery.getQueryTerm();

		Assert.assertEquals("title", queryTerm.getField());
		Assert.assertEquals("test", queryTerm.getValue());
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

		QueryFilter queryFilter = (QueryFilter)_expressionConvertImpl.convert(
			memberExpression, LocaleUtil.getDefault(), _entityModel);

		TermQuery termQuery = (TermQuery)queryFilter.getQuery();

		QueryTerm queryTerm = termQuery.getQueryTerm();

		Assert.assertNotNull(queryTerm);
		Assert.assertEquals("keywords.raw", queryTerm.getField());
		Assert.assertEquals("keyword1", queryTerm.getValue());
	}

	private static final EntityModel _entityModel = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return Stream.of(
				new CollectionEntityField(
					new StringEntityField(
						"keywords", locale -> "keywords.raw")),
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

	private final ExpressionConvertImpl _expressionConvertImpl =
		new ExpressionConvertImpl() {
			{
				nestedFieldQueryHelper = new NestedFieldQueryHelperImpl();
			}
		};

}