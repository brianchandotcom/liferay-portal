/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.test.util.ExpandoTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.search.test.util.HitsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CPDefinitionIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_indexer = _indexerRegistry.getIndexer(CPDefinition.class);
	}

	@Test
	public void testGetCPDefinitionCPInstanceExpandoAttributes()
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				LocaleUtil.US.getDisplayLanguage(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			commerceCatalog.getGroupId());

		String expectedValue =
			RandomTestUtil.randomString() + cpInstance.getCPInstanceId();

		_setUp(expectedValue, cpInstance);

		Document document = _indexer.getDocument(cpInstance.getCPDefinition());

		Assert.assertEquals(
			expectedValue,
			document.get(
				"expando__keyword__custom_fields__" + _EXPANDO_COLUMN_NAME));
	}

	@Test
	public void testSearch() throws Exception {
		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				LocaleUtil.US.getDisplayLanguage(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		_cpDefinitionLocalService.updateCPDefinitionLocalization(
			cpDefinition, "en_US", null, null, "<p>test</p>", null, null, null);

		_indexer.reindex(cpDefinition);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setEntryClassNames(
			new String[] {CPDefinition.class.getName()});
		searchContext.setGroupIds(new long[] {catalog.getGroupId()});

		Hits hits = _indexer.search(searchContext);

		Document document = HitsAssert.assertOnlyOne(hits);

		Assert.assertEquals(
			String.valueOf(cpDefinition.getCPDefinitionId()),
			document.get(Field.ENTRY_CLASS_PK));

		Summary summary = _indexer.getSummary(document, LocaleUtil.US, null);

		Assert.assertEquals("test", summary.getContent());
	}

	@Test
	public void testSearchByGtin() throws Exception {
		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				LocaleUtil.US.getDisplayLanguage(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			commerceCatalog.getGroupId());

		cpInstance.setGtin(
			RandomTestUtil.randomString() + cpInstance.getCPInstanceId());

		cpInstance = _cpInstanceLocalService.updateCPInstance(cpInstance);

		_indexer.reindex(cpInstance.getCPDefinition());

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setEntryClassNames(
			new String[] {CPDefinition.class.getName()});
		searchContext.setGroupIds(new long[] {commerceCatalog.getGroupId()});
		searchContext.setKeywords(RandomTestUtil.randomString());

		Hits hits = _indexer.search(searchContext);

		HitsAssert.assertNoHits(hits);

		searchContext.setKeywords(cpInstance.getGtin());

		hits = _indexer.search(searchContext);

		Document actualDocument = HitsAssert.assertOnlyOne(hits);

		Document expectedDocument = _indexer.getDocument(
			cpInstance.getCPDefinition());

		Assert.assertEquals(
			expectedDocument.get("entryClassPK"),
			actualDocument.get("entryClassPK"));
		Assert.assertEquals(
			expectedDocument.get("gtins"), cpInstance.getGtin());
	}

	private void _setUp(String expandoValue, CPInstance cpInstance)
		throws Exception {

		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.fetchTable(
			_group.getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(CPInstance.class),
			"CUSTOM_FIELDS");

		if (expandoTable == null) {
			expandoTable = ExpandoTableLocalServiceUtil.addTable(
				_group.getCompanyId(),
				ClassNameLocalServiceUtil.getClassNameId(CPInstance.class),
				"CUSTOM_FIELDS");

			_expandoTables.add(expandoTable);
		}

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, _EXPANDO_COLUMN_NAME, ExpandoColumnConstants.STRING);

		_expandoColumns.add(expandoColumn);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE,
			String.valueOf(ExpandoColumnConstants.INDEX_TYPE_KEYWORD));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		ExpandoColumnLocalServiceUtil.updateExpandoColumn(expandoColumn);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setExpandoBridgeAttributes(
			Collections.singletonMap(_EXPANDO_COLUMN_NAME, expandoValue));

		cpInstance.setExpandoBridgeAttributes(serviceContext);

		cpInstance = _cpInstanceLocalService.updateCPInstance(cpInstance);

		_indexer.reindex(cpInstance.getCPDefinition());
	}

	private static final String _EXPANDO_COLUMN_NAME =
		RandomTestUtil.randomString();

	private static Indexer<CPDefinition> _indexer;

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@DeleteAfterTestRun
	private List<ExpandoColumn> _expandoColumns = new ArrayList<>();

	@DeleteAfterTestRun
	private List<ExpandoTable> _expandoTables = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

}