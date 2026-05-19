/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.PropsValues;

/**
 * @author Rubén Pulido
 */
public class BasicAuthorizationTestUtil {

	public static String getBasicAuthorization() throws Exception {
		if (_basicAuthorization != null) {
			return _basicAuthorization;
		}

		User user = UserTestUtil.getAdminUser(TestPropsValues.getCompanyId());

		String credentials =
			user.getEmailAddress() + StringPool.COLON +
				PropsValues.DEFAULT_ADMIN_PASSWORD;

		_basicAuthorization = "Basic " + Base64.encode(credentials.getBytes());

		return _basicAuthorization;
	}

	private static String _basicAuthorization;

}