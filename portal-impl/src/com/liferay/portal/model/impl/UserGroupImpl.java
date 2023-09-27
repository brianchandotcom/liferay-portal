/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

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
	public int getPrivateLayoutsPageCount() throws PortalException {
		Group group = getGroup();

		return group.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount() throws PortalException {
		Group group = getGroup();

		return group.getPublicLayoutsPageCount();
	}

	@Override
	public boolean hasPrivateLayouts() throws PortalException {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPublicLayouts() throws PortalException {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}

		return false;
	}

}