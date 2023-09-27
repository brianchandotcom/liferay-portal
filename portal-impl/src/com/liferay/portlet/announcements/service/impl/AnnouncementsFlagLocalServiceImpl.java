/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.announcements.service.impl;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.announcements.service.base.AnnouncementsFlagLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Thiago Moreira
 * @author Raymond Augé
 */
public class AnnouncementsFlagLocalServiceImpl
	extends AnnouncementsFlagLocalServiceBaseImpl {

	@Override
	public AnnouncementsFlag addFlag(long userId, long entryId, int value) {
		long flagId = counterLocalService.increment();

		AnnouncementsFlag flag = announcementsFlagPersistence.create(flagId);

		flag.setUserId(userId);
		flag.setCreateDate(new Date());
		flag.setEntryId(entryId);
		flag.setValue(value);

		return announcementsFlagPersistence.update(flag);
	}

	@Override
	public void deleteFlag(AnnouncementsFlag flag) {
		announcementsFlagPersistence.remove(flag);
	}

	@Override
	public void deleteFlag(long flagId) throws PortalException {
		AnnouncementsFlag flag = announcementsFlagPersistence.findByPrimaryKey(
			flagId);

		deleteFlag(flag);
	}

	@Override
	public void deleteFlags(long entryId) {
		List<AnnouncementsFlag> flags =
			announcementsFlagPersistence.findByEntryId(entryId);

		for (AnnouncementsFlag flag : flags) {
			deleteFlag(flag);
		}
	}

	@Override
	public AnnouncementsFlag getFlag(long userId, long entryId, int value)
		throws PortalException {

		return announcementsFlagPersistence.findByU_E_V(userId, entryId, value);
	}

}