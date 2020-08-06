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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class PhoneResourceTest extends BasePhoneResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_organization = OrganizationTestUtil.addOrganization();
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
	protected Phone testGetOrganizationPhonesPage_addPhone(
			String organizationId, Phone phone)
		throws Exception {

		return _addPhone(
			phone, _organization.getModelClassName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_PHONE);
	}

	@Override
	protected String testGetOrganizationPhonesPage_getOrganizationId() {
		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected Phone testGetPhone_addPhone() throws Exception {
		return _addPhone(
			randomPhone(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected Phone testGetUserAccountPhonesPage_addPhone(
			Long userAccountId, Phone phone)
		throws Exception {

		return _addPhone(
			phone, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected Long testGetUserAccountPhonesPage_getUserAccountId() {
		return _user.getUserId();
	}

	@Override
	protected Phone testGraphQLPhone_addPhone() throws Exception {
		return testGetPhone_addPhone();
	}

	private Phone _addPhone(
			Phone phone, String className, long classPK, String listTypeId)
		throws Exception {

		return _toPhone(
			PhoneLocalServiceUtil.addPhone(
				_user.getUserId(), className, classPK, phone.getPhoneNumber(),
				phone.getExtension(), _getListTypeId(listTypeId),
				phone.getPrimary(), new ServiceContext()));
	}

	private long _getListTypeId(String listTypeId) {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
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
	private Organization _organization;

	@DeleteAfterTestRun
	private User _user;

}