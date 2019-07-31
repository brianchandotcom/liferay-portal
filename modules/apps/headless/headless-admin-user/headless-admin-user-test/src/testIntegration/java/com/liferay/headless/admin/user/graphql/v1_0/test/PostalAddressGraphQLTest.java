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
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
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
public class PostalAddressGraphQLTest extends BasePostalAddressGraphQLTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

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
	protected PostalAddress testPostalAddress_addPostalAddress()
		throws Exception {

		PostalAddress postalAddress = randomPostalAddress();

		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(
			ListTypeConstants.CONTACT_ADDRESS);

		ListType listType = listTypes.get(0);

		return _toPostalAddress(
			AddressLocalServiceUtil.addAddress(
				_user.getUserId(), Contact.class.getName(),
				_user.getContactId(), postalAddress.getStreetAddressLine1(),
				postalAddress.getStreetAddressLine2(),
				postalAddress.getStreetAddressLine3(),
				postalAddress.getAddressLocality(),
				postalAddress.getPostalCode(), 0, 0, listType.getListTypeId(),
				false, postalAddress.getPrimary(), new ServiceContext()));
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
	private User _user;

}