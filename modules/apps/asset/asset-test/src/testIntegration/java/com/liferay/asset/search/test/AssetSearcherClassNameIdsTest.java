/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.search.AssetSearcherFactory;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.bookmarks.constants.BookmarksFolderConstants;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Eric Yan
 */
@RunWith(Arquillian.class)
public class AssetSearcherClassNameIdsTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_journalArticleFixture.setDDMStructureLocalService(
			_ddmStructureLocalService);
		_group = GroupTestUtil.addGroup();

		_journalArticleFixture.setGroup(_group);

		_journalArticleFixture.setJournalArticleLocalService(
			_journalArticleLocalService);
	}

	@Test
	public void testAll() throws Exception {
		UserTestUtil.setUser(addUser());

		addBlogsEntry();
		addBookmarksEntry();
		addJournalArticle();

		Hits hits = search(getAssetEntryQuery(), getSearchContext());

		Assert.assertEquals(hits.toString(), 3, hits.getLength());
	}

	@Test
	public void testMultiple() throws Exception {
		UserTestUtil.setUser(addUser());

		addBlogsEntry();
		addBookmarksEntry();
		addJournalArticle();

		Hits hits = search(
			getAssetEntryQuery(
				"com.liferay.bookmarks.model.BookmarksEntry",
				"com.liferay.journal.model.JournalArticle"),
			getSearchContext());

		Assert.assertEquals(hits.toString(), 2, hits.getLength());
	}

	@Test
	public void testSingle() throws Exception {
		UserTestUtil.setUser(addUser());

		addBlogsEntry();
		addBookmarksEntry();
		addJournalArticle();

		Hits hits = search(
			getAssetEntryQuery("com.liferay.journal.model.JournalArticle"),
			getSearchContext());

		Assert.assertEquals(hits.toString(), 1, hits.getLength());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected BlogsEntry addBlogsEntry() throws Exception {
		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), getServiceContext());
	}

	protected BookmarksEntry addBookmarksEntry() throws Exception {
		return _bookmarksEntryLocalService.addEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), "http://www.liferay.com",
			RandomTestUtil.randomString(), getServiceContext());
	}

	protected JournalArticle addJournalArticle() throws Exception {
		return _journalArticleFixture.addJournalArticle(getServiceContext());
	}

	protected User addUser() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		_users.add(user);

		return user;
	}

	protected AssetEntryQuery getAssetEntryQuery(String... classNames) {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setClassNameIds(getClassNameIds(classNames));
		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		return assetEntryQuery;
	}

	protected long[] getClassNameIds(String... classNames) {
		return TransformUtil.transformToLongArray(
			Arrays.asList(classNames), PortalUtil::getClassNameId);
	}

	protected SearchContext getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_group.getCompanyId());
		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		return searchContext;
	}

	protected ServiceContext getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	protected Hits search(
			AssetEntryQuery assetEntryQuery, SearchContext searchContext)
		throws Exception {

		BaseSearcher baseSearcher = _assetSearcherFactory.createBaseSearcher(
			assetEntryQuery);

		return baseSearcher.search(searchContext);
	}

	@Inject
	private static AssetSearcherFactory _assetSearcherFactory;

	@Inject
	private static BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private static BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Inject
	private static DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private final JournalArticleFixture _journalArticleFixture =
		new JournalArticleFixture();
	private final List<User> _users = new ArrayList<>();

}