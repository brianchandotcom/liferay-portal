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

package com.liferay.mail.service.persistence;

import com.liferay.mail.NoSuchCyrusUserException;
import com.liferay.mail.model.CyrusUser;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Brian Wing Shun Chan
 */
public class CyrusUserUtil {

	public static CyrusUser findByPrimaryKey(long userId)
		throws NoSuchCyrusUserException, SystemException {

		return getPersistence().findByPrimaryKey(userId);
	}

	public static CyrusUserPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CyrusUserPersistence)PortalBeanLocatorUtil.locate(
				CyrusUserPersistence.class.getName());
		}

		return _persistence;
	}

	public static void remove(long userId)
		throws NoSuchCyrusUserException, SystemException {

		getPersistence().remove(userId);
	}

	public static void update(CyrusUser user) {
		getPersistence().update(user);
	}

	public void setPersistence(CyrusUserPersistence persistence) {
		_persistence = persistence;
	}

	private static CyrusUserPersistence _persistence;

}