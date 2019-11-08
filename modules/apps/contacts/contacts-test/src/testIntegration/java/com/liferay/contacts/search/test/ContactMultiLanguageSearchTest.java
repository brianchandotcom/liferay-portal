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

package com.liferay.contacts.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vagner B.Castro
 */
@RunWith(Arquillian.class)
public class ContactMultiLanguageSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpContactFixture();
		setUpContactIndexerFixture();

		_defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
	}

	@Test
	public void testChineseValue() throws Exception {
		Locale locale = LocaleUtil.CHINA;

		setTestLocale(locale);

		String keywords = "你好";

		Contact contact = contactFixture.addContact(keywords);

		Map<String, String> map = HashMapBuilder.<String, String>put(
			_PREFIX, contact.getFullName()
		).build();

		assertFieldValues(_PREFIX, locale, map, keywords);
	}

	@Test
	public void testEnglishValue() throws Exception {
		Locale locale = LocaleUtil.US;

		setTestLocale(locale);

		Contact contact = contactFixture.addContact();

		String keywords = contact.getFullName();

		Map<String, String> map = HashMapBuilder.<String, String>put(
			_PREFIX, keywords
		).build();

		assertFieldValues(_PREFIX, locale, map, keywords);
	}

	@Test
	public void testJapaneseValue() throws Exception {
		Locale locale = LocaleUtil.JAPAN;

		setTestLocale(locale);

		String keywords = "東京";

		Contact contact = contactFixture.addContact(keywords);

		Map<String, String> map = HashMapBuilder.<String, String>put(
			_PREFIX, contact.getFullName()
		).build();

		assertFieldValues(_PREFIX, locale, map, keywords);
	}

	protected void assertFieldValues(
		String prefix, Locale locale, Map<String, String> map,
		String searchTerm) {

		Document document = contactIndexerFixture.searchOnlyOne(
			user.getUserId(), searchTerm, locale);

		FieldValuesAssert.assertFieldValues(map, prefix, document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		contactFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpContactFixture() throws Exception {
		contactFixture = new ContactFixture(contactLocalService);

		contactFixture.setUp();

		contactFixture.setUser(user);
		contactFixture.setGroup(group);

		_contacts = contactFixture.getContacts();
	}

	protected void setUpContactIndexerFixture() {
		contactIndexerFixture = new IndexerFixture<>(Contact.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();
		_users = userSearchFixture.getUsers();

		group = userSearchFixture.addGroup();

		user = userSearchFixture.addUser(RandomTestUtil.randomString(), group);
	}

	protected ContactFixture contactFixture;
	protected IndexerFixture<Contact> contactIndexerFixture;

	@Inject
	protected ContactLocalService contactLocalService;

	protected Group group;
	protected User user;
	protected UserSearchFixture userSearchFixture;

	private static final String _PREFIX = "fullName";

	@DeleteAfterTestRun
	private List<Contact> _contacts;

	private Locale _defaultLocale;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}