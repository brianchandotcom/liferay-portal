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

package com.liferay.portal.search.indexer.clauses.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.test.util.search.BlogsEntryBlueprint.BlogsEntryBlueprintBuilder;
import com.liferay.blogs.test.util.search.BlogsEntrySearchFixture;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.search.JournalArticleBlueprintBuilder;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleSearchFixture;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class IndexerClausesChangeTrackingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		BlogsEntrySearchFixture blogsEntrySearchFixture =
			new BlogsEntrySearchFixture(blogsEntryLocalService);

		GroupSearchFixture groupSearchFixture = new GroupSearchFixture();

		JournalArticleSearchFixture journalArticleSearchFixture =
			new JournalArticleSearchFixture(_journalArticleLocalService);

		_blogsEntries = blogsEntrySearchFixture.getBlogsEntries();
		_blogsEntrySearchFixture = blogsEntrySearchFixture;
		_ctCollection = addCTCollection();
		_group = groupSearchFixture.addGroup(new GroupBlueprint());
		_groups = groupSearchFixture.getGroups();
		_journalArticles = journalArticleSearchFixture.getJournalArticles();
		_journalArticleSearchFixture = journalArticleSearchFixture;
		_user = TestPropsValues.getUser();
	}

	@Test
	public void testBaseIndexer() throws Exception {
		Assert.assertTrue(journalArticleIndexer instanceof BaseIndexer);

		JournalArticle journalArticle = addJournalArticle("Gamma Article");

		assertSearch(
			getSearchRequestBuilder(
			).modelIndexerClasses(
				JournalArticle.class
			).queryString(
				"gamma"
			),
			Arrays.asList("Gamma Article"));

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			updateJournalArticleTitle(journalArticle);

			assertSearch(
				getSearchRequestBuilder(
				).modelIndexerClasses(
					JournalArticle.class
				).queryString(
					"gamma"
				),
				Collections.emptyList());
		}

		assertSearch(
			getSearchRequestBuilder(
			).modelIndexerClasses(
				JournalArticle.class
			).queryString(
				"gamma"
			),
			Arrays.asList("Gamma Article"));
	}

	@Test
	public void testDefaultIndexer() throws Exception {
		Assert.assertEquals(
			"class com.liferay.portal.search.internal.indexer.DefaultIndexer",
			String.valueOf(blogsEntryIndexer.getClass()));

		BlogsEntry blogsEntry = addBlogsEntry("Gamma Blog");

		assertSearch(
			getSearchRequestBuilder(
			).modelIndexerClasses(
				BlogsEntry.class
			).queryString(
				"gamma"
			),
			Arrays.asList("Gamma Blog"));

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			updateBlogsEntryTitle(blogsEntry);

			assertSearch(
				getSearchRequestBuilder(
				).modelIndexerClasses(
					BlogsEntry.class
				).queryString(
					"gamma"
				),
				Collections.emptyList());
		}

		addBlogsEntry("Gamma Blog");

		assertSearch(
			getSearchRequestBuilder(
			).modelIndexerClasses(
				BlogsEntry.class
			).queryString(
				"gamma"
			),
			Arrays.asList("Gamma Blog"));
	}

	@Test
	public void testFacetedSearcher() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("Gamma Blog");
		JournalArticle journalArticle = addJournalArticle("Gamma Article");

		assertSearch(
			getSearchRequestBuilder(
			).modelIndexerClasses(
				BlogsEntry.class, JournalArticle.class
			).queryString(
				"gamma"
			),
			Arrays.asList("Gamma Article", "Gamma Blog"));

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			updateJournalArticleTitle(journalArticle);
			updateBlogsEntryTitle(blogsEntry);

			assertSearch(
				getSearchRequestBuilder(
				).modelIndexerClasses(
					JournalArticle.class
				).queryString(
					"gamma"
				),
				Collections.emptyList());
		}

		assertSearch(
			getSearchRequestBuilder(
			).modelIndexerClasses(
				BlogsEntry.class, JournalArticle.class
			).queryString(
				"gamma"
			),
			Arrays.asList("Gamma Article", "Gamma Blog"));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Rule
	public TestName testName = new TestName();

	protected BlogsEntry addBlogsEntry(String title) {
		return _blogsEntrySearchFixture.addBlogsEntry(
			BlogsEntryBlueprintBuilder.builder(
			).content(
				RandomTestUtil.randomString()
			).groupId(
				_group.getGroupId()
			).title(
				title
			).userId(
				_user.getUserId()
			).build());
	}

	protected CTCollection addCTCollection() throws PortalException {
		return _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(),
			IndexerClausesChangeTrackingTest.class.getName());
	}

	protected JournalArticle addJournalArticle(String title) {
		return _journalArticleSearchFixture.addArticle(
			JournalArticleBlueprintBuilder.builder(
			).groupId(
				_group.getGroupId()
			).journalArticleContent(
				new JournalArticleContent() {
					{
						put(LocaleUtil.US, RandomTestUtil.randomString());

						setDefaultLocale(LocaleUtil.US);
						setName("content");
					}
				}
			).journalArticleTitle(
				new JournalArticleTitle() {
					{
						put(LocaleUtil.US, title);
					}
				}
			).userId(
				_user.getUserId()
			).build());
	}

	protected void assertSearch(
		SearchRequestBuilder searchRequestBuilder,
		Collection<String> expectedValues) {

		SearchResponse searchResponse = searcher.search(
			searchRequestBuilder.build());

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), _TITLE_EN_US,
			expectedValues.stream());
	}

	protected SearchRequestBuilder getSearchRequestBuilder() {
		return searchRequestBuilderFactory.builder(
		).companyId(
			_group.getCompanyId()
		).fields(
			StringPool.STAR
		).groupIds(
			_group.getGroupId()
		);
	}

	protected void updateBlogsEntryTitle(BlogsEntry blogsEntry)
		throws PortalException {

		blogsEntryLocalService.updateEntry(
			blogsEntry.getUserId(), blogsEntry.getEntryId(),
			RandomTestUtil.randomString(), blogsEntry.getContent(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	protected void updateJournalArticleTitle(JournalArticle journalArticle)
		throws Exception {

		journalArticle.setTitleMap(
			RandomTestUtil.randomLocaleStringMap(LocaleUtil.US));

		_journalArticleSearchFixture.updateArticle(journalArticle);
	}

	@Inject(filter = "indexer.class.name=com.liferay.blogs.model.BlogsEntry")
	protected Indexer<BlogsEntry> blogsEntryIndexer;

	@Inject
	protected BlogsEntryLocalService blogsEntryLocalService;

	@Inject(filter = "component.name=*.JournalArticleIndexer")
	protected Indexer<JournalArticle> journalArticleIndexer;

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private static final String _TITLE_EN_US = StringBundler.concat(
		Field.TITLE, StringPool.UNDERLINE, LocaleUtil.US);

	@DeleteAfterTestRun
	private List<BlogsEntry> _blogsEntries;

	private BlogsEntrySearchFixture _blogsEntrySearchFixture;

	//@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles;

	private JournalArticleSearchFixture _journalArticleSearchFixture;
	private User _user;

}