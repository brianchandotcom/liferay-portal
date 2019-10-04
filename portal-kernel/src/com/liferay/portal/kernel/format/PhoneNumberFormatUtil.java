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

package com.liferay.portal.kernel.format;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 * @author Peter Fellwock
 */
public class PhoneNumberFormatUtil {

	public static String format(String phoneNumber) {
		return getPhoneNumberFormat().format(phoneNumber);
	}

	public static PhoneNumberFormat getPhoneNumberFormat() {
		return _phoneNumberFormatUtil._serviceTracker.getService();
	}

	public static String strip(String phoneNumber) {
		return getPhoneNumberFormat().strip(phoneNumber);
	}

	public static boolean validate(String phoneNumber) {
		return getPhoneNumberFormat().validate(phoneNumber);
	}

	private PhoneNumberFormatUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(PhoneNumberFormat.class);

		_serviceTracker.open();
	}

	private static final PhoneNumberFormatUtil _phoneNumberFormatUtil =
		new PhoneNumberFormatUtil();

	private final ServiceTracker<PhoneNumberFormat, PhoneNumberFormat>
		_serviceTracker;

}