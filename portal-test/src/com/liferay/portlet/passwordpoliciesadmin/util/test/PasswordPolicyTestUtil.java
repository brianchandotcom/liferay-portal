/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.passwordpoliciesadmin.util.test;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Daniela Zapata Riesco
 */
public class PasswordPolicyTestUtil {

	public static PasswordPolicy addPasswordPolicy(
			ServiceContext serviceContext)
		throws Exception {

		return addPasswordPolicy(serviceContext, false);
	}

	public static PasswordPolicy addPasswordPolicy(
			ServiceContext serviceContext, boolean defaultPolicy)
		throws Exception {

		return PasswordPolicyLocalServiceUtil.addPasswordPolicy(
			serviceContext.getUserId(), defaultPolicy,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt(), "(?=.{4})(?:[a-zA-Z0-9]*)",
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), serviceContext);
	}

}