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
public class InternationalPhoneNumberFormatImplTest
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
		return new InternationalPhoneNumberFormatImpl();
	}

	@Override
	public String[] getValidPhoneNumbers() {
		/*return new String[] {
			"+34 91 733 63 43", "+55 81 3033 1405", "+49 (0) 6196 773 0680",
			"+36 (1) 786 4575", "+86 (0411) 8812-0855", "1-123-456-7890",
			"1.123.456.7890"
		};*/

		return new String[0];
	}

}