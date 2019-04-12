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

package com.liferay.portal.search.indexer.post.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class IndexerPostProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_userSearchFixture.setUp();

		_users = _userSearchFixture.getUsers();

		_group = _userSearchFixture.addGroup();

		_indexer = indexerRegistry.getIndexer(User.class);
	}

	@After
	public void tearDown() throws Exception {
		unregisterIndexerPostProcessor();
	}

	@Test
	public void testNeverRegister() throws Exception {
		indexUser("postAlpha");
		indexUser("postBeta");

		assertSearch("post", Arrays.asList(StringPool.BLANK, StringPool.BLANK));
	}

	@Test
	public void testRegister() throws Exception {
		registerIndexerPostProcessor();

		indexUser("postAlpha");
		indexUser("postBeta");

		assertSearch("post", Arrays.asList(_TEST_VALUE, _TEST_VALUE));
	}

	@Test
	public void testUnregister() throws Exception {
		indexUser("postAlpha");

		registerIndexerPostProcessor();

		indexUser("postBeta");

		unregisterIndexerPostProcessor();

		indexUser("postCharlie");

		assertSearch(
			"post",
			Arrays.asList(StringPool.BLANK, _TEST_VALUE, StringPool.BLANK));
	}

	protected static IndexerPostProcessor createIndexerPostProcessor() {
		return new BaseIndexerPostProcessor() {

			@Override
			public void postProcessDocument(Document document, Object obj)
				throws Exception {

				document.addText(_TEST_FIELD, _TEST_VALUE);
			}

		};
	}

	protected void assertSearch(
			String keywords, List<String> expectedFieldValues)
		throws Exception {

		SearchContext searchContext = buildSearchContext(keywords);

		Hits results = _indexer.search(searchContext);

		DocumentsAssert.assertValuesIgnoreRelevance(
			(String)searchContext.getAttribute("queryString"),
			results.getDocs(), _TEST_FIELD, expectedFieldValues);
	}

	protected SearchContext buildSearchContext(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setGroupIds(new long[0]);
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames("screenName", _TEST_FIELD);

		return searchContext;
	}

	protected void indexUser(String screenName) throws Exception {
		_indexer.reindex(
			_userSearchFixture.addUser(
				screenName, _group, RandomTestUtil.randomString()));
	}

	protected void registerIndexerPostProcessor() {
		_indexer.registerIndexerPostProcessor(_indexerPostProcessor);
	}

	protected void unregisterIndexerPostProcessor() {
		_indexer.unregisterIndexerPostProcessor(_indexerPostProcessor);
	}

	@Inject
	protected IndexerRegistry indexerRegistry;

	private static final String _TEST_FIELD = "testField";

	private static final String _TEST_VALUE = "DO_NOT_MATCH_THIS";

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<User> _indexer;
	private final IndexerPostProcessor _indexerPostProcessor =
		createIndexerPostProcessor();

	@DeleteAfterTestRun
	private List<User> _users;

	private final UserSearchFixture _userSearchFixture =
		new UserSearchFixture();

}