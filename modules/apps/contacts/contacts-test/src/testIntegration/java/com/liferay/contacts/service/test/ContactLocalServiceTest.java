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

package com.liferay.contacts.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.exception.ContactBirthdayException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Calendar;
import java.util.Date;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrew Betts
 */
@RunWith(Arquillian.class)
public class ContactLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = ContactBirthdayException.class)
	public void testCustomAddContactWithFutureBirthday() throws Exception {
		_user = UserTestUtil.addUser();

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.YEAR, 1000);

		_contactLocalService.addContact(
			_user.getUserId(), Contact.class.getName(), _user.getUserId(),
			_user.getEmailAddress(), _user.getFirstName(),
			_user.getMiddleName(), _user.getLastName(), 0, 0, _user.getMale(),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
			calendar.get(Calendar.YEAR), "", "", "", "", "",
			_user.getJobTitle());
	}

	@Test(expected = ContactBirthdayException.class)
	public void testCustomUpdateContactWithFutureBirthday() throws Exception {
		_user = UserTestUtil.addUser();

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.YEAR, 1000);

		_contactLocalService.updateContact(
			_user.getContactId(), _user.getEmailAddress(), _user.getFirstName(),
			_user.getMiddleName(), _user.getLastName(), 0, 0, _user.getMale(),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
			calendar.get(Calendar.YEAR), "", "", "", "", "",
			_user.getJobTitle());
	}

	@Test(expected = SystemException.class)
	public void testDefaultAddContactWithFutureBirthday() {
		Contact contact = _contactLocalService.createContact(
			_counterLocalService.increment());

		contact.setBirthday(new Date(System.currentTimeMillis() + 100000));

		_contactLocalService.addContact(contact);
	}

	@Test(expected = SystemException.class)
	public void testDefaultUpdateContactWithFutureBirthday() {
		Contact contact = _contactLocalService.createContact(
			_counterLocalService.increment());

		contact.setBirthday(new Date(System.currentTimeMillis() + 100000));

		_contactLocalService.updateContact(contact);
	}

	@Inject
	private ContactLocalService _contactLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

	@DeleteAfterTestRun
	private User _user;

}