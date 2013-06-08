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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.base.SystemEventLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SystemEventLocalServiceImpl
	extends SystemEventLocalServiceBaseImpl {

	@Override
	public void addEvent(
			long userId, long groupId, int type, long classNameId,
			long classPK, String classUuid)
		throws PortalException, SystemException {

		addEvent(userId, groupId, type, classNameId, classPK, classUuid, null);
	}


	@Override
	public void addEvent(
			long userId, long groupId, int type, long classNameId,
			long classPK, String classUuid, String extraData)
		throws PortalException, SystemException {

		long companyId = 0;
		String userName;

		if (userId == 0) {
			userId = PrincipalThreadLocal.getUserId();
		}

		if (userId > 0) {
			User user = userPersistence.findByPrimaryKey(userId);

			companyId = user.getCompanyId();
			userName = user.getFullName();
		}
		else {
			userName = UserConstants.SYSTEM_USER_NAME;
		}

		if (companyId == 0) {
			if (groupId > 0) {
				Group group = groupLocalService.getGroup(groupId);

				companyId = group.getCompanyId();
			}
			else {
				throw new IllegalArgumentException(
					"No company id can be determined");
			}
		}

		long systemEventId = counterLocalService.increment();

		SystemEvent systemEvent = systemEventPersistence.create(systemEventId);

		systemEvent.setCompanyId(companyId);
		systemEvent.setCreateDate(new Date());
		systemEvent.setGroupId(groupId);
		systemEvent.setUserId(userId);
		systemEvent.setUserName(userName);

		systemEvent.setClassNameId(classNameId);
		systemEvent.setClassPK(classPK);
		systemEvent.setClassUuid(classUuid);
		systemEvent.setExtraData(extraData);
		systemEvent.setType(type);

		systemEventPersistence.update(systemEvent);
	}

	@Override
	public void deleteEvents(long groupId) throws SystemException {
		systemEventPersistence.removeByGroupId(groupId);
	}

	@Override
	public SystemEvent fetchEvent(
			long groupId, long classNameId, long classPK, int type)
		throws SystemException {
		return systemEventPersistence.fetchByG_C_C_T_First(
			groupId, classNameId, classPK, type, null);
	}

	@Override
	public List<SystemEvent> getEvents(
			long groupId, long classNameId, long classPK, int type)
		throws SystemException {

		return systemEventPersistence.findByG_C_C_T(
			groupId, classNameId, classPK, type);
	}

	@Override
	public List<SystemEvent> getEvents(
			long groupId, long classNameId, long classPK)
		throws SystemException {

		return systemEventPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

}