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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class UserGroupImpl extends UserGroupBaseImpl {

	public UserGroupImpl() {
	}

	@Override
	public Group getGroup() throws PortalException, SystemException {
		return GroupLocalServiceUtil.getUserGroupGroup(
			getCompanyId(), getUserGroupId());
	}

	@Override
	public int getPrivateLayoutsPageCount()
		throws PortalException, SystemException {

		Group group = getGroup();

		return group.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount()
		throws PortalException, SystemException {

		Group group = getGroup();

		return group.getPublicLayoutsPageCount();
	}

	@Override
	public boolean hasPrivateLayouts() throws PortalException, SystemException {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasPublicLayouts() throws PortalException, SystemException {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

}