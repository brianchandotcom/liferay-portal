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
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portlet.announcements.service.base.AnnouncementsFlagServiceBaseImpl;

/**
 * @author Thiago Moreira
 * @author Raymond Augé
 */
public class AnnouncementsFlagServiceImpl
	extends AnnouncementsFlagServiceBaseImpl {

	@Override
	public void addFlag(long entryId, int value) throws PortalException {
		announcementsFlagLocalService.addFlag(getUserId(), entryId, value);
	}

	@Override
	public void deleteFlag(long flagId) throws PortalException {
		AnnouncementsFlag flag = announcementsFlagPersistence.findByPrimaryKey(
			flagId);

		if (flag.getUserId() != getUserId()) {
			throw new PrincipalException();
		}

		announcementsFlagLocalService.deleteFlag(flagId);
	}

	@Override
	public AnnouncementsFlag getFlag(long entryId, int value)
		throws PortalException {

		return announcementsFlagLocalService.getFlag(
			getUserId(), entryId, value);
	}

}