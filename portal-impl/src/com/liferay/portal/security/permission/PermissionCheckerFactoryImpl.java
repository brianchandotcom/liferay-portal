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

package com.liferay.portal.security.permission;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;
import com.liferay.portal.kernel.security.permission.wrapper.PermissionCheckerWrapperFactory;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PermissionCheckerFactoryImpl implements PermissionCheckerFactory {

	public PermissionCheckerFactoryImpl() throws Exception {
		Class<PermissionChecker> clazz =
			(Class<PermissionChecker>)Class.forName(
				PropsValues.PERMISSIONS_CHECKER);

		_permissionChecker = clazz.newInstance();
	}

	public void afterPropertiesSet() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_permissionCheckerWrapperFactories = ServiceTrackerListFactory.open(
			bundleContext, PermissionCheckerWrapperFactory.class);
		_roleContributors = ServiceTrackerListFactory.open(
			bundleContext, RoleContributor.class);
	}

	@Override
	public PermissionChecker create(User user) {
		PermissionChecker permissionChecker = _permissionChecker.clone();

		List<RoleContributor> roleContributors = new ArrayList<>();

		_roleContributors.forEach(roleContributors::add);

		permissionChecker.init(
			user, roleContributors.toArray(new RoleContributor[0]));

		permissionChecker = new StagingPermissionChecker(permissionChecker);

		for (PermissionCheckerWrapperFactory permissionCheckerWrapperFactory :
				_permissionCheckerWrapperFactories) {

			permissionChecker =
				permissionCheckerWrapperFactory.wrapPermissionChecker(
					permissionChecker);
		}

		return permissionChecker;
	}

	public void destroy() {
		_permissionCheckerWrapperFactories.close();
		_roleContributors.close();
	}

	private final PermissionChecker _permissionChecker;
	private ServiceTrackerList<PermissionCheckerWrapperFactory>
		_permissionCheckerWrapperFactories;
	private ServiceTrackerList<RoleContributor> _roleContributors;

}