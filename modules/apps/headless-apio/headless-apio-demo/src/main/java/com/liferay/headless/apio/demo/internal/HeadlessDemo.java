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

package com.liferay.headless.apio.demo.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class HeadlessDemo extends BasePortalInstanceLifecycleListener {

	public static final String SITE_NAME = "Sports Magazine";

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try {
			Role role = _roleLocalService.getRole(
				company.getCompanyId(), RoleConstants.ADMINISTRATOR);

			List<User> users = _userLocalService.getRoleUsers(
				role.getRoleId(), 0, 1);

			User user = users.get(0);

			_group = _createDemoGroup(company, user);
		}
		catch (Exception e) {
			_log.error("Error initializing data ", e);
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		if (_group != null) {
			_groupLocalService.deleteGroup(_group);
		}
	}

	private Group _createDemoGroup(Company company, User user)
		throws Exception {

		return _groupLocalService.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			Collections.singletonMap(LocaleUtil.US, SITE_NAME),
			Collections.singletonMap(LocaleUtil.US, SITE_NAME),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(SITE_NAME),
			true, true,
			_getServiceContext(
				company,
				_groupLocalService.getGroup(
					company.getCompanyId(), GroupConstants.GUEST),
				user));
	}

	private ServiceContext _getServiceContext(
		Company company, Group group, User user) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(company.getCompanyId());
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(HeadlessDemo.class);

	private Group _group;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}