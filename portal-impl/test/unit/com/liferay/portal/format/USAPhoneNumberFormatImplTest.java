/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.format;

import com.liferay.portal.kernel.format.PhoneNumberFormat;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class USAPhoneNumberFormatImplTest
	extends BasePhoneNumberFormatImplTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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