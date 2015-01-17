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

package com.liferay.portlet.usersadmin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.SynchronousDestinationTestRule;
import com.liferay.portal.util.test.SearchContextTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portal.util.test.UserTestUtil.AddUserCommand;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class UserIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_indexer = new UserIndexer();

		_serviceContext = ServiceContextTestUtil.getServiceContext();
	}

	@Test
	public void testNameFields() throws Exception {
		Assume.assumeTrue(
			isCaseInsensitiveWildcardsImplementedForSearchEngine());

		User user = UserTestUtil.createUser();

		user.setFirstName("First");
		user.setLastName("Last");
		user.setMiddleName("Middle");
		user.setScreenName("Screen");

		addUser(user);

		user = assertSearchOneUser("");

		Assert.assertEquals("First Middle Last", user.getFullName());

		user = assertSearchOneUser("Fir");

		Assert.assertEquals("First", user.getFirstName());

		user = assertSearchOneUser("asT");

		Assert.assertEquals("Last", user.getLastName());

		user = assertSearchOneUser("idd");

		Assert.assertEquals("Middle", user.getMiddleName());

		user = assertSearchOneUser("SCREE");

		Assert.assertEquals("screen", user.getScreenName());
	}

	protected void addUser(User user) throws PortalException {
		user.setCompanyId(TestPropsValues.getCompanyId());

		AddUserCommand addUserCommand = new AddUserCommand();

		addUserCommand.groupIds = new long[] {TestPropsValues.getGroupId()};

		_users.add(addUserCommand.copy(user, _serviceContext));
	}

	protected User assertSearchOneUser(String keywords) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		Hits hits = _indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());

		long userId = UserIndexer.getUserId(hits.doc(0));

		return UserLocalServiceUtil.getUser(userId);
	}

	protected boolean isCaseInsensitiveWildcardsImplementedForSearchEngine() {
		SearchEngine searchEngine = SearchEngineUtil.getSearchEngine(
			SearchEngineUtil.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

		if (vendor.equals("Elasticsearch")) {
			return true;
		}

		return false;
	}

	private Indexer _indexer;
	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}