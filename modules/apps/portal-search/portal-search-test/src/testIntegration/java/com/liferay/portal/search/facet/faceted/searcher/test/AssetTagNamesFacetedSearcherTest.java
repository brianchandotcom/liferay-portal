/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.search.JournalArticleBlueprint;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.tag.AssetTagNamesFacetFactory;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrew Betts
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class AssetTagNamesFacetedSearcherTest
	extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAggregation() throws Exception {
		String keyword = RandomTestUtil.randomString();

		Group group = userSearchFixture.addGroup();
		String title = keyword;

		addJournalArticle(group, title);

		String tag = keyword;

		addUser(group, tag);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = _assetTagNamesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
			Arrays.asList(JournalArticle.class.getName(), User.class.getName()),
			hits, searchContext);

		Map<String, Integer> frequencies = Collections.singletonMap(
			StringUtil.toLowerCase(tag), 1);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);
	}

	@Test
	public void testSearchByFacet() throws Exception {
		String tag = "enterprise. open-source for life";

		addUser(tag);

		SearchContext searchContext = getSearchContext(tag);

		Facet facet = _assetTagNamesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = Collections.singletonMap(tag, 1);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);
	}

	@Test
	public void testSearchQuoted() throws Exception {
		String[] assetTagNames = {"Enterprise", "Open Source", "For   Life"};

		User user = addUser(assetTagNames);

		Map<String, String> expected = userSearchFixture.toMap(
			user, assetTagNames);

		assertTags("\"Enterprise\"", expected);
		assertTags("\"Open\"", expected);
		assertTags("\"Source\"", expected);
		assertTags("\"Open Source\"", expected);
		assertTags("\"For   Life\"", expected);
	}

	@Test
	public void testSelection() throws Exception {
		String keyword = RandomTestUtil.randomString();

		Group group = userSearchFixture.addGroup();
		String title = keyword;

		addJournalArticle(group, title);

		String tag = keyword;

		addUser(group, tag);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = _assetTagNamesFacetFactory.newInstance(searchContext);

		String tagToLowerCase = StringUtil.toLowerCase(tag);

		facet.select(tagToLowerCase);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
			Arrays.asList(User.class.getName()), hits, searchContext);

		Map<String, Integer> frequencies = Collections.singletonMap(
			tagToLowerCase, 1);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);
	}

	protected void addJournalArticle(Group group, String title)
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

	protected User addUser(String... assetTagNames) throws Exception {
		Group group = userSearchFixture.addGroup();

		return addUser(group, assetTagNames);
	}

	protected void assertEntryClassNames(
		List<String> entryClassNames, Hits hits, SearchContext searchContext) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			Field.ENTRY_CLASS_NAME, entryClassNames);
	}

	protected void assertTags(String keywords, Map<String, String> expected)
		throws Exception {

		SearchContext searchContext = getSearchContext(keywords);

		Hits hits = search(searchContext);

		assertTags(keywords, hits, expected, searchContext);
	}

	@Inject
	private static AssetTagNamesFacetFactory _assetTagNamesFacetFactory;

}