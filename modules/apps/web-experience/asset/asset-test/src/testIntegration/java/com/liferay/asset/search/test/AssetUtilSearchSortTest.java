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

package com.liferay.asset.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class AssetUtilSearchSortTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_journalArticleFixture.setGroup(_group);

		_journalArticleFixture.setJournalArticleLocalService(
			_journalArticleLocalService);

		_journalArticles = _journalArticleFixture.getJournalArticles();
	}

	@Test
	public void testPriority() throws Exception {
		double[] priorities = {10, 1, 40, 5.3};

		for (double priority : priorities) {
			addJournalArticle(
				serviceContext -> serviceContext.setAssetPriority(priority));
		}

		SearchContext searchContext = createSearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(Field.PRIORITY);

		AssetEntryQuery assetEntryQuery = createAssetEntryQueryOrderBy(
			Field.PRIORITY);

		Hits hits = _assetHelper.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		DocumentsAssert.assertValues(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			Field.PRIORITY, Arrays.asList("1.0", "5.3", "10.0", "40.0"));
	}

	protected void addJournalArticle(Consumer<ServiceContext> consumer)
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		consumer.accept(serviceContext);

		addJournalArticle(serviceContext);
	}

	protected JournalArticle addJournalArticle(ServiceContext serviceContext)
		throws Exception {

		return _journalArticleFixture.addJournalArticle(serviceContext);
	}

	protected AssetEntryQuery createAssetEntryQueryOrderBy(String orderByCol1) {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});
		assetEntryQuery.setOrderByCol1(orderByCol1);
		assetEntryQuery.setOrderByType1("ASC");

		return assetEntryQuery;
	}

	protected SearchContext createSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(Field.GROUP_ID, _group.getGroupId());
		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setGroupIds(new long[] {_group.getGroupId()});
		searchContext.setKeywords(StringPool.BLANK);
		searchContext.setUserId(_group.getCreatorUserId());

		return searchContext;
	}

	protected ServiceContext createServiceContext() throws PortalException {
		return _journalArticleFixture.createServiceContext();
	}

	@Inject
	private static AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private AssetHelper _assetHelper;

	@DeleteAfterTestRun
	private Group _group;

	private final JournalArticleFixture _journalArticleFixture =
		new JournalArticleFixture();

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles;

}