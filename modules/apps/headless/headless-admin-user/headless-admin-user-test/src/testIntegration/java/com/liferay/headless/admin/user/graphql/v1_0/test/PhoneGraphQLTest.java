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

package com.liferay.headless.admin.user.graphql.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeLocalServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class PhoneGraphQLTest extends BasePhoneGraphQLTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"extension", "phoneNumber", "primary"};
	}

	@Override
	protected Phone randomPhone() {
		return new Phone() {
			{
				extension = String.valueOf(RandomTestUtil.randomInt());
				phoneNumber = RandomTestUtil.randomString();
				primary = false;
			}
		};
	}

	@Override
	protected Phone testPhone_addPhone() throws Exception {
		Phone phone = randomPhone();

		List<ListType> listTypes = ListTypeLocalServiceUtil.getListTypes(
			ListTypeConstants.CONTACT_PHONE);

		ListType listType = listTypes.get(0);

		return _toPhone(
			PhoneLocalServiceUtil.addPhone(
				_user.getUserId(), Contact.class.getName(),
				_user.getContactId(), phone.getPhoneNumber(),
				phone.getExtension(), listType.getListTypeId(),
				phone.getPrimary(), new ServiceContext()));
	}

	private Phone _toPhone(com.liferay.portal.kernel.model.Phone phone) {
		return new Phone() {
			{
				extension = phone.getExtension();
				id = phone.getPhoneId();
				phoneNumber = phone.getNumber();
				primary = phone.isPrimary();
			}
		};
	}

	@DeleteAfterTestRun
	private User _user;

}