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

		announcementsFlagPersistence.update(flag);

		return flag;
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