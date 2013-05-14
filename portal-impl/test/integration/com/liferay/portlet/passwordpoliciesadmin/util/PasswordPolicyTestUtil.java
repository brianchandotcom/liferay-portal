/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.passwordpoliciesadmin.util;

import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;

/**
 * @author Daniela Zapata Riesco
 */
public class PasswordPolicyTestUtil {

	public static PasswordPolicy addPasswordPolicy(
			ServiceContext serviceContext)
		throws Exception {

		return PasswordPolicyLocalServiceUtil.addPasswordPolicy(
			serviceContext.getUserId(), ServiceTestUtil.randomBoolean(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomBoolean(), ServiceTestUtil.randomBoolean(),
			ServiceTestUtil.randomLong(), ServiceTestUtil.randomBoolean(),
			ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.nextInt(), "(?=.{4})(?:[a-zA-Z0-9]*)",
			ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.randomBoolean(), ServiceTestUtil.randomLong(),
			ServiceTestUtil.randomLong(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextInt(),
			ServiceTestUtil.randomLong(), ServiceTestUtil.randomLong(),
			ServiceTestUtil.randomLong(), serviceContext);
	}

}