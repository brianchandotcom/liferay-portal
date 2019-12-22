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
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
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
public class PostalAddressResourceTest
	extends BasePostalAddressResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_organization = OrganizationTestUtil.addOrganization();
		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"postalCode", "primary", "streetAddressLine1"};
	}

	@Override
	protected PostalAddress randomPostalAddress() {
		return new PostalAddress() {
			{
				addressLocality = RandomTestUtil.randomString();
				postalCode = RandomTestUtil.randomString();
				primary = false;
				streetAddressLine1 = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected PostalAddress
			testGetOrganizationPostalAddressesPage_addPostalAddress(
				String organizationId, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, _organization.getModelClassName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_ADDRESS);
	}

	@Override
	protected String
		testGetOrganizationPostalAddressesPage_getOrganizationId() {

		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected PostalAddress testGetPostalAddress_addPostalAddress()
		throws Exception {

		return _addPostalAddress(
			randomPostalAddress(), Contact.class.getName(),
			_user.getContactId(), ListTypeConstants.CONTACT_ADDRESS);
	}

	@Override
	protected PostalAddress
			testGetUserAccountPostalAddressesPage_addPostalAddress(
				Long userAccountId, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_ADDRESS);
	}

	@Override
	protected Long testGetUserAccountPostalAddressesPage_getUserAccountId() {
		return _user.getUserId();
	}

	@Override
	protected PostalAddress testGraphQLPostalAddress_addPostalAddress()
		throws Exception {

		return testGetPostalAddress_addPostalAddress();
	}

	private PostalAddress _addPostalAddress(
			PostalAddress postalAddress, String className, long classPK,
			String listTypeId)
		throws Exception {

		return _toPostalAddress(
			AddressLocalServiceUtil.addAddress(
				_user.getUserId(), className, classPK,
				postalAddress.getStreetAddressLine1(),
				postalAddress.getStreetAddressLine2(),
				postalAddress.getStreetAddressLine3(),
				postalAddress.getAddressLocality(),
				postalAddress.getPostalCode(), 0, 0, _getListTypeId(listTypeId),
				false, postalAddress.getPrimary(), new ServiceContext()));
	}

	private long _getListTypeId(String listTypeId) {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

	private PostalAddress _toPostalAddress(Address address) {
		return new PostalAddress() {
			{
				addressLocality = address.getCity();
				id = address.getAddressId();
				postalCode = address.getZip();
				primary = address.isPrimary();
				streetAddressLine1 = address.getStreet1();
			}
		};
	}

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _user;

}