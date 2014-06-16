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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntry;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.base.SystemEventLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SystemEventLocalServiceImpl
	extends SystemEventLocalServiceBaseImpl {

	@Override
	public SystemEvent addSystemEvent(
			long userId, long groupId, String className, long classPK,
			String classUuid, String referrerClassName, int type,
			String extraData)
		throws PortalException {

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
		else if (groupId > 0) {
			Group group = groupPersistence.findByPrimaryKey(groupId);

			companyId = group.getCompanyId();
		}

		return addSystemEvent(
			userId, companyId, groupId, className, classPK, classUuid,
			referrerClassName, type, extraData, userName);
	}

	@Override
	public SystemEvent addSystemEvent(
			long companyId, String className, long classPK, String classUuid,
			String referrerClassName, int type, String extraData)
		throws PortalException {

		return addSystemEvent(
			0, companyId, 0, className, classPK, classUuid, referrerClassName,
			type, extraData, StringPool.BLANK);
	}

	@Override
	public void deleteSystemEvents(long groupId) {
		systemEventPersistence.removeByGroupId(groupId);
	}

	@Override
	public void deleteSystemEvents(long groupId, long systemEventSetKey) {
		systemEventPersistence.removeByG_S(groupId, systemEventSetKey);
	}

	@Override
	public SystemEvent fetchSystemEvent(
		long groupId, long classNameId, long classPK, int type) {

		return systemEventPersistence.fetchByG_C_C_T_First(
			groupId, classNameId, classPK, type, null);
	}

	@Override
	public List<SystemEvent> getSystemEvents(
		long groupId, long classNameId, long classPK) {

		return systemEventPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<SystemEvent> getSystemEvents(
		long groupId, long classNameId, long classPK, int type) {

		return systemEventPersistence.findByG_C_C_T(
			groupId, classNameId, classPK, type);
	}

	protected SystemEvent addSystemEvent(
			long userId, long companyId, long groupId, String className,
			long classPK, String classUuid, String referrerClassName, int type,
			String extraData, String userName)
		throws PortalException {

		SystemEventHierarchyEntry systemEventHierarchyEntry =
			SystemEventHierarchyEntryThreadLocal.peek();

		int action = SystemEventConstants.ACTION_NONE;

		if (systemEventHierarchyEntry != null) {
			action = systemEventHierarchyEntry.getAction();

			if ((action == SystemEventConstants.ACTION_SKIP) &&
				!systemEventHierarchyEntry.hasTypedModel(
					className, classPK)) {

				return null;
			}
		}

		if (!CompanyThreadLocal.isDeleteInProcess()) {
			Company company = companyPersistence.findByPrimaryKey(companyId);

			Group companyGroup = company.getGroup();

			if (companyGroup.getGroupId() == groupId) {
				groupId = 0;
			}
		}

		if (Validator.isNotNull(referrerClassName) &&
			referrerClassName.equals(className)) {

			referrerClassName = null;
		}

		long systemEventId = 0;

		if ((systemEventHierarchyEntry != null) &&
			systemEventHierarchyEntry.hasTypedModel(className, classPK)) {

			systemEventId = systemEventHierarchyEntry.getSystemEventId();
		}
		else {
			systemEventId = counterLocalService.increment();
		}

		SystemEvent systemEvent = systemEventPersistence.create(systemEventId);

		systemEvent.setGroupId(groupId);
		systemEvent.setCompanyId(companyId);
		systemEvent.setUserId(userId);
		systemEvent.setUserName(userName);
		systemEvent.setCreateDate(new Date());
		systemEvent.setClassName(className);
		systemEvent.setClassPK(classPK);
		systemEvent.setClassUuid(classUuid);
		systemEvent.setReferrerClassName(referrerClassName);

		long parentSystemEventId = 0;

		if (action == SystemEventConstants.ACTION_HIERARCHY) {
			if (systemEventHierarchyEntry.hasTypedModel(className, classPK)) {
				parentSystemEventId =
					systemEventHierarchyEntry.getParentSystemEventId();
			}
			else {
				parentSystemEventId =
					systemEventHierarchyEntry.getSystemEventId();
			}
		}

		systemEvent.setParentSystemEventId(parentSystemEventId);

		long systemEventSetKey = 0;

		if ((action == SystemEventConstants.ACTION_GROUP) ||
			(action == SystemEventConstants.ACTION_HIERARCHY)) {

			systemEventSetKey =
				systemEventHierarchyEntry.getSystemEventSetKey();
		}
		else {
			systemEventSetKey = counterLocalService.increment();
		}

		systemEvent.setSystemEventSetKey(systemEventSetKey);

		systemEvent.setType(type);
		systemEvent.setExtraData(extraData);

		return systemEventPersistence.update(systemEvent);
	}

}