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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class UserGroupImpl extends UserGroupBaseImpl {

	@Override
	public Group getGroup() throws PortalException {
		return GroupLocalServiceUtil.getUserGroupGroup(
			getCompanyId(), getUserGroupId());
	}

	@Override
	public long getGroupId() throws PortalException {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public int getLayoutsPageCount() throws PortalException {
		Group group = getGroup();

		return group.getLayoutsPageCount();
	}

	@Override
	public boolean hasLayouts() throws PortalException {
		if (getLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

}