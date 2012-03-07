/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class VerifyCounter extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {

		// Resource

		long latestResourceId = ResourceLocalServiceUtil.getLatestResourceId();

		long counterResourceId = CounterLocalServiceUtil.increment(
			Resource.class.getName());

		if (latestResourceId > (counterResourceId - 1)) {
			CounterLocalServiceUtil.reset(
				Resource.class.getName(), latestResourceId);
		}

		// Permission

		long latestPermissionId =
			PermissionLocalServiceUtil.getLatestPermissionId();

		long counterPermissionId = CounterLocalServiceUtil.increment(
			Permission.class.getName());

		if (latestPermissionId > (counterPermissionId - 1)) {
			CounterLocalServiceUtil.reset(
				Permission.class.getName(), latestPermissionId);
		}
	}

}