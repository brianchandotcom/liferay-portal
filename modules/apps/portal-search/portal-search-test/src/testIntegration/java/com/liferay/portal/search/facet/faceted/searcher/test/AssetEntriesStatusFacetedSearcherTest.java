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
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.search.JournalArticleBlueprint;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class AssetEntriesStatusFacetedSearcherTest
	extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDraft() throws Exception {
		String keyword = RandomTestUtil.randomString();

		index(keyword, true);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = createFacet(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(Collections.emptyList(), hits, keyword, facet);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, Collections.emptyMap());
	}

	@Test
	public void testPublished() throws Exception {
		String keyword = RandomTestUtil.randomString();

		index(keyword, false);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = createFacet(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		assertEntryClassNames(
			Arrays.asList(JournalArticle.class.getName()), hits, keyword,
			facet);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits,
			Collections.singletonMap(JournalArticle.class.getName(), 1));
	}

	protected void assertEntryClassNames(
		List<String> entryclassnames, Hits hits, String keyword, Facet facet) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			keyword, hits.getDocs(), facet.getFieldName(), entryclassnames);
	}

	protected Facet createFacet(SearchContext searchContext) {
		return assetEntriesFacetFactory.newInstance(searchContext);
	}

	protected void index(String keyword, boolean draft) throws Exception {
		Group group = userSearchFixture.addGroup();

		journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setDraft(draft);
					setGroupId(group.getGroupId());
					setJournalArticleContent(
						new JournalArticleContent() {
							{
								setName("content");
								setDefaultLocale(LocaleUtil.US);

								put(
									LocaleUtil.US,
									RandomTestUtil.randomString());
							}
						});
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(LocaleUtil.US, keyword);
							}
						});
					setWorkflowEnabled(true);
				}
			});

		User user = UserTestUtil.getAdminUser(group.getCompanyId());

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user));
	}

	@Inject
	protected AssetEntriesFacetFactory assetEntriesFacetFactory;

	@Inject
	protected PermissionCheckerFactory permissionCheckerFactory;

}