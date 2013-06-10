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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.base.SystemEventLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SystemEventLocalServiceImpl
	extends SystemEventLocalServiceBaseImpl {

	@Override
	public void addSystemEvent(
			long userId, long groupId, long classNameId, long classPK,
			String classUuid, int type)
		throws PortalException, SystemException {

		addSystemEvent(
			userId, groupId, classNameId, classPK, classUuid, type, null);
	}

	@Override
	public void addSystemEvent(
			long userId, long groupId, long classNameId, long classPK,
			String classUuid, int type, String extraData)
		throws PortalException, SystemException {

		if (userId == 0) {
			userId = PrincipalThreadLocal.getUserId();
		}

		long companyId = 0;
		String userName = StringPool.BLANK;

		if (userId > 0) {
			User user = userPersistence.findByPrimaryKey(userId);

			companyId = user.getCompanyId();
			userName = user.getFullName();
		}

		if (companyId == 0) {
			if (groupId > 0) {
				Group group = groupPersistence.findByPrimaryKey(groupId);

				companyId = group.getCompanyId();
			}
			else {
				throw new IllegalArgumentException(
					"Unable to determine company");
			}
		}

		long systemEventId = counterLocalService.increment();

		SystemEvent systemEvent = systemEventPersistence.create(systemEventId);

		systemEvent.setGroupId(groupId);
		systemEvent.setCompanyId(companyId);
		systemEvent.setUserId(userId);
		systemEvent.setUserName(userName);
		systemEvent.setCreateDate(new Date());
		systemEvent.setClassNameId(classNameId);
		systemEvent.setClassPK(classPK);
		systemEvent.setClassUuid(classUuid);
		systemEvent.setType(type);
		systemEvent.setExtraData(extraData);

		systemEventPersistence.update(systemEvent);
	}

	@Override
	public void addSystemEvent(
			long groupId, String className, long classPK, String classUuid,
			int type)
		throws PortalException, SystemException {

		addSystemEvent(
			0, groupId, PortalUtil.getClassNameId(className), classPK,
			classUuid, type, null);
	}

	@Override
	public void deleteSystemEvents(long groupId) throws SystemException {
		systemEventPersistence.removeByGroupId(groupId);
	}

	@Override
	public SystemEvent fetchSystemEvent(
			long groupId, long classNameId, long classPK, int type)
		throws SystemException {

		return systemEventPersistence.fetchByG_C_C_T_First(
			groupId, classNameId, classPK, type, null);
	}

	@Override
	public List<SystemEvent> getSystemEvents(
			long groupId, long classNameId, long classPK)
		throws SystemException {

		return systemEventPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<SystemEvent> getSystemEvents(
			long groupId, long classNameId, long classPK, int type)
		throws SystemException {

		return systemEventPersistence.findByG_C_C_T(
			groupId, classNameId, classPK, type);
	}

}