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

package com.liferay.portal.kernel.format.bundle.phonenumberformatutil;

import com.liferay.portal.kernel.format.PhoneNumberFormat;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestPhoneNumberFormat implements PhoneNumberFormat {

	public static final String FORMATTED = "123-456-7890";

	public static final String UNFORMATTED = "1234567890";

	@Override
	public String format(String phoneNumber) {
		if (phoneNumber.equals(UNFORMATTED)) {
			return FORMATTED;
		}

		return "";
	}

	@Override
	public String strip(String phoneNumber) {
		if (phoneNumber.equals(FORMATTED)) {
			return UNFORMATTED;
		}

		return "";
	}

	@Override
	public boolean validate(String phoneNumber) {
		if (phoneNumber.equals(FORMATTED)) {
			return true;
		}

		return false;
	}

}