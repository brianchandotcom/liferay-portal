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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
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
	public AnnouncementsFlag addFlag(long userId, long entryId, int value)
		throws SystemException {

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
	public void deleteFlag(AnnouncementsFlag flag) throws SystemException {
		announcementsFlagPersistence.remove(flag);
	}

	@Override
	public void deleteFlag(long flagId)
		throws PortalException, SystemException {

		AnnouncementsFlag flag = announcementsFlagPersistence.findByPrimaryKey(
			flagId);

		deleteFlag(flag);
	}

	@Override
	public void deleteFlags(long entryId) throws SystemException {
		List<AnnouncementsFlag> flags =
			announcementsFlagPersistence.findByEntryId(entryId);

		for (AnnouncementsFlag flag : flags) {
			deleteFlag(flag);
		}
	}

	@Override
	public AnnouncementsFlag getFlag(long userId, long entryId, int value)
		throws PortalException, SystemException {

		return announcementsFlagPersistence.findByU_E_V(userId, entryId, value);
	}

}