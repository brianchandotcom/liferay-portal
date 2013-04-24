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
import com.liferay.portal.kernel.util.Validator;

import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultAcceptChecker extends BaseChecker {

	public void afterPropertiesSet() {
	}

	public boolean implies(Permission permission) {
		if (_log.isDebugEnabled()) {
			Thread.dumpStack();
		}

		if (!_log.isInfoEnabled()) {
			return true;
		}

		Class<?> clazz = permission.getClass();
		String name = permission.getName();
		String actions = permission.getActions();

		if (Validator.isNotNull(actions)) {
			_log.info(
				"Allowing permission " + clazz.getName() + " to " + name +
					" on " + actions);
		}
		else {
			_log.info("Allowing permission " + clazz.getName() + " to " + name);
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(DefaultAcceptChecker.class);

}