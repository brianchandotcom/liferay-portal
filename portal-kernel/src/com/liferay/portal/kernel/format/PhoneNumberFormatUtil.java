/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.format;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 * @author Peter Fellwock
 */
public class PhoneNumberFormatUtil {

	public static String format(String phoneNumber) {
		return _phoneNumberFormat.format(phoneNumber);
	}

	public static PhoneNumberFormat getPhoneNumberFormat() {
		return _phoneNumberFormat;
	}

	public static String strip(String phoneNumber) {
		return _phoneNumberFormat.strip(phoneNumber);
	}

	public static boolean validate(String phoneNumber) {
		return _phoneNumberFormat.validate(phoneNumber);
	}

	private PhoneNumberFormatUtil() {
	}

	private static volatile PhoneNumberFormat _phoneNumberFormat =
		ServiceProxyFactory.newServiceTrackedInstance(
			PhoneNumberFormat.class, PhoneNumberFormatUtil.class,
			"_phoneNumberFormat", false, true);

}