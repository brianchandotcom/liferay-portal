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
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
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
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_indexer = new UserIndexer();

		_serviceContext = ServiceContextTestUtil.getServiceContext();
	}

	@Test
	public void testEmailAddress() throws Exception {
		addUserEmailAddress("Em.Ail@liferay.com");

		User user = assertSearchOneUser("Em.Ail@liferay.com");

		Assert.assertEquals("em.ail@liferay.com", user.getEmailAddress());
	}

	@Test
	public void testEmailAddressField() throws Exception {
		addUserEmailAddress("Em.Ail@liferay.com");

		User user = assertSearchOneUser("emailAddress", "em.ail@liferay.com");

		Assert.assertEquals("em.ail@liferay.com", user.getEmailAddress());
	}

	@Test
	public void testEmailAddressPrefix() throws Exception {
		addUserEmailAddress("Em.Ail@liferay.com");

		User user = assertSearchOneUser("EM.AIL");

		Assert.assertEquals("em.ail@liferay.com", user.getEmailAddress());
	}

	@Test
	public void testEmailAddressSubstring() throws Exception {
		addUserEmailAddress("Em.Ail@liferay.com");

		User user = assertSearchOneUser("ail@life");

		Assert.assertEquals("em.ail@liferay.com", user.getEmailAddress());
	}

	@Test
	public void testEmptyQuery() throws Exception {
		addUser();

		assertHits("", 1);
	}

	@Test
	public void testFirstNameExactPhrase() throws Exception {
		String firstName = "Mary Jane";
		String middleName = "Watson";
		String lastName = "Parker";

		addUserNameFields(firstName, lastName, middleName);

		User user = assertSearchOneUser("firstName", "\"Mary Jane\"");

		Assert.assertEquals(firstName, user.getFirstName());
	}

	@Test
	public void testFirstNameMixedExactPhrase() throws Exception {
		String firstName = "Mary Jane Watson";
		String middleName = "Joanne";
		String lastName = "Parker";

		addUserNameFields(firstName, lastName, middleName);

		assertHits("firstName", "\"Mary Watson\"", 0);

		User user = assertSearchOneUser("firstName", "Mary \"Jane Watson\"");

		Assert.assertEquals(firstName, user.getFirstName());

		user = assertSearchOneUser("firstName", "\"Mary Jane\" Trial");

		Assert.assertEquals(firstName, user.getFirstName());
	}

	@Test
	public void testLikeCharacter() throws Exception {
		addUser();

		assertHits("%", 1);
		assertHits("%" + RandomTestUtil.randomString(), 0);
	}

	@Test
	public void testLuceneQueryParserUnfriendlyCharacters() throws Exception {
		User user1 = addUser();
		User user2 = assertSearchOneUser("@");

		Assert.assertEquals(user1.getEmailAddress(), user2.getEmailAddress());

		assertHits("@" + RandomTestUtil.randomString(), 0);
		assertHits("!", 0);
		assertHits("!" + RandomTestUtil.randomString(), 0);
	}

	@Test
	public void testNameFieldsNotTokenized() throws Exception {
		String firstName = "Liferay7";
		String lastName = "dell'Apostrophe";
		String middleName = "ALLOY_4";

		testNameFields(firstName, lastName, middleName);
	}

	@Test
	public void testNameFieldsNotTokenizedLowercase() throws Exception {
		String firstName = "liferay7";
		String lastName = "dell'apostrophe";
		String middleName = "alloy_4";

		testNameFields(firstName, lastName, middleName);
	}

	@Test
	public void testNamesPrefix() throws Exception {
		String firstName = "First";
		String lastName = "Last";
		String middleName = "Middle";

		addUserNameFields(firstName, lastName, middleName);

		User user = assertSearchOneUser("Fir");

		Assert.assertEquals("First", user.getFirstName());

		user = assertSearchOneUser("LasT");

		Assert.assertEquals("Last", user.getLastName());

		user = assertSearchOneUser("midd");

		Assert.assertEquals("Middle", user.getMiddleName());
	}

	@Test
	public void testNamesSubstring() throws Exception {
		String firstName = "First";
		String lastName = "Last";
		String middleName = "Middle";

		addUserNameFields(firstName, lastName, middleName);

		User user = assertSearchOneUser("Fir");

		Assert.assertEquals("First", user.getFirstName());

		user = assertSearchOneUser("asT");

		Assert.assertEquals("Last", user.getLastName());

		user = assertSearchOneUser("idd");

		Assert.assertEquals("Middle", user.getMiddleName());
	}

	@Test
	public void testScreenName() throws Exception {
		addUserScreenName("Open4Life");

		User user = assertSearchOneUser("Open4Life");

		Assert.assertEquals("open4life", user.getScreenName());
	}

	@Test
	public void testScreenNameField() throws Exception {
		addUserScreenName("Open4Life");

		User user = assertSearchOneUser("screenName", "open4life");

		Assert.assertEquals("open4life", user.getScreenName());
	}

	@Test
	public void testScreenNamePrefix() throws Exception {
		addUserScreenName("Open4Life");

		User user = assertSearchOneUser("OPE");

		Assert.assertEquals("open4life", user.getScreenName());
	}

	@Test
	public void testScreenNameSubstring() throws Exception {
		addUserScreenName("Open4Life");

		User user = assertSearchOneUser("4lif");

		Assert.assertEquals("open4life", user.getScreenName());
	}

	@Test
	public void testScreenNameTwoWords() throws Exception {
		addUserScreenName("Open4Life");

		User user = assertSearchOneUser("screenName", "open lite");

		Assert.assertEquals("open4life", user.getScreenName());
	}

	protected User addUser() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		return user;
	}

	protected void addUser(
			String firstName, String lastName, String middleName,
			String screenName, String emailAddress)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = false;
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

	protected void addUserEmailAddress(String emailAddress) throws Exception {
		String firstName = RandomTestUtil.randomString();
		String lastName = RandomTestUtil.randomString();
		String middleName = RandomTestUtil.randomString();
		String screenName = RandomTestUtil.randomString();

		addUser(firstName, lastName, middleName, screenName, emailAddress);
	}

	protected void addUserNameFields(
			String firstName, String lastName, String middleName)
		throws Exception {

		String screenName = RandomTestUtil.randomString();
		String emailAddress = RandomTestUtil.randomString() + "@liferay.com";

		addUser(firstName, lastName, middleName, screenName, emailAddress);
	}

	protected void addUserScreenName(String screenName) throws Exception {
		String firstName = RandomTestUtil.randomString();
		String lastName = RandomTestUtil.randomString();
		String middleName = RandomTestUtil.randomString();
		String emailAddress = RandomTestUtil.randomString() + "@liferay.com";

		addUser(firstName, lastName, middleName, screenName, emailAddress);
	}

	protected Hits assertHits(
			final SearchContext searchContext, final int length)
		throws Exception {

		return IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Hits>() {

				@Override
				public Hits call() throws Exception {
					Hits hits = _indexer.search(searchContext);

					Assert.assertEquals(length, hits.getLength());

					return hits;
				}

			});
	}

	protected Hits assertHits(String keywords, int length) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setKeywords(keywords);

		return assertHits(searchContext, length);
	}

	protected Hits assertHits(String field, String value, int length)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setAttribute(field, value);

		return assertHits(searchContext, length);
	}

	protected User assertSearchOneUser(String keywords) throws Exception {
		Hits hits = assertHits(keywords, 1);

		return getUser(hits);
	}

	protected User assertSearchOneUser(String field, String value)
		throws Exception {

		Hits hits = assertHits(field, value, 1);

		return getUser(hits);
	}

	protected User getUser(Hits hits) throws PortalException {
		long userId = UserIndexer.getUserId(hits.doc(0));

		return UserLocalServiceUtil.getUser(userId);
	}

	protected void testNameFields(
			String firstName, String lastName, String middleName)
		throws Exception {

		addUserNameFields(firstName, lastName, middleName);

		User user = assertSearchOneUser("firstName", firstName);

		Assert.assertEquals(firstName, user.getFirstName());

		user = assertSearchOneUser("lastName", lastName);

		Assert.assertEquals(lastName, user.getLastName());

		user = assertSearchOneUser("middleName", middleName);

		Assert.assertEquals(middleName, user.getMiddleName());
	}

	private Indexer<User> _indexer;
	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}