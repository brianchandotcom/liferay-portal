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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lucas Marques de Paula
 */
@RunWith(Arquillian.class)
public class ContactIndexerIndexedFieldsTest {

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

		setUpIndexedFieldsFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		Locale locale = LocaleUtil.US;

		contactFixture.updateDisplaySettings(locale);

		Contact contact = contactFixture.addContact();

		String searchTerm = contact.getFullName();

		Document document = contactIndexerFixture.searchOnlyOne(
			user.getUserId(), searchTerm, locale);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			expectedFieldValues(contact), document, searchTerm);
	}

	protected Map<String, String> expectedFieldValues(Contact contact)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		indexedFieldsFixture.populateUID(
			contact.getModelClassName(), contact.getContactId(), map);
		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, contact.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, contact.getModifiedDate(), map);

		map.put(Field.CLASS_NAME_ID, String.valueOf(contact.getClassNameId()));
		map.put(Field.CLASS_PK, String.valueOf(contact.getClassPK()));
		map.put(Field.COMPANY_ID, String.valueOf(contact.getCompanyId()));
		map.put(Field.ENTRY_CLASS_NAME, contact.getModelClassName());
		map.put(Field.ENTRY_CLASS_PK, String.valueOf(contact.getPrimaryKey()));
		map.put(Field.USER_ID, String.valueOf(contact.getUserId()));
		map.put(Field.USER_NAME, contact.getFullName());
		map.put("emailAddress", contact.getEmailAddress());
		map.put("firstName", contact.getFirstName());
		map.put(
			"firstName_sortable",
			StringUtil.toLowerCase(contact.getFirstName()));
		map.put("fullName", contact.getFullName());
		map.put("jobTitle", contact.getJobTitle());
		map.put(
			"jobTitle_sortable", StringUtil.toLowerCase(contact.getJobTitle()));
		map.put("lastName", contact.getLastName());
		map.put(
			"lastName_sortable", StringUtil.toLowerCase(contact.getLastName()));
		map.put("middleName", contact.getMiddleName());

		return map;
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

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
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
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected User user;
	protected UserSearchFixture userSearchFixture;

	@DeleteAfterTestRun
	private List<Contact> _contacts;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}