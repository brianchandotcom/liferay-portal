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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.security.Permission;

import javax.management.MBeanPermission;
import javax.management.MBeanTrustPermission;

/**
 * @author Raymond Augé
 */
public class MBeanChecker extends BaseChecker {

	public void afterPropertiesSet() {
	}

	public boolean implies(Permission permission) {
		String name = permission.getName();
		String actions = permission.getActions();

		if ((permission instanceof MBeanPermission) &&
			(actions.equals(MBEAN_PERMISSION_IS_INSTANCE_OF) ||
			 actions.equals(MBEAN_PERMISSION_REGISTER_MBEAN) ||
			 actions.equals(MBEAN_PERMISSION_UNREGISTER_MBEAN))) {

			return true;
		}
		else if ((permission instanceof MBeanTrustPermission) &&
				 name.equals(MBEAN_TRUST_PERMISSION_REGISTER)) {

			return true;
		}

		logSecurityException(
			_log, "Attempted to perform MBean operation " + permission);

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(MBeanChecker.class);

}