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
import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class EmailAddressResourceTest extends BaseEmailAddressResourceTestCase {

	@ClassRule
	@Rule
	public static final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_organization = OrganizationTestUtil.addOrganization();
		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"emailAddress", "primary"};
	}

	@Override
	protected EmailAddress randomEmailAddress() {
		return new EmailAddress() {
			{
				emailAddress = RandomTestUtil.randomString() + "@liferay.com";
				primary = false;
			}
		};
	}

	@Override
	protected EmailAddress testGetEmailAddress_addEmailAddress()
		throws Exception {

		return _addEmailAddress(
			randomEmailAddress(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected EmailAddress
			testGetOrganizationEmailAddressesPage_addEmailAddress(
				String organizationId, EmailAddress emailAddress)
		throws Exception {

		return _addEmailAddress(
			emailAddress, _organization.getModelClassName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_EMAIL_ADDRESS);
	}

	@Override
	protected String testGetOrganizationEmailAddressesPage_getOrganizationId() {
		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected EmailAddress testGetUserAccountEmailAddressesPage_addEmailAddress(
			Long userAccountId, EmailAddress emailAddress)
		throws Exception {

		return _addEmailAddress(
			emailAddress, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected Long testGetUserAccountEmailAddressesPage_getUserAccountId() {
		return _user.getUserId();
	}

	@Override
	protected EmailAddress testGraphQLEmailAddress_addEmailAddress()
		throws Exception {

		return testGetEmailAddress_addEmailAddress();
	}

	private EmailAddress _addEmailAddress(
			EmailAddress emailAddress, String className, long classPK,
			String listTypeId)
		throws Exception {

		return _toEmailAddress(
			EmailAddressLocalServiceUtil.addEmailAddress(
				_user.getUserId(), className, classPK,
				emailAddress.getEmailAddress(), _getListTypeId(listTypeId),
				emailAddress.getPrimary(), new ServiceContext()));
	}

	private long _getListTypeId(String listTypeId) {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

	private EmailAddress _toEmailAddress(
		com.liferay.portal.kernel.model.EmailAddress
			serviceBuilderEmailAddress) {

		return new EmailAddress() {
			{
				emailAddress = serviceBuilderEmailAddress.getAddress();
				id = serviceBuilderEmailAddress.getEmailAddressId();
				primary = serviceBuilderEmailAddress.isPrimary();
			}
		};
	}

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _user;

}