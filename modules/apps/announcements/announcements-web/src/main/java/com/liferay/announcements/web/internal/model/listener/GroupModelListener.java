/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.model.listener;

import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christopher Kian
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {
			if (group.isSite()) {
				_announcementsEntryLocalService.deleteEntries(
					group.getClassNameId(), group.getGroupId());
			}
			else {
				_announcementsEntryLocalService.deleteEntries(
					group.getClassNameId(), group.getClassPK());

				if (group.isOrganization()) {
					_announcementsEntryLocalService.deleteEntries(
						_classNameLocalService.getClassNameId(Group.class),
						group.getGroupId());
				}
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}