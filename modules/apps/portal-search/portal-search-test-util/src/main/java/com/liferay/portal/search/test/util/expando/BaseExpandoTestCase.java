/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.expando;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.query.FieldQueryFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.search.analysis.FieldQueryBuilderFactory;
import com.liferay.portal.search.expando.ExpandoBridgeIndexer;
import com.liferay.portal.search.internal.analysis.DescriptionFieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.SubstringFieldQueryBuilder;
import com.liferay.portal.search.internal.expando.ExpandoFieldQueryBuilderFactory;
import com.liferay.portal.search.internal.expando.helper.ExpandoQueryContributorHelper;
import com.liferay.portal.search.internal.expando.helper.ExpandoQueryContributorHelperImpl;
import com.liferay.portal.search.internal.query.FieldQueryFactoryImpl;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Bryan Engler
 */
public abstract class BaseExpandoTestCase extends BaseIndexingTestCase {

	@BeforeClass
	public static void setUpClassBaseExpandoTestCase() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_fieldQueryBuilderFactoryServiceRegistration =
			bundleContext.registerService(
				FieldQueryBuilderFactory.class,
				createExpandoFieldQueryBuilderFactory(), null);

		_fieldQueryFactoryServiceRegistration = bundleContext.registerService(
			FieldQueryFactory.class, createFieldQueryFactory(bundleContext),
			null);
	}

	@AfterClass
	public static void tearDownClassBaseExpandoTestCase() {
		ReflectionTestUtil.invoke(
			_fieldQueryFactoryImpl, "deactivate", new Class<?>[0], null);

		_fieldQueryBuilderFactoryServiceRegistration.unregister();

		_fieldQueryFactoryServiceRegistration.unregister();
	}

	@Test
	public void testMultipleClassNames() throws Exception {
		String[] duplicates = {
			"alpha", "alpha", "alpha bravo", "charlie", "delta"
		};

		addDocuments(this::addKeyword, duplicates);

		addDocuments(this::addKeyword, "keyword");
		addDocuments(this::addText, duplicates);
		addDocuments(this::addText, "text");

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
		BundleContext bundleContext) {

		_fieldQueryFactoryImpl = new FieldQueryFactoryImpl();

		ReflectionTestUtil.setFieldValue(
			_fieldQueryFactoryImpl, "_descriptionFieldQueryBuilder",
			createDescriptionFieldQueryBuilder());

		ReflectionTestUtil.invoke(
			_fieldQueryFactoryImpl, "activate",
			new Class<?>[] {BundleContext.class}, bundleContext);

		return _fieldQueryFactoryImpl;
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
			Mockito.nullable(String.class)
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
			Mockito.anyLong(), Mockito.eq(_CLASS_NAME_KEYWORD)
		);

		Mockito.doReturn(
			createExpandoBridge(
				_ATTRIBUTE_TEXT, ExpandoColumnConstants.INDEX_TYPE_TEXT)
		).when(
			expandoBridgeFactory
		).getExpandoBridge(
			Mockito.anyLong(), Mockito.eq(_CLASS_NAME_TEXT)
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
			Mockito.eq(_indexTypeKeywordExpandoColumn)
		);

		Mockito.doReturn(
			_FIELD_TEXT
		).when(
			expandoBridgeIndexer
		).encodeFieldName(
			Mockito.eq(_indexTypeTextExpandoColumn)
		);

		Mockito.doReturn(
			StringPool.BLANK
		).when(
			expandoBridgeIndexer
		).getNumericSuffix(
			Mockito.anyInt()
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
			_indexTypeKeywordExpandoColumn
		).when(
			expandoColumnLocalService
		).getDefaultTableColumn(
			Mockito.anyLong(), Mockito.nullable(String.class),
			Mockito.eq(_ATTRIBUTE_KEYWORD)
		);

		Mockito.doReturn(
			_indexTypeTextExpandoColumn
		).when(
			expandoColumnLocalService
		).getDefaultTableColumn(
			Mockito.anyLong(), Mockito.nullable(String.class),
			Mockito.eq(_ATTRIBUTE_TEXT)
		);

		return expandoColumnLocalService;
	}

	protected ExpandoQueryContributorHelper
		createExpandoQueryContributorHelper() {

		ExpandoQueryContributorHelperImpl expandoQueryContributorHelperImpl =
			new ExpandoQueryContributorHelperImpl();

		ReflectionTestUtil.setFieldValue(
			expandoQueryContributorHelperImpl, "_expandoBridgeFactory",
			createExpandoBridgeFactory());
		ReflectionTestUtil.setFieldValue(
			expandoQueryContributorHelperImpl, "_expandoBridgeIndexer",
			createExpandoBridgeIndexer());
		ReflectionTestUtil.setFieldValue(
			expandoQueryContributorHelperImpl, "_expandoColumnLocalService",
			createExpandoColumnLocalService());

		return expandoQueryContributorHelperImpl;
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

	private static ServiceRegistration<FieldQueryBuilderFactory>
		_fieldQueryBuilderFactoryServiceRegistration;
	private static FieldQueryFactoryImpl _fieldQueryFactoryImpl;
	private static ServiceRegistration<?> _fieldQueryFactoryServiceRegistration;

	@Mock
	private ExpandoColumn _indexTypeKeywordExpandoColumn = createExpandoColumn(
		ExpandoColumnConstants.INDEX_TYPE_KEYWORD);

	@Mock
	private ExpandoColumn _indexTypeTextExpandoColumn = createExpandoColumn(
		ExpandoColumnConstants.INDEX_TYPE_TEXT);

}