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

package com.liferay.wiki.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;
import com.liferay.wiki.test.util.WikiTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class WikiPageSearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Override
	@Test
	public void testLocalizedSearch() throws Exception {
	}

	@Override
	@Test
	public void testSearchByDDMStructureField() throws Exception {
	}

	@Override
	@Test
	public void testSearchByKeywordsInsideParentBaseModel() throws Exception {
	}

	@Override
	@Test
	public void testSearchMyEntries() throws Exception {
	}

	@Override
	@Test
	public void testSearchRecentEntries() throws Exception {
	}

	@Override
	@Test
	public void testSearchStatus() throws Exception {
	}

	@Override
	@Test
	public void testSearchWithinDDMStructure() throws Exception {
	}

	@Test
	public void testSpecificFields() throws Exception {
		TestSpecificFieldsHelper testSpecificFieldsHelper =
			new TestSpecificFieldsHelper();

		testSpecificFieldsHelper.testSearchSpecificFields();
	}

	@Override
	protected void addAttachment(ClassedModel classedModel) throws Exception {
		WikiPage page = (WikiPage)classedModel;

		WikiTestUtil.addWikiAttachment(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle(),
			WikiPageSearchTest.class);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return WikiTestUtil.addPage(
			TestPropsValues.getUserId(),
			(Long)parentBaseModel.getPrimaryKeyObj(),
			RandomTestUtil.randomString(), keywords, approved, serviceContext);
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPageByPageId(primaryKey);

		WikiPageLocalServiceUtil.deletePage(page);
	}

	@Override
	protected void expireBaseModelVersions(
			BaseModel<?> baseModel, boolean expireAllVersions,
			ServiceContext serviceContext)
		throws Exception {

		WikiPage page = (WikiPage)baseModel;

		if (expireAllVersions) {
			WikiPageServiceUtil.deletePage(page.getNodeId(), page.getTitle());
		}
		else {
			List<WikiPage> pages = WikiPageServiceUtil.getPages(
				page.getGroupId(), page.getNodeId(), false,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			WikiPage previousPage = pages.get(0);

			WikiPageServiceUtil.revertPage(
				page.getNodeId(), page.getTitle(), previousPage.getVersion(),
				serviceContext);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return WikiPage.class;
	}

	@Override
	protected Long getBaseModelClassPK(ClassedModel classedModel) {
		WikiPage page = (WikiPage)classedModel;

		return page.getResourcePrimKey();
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_APPROVED);

		return WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), getSearchKeywords(),
			getSearchKeywords(), serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected boolean isExpirableAllVersions() {
		return true;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPageByPageId(primaryKey);

		WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle());
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		WikiPage page = (WikiPage)baseModel;

		return WikiTestUtil.updatePage(
			page, TestPropsValues.getUserId(), keywords, serviceContext);
	}

	protected class TestSpecificFieldsHelper {

		/**
		 * See
		 * https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/searching-for-content-in-liferay
		 */
		public void testSearchSpecificFields() throws Exception {
			Assume.assumeTrue(
				isSearchSpecificFieldsImplementedForSearchEngine());

			addPageWithTitle(RandomTestUtil.randomString());
			addPageWithTitle("foo");
			addPageWithTitle("bar");
			addPageWithTitle("foo bar");
			addPageWithTitle("fooxyz");

			assertSearch("foo", 2);
			assertSearch("title:foo", 2);
			assertSearch("title:foo -title:bar", 1);
		}

		protected TestSpecificFieldsHelper() throws Exception {
			_serviceContext = ServiceContextTestUtil.getServiceContext(
				group.getGroupId());

			_parentBaseModel = getParentBaseModel(group, _serviceContext);
		}

		protected void addPageWithTitle(String title) throws Exception {
			WikiTestUtil.addPage(
				TestPropsValues.getUserId(),
				(Long)_parentBaseModel.getPrimaryKeyObj(), title,
				RandomTestUtil.randomString(), true, _serviceContext);
		}

		protected void assertSearch(String keywords, int count)
			throws Exception {

			SearchContext searchContext =
				SearchContextTestUtil.getSearchContext(group.getGroupId());

			searchContext.setKeywords(keywords);

			Assert.assertEquals(
				count,
				searchBaseModelsCount(
					getBaseModelClass(), group.getGroupId(), searchContext));
		}

		protected boolean isSearchSpecificFieldsImplementedForSearchEngine() {
			SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine(
				SearchEngineHelperUtil.getDefaultSearchEngineId());

			String vendor = searchEngine.getVendor();

			if (vendor.equals("Lucene")) {
				return true;
			}

			return false;
		}

		private final BaseModel<?> _parentBaseModel;
		private final ServiceContext _serviceContext;

	}

}