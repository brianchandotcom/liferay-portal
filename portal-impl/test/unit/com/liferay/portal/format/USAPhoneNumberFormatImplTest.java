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

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class USAPhoneNumberFormatImplTest
	extends BasePhoneNumberFormatImplTestCase {

	@Override
	public String[] getInvalidPhoneNumbers() {
		return new String[0];
	}

	@Override
	public PhoneNumberFormat getPhoneNumberFormat() {
		return new USAPhoneNumberFormatImpl();
	}

	@Override
	public String[] getValidPhoneNumbers() {
		return new String[] {
			"1234567890", "123-456-7890", "123.456.7890", "123 456 7890",
			"(123) 456 7890", "(012) 345-6789", "(123) 456-7890", "012-3456",
			"+1 (123) 456-7890", "1-123-456-7890", "1.123.456.7890"
		};
	}

}