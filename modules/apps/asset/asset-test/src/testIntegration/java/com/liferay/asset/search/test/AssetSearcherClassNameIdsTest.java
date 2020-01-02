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
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.util.AssetSearcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
		_group = GroupTestUtil.addGroup();
		_users = new ArrayList<>();

		_journalArticleFixture.setGroup(_group);

		_journalArticleFixture.setJournalArticleLocalService(
			_journalArticleLocalService);
	}

	@Test
	public void testAll() throws Exception {
		User user = addUser();

		UserTestUtil.setUser(user);

		addBlogsEntry();
		addFileEntry();
		addJournalArticle();

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery();

		Hits hits = search(assetEntryQuery, getSearchContext());

		Assert.assertEquals(hits.toString(), 3, hits.getLength());
	}

	@Test
	public void testMultiple() throws Exception {
		User user = addUser();

		UserTestUtil.setUser(user);

		addBlogsEntry();
		addFileEntry();
		addJournalArticle();

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			"com.liferay.document.library.kernel.model.DLFileEntry",
			"com.liferay.journal.model.JournalArticle");

		Hits hits = search(assetEntryQuery, getSearchContext());

		Assert.assertEquals(hits.toString(), 2, hits.getLength());
	}

	@Test
	public void testSingle() throws Exception {
		User user = addUser();

		UserTestUtil.setUser(user);

		addBlogsEntry();
		addFileEntry();
		addJournalArticle();

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			"com.liferay.journal.model.JournalArticle");

		Hits hits = search(assetEntryQuery, getSearchContext());

		Assert.assertEquals(hits.toString(), 1, hits.getLength());
	}

	protected BlogsEntry addBlogsEntry() throws Exception {
		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), getServiceContext());
	}

	protected FileEntry addFileEntry() throws Exception {
		return _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, new byte[0],
			getServiceContext());
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
		return Stream.of(
			classNames
		).mapToLong(
			PortalUtil::getClassNameId
		).toArray();
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

		AssetSearcher assetSearcher = new AssetSearcher();

		assetSearcher.setAssetEntryQuery(assetEntryQuery);

		return assetSearcher.search(searchContext);
	}

	@Inject
	private static BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private static DLAppLocalService _dlAppLocalService;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private final JournalArticleFixture _journalArticleFixture =
		new JournalArticleFixture();
	private List<User> _users;

}