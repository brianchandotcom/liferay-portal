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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

		String firstName = "First";
		String lastName = "Last";
		String middleName = "Middle";
		String screenName = "Screen";

		addUser(firstName, lastName, middleName, screenName);

		User user;

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

	protected void addUser(
			String firstName, String lastName, String middleName,
			String screenName)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = false;
		String emailAddress = RandomTestUtil.randomString() + "@liferay.com";
		long facebookId = 0;
		String openId = null;
		Locale locale = LocaleUtil.getDefault();
		long prefixId = 0;
		long suffixId = 0;
		boolean male = false;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		long[] groupIds = new long[] {TestPropsValues.getGroupId()};
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		User user = UserServiceUtil.addUser(
			companyId, autoPassword, password1, password2, autoScreenName,
			screenName, emailAddress, facebookId, openId, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
			roleIds, userGroupIds, sendMail, _serviceContext);

		_users.add(user);
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