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

package com.liferay.message.boards.internal.service;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageService;
import com.liferay.message.boards.kernel.service.MBMessageServiceWrapper;
import com.liferay.message.boards.service.permission.MBDiscussionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class MBDiscussionMBMessageServiceWrapper
	extends MBMessageServiceWrapper {

	public MBDiscussionMBMessageServiceWrapper() {
		super(null);
	}

	public MBDiscussionMBMessageServiceWrapper(
		MBMessageService mbMessageService) {

		super(mbMessageService);
	}

	@Override
	public MBMessage addDiscussionMessage(
			long groupId, String className, long classPK, long threadId,
			long parentMessageId, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _getGuestOrUser();

		MBDiscussionPermission.check(
			_getPermissionChecker(), user.getCompanyId(),
			serviceContext.getScopeGroupId(), className, classPK,
			ActionKeys.ADD_DISCUSSION);

		return super.addDiscussionMessage(
			groupId, className, classPK, threadId, parentMessageId, subject,
			body, serviceContext);
	}

	@Override
	public void deleteDiscussionMessage(long messageId) throws PortalException {
		MBDiscussionPermission.check(
			_getPermissionChecker(), messageId, ActionKeys.DELETE_DISCUSSION);

		super.deleteDiscussionMessage(messageId);
	}

	@Override
	public MBMessage updateDiscussionMessage(
			String className, long classPK, long messageId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		MBDiscussionPermission.check(
			_getPermissionChecker(), messageId, ActionKeys.UPDATE_DISCUSSION);

		return super.updateDiscussionMessage(
			className, classPK, messageId, subject, body, serviceContext);
	}

	private User _getGuestOrUser() throws PortalException {
		try {
			return _getUser();
		}
		catch (PrincipalException pe) {
			try {
				return _userLocalService.getDefaultUser(
					CompanyThreadLocal.getCompanyId());
			}
			catch (Exception e) {
				throw pe;
			}
		}
	}

	private PermissionChecker _getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException("PermissionChecker not initialized");
		}

		return permissionChecker;
	}

	private User _getUser() throws PortalException {
		return _userLocalService.getUserById(_getUserId());
	}

	private long _getUserId() throws PrincipalException {
		String name = PrincipalThreadLocal.getName();

		if (Validator.isNull(name)) {
			throw new PrincipalException("Principal is null");
		}
		else {
			for (int i = 0; i < _ANONYMOUS_NAMES.length; i++) {
				if (StringUtil.equalsIgnoreCase(name, _ANONYMOUS_NAMES[i])) {
					throw new PrincipalException(
						"Principal cannot be " + _ANONYMOUS_NAMES[i]);
				}
			}
		}

		return GetterUtil.getLong(name);
	}

	private static final String[] _ANONYMOUS_NAMES = {
		BaseServiceImpl.JRUN_ANONYMOUS, BaseServiceImpl.ORACLE_ANONYMOUS,
		BaseServiceImpl.SUN_ANONYMOUS, BaseServiceImpl.WEBLOGIC_ANONYMOUS
	};

	@Reference
	private UserLocalService _userLocalService;

}