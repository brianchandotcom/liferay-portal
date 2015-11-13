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

package com.liferay.social.networking.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.social.networking.model.MeetupsRegistration;
import com.liferay.social.networking.service.base.MeetupsRegistrationLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MeetupsRegistrationLocalServiceImpl
	extends MeetupsRegistrationLocalServiceBaseImpl {

	@Override
	public MeetupsRegistration getMeetupsRegistration(
			long userId, long meetupsEntryId)
		throws PortalException {

		return meetupsRegistrationPersistence.findByU_ME(
			userId, meetupsEntryId);
	}

	@Override
	public List<MeetupsRegistration> getMeetupsRegistrations(
		long meetupsEntryId, int status, int start, int end) {

		return meetupsRegistrationPersistence.findByME_S(
			meetupsEntryId, status, start, end);
	}

	@Override
	public int getMeetupsRegistrationsCount(long meetupsEntryId, int status) {
		return meetupsRegistrationPersistence.countByME_S(
			meetupsEntryId, status);
	}

	@Override
	public MeetupsRegistration updateMeetupsRegistration(
			long userId, long meetupsEntryId, int status, String comments)
		throws PortalException {

		User user = userLocalService.getUserById(userId);

		MeetupsRegistration meetupsRegistration =
			meetupsRegistrationPersistence.fetchByU_ME(userId, meetupsEntryId);

		if (meetupsRegistration == null) {
			long meetupsRegistrationId = counterLocalService.increment();

			meetupsRegistration = meetupsRegistrationPersistence.create(
				meetupsRegistrationId);

			meetupsRegistration.setCompanyId(user.getCompanyId());
			meetupsRegistration.setUserId(user.getUserId());
			meetupsRegistration.setUserName(user.getFullName());
			meetupsRegistration.setMeetupsEntryId(meetupsEntryId);
		}

		meetupsRegistration.setStatus(status);
		meetupsRegistration.setComments(comments);

		meetupsRegistrationPersistence.update(meetupsRegistration);

		return meetupsRegistration;
	}

}