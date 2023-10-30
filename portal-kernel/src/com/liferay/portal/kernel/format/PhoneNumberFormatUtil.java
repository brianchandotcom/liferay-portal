/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.format;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 * @author Peter Fellwock
 */
public class PhoneNumberFormatUtil {

	public static String format(String phoneNumber) {
		PhoneNumberFormat phoneNumberFormat = _phoneNumberFormatSnapshot.get();

		return phoneNumberFormat.format(phoneNumber);
	}

	public static String strip(String phoneNumber) {
		PhoneNumberFormat phoneNumberFormat = _phoneNumberFormatSnapshot.get();

		return phoneNumberFormat.strip(phoneNumber);
	}

	public static boolean validate(String phoneNumber) {
		PhoneNumberFormat phoneNumberFormat = _phoneNumberFormatSnapshot.get();

		return phoneNumberFormat.validate(phoneNumber);
	}

	private PhoneNumberFormatUtil() {
	}

	private static final Snapshot<PhoneNumberFormat>
		_phoneNumberFormatSnapshot = new Snapshot<>(
			PhoneNumberFormatUtil.class, PhoneNumberFormat.class, null, true);

}