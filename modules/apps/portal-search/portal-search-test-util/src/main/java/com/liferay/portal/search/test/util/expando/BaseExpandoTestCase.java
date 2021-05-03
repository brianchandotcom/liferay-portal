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

package com.liferay.portal.search.test.util.expando;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.query.FieldQueryFactory;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.search.analysis.FieldQueryBuilderFactory;
import com.liferay.portal.search.internal.analysis.DescriptionFieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.SubstringFieldQueryBuilder;
import com.liferay.portal.search.internal.expando.ExpandoFieldQueryBuilderFactory;
import com.liferay.portal.search.internal.expando.ExpandoQueryContributorHelper;
import com.liferay.portal.search.internal.expando.ExpandoQueryContributorHelperImpl;
import com.liferay.portal.search.internal.query.FieldQueryFactoryImpl;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Bryan Engler
 */
public abstract class BaseExpandoTestCase extends BaseIndexingTestCase {

	@BeforeClass
	public static void setUpClassBaseExpandoTestCase() {
		Registry registry = new BasicRegistryImpl();

		registry.registerService(
			FieldQueryFactory.class,
			createFieldQueryFactory(createExpandoFieldQueryBuilderFactory()));

		RegistryUtil.setRegistry(registry);
	}

	@AfterClass
	public static void tearDownClassBaseExpandoTestCase() {
		RegistryUtil.setRegistry(null);
	}

	@Test
	public void testMultipleClassNames() throws Exception {
		List<String> duplicates = Arrays.asList(
			"alpha", "alpha", "alpha bravo", "charlie", "delta");

		addDocuments(this::addKeyword, duplicates);

		addDocuments(this::addKeyword, Arrays.asList("keyword"));

		addDocuments(this::addText, duplicates);

		addDocuments(this::addText, Arrays.asList("text"));

		assertSearch("alpha", 6);
		assertSearch("bravo", 2);
		assertSearch("alpha bravo", 6);
		assertSearch("rlie", 1);
		assertSearch("echo", 0);
		assertSearch("keyword", 1);
		assertSearch("text", 1);
	}

	protected static DescriptionFieldQueryBuilder
		createDescriptionFieldQueryBuilder() {

		return new DescriptionFieldQueryBuilder() {
			{
				keywordTokenizer = new SimpleKeywordTokenizer();
			}
		};
	}

	protected static ExpandoFieldQueryBuilderFactory
		createExpandoFieldQueryBuilderFactory() {

		return new ExpandoFieldQueryBuilderFactory() {
			{
				substringQueryBuilder = new SubstringFieldQueryBuilder() {
					{
						keywordTokenizer = new SimpleKeywordTokenizer();
					}
				};
			}
		};
	}

	protected static FieldQueryFactoryImpl createFieldQueryFactory(
		FieldQueryBuilderFactory fieldQueryBuilderFactory) {

		return new FieldQueryFactoryImpl() {
			{
				descriptionFieldQueryBuilder =
					createDescriptionFieldQueryBuilder();

				addFieldQueryBuilderFactory(fieldQueryBuilderFactory);
			}
		};
	}

	protected DocumentCreationHelper addKeyword(String value) {
		return document -> {
			document.addKeyword(Field.STATUS, _FIELD_KEYWORD + value);

			document.addKeyword(_FIELD_KEYWORD, value);
		};
	}

	protected DocumentCreationHelper addText(String value) {
		return document -> {
			document.addKeyword(Field.STATUS, _FIELD_TEXT + value);

			document.addText(_FIELD_TEXT, value);
		};
	}

	protected void assertSearch(String keywords, int expectedCount)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS, () -> doAssertSearch(keywords, expectedCount));
	}

	protected ExpandoBridge createExpandoBridge(
		String attributeName, int indexType) {

		ExpandoBridge expandoBridge = Mockito.mock(ExpandoBridge.class);

		Mockito.doReturn(
			Collections.enumeration(Collections.singletonList(attributeName))
		).when(
			expandoBridge
		).getAttributeNames();

		Mockito.doReturn(
			createUnicodeProperties(indexType)
		).when(
			expandoBridge
		).getAttributeProperties(
			Mockito.anyString()
		);

		return expandoBridge;
	}

	protected ExpandoBridgeFactory createExpandoBridgeFactory() {
		ExpandoBridgeFactory expandoBridgeFactory = Mockito.mock(
			ExpandoBridgeFactory.class);

		Mockito.doReturn(
			createExpandoBridge(
				_ATTRIBUTE_KEYWORD, ExpandoColumnConstants.INDEX_TYPE_KEYWORD)
		).when(
			expandoBridgeFactory
		).getExpandoBridge(
			Mockito.anyLong(), Matchers.eq(_CLASS_NAME_KEYWORD)
		);

		Mockito.doReturn(
			createExpandoBridge(
				_ATTRIBUTE_TEXT, ExpandoColumnConstants.INDEX_TYPE_TEXT)
		).when(
			expandoBridgeFactory
		).getExpandoBridge(
			Mockito.anyLong(), Matchers.eq(_CLASS_NAME_TEXT)
		);

		return expandoBridgeFactory;
	}

	protected ExpandoBridgeIndexer createExpandoBridgeIndexer() {
		ExpandoBridgeIndexer expandoBridgeIndexer = Mockito.mock(
			ExpandoBridgeIndexer.class);

		Mockito.doReturn(
			_FIELD_KEYWORD
		).when(
			expandoBridgeIndexer
		).encodeFieldName(
			Mockito.anyString(),
			Matchers.eq(ExpandoColumnConstants.INDEX_TYPE_KEYWORD)
		);

		Mockito.doReturn(
			_FIELD_TEXT
		).when(
			expandoBridgeIndexer
		).encodeFieldName(
			Mockito.anyString(),
			Matchers.eq(ExpandoColumnConstants.INDEX_TYPE_TEXT)
		);

		return expandoBridgeIndexer;
	}

	protected ExpandoColumn createExpandoColumn(int indexType) {
		ExpandoColumn expandoColumn = Mockito.mock(ExpandoColumn.class);

		Mockito.doReturn(
			createUnicodeProperties(indexType)
		).when(
			expandoColumn
		).getTypeSettingsProperties();

		Mockito.doReturn(
			ExpandoColumnConstants.STRING
		).when(
			expandoColumn
		).getType();

		return expandoColumn;
	}

	protected ExpandoColumnLocalService createExpandoColumnLocalService() {
		ExpandoColumnLocalService expandoColumnLocalService = Mockito.mock(
			ExpandoColumnLocalService.class);

		Mockito.doReturn(
			createExpandoColumn(ExpandoColumnConstants.INDEX_TYPE_KEYWORD)
		).when(
			expandoColumnLocalService
		).getDefaultTableColumn(
			Mockito.anyLong(), Mockito.anyString(),
			Mockito.eq(_ATTRIBUTE_KEYWORD)
		);

		Mockito.doReturn(
			createExpandoColumn(ExpandoColumnConstants.INDEX_TYPE_TEXT)
		).when(
			expandoColumnLocalService
		).getDefaultTableColumn(
			Mockito.anyLong(), Mockito.anyString(), Mockito.eq(_ATTRIBUTE_TEXT)
		);

		return expandoColumnLocalService;
	}

	protected ExpandoQueryContributorHelper
		createExpandoQueryContributorHelper() {

		return new ExpandoQueryContributorHelperImpl() {
			{
				setExpandoBridgeFactory(createExpandoBridgeFactory());
				setExpandoBridgeIndexer(createExpandoBridgeIndexer());
				setExpandoColumnLocalService(createExpandoColumnLocalService());
			}
		};
	}

	protected UnicodeProperties createUnicodeProperties(int indexType) {
		UnicodeProperties unicodeProperties = Mockito.mock(
			UnicodeProperties.class);

		Mockito.doReturn(
			String.valueOf(indexType)
		).when(
			unicodeProperties
		).getProperty(
			ExpandoColumnConstants.INDEX_TYPE
		);

		return unicodeProperties;
	}

	protected Void doAssertSearch(String keywords, int expectedCount)
		throws Exception {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		SearchContext searchContext = createSearchContext();

		ExpandoQueryContributorHelper expandoQueryContributorHelper =
			createExpandoQueryContributorHelper();

		expandoQueryContributorHelper.contribute(
			keywords, booleanQuery,
			Arrays.asList(_CLASS_NAME_KEYWORD, _CLASS_NAME_TEXT),
			searchContext);

		Hits hits = search(searchContext, booleanQuery);

		DocumentsAssert.assertCount(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			Field.STATUS, expectedCount);

		return null;
	}

	private static final String _ATTRIBUTE_KEYWORD =
		RandomTestUtil.randomString();

	private static final String _ATTRIBUTE_TEXT = RandomTestUtil.randomString();

	private static final String _CLASS_NAME_KEYWORD =
		RandomTestUtil.randomString();

	private static final String _CLASS_NAME_TEXT =
		RandomTestUtil.randomString();

	private static final String _FIELD_KEYWORD =
		"expando__keyword__custom_fields__testColumnName";

	private static final String _FIELD_TEXT =
		"expando__custom_fields__testColumnName";

}