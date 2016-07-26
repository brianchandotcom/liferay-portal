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

package com.liferay.portal.service;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.ContactBirthdayException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ContactLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Andrew Betts
 */
public class ContactLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = SystemException.class)
	public void testAddFutureBirthdayByContact() throws Exception {
		Date future = new Date(System.currentTimeMillis() + 100000);

		Contact contact = ContactLocalServiceUtil.createContact(
			CounterLocalServiceUtil.increment());

		contact.setBirthday(future);

		ContactLocalServiceUtil.addContact(contact);
	}

	@Test(expected = ContactBirthdayException.class)
	public void testAddFutureBirthdayByProperties() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		Calendar future = Calendar.getInstance();

		future.add(Calendar.YEAR, 1000);

		ContactLocalServiceUtil.addContact(
			user.getUserId(), Contact.class.getName(), user.getUserId(),
			user.getEmailAddress(), user.getFirstName(), user.getMiddleName(),
			user.getLastName(), 0, 0, user.getMale(),
			future.get(Calendar.MONTH), future.get(Calendar.DATE),
			future.get(Calendar.YEAR), "", "", "", "", "", user.getJobTitle());
	}

	@Test(expected = SystemException.class)
	public void testUpdateFutureBirthdayByContact() throws Exception {
		Date future = new Date(System.currentTimeMillis() + 100000);

		Contact contact = ContactLocalServiceUtil.createContact(
			CounterLocalServiceUtil.increment());

		contact.setBirthday(future);

		ContactLocalServiceUtil.updateContact(contact);
	}

	@Test(expected = ContactBirthdayException.class)
	public void testUpdateFutureBirthdayByProperties() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		Calendar future = Calendar.getInstance();

		future.add(Calendar.YEAR, 1000);

		ContactLocalServiceUtil.updateContact(
			user.getContactId(), user.getEmailAddress(), user.getFirstName(),
			user.getMiddleName(), user.getLastName(), 0, 0, user.getMale(),
			future.get(Calendar.MONTH), future.get(Calendar.DATE),
			future.get(Calendar.YEAR), "", "", "", "", "", user.getJobTitle());
	}

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}