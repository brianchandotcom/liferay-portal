/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.announcements.model;

import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;

/**
 * @author Christopher Kian
 */
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {
			if (group.isSite()) {
				AnnouncementsEntryLocalServiceUtil.deleteEntries(
					group.getClassNameId(), group.getGroupId());
			}
			else {
				AnnouncementsEntryLocalServiceUtil.deleteEntries(
					group.getClassNameId(), group.getClassPK());

				if (group.isOrganization()) {
					AnnouncementsEntryLocalServiceUtil.deleteEntries(
						ClassNameLocalServiceUtil.getClassNameId(Group.class),
						group.getGroupId());
				}
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

}