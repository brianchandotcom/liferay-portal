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

package com.liferay.message.boards.uad.anonymizer;

import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.uad.constants.MBUADConstants;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + MBUADConstants.CLASS_NAME_MB_THREAD}, service = UADAnonymizer.class)
public class MBThreadUADAnonymizer extends DynamicQueryUADAnonymizer<MBThread> {
	@Override
	public void autoAnonymize(MBThread mbThread, long userId, User anonymousUser)
		throws PortalException {
		if (mbThread.getUserId() == userId) {
			mbThread.setUserId(anonymousUser.getUserId());
			mbThread.setUserName(anonymousUser.getFullName());
		}

		if (mbThread.getRootMessageUserId() == userId) {
			mbThread.setRootMessageUserId(anonymousUser.getUserId());
		}

		if (mbThread.getLastPostByUserId() == userId) {
			mbThread.setLastPostByUserId(anonymousUser.getUserId());
		}

		if (mbThread.getStatusByUserId() == userId) {
			mbThread.setStatusByUserId(anonymousUser.getUserId());
			mbThread.setStatusByUserName(anonymousUser.getFullName());
		}

		_mbThreadLocalService.updateMBThread(mbThread);
	}

	@Override
	public void delete(MBThread mbThread) throws PortalException {
		_mbThreadLocalService.deleteThread(mbThread);
	}

	@Override
	public List<String> getNonanonymizableFieldNames() {
		return Arrays.asList();
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _mbThreadLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return MBUADConstants.USER_ID_FIELD_NAMES_MB_THREAD;
	}

	@Reference
	private MBThreadLocalService _mbThreadLocalService;
}