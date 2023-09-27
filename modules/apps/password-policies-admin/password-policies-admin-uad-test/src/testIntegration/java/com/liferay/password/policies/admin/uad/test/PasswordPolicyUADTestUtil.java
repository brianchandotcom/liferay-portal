/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.password.policies.admin.uad.test;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyUADTestUtil {

	public static PasswordPolicy addPasswordPolicy(long userId)
		throws Exception {

		return PasswordPolicyTestUtil.addPasswordPolicy(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), userId));
	}

}