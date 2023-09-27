/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.search.JournalArticleBlueprint;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		assertEntryClassNames(_entryClassNames, hits, facet, searchContext);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, toMap(_entryClassNames));
	}

	@Test
	public void testAvoidResidualDataFromDDMStructureLocalServiceTest()
		throws Exception {

		// See LPS-58543

		String keyword = "To Do";

		index(keyword);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = createFacet(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(_entryClassNames, hits, facet, searchContext);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, toMap(_entryClassNames));
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
			Arrays.asList(JournalArticle.class.getName()), hits, facet,
			searchContext);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, toMap(_entryClassNames));
	}

	protected static Map<String, Integer> toMap(String key, Integer value) {
		return Collections.singletonMap(key, value);
	}

	protected void addFileEntry(String title, User user, Group group)
		throws Exception {

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			user.getUserId(), group.getGroupId(), 0, StringPool.BLANK, title,
			true, ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		_fileEntries.add(fileEntry);
	}

	protected void addJournalArticle(String title, Group group)
		throws Exception {

		journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(group.getGroupId());
					setJournalArticleContent(
						new JournalArticleContent() {
							{
								put(
									LocaleUtil.US,
									RandomTestUtil.randomString());

								setDefaultLocale(LocaleUtil.US);
								setName("content");
							}
						});
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(LocaleUtil.US, title);
							}
						});
				}
			});
	}

	protected void assertEntryClassNames(
		List<String> entryclassNames, Hits hits, Facet facet,
		SearchContext searchContext) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			facet.getFieldName(), entryclassNames);
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
		return new HashMap<String, Integer>() {
			{
				for (String string : strings) {
					put(string, 1);
				}
			}
		};
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