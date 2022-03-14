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

package com.liferay.commerce.product.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class CPInstanceIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_indexer = _indexerRegistry.getIndexer(CPInstance.class);
	}

	@Test
	public void testSkuPrefix() throws Exception {
		CommerceCatalog catalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				LocaleUtil.US.getDisplayLanguage(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		CPInstance cpInstance = CPTestUtil.addCPInstanceFromCatalog(
			catalog.getGroupId());

		cpInstance.setPurchasable(true);

		String sku = "Open4Life" + RandomTestUtil.randomString();

		cpInstance.setSku(sku);

		_cpInstanceLocalService.updateCPInstance(cpInstance);

		_assertSearch("open", cpInstance.getCPDefinitionId(), cpInstance);
		_assertSearch("open4life", cpInstance.getCPDefinitionId(), cpInstance);
		_assertSearch("OPE", cpInstance.getCPDefinitionId(), cpInstance);
		_assertSearch("4lif", cpInstance.getCPDefinitionId(), cpInstance);
	}

	protected Hits search(String keywords, long commerceOrderId)
		throws SearchException {

		SearchContext searchContext = _getSearchContext(commerceOrderId);

		searchContext.setKeywords(keywords);

		return _indexer.search(searchContext);
	}

	private void _assertSearch(Hits hits, CPInstance... expectedCPInstances)
		throws Exception {

		List<CPInstance> actualCPInstances = _getCPInstances(hits);

		long[] actualCPInstancesIds = _getCPInstanceIds(actualCPInstances);

		long[] expectedCPInstancesIds = _getCPInstanceIds(
			Arrays.asList(expectedCPInstances));

		Assert.assertArrayEquals(expectedCPInstancesIds, actualCPInstancesIds);
	}

	private void _assertSearch(
			String keywords, long cpDefinitionId,
			CPInstance... expectedCPInstances)
		throws Exception {

		Hits hits = search(keywords, cpDefinitionId);

		_assertSearch(hits, expectedCPInstances);
	}

	private CPInstance _getCPInstance(Document document) throws Exception {
		long cpInstanceId = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return _cpInstanceLocalService.getCPInstance(cpInstanceId);
	}

	private long[] _getCPInstanceIds(List<CPInstance> cpInstances) {
		long[] cpInstanceIds = new long[cpInstances.size()];

		for (int i = 0; i < cpInstances.size(); i++) {
			CPInstance cpInstance = cpInstances.get(i);

			cpInstanceIds[i] = cpInstance.getCPInstanceId();
		}

		Arrays.sort(cpInstanceIds);

		return cpInstanceIds;
	}

	private List<CPInstance> _getCPInstances(Hits hits) throws Exception {
		Document[] documents = hits.getDocs();

		List<CPInstance> cpInstances = new ArrayList<>(documents.length);

		for (Document document : documents) {
			cpInstances.add(_getCPInstance(document));
		}

		return cpInstances;
	}

	private SearchContext _getSearchContext(long cpDefinitionId) {
		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(CPField.CP_DEFINITION_ID, cpDefinitionId);
		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setSorts(SortFactoryUtil.getDefaultSorts());

		return searchContext;
	}

	private static Indexer<CPInstance> _indexer;

	@Inject
	private static IndexerRegistry _indexerRegistry;

	private static User _user;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CPInstanceLocalService _cpInstanceLocalService;

	@DeleteAfterTestRun
	private Group _group;

}