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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.util.BaseServiceHelperUtil;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseServiceImpl implements BaseService {

	public static final String[] ANONYMOUS_NAMES = {
		BaseServiceImpl.JRUN_ANONYMOUS, BaseServiceImpl.ORACLE_ANONYMOUS,
		BaseServiceImpl.SUN_ANONYMOUS, BaseServiceImpl.WEBLOGIC_ANONYMOUS
	};

	public static final String JRUN_ANONYMOUS = "anonymous-guest";

	public static final String ORACLE_ANONYMOUS = "guest";

	public static final String SUN_ANONYMOUS = "ANONYMOUS";

	public static final String WEBLOGIC_ANONYMOUS = "<anonymous>";

	public User getGuestOrUser() throws PortalException {
		return BaseServiceHelperUtil.getGuestOrUser(getUser());
	}

	public long getGuestOrUserId() throws PrincipalException {
		return BaseServiceHelperUtil.getGuestOrUserId();
	}

	public PermissionChecker getPermissionChecker() throws PrincipalException {
		return BaseServiceHelperUtil.getPermissionChecker();
	}

	public User getUser() throws PortalException {
		return BaseServiceHelperUtil.getUser(getUserId());
	}

	public long getUserId() throws PrincipalException {
		return BaseServiceHelperUtil.getUserId();
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

}