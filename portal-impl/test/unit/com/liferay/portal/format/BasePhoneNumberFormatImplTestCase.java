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

package com.liferay.portal.format;

import com.liferay.portal.kernel.format.PhoneNumberFormat;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public abstract class BasePhoneNumberFormatImplTestCase {

	@Test
	public void testInvalidPhoneNumbers() {
		PhoneNumberFormat phoneNumberFormat = getPhoneNumberFormat();

		String[] phoneNumbers = getInvalidPhoneNumbers();

		for (String phoneNumber : phoneNumbers) {
			Assert.assertFalse(
				phoneNumber, phoneNumberFormat.validate(phoneNumber));
		}
	}

	@Test
	public void testValidPhoneNumbers() {
		PhoneNumberFormat phoneNumberFormat = getPhoneNumberFormat();

		String[] phoneNumbers = getValidPhoneNumbers();

		for (String phoneNumber : phoneNumbers) {
			Assert.assertTrue(
				phoneNumber, phoneNumberFormat.validate(phoneNumber));
		}
	}

	protected abstract String[] getInvalidPhoneNumbers();

	protected abstract PhoneNumberFormat getPhoneNumberFormat();

	protected abstract String[] getValidPhoneNumbers();

}