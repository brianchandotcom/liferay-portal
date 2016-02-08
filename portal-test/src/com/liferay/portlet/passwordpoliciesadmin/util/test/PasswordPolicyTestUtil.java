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