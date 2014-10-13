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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class DoAsUserThread extends Thread {

	public DoAsUserThread(long userId) {
		_userId = userId;
	}

	public boolean isSuccess() {
		return _success;
	}

	@Override
	public void run() {
		try {
			PrincipalThreadLocal.setName(_userId);

			User user = UserLocalServiceUtil.getUserById(_userId);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			doRun();

			_success = true;
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			PrincipalThreadLocal.setName(null);
			PermissionThreadLocal.setPermissionChecker(null);
		}
	}

	protected abstract void doRun() throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(DoAsUserThread.class);

	private boolean _success;
	private final long _userId;

}