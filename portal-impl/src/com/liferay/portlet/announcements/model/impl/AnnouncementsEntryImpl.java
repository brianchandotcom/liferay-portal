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

package com.liferay.portlet.announcements.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class AnnouncementsEntryImpl extends AnnouncementsEntryBaseImpl {

	@Override
	public long getGroupId() throws PortalException {
		long groupId = 0;

		long classPK = getClassPK();

		if (classPK > 0) {
			String className = getClassName();

			if (className.equals(Group.class.getName())) {
				Group group = GroupLocalServiceUtil.getGroup(classPK);

				groupId = group.getGroupId();
			}
			else if (className.equals(Organization.class.getName())) {
				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(classPK);

				groupId = organization.getGroupId();
			}
		}

		return groupId;
	}

}