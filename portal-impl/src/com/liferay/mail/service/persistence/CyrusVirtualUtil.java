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

import com.liferay.mail.NoSuchCyrusVirtualException;
import com.liferay.mail.model.CyrusVirtual;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CyrusVirtualUtil {

	public static CyrusVirtual findByPrimaryKey(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException {

		return getPersistence().findByPrimaryKey(emailAddress);
	}

	public static List<CyrusVirtual> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	public static CyrusVirtualPersistence getPersistence() {
		if (_persistence == null) {
			_persistence =
				(CyrusVirtualPersistence)PortalBeanLocatorUtil.locate(
					CyrusVirtualPersistence.class.getName());
		}

		return _persistence;
	}

	public static void remove(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException {

		getPersistence().remove(emailAddress);
	}

	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	public static void update(CyrusVirtual user) {
		getPersistence().update(user);
	}

	public void setPersistence(CyrusVirtualPersistence persistence) {
		_persistence = persistence;
	}

	private static CyrusVirtualPersistence _persistence;

}