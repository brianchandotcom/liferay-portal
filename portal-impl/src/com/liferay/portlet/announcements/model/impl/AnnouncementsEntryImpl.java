/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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