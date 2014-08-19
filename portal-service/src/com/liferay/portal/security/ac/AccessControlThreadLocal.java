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

package com.liferay.portal.security.ac;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlThreadLocal {

	public static boolean isRemoteAccess() {
		return _remoteAccess.get();
	}

	public static void setRemoteAccess(boolean remoteAccess) {
		_remoteAccess.set(remoteAccess);
	}

	private static final ThreadLocal<Boolean> _remoteAccess =
		new AutoResetThreadLocal<Boolean>(
			AutoResetThreadLocal.class + "._remoteAccess", false);

}