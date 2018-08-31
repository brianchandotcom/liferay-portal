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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.site.SiteFacetFactory;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.facet.user.UserFacetFactory;
import com.liferay.portal.search.test.blogs.util.BlogsEntrySearchFixture;
import com.liferay.portal.search.test.documentlibrary.util.FileEntrySearchFixture;
import com.liferay.portal.search.test.journal.util.JournalArticleBlueprint;
import com.liferay.portal.search.test.journal.util.JournalArticleContent;
import com.liferay.portal.search.test.journal.util.JournalArticleTitle;
import com.liferay.portal.search.test.util.SearchMapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
@Sync
public class AggregationFilteringTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpBlogsEntrySearchFixture();
		setUpFileEntrySearchFixture();

		_group1 = addGroup();
		_group2 = addGroup();

		_user1 = addUser();
		_user2 = addUser();
		_user3 = addUser();

		_keyword = RandomTestUtil.randomString();

		addBlogsEntry(_group1, _user1, _keyword);
		addBlogsEntry(_group1, _user2, _keyword);
		addBlogsEntry(_group2, _user2, _keyword);

		addFileEntry(_group1, _user1, _keyword);
		addFileEntry(_group2, _user2, _keyword);
		addFileEntry(_group2, _user3, _keyword);

		addJournalArticle(_group1, _user1, _keyword);
		addJournalArticle(_group2, _user1, _keyword);
		addJournalArticle(_group1, _user3, _keyword);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		blogsEntrySearchFixture.tearDown();
		fileEntrySearchFixture.tearDown();
	}

	@Test
	public void testSelectNone() throws Exception {
		assertSearch(
			new Expectations() {
				{
					groupFrequencies = SearchMapUtil.join(
						toMap(_group1, 5), toMap(_group2, 4));
					typeFrequencies = SearchMapUtil.join(
						toMap(BlogsEntry.class, 3), toMap(DLFileEntry.class, 3),
						toMap(JournalArticle.class, 3));
					userFrequencies = SearchMapUtil.join(
						toMap(_user1, 4), toMap(_user2, 3), toMap(_user3, 2));
				}
			});
	}

	@Test
	public void testSelectOneGroupOneUser() throws Exception {
		assertSearch(
			new Expectations() {
				{
					selectGroups = new Group[] {_group2};
					selectUsers = new User[] {_user1};

					groupFrequencies = SearchMapUtil.join(
						toMap(_group1, 3), toMap(_group2, 1));
					typeFrequencies = toMap(JournalArticle.class, 1);
					userFrequencies = SearchMapUtil.join(
						toMap(_user1, 1), toMap(_user2, 2), toMap(_user3, 1));
				}
			});
	}

	@Test
	public void testSelectOneGroupOneUserOneType() throws Exception {
		assertSearch(
			new Expectations() {
				{
					selectGroups = new Group[] {_group2};
					selectTypes = new Class<?>[] {JournalArticle.class};
					selectUsers = new User[] {_user1};

					groupFrequencies = SearchMapUtil.join(
						toMap(_group1, 1), toMap(_group2, 1));
					typeFrequencies = toMap(JournalArticle.class, 1);
					userFrequencies = toMap(_user1, 1);
				}
			});
	}

	@Test
	public void testSelectOneUser() throws Exception {
		assertSearch(
			new Expectations() {
				{
					selectUsers = new User[] {_user1};

					groupFrequencies = SearchMapUtil.join(
						toMap(_group1, 3), toMap(_group2, 1));
					typeFrequencies = SearchMapUtil.join(
						toMap(BlogsEntry.class, 1), toMap(DLFileEntry.class, 1),
						toMap(JournalArticle.class, 2));
					userFrequencies = SearchMapUtil.join(
						toMap(_user1, 4), toMap(_user2, 3), toMap(_user3, 2));
				}
			});
	}

	@Test
	public void testSelectOneUserOneType() throws Exception {
		assertSearch(
			new Expectations() {
				{
					selectUsers = new User[] {_user1};
					selectTypes = new Class<?>[] {JournalArticle.class};

					groupFrequencies = SearchMapUtil.join(
						toMap(_group1, 1), toMap(_group2, 1));
					typeFrequencies = SearchMapUtil.join(
						toMap(BlogsEntry.class, 1), toMap(DLFileEntry.class, 1),
						toMap(JournalArticle.class, 2));
					userFrequencies = SearchMapUtil.join(
						toMap(_user1, 2), toMap(_user3, 1));
				}
			});
	}

	@Test
	public void testSelectOneUserTwoTypes() throws Exception {
		assertSearch(
			new Expectations() {
				{
					selectTypes = new Class<?>[] {
						DLFileEntry.class, JournalArticle.class
					};
					selectUsers = new User[] {_user1};

					groupFrequencies = SearchMapUtil.join(
						toMap(_group1, 2), toMap(_group2, 1));
					typeFrequencies = SearchMapUtil.join(
						toMap(BlogsEntry.class, 1), toMap(DLFileEntry.class, 1),
						toMap(JournalArticle.class, 2));
					userFrequencies = SearchMapUtil.join(
						toMap(_user1, 3), toMap(_user2, 1), toMap(_user3, 2));
				}
			});
	}

	protected static String[] getClassNames(Class... classes) {
		Stream<Class> stream = Arrays.stream(classes);

		return stream.map(
			Class::getName
		).toArray(
			String[]::new
		);
	}

	protected static String[] getGroupIdStrings(Group... groups) {
		Stream<Group> stream = Arrays.stream(groups);

		return stream.map(
			Group::getGroupId
		).map(
			String::valueOf
		).toArray(
			String[]::new
		);
	}

	protected static String[] getUserFullNames(User... users) {
		Stream<User> stream = Arrays.stream(users);

		return stream.map(
			User::getFullName
		).map(
			StringUtil::toLowerCase
		).toArray(
			String[]::new
		);
	}

	protected static <K, V> Map<K, V> toMap(K key, V value) {
		return Collections.singletonMap(key, value);
	}

	protected void addBlogsEntry(Group group, User user, String keyword)
		throws Exception {

		blogsEntrySearchFixture.addBlogsEntry(group, user, keyword);
	}

	protected void addFileEntry(Group group, User user, String keyword)
		throws Exception {

		fileEntrySearchFixture.addFileEntry(group, user, keyword);
	}

	protected Group addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	protected JournalArticle addJournalArticle(
			Group group, User user, String keyword)
		throws Exception {

		return journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					groupId = group.getGroupId();
					journalArticleContent = new JournalArticleContent() {
						{
							defaultLocale = LocaleUtil.US;
							name = "content";

							put(LocaleUtil.US, RandomTestUtil.randomString());
						}
					};
					journalArticleTitle = new JournalArticleTitle() {
						{
							put(LocaleUtil.US, keyword);
						}
					};
					userId = user.getUserId();
				}
			});
	}

	protected User addUser() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		return user;
	}

	protected void assertSearch(Expectations expectations) throws Exception {
		SearchContext searchContext = getSearchContext(_keyword);

		searchContext.addFacet(
			createSiteFacet(expectations.selectGroups, searchContext));
		searchContext.addFacet(
			createTypeFacet(expectations.selectTypes, searchContext));
		searchContext.addFacet(
			createUserFacet(expectations.selectUsers, searchContext));

		search(searchContext);

		Set<Map.Entry<Group, Integer>> groupFrequenciesEntrySet =
			expectations.groupFrequencies.entrySet();

		Map<String, Integer> groupFrequencies =
			groupFrequenciesEntrySet.stream().collect(
				Collectors.toMap(
					entry -> String.valueOf(entry.getKey().getGroupId()),
					Map.Entry::getValue));

		assertFrequencies(Field.GROUP_ID, searchContext, groupFrequencies);

		Set<Map.Entry<Class<?>, Integer>> typeFrequenciesEntrySet =
			expectations.typeFrequencies.entrySet();

		Map<String, Integer> typeFrequencies =
			typeFrequenciesEntrySet.stream().collect(
				Collectors.toMap(
					entry -> entry.getKey().getName(), Map.Entry::getValue));

		assertFrequencies(
			Field.ENTRY_CLASS_NAME, searchContext, typeFrequencies);

		Set<Map.Entry<User, Integer>> userFrequenciesEntrySet =
			expectations.userFrequencies.entrySet();

		Map<String, Integer> userFrequencies =
			userFrequenciesEntrySet.stream().collect(
				Collectors.toMap(
					entry -> StringUtil.toLowerCase(
						entry.getKey().getFullName()),
					Map.Entry::getValue));

		assertFrequencies(Field.USER_NAME, searchContext, userFrequencies);
	}

	protected Facet createSiteFacet(
		Group[] groups, SearchContext searchContext) {

		Facet facet = siteFacetFactory.newInstance(searchContext);

		facet.select(getGroupIdStrings(groups));

		return facet;
	}

	protected Facet createTypeFacet(
		Class[] classes, SearchContext searchContext) {

		Facet facet = assetEntriesFacetFactory.newInstance(searchContext);

		facet.select(getClassNames(classes));

		return facet;
	}

	protected Facet createUserFacet(User[] users, SearchContext searchContext) {
		Facet facet = userFacetFactory.newInstance(searchContext);

		facet.select(getUserFullNames(users));

		return facet;
	}

	protected void setUpBlogsEntrySearchFixture() {
		blogsEntrySearchFixture = new BlogsEntrySearchFixture(
			blogsEntryLocalService);

		blogsEntrySearchFixture.setUp();

		_blogsEntries = blogsEntrySearchFixture.getBlogsEntries();
	}

	protected void setUpFileEntrySearchFixture() {
		fileEntrySearchFixture = new FileEntrySearchFixture(dlAppLocalService);

		fileEntrySearchFixture.setUp();
	}

	@Inject
	protected AssetEntriesFacetFactory assetEntriesFacetFactory;

	@Inject
	protected BlogsEntryLocalService blogsEntryLocalService;

	protected BlogsEntrySearchFixture blogsEntrySearchFixture;

	@Inject
	protected DLAppLocalService dlAppLocalService;

	protected FileEntrySearchFixture fileEntrySearchFixture;

	@Inject
	protected SiteFacetFactory siteFacetFactory;

	@Inject
	protected UserFacetFactory userFacetFactory;

	protected static class Expectations {

		protected Map<Group, Integer> groupFrequencies;
		protected Group[] selectGroups = {};
		protected Class<?>[] selectTypes = {};
		protected User[] selectUsers = {};
		protected Map<Class<?>, Integer> typeFrequencies;
		protected Map<User, Integer> userFrequencies;

	}

	@DeleteAfterTestRun
	private List<BlogsEntry> _blogsEntries;

	private Group _group1;
	private Group _group2;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	private String _keyword;
	private User _user1;
	private User _user2;
	private User _user3;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}