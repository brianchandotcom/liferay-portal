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

package com.liferay.portal.search.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.Rescore;
import com.liferay.portal.search.rescore.RescoreBuilder;
import com.liferay.portal.search.rescore.RescoreBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@Ignore
@RunWith(Arquillian.class)
public class SearchRequestBuilderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_userSearchFixture = new UserSearchFixture();

		_userSearchFixture.setUp();

		_users = _userSearchFixture.getUsers();

		_addGroupAndUser();
	}

	@After
	public void tearDown() throws Exception {
		_userSearchFixture.tearDown();
	}

	@Test
	public void testAddPostFilterQueryPart() throws Exception {
		_addUser("alpha", "alpha", "omega");
		_addUser("omega", "alpha", "omega");
		_addUser("phi", "alpha", "omega");
		_addUser("sigma", "zeta", "omega");

		String queryString = "omega";

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).groupIds(
				_group.getGroupId()
			).queryString(
				queryString
			);

		_assertSearch(
			"[alpha omega, alpha omega, alpha omega, zeta omega]", "userName",
			searchRequestBuilder);

		searchRequestBuilder.postFilterQuery(
			_queries.term("firstName", "alpha"));

		_assertSearch(
			"[alpha omega, alpha omega, alpha omega]", "userName",
			searchRequestBuilder);

		searchRequestBuilder.postFilterQuery(
			null
		).addPostFilterQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).occur(
				"filter"
			).query(
				_queries.term("firstName", "alpha")
			).build()
		);

		_assertSearch(
			"[alpha omega, alpha omega, alpha omega]", "userName",
			searchRequestBuilder);

		searchRequestBuilder.postFilterQuery(
			null
		).addPostFilterQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).occur(
				"filter"
			).query(
				_queries.term("screenName", "alpha")
			).build()
		);

		_assertSearch("[alpha omega]", "userName", searchRequestBuilder);
	}

	@Test
	public void testAddPostFilterQueryPartAdditive() throws Exception {
		_addUser("alpha", "alpha", "delta");
		_addUser("omega", "alpha", "delta");
		_addUser("gamma", "alpha", "delta");
		_addUser("sigma", "omega", "delta");

		String queryString = "delta";

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).groupIds(
				_group.getGroupId()
			).queryString(
				queryString
			).addPostFilterQueryPart(
				_complexQueryPartBuilderFactory.builder(
				).occur(
					"should"
				).query(
					_queries.term("screenName", "alpha")
				).build()
			);

		_assertSearch("[alpha delta]", "userName", searchRequestBuilder);

		searchRequestBuilder.addPostFilterQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).occur(
				"should"
			).query(
				_queries.term("screenName", "sigma")
			).build());

		_assertSearch(
			"[alpha delta, omega delta]", "userName", searchRequestBuilder);
	}

	@Test
	public void testAddRescore() throws Exception {
		_addUser("AlphaDelta", "alpha", "delta");
		_addUser("BetaDelta", "beta", "delta");
		_addUser("GammaDelta", "gamma", "delta");

		_assertSearch(
			"[alpha delta, beta delta, gamma delta]", "userName", "delta",
			(List<Rescore>)null);

		List<Rescore> rescores = Arrays.asList(
			_buildRescore("userName", "beta delta"));

		_assertSearch(
			"[beta delta, alpha delta, gamma delta]", "userName", "delta",
			rescores);

		rescores = Arrays.asList(
			_buildRescore("userName", "beta delta"),
			_buildRescore("userName", "gamma delta"));

		_assertSearch(
			"[beta delta, gamma delta, alpha delta]", "userName", "delta",
			rescores);
	}

	@Test
	public void testAddSort() throws Exception {
		_addUser("name1", "firstName2", "lastName3");
		_addUser("name2", "firstName3", "lastName1");
		_addUser("name3", "firstName1", "lastName2");

		FieldSort fieldSort = _sorts.field("screenName", SortOrder.DESC);

		_assertSearch("[name3, name2, name1]", "screenName", "name", fieldSort);

		fieldSort = _sorts.field("userName", SortOrder.ASC);

		_assertSearch(
			"[firstname1 lastname2, firstname2 lastname3, firstname3 " +
				"lastname1]",
			"userName", "name", fieldSort);
	}

	@Test
	public void testModelIndexerClassNames() throws Exception {
		_addUser("epsilon", "epsilon", "lambda1");
		_addUser("theta", "theta", "lambda2");
		_addUser("kappa", "kappa", "lambda3");

		String queryString = "lambda";

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).fields(
				StringPool.STAR
			).groupIds(
				_group.getGroupId()
			).modelIndexerClassNames(
				User.class.getCanonicalName()
			).queryString(
				queryString
			);

		_assertSearch(
			"[epsilon lambda1, theta lambda2, kappa lambda3]", "userName",
			searchRequestBuilder);

		searchRequestBuilder.modelIndexerClassNames(
			User.class.getCanonicalName(), UserGroup.class.getCanonicalName());

		_assertSearch(
			"[epsilon lambda1, theta lambda2, kappa lambda3]", "userName",
			searchRequestBuilder);

		searchRequestBuilder.modelIndexerClassNames(
			UserGroup.class.getCanonicalName());

		_assertSearch("[]", "userName", searchRequestBuilder);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Rule
	public TestName testName = new TestName();

	private void _addGroupAndUser() throws Exception {
		GroupSearchFixture groupSearchFixture = new GroupSearchFixture();

		_group = groupSearchFixture.addGroup(new GroupBlueprint());

		_groups = groupSearchFixture.getGroups();

		_user = TestPropsValues.getUser();

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(_user));
	}

	private void _addUser(String userName, String firstName, String lastName)
		throws Exception {

		String[] assetTagNames = {};

		_userSearchFixture.addUser(
			userName, firstName, lastName, LocaleUtil.US, _group,
			assetTagNames);
	}

	private void _assertSearch(
		String expected, String fieldName,
		SearchRequestBuilder searchRequestBuilder) {

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), fieldName, expected);
	}

	private void _assertSearch(
		String expected, String fieldName, String queryString,
		List<Rescore> rescores) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).groupIds(
				_group.getGroupId()
			).queryString(
				queryString
			);

		if (rescores != null) {
			for (Rescore rescore : rescores) {
				searchRequestBuilder.addRescore(rescore);
			}
		}

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), fieldName, expected);
	}

	private void _assertSearch(
		String expected, String fieldName, String queryString, Sort sort) {

		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).groupIds(
				_group.getGroupId()
			).queryString(
				queryString
			).addSort(
				sort
			).build());

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), fieldName, expected);
	}

	private Rescore _buildRescore(String fieldName, String value) {
		RescoreBuilder rescoreBuilder =
			_rescoreBuilderFactory.getRescoreBuilder();

		Query rescoreQuery = _queries.match(fieldName, value);

		return rescoreBuilder.query(
			rescoreQuery
		).windowSize(
			100
		).build();
	}

	@Inject
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private Queries _queries;

	@Inject
	private RescoreBuilderFactory _rescoreBuilderFactory;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Inject
	private Sorts _sorts;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

	private UserSearchFixture _userSearchFixture;

}