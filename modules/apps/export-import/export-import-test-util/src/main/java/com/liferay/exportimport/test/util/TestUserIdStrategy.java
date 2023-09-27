/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util;

import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Máté Thurzó
 */
public class TestUserIdStrategy implements UserIdStrategy {

	@Override
	public long getUserId(String userUuid) {
		try {
			return TestPropsValues.getUserId();
		}
		catch (Exception exception) {
			return 0;
		}
	}

}