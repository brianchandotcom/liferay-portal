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

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.test.journal.util.JournalArticleBuilder;
import com.liferay.portal.search.test.journal.util.JournalArticleContent;
import com.liferay.portal.search.test.journal.util.JournalArticleTitle;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class AssetEntriesFacetedSearcherTest
	extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		deleteAfterTestRun(_fileEntries);
	}

	@Test
	public void testAggregation() throws Exception {
		String keyword = RandomTestUtil.randomString();

		index(keyword);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = createFacet(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(_entryClassNames, hits, keyword, facet);

		assertFrequencies(
			facet.getFieldName(), searchContext, toMap(_entryClassNames));
	}

	@Test
	public void testSelection() throws Exception {
		String keyword = RandomTestUtil.randomString();

		index(keyword);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = createFacet(searchContext);

		facet.select(JournalArticle.class.getName());

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
			Arrays.asList(JournalArticle.class.getName()), hits, keyword,
			facet);

		assertFrequencies(
			facet.getFieldName(), searchContext, toMap(_entryClassNames));
	}

	protected static Map<String, Integer> toMap(String key, Integer value) {
		return Collections.singletonMap(key, value);
	}

	protected void addFileEntry(String title, User user, Group group)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			user.getUserId(), group.getGroupId(), 0, StringPool.BLANK, title,
			true, serviceContext);

		_fileEntries.add(fileEntry);
	}

	protected void addJournalArticle(String title, Group group)
		throws Exception {

		JournalArticleBuilder journalArticleBuilder =
			new JournalArticleBuilder();

		journalArticleBuilder.setContent(
			new JournalArticleContent() {
				{
					name = "content";
					defaultLocale = LocaleUtil.US;

					put(LocaleUtil.US, RandomTestUtil.randomString());
				}
			});
		journalArticleBuilder.setGroupId(group.getGroupId());
		journalArticleBuilder.setTitle(
			new JournalArticleTitle() {
				{
					put(LocaleUtil.US, title);
				}
			});

		journalArticleSearchFixture.addArticle(journalArticleBuilder);
	}

	protected void assertEntryClassNames(
		List<String> entryclassnames, Hits hits, String keyword, Facet facet) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			keyword, hits.getDocs(), facet.getFieldName(), entryclassnames);
	}

	protected Facet createFacet(SearchContext searchContext) {
		return assetEntriesFacetFactory.newInstance(searchContext);
	}

	protected void deleteAfterTestRun(List<FileEntry> fileEntries)
		throws Exception {

		for (FileEntry fileEntry : fileEntries) {
			dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());
		}
	}

	protected void index(String keyword) throws Exception, PortalException {
		Group group = userSearchFixture.addGroup();

		User user = addUser(group, keyword);

		addFileEntry(keyword, user, group);

		addJournalArticle(keyword, group);

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user));
	}

	protected Map<String, Integer> toMap(Collection<String> strings) {
		return strings.stream().collect(Collectors.toMap(s -> s, s -> 1));
	}

	@Inject
	protected AssetEntriesFacetFactory assetEntriesFacetFactory;

	@Inject
	protected DLAppLocalService dlAppLocalService;

	@Inject
	protected PermissionCheckerFactory permissionCheckerFactory;

	private static final List<String> _entryClassNames = Arrays.asList(
		DLFileEntry.class.getName(), JournalArticle.class.getName(),
		User.class.getName());

	private final List<FileEntry> _fileEntries = new ArrayList<>();

}