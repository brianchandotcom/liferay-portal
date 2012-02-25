/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.model.impl.EmailAddressModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Wesley Gong
 */
public class OrderByComparatorFactoryImplTest extends BaseTestCase {

	public void testCollectionsSortMultipleColumnsAscending() throws Exception {
		EmailAddress emailAddress1 = newEmailAddress(
			newDate(0, 1, 2012), "abc@liferay.com");
		EmailAddress emailAddress2 = newEmailAddress(
			newDate(0, 2, 2012), "abc@liferay.com");

		List<EmailAddress> expectedList = new ArrayList<EmailAddress>();

		expectedList.add(emailAddress1);
		expectedList.add(emailAddress2);

		List<EmailAddress> actualList = new ArrayList<EmailAddress>();

		actualList.add(emailAddress2);
		actualList.add(emailAddress1);

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(
			EmailAddressModelImpl.TABLE_NAME, "address", false, "createDate",
			true);

		Collections.sort(actualList, obc);

		assertEquals(expectedList, actualList);
	}

	public void testCollectionsSortMultipleColumnsDescending()
		throws Exception {

		EmailAddress emailAddress1 = newEmailAddress(
			newDate(0, 1, 2012), "abc@liferay.com");
		EmailAddress emailAddress2 = newEmailAddress(
			newDate(0, 2, 2012), "abc@liferay.com");

		List<EmailAddress> expectedList = new ArrayList<EmailAddress>();

		expectedList.add(emailAddress2);
		expectedList.add(emailAddress1);

		List<EmailAddress> actualList = new ArrayList<EmailAddress>();

		actualList.add(emailAddress1);
		actualList.add(emailAddress2);

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(
			EmailAddressModelImpl.TABLE_NAME, "address", false, "createDate",
			false);

		Collections.sort(actualList, obc);

		assertEquals(expectedList, actualList);
	}

	public void testCollectionsSortSingleColumnAscending() throws Exception {
		EmailAddress emailAddress1 = newEmailAddress(
			newDate(0, 1, 2012), "abc@liferay.com");
		EmailAddress emailAddress2 = newEmailAddress(
			newDate(0, 2, 2012), "def@liferay.com");

		List<EmailAddress> expectedList = new ArrayList<EmailAddress>();

		expectedList.add(emailAddress1);
		expectedList.add(emailAddress2);

		List<EmailAddress> actualList = new ArrayList<EmailAddress>();

		actualList.add(emailAddress2);
		actualList.add(emailAddress1);

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(
			EmailAddressModelImpl.TABLE_NAME, "address", true);

		Collections.sort(actualList, obc);

		assertEquals(expectedList, actualList);
	}

	public void testCollectionsSortSingleColumnDescending() throws Exception {
		EmailAddress emailAddress1 = newEmailAddress(
			newDate(0, 1, 2012), "abc@liferay.com");
		EmailAddress emailAddress2 = newEmailAddress(
			newDate(0, 2, 2012), "def@liferay.com");

		List<EmailAddress> expectedList = new ArrayList<EmailAddress>();

		expectedList.add(emailAddress2);
		expectedList.add(emailAddress1);

		List<EmailAddress> actualList = new ArrayList<EmailAddress>();

		actualList.add(emailAddress1);
		actualList.add(emailAddress2);

		OrderByComparator obc = OrderByComparatorFactoryUtil.create(
			EmailAddressModelImpl.TABLE_NAME, "address", false);

		Collections.sort(actualList, obc);

		assertEquals(expectedList, actualList);
	}

	public void testGetOrderByMultipleColumns() throws Exception {
		OrderByComparator obc = OrderByComparatorFactoryUtil.create(
			EmailAddressModelImpl.TABLE_NAME, "address", true, "createDate",
			false);

		assertEquals(
			"EmailAddress.address ASC,EmailAddress.createDate DESC",
			obc.getOrderBy());

		obc = OrderByComparatorFactoryUtil.create(
			EmailAddressModelImpl.TABLE_NAME, "address", false, "createDate",
			true);

		assertEquals(
			"EmailAddress.address DESC,EmailAddress.createDate ASC",
			obc.getOrderBy());
	}

	public void testGetOrderBySingleColumn() throws Exception {
		OrderByComparator obc = OrderByComparatorFactoryUtil.create(
			EmailAddressModelImpl.TABLE_NAME, "address", true);

		assertEquals("EmailAddress.address ASC", obc.getOrderBy());

		obc = OrderByComparatorFactoryUtil.create(
			EmailAddressModelImpl.TABLE_NAME, "address", false);

		assertEquals("EmailAddress.address DESC", obc.getOrderBy());
	}

	public void testInvalidColumns() throws Exception {
		try {
			OrderByComparatorFactoryUtil.create(
				EmailAddressModelImpl.TABLE_NAME);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			OrderByComparatorFactoryUtil.create(
				EmailAddressModelImpl.TABLE_NAME, "address");

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			OrderByComparatorFactoryUtil.create(
				EmailAddressModelImpl.TABLE_NAME, "address", true,
				"createDate");

			fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	protected EmailAddress newEmailAddress(Date createDate, String address) {
		EmailAddress emailAddress = new EmailAddressImpl();

		emailAddress.setCreateDate(createDate);
		emailAddress.setAddress(address);

		return emailAddress;
	}

}