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

package com.liferay.portal.search.indexer.clause.contributors.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.test.util.search.BlogsEntryBlueprint.BlogsEntryBlueprintBuilder;
import com.liferay.blogs.test.util.search.BlogsEntrySearchFixture;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.search.JournalArticleBlueprintBuilder;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleSearchFixture;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class IndexerClauseContributorsTest {

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
			new JournalArticleSearchFixture(journalArticleLocalService);

		_blogsEntries = blogsEntrySearchFixture.getBlogsEntries();
		_blogsEntrySearchFixture = blogsEntrySearchFixture;
		_group = groupSearchFixture.addGroup(new GroupBlueprint());
		_groups = groupSearchFixture.getGroups();
		_journalArticles = journalArticleSearchFixture.getJournalArticles();
		_journalArticleSearchFixture = journalArticleSearchFixture;
		_user = TestPropsValues.getUser();
	}

	@Test
	public void testBlogsEntryCanExcludeTitleAndTitleMultilang()
		throws Exception {

		Assert.assertEquals(
			"class com.liferay.portal.search.internal.indexer.DefaultIndexer",
			String.valueOf(blogsEntryIndexer.getClass()));

		addBlogsEntry("Gamma Blog");
		addBlogsEntry("Omega Blog");

		Consumer<SearchRequestBuilder> consumer =
			searchRequestBuilder -> searchRequestBuilder.modelIndexerClasses(
				BlogsEntry.class
			).queryString(
				"gamma"
			);

		String titleContributorId =
			"com.liferay.portal.search.internal.spi.model.query.contributor." +
				"AlwaysPresentFieldsKeywordQueryContributor";
		String titleMultilangContributorId =
			"com.liferay.blogs.internal.search.spi.model.query.contributor." +
				"BlogsEntryKeywordQueryContributor";

		assertSearch(
			"[Gamma Blog]",
			withIncludes(titleContributorId, titleMultilangContributorId),
			consumer);

		assertSearch(
			"[Gamma Blog]",
			withIncludes(titleContributorId, titleMultilangContributorId),
			withExcludes(titleMultilangContributorId), consumer);

		assertSearch(
			"[Gamma Blog, Omega Blog]",
			withIncludes(titleContributorId, titleMultilangContributorId),
			withExcludes(titleContributorId, titleMultilangContributorId),
			consumer);
	}

	@Test
	public void testFacetedSearcher() throws Exception {
		addBlogsEntry("Gamma Blog");
		addBlogsEntry("Omega Blog");
		addJournalArticle("Gamma Article");
		addJournalArticle("Omega Article");
		addMessage("Gamma Message");
		addMessage("Omega Message");

		Consumer<SearchRequestBuilder> consumer =
			searchRequestBuilder -> searchRequestBuilder.modelIndexerClasses(
				BlogsEntry.class, MBMessage.class
			).queryString(
				"gamma"
			);

		String titleContributorId =
			"com.liferay.portal.search.internal.spi.model.query.contributor." +
				"AlwaysPresentFieldsKeywordQueryContributor";
		String titleMultilangContributorId1 =
			"com.liferay.blogs.internal.search.spi.model.query.contributor." +
				"BlogsEntryKeywordQueryContributor";
		String titleMultilangContributorId2 =
			"com.liferay.message.boards.internal.search.spi.model.query." +
				"contributor.MBMessageKeywordQueryContributor";

		assertSearch(
			"[Gamma Blog, Gamma Message]",
			withIncludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			consumer);

		assertSearch(
			"[Gamma Blog, Gamma Message]",
			withIncludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			withExcludes(titleContributorId), consumer);

		assertSearch(
			"[Gamma Blog]",
			withIncludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			withExcludes(
				titleMultilangContributorId1, titleMultilangContributorId2),
			consumer);

		assertSearch(
			"[Gamma Blog, Gamma Message, Omega Blog, Omega Message]",
			withIncludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			withExcludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			consumer);
	}

	@Test
	public void testFacetedSearcherGetsForcedTitleMultilangFromJournalArticle()
		throws Exception {

		addBlogsEntry("Gamma Blog");
		addBlogsEntry("Omega Blog");
		addJournalArticle("Gamma Article");
		addJournalArticle("Omega Article");
		addMessage("Gamma Message");
		addMessage("Omega Message");

		Consumer<SearchRequestBuilder> consumer =
			searchRequestBuilder -> searchRequestBuilder.modelIndexerClasses(
				BlogsEntry.class, JournalArticle.class, MBMessage.class
			).queryString(
				"gamma"
			);

		String titleContributorId =
			"com.liferay.portal.search.internal.spi.model.query.contributor." +
				"AlwaysPresentFieldsKeywordQueryContributor";
		String titleMultilangContributorId1 =
			"com.liferay.blogs.internal.search.spi.model.query.contributor." +
				"BlogsEntryKeywordQueryContributor";
		String titleMultilangContributorId2 =
			"com.liferay.message.boards.internal.search.spi.model.query." +
				"contributor.MBMessageKeywordQueryContributor";

		assertSearch(
			"[Gamma Article, Gamma Blog, Gamma Message]",
			withIncludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			consumer);

		assertSearch(
			"[Gamma Article, Gamma Blog, Gamma Message]",
			withIncludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			withExcludes(titleContributorId), consumer);

		assertSearch(
			"[Gamma Article, Gamma Blog, Gamma Message]",
			withIncludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			withExcludes(
				titleMultilangContributorId1, titleMultilangContributorId2),
			consumer);

		assertSearch(
			"[Gamma Article, Gamma Blog, Gamma Message]",
			withIncludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			withExcludes(
				titleContributorId, titleMultilangContributorId1,
				titleMultilangContributorId2),
			consumer);
	}

	@Test
	public void testJournalArticleForcesTitleMultilang() throws Exception {
		Assert.assertTrue(journalArticleIndexer instanceof BaseIndexer);

		addJournalArticle("Gamma Article");
		addJournalArticle("Omega Article");

		Consumer<SearchRequestBuilder> consumer =
			searchRequestBuilder -> searchRequestBuilder.modelIndexerClasses(
				JournalArticle.class
			).queryString(
				"gamma"
			);

		String id =
			"com.liferay.portal.search.internal.spi.model.query.contributor." +
				"AlwaysPresentFieldsKeywordQueryContributor";

		assertSearch("[Gamma Article]", withIncludes(id), consumer);

		assertSearch(
			"[Gamma Article]", withIncludes(id), withExcludes(id), consumer);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

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

	protected MBMessage addMessage(String title) throws Exception {
		return mbMessageLocalService.addMessage(
			null, _user.getUserId(), RandomTestUtil.randomString(),
			_group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			0L, MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, title,
			RandomTestUtil.randomString(), MBMessageConstants.DEFAULT_FORMAT,
			null, false, 0.0, false, _createServiceContext());
	}

	protected void assertSearch(
		String expected, Consumer<SearchRequestBuilder>... consumers) {

		SearchResponse searchResponse = searcher.search(
			searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).fields(
				StringPool.STAR
			).groupIds(
				_group.getGroupId()
			).withSearchRequestBuilder(
				consumers
			).build());

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), _TITLE_EN_US, expected);
	}

	protected Consumer<SearchRequestBuilder> withExcludes(String... excludes) {
		return searchRequestBuilder -> searchRequestBuilder.withSearchContext(
			searchContext -> searchContext.setAttribute(
				"search.full.query.clause.contributors.excludes",
				StringUtil.merge(excludes)));
	}

	protected Consumer<SearchRequestBuilder> withIncludes(String... includes) {
		return searchRequestBuilder -> searchRequestBuilder.withSearchContext(
			searchContext -> searchContext.setAttribute(
				"search.full.query.clause.contributors.includes",
				StringUtil.merge(includes)));
	}

	protected Consumer<SearchRequestBuilder> withoutIndexerClauses() {
		return searchRequestBuilder -> searchRequestBuilder.withSearchContext(
			searchContext -> searchContext.setAttribute(
				"search.full.query.suppress.indexer.provided.clauses", true));
	}

	@Inject(filter = "indexer.class.name=com.liferay.blogs.model.BlogsEntry")
	protected Indexer<BlogsEntry> blogsEntryIndexer;

	@Inject
	protected BlogsEntryLocalService blogsEntryLocalService;

	@Inject(filter = "component.name=*.JournalArticleIndexer")
	protected Indexer<JournalArticle> journalArticleIndexer;

	@Inject
	protected JournalArticleLocalService journalArticleLocalService;

	@Inject
	protected MBMessageLocalService mbMessageLocalService;

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private ServiceContext _createServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	private static final String _TITLE_EN_US = StringBundler.concat(
		Field.TITLE, StringPool.UNDERLINE, LocaleUtil.US);

	@DeleteAfterTestRun
	private List<BlogsEntry> _blogsEntries;

	private BlogsEntrySearchFixture _blogsEntrySearchFixture;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles;

	private JournalArticleSearchFixture _journalArticleSearchFixture;
	private User _user;

}