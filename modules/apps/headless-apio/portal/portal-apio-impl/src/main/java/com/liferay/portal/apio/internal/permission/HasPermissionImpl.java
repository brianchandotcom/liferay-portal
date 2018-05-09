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

package com.liferay.portal.apio.internal.permission;

import static com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory.openSingleValueMap;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.journal.model.JournalFolder;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;

import java.util.function.BiFunction;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class HasPermissionImpl implements HasPermission {

	@Override
	public BiFunction<Credentials, Long, Boolean> forAddingEntries(
		Class<?> clazz) {

		return (credentials, groupId) -> {
			Try<PermissionChecker> permissionCheckerTry =
				getPermissionCheckerTry(credentials);

			return Try.success(
				clazz.getName()
			).map(
				_portletResourcePermissions::getService
			).flatMap(
				portletResourcePermission -> permissionCheckerTry.map(
					permissionChecker -> portletResourcePermission.contains(
						permissionChecker, groupId, ActionKeys.ADD_ENTRY))
			).orElse(
				false
			);
		};
	}

	@Override
	public Boolean forAddingRootJournalArticle(
		Credentials credentials, Long groupId) {

		Try<PermissionChecker> permissionCheckerTry = getPermissionCheckerTry(
			credentials);

		return Try.success(
			JournalFolder.class.getName()
		).map(
			_modelResourcePermissions::getService
		).flatMap(
			modelResourcePermission -> permissionCheckerTry.map(
				permissionChecker -> modelResourcePermission.contains(
					permissionChecker, 0, ActionKeys.ADD_ARTICLE))
		).orElse(
			false
		);
	}

	@Override
	public Boolean forAddingUsers(Credentials credentials) {
		Try<PermissionChecker> permissionCheckerTry = getPermissionCheckerTry(
			credentials);

		return permissionCheckerTry.map(
			permissionChecker -> PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.ADD_USER)
		).orElse(
			false
		);
	}

	@Override
	public BiFunction<Credentials, Long, Boolean> forDeleting(
		Class<? extends ClassedModel> clazz) {

		return (credentials, identifier) -> {
			Try<PermissionChecker> permissionCheckerTry =
				getPermissionCheckerTry(credentials);

			return Try.success(
				clazz.getName()
			).map(
				_modelResourcePermissions::getService
			).flatMap(
				modelResourcePermission -> permissionCheckerTry.map(
					permissionChecker -> modelResourcePermission.contains(
						permissionChecker, identifier, ActionKeys.DELETE))
			).orElse(
				false
			);
		};
	}

	@Override
	public Boolean forDeletingLayouts(Credentials credentials, Long plid) {
		Try<PermissionChecker> permissionCheckerTry = getPermissionCheckerTry(
			credentials);

		return permissionCheckerTry.map(
			permissionChecker -> LayoutPermissionUtil.contains(
				permissionChecker, plid, ActionKeys.DELETE)
		).orElse(
			false
		);
	}

	@Override
	public BiFunction<Credentials, Long, Boolean> forUpdating(
		Class<? extends ClassedModel> clazz) {

		return (credentials, identifier) -> {
			Try<PermissionChecker> permissionCheckerTry =
				getPermissionCheckerTry(credentials);

			return Try.success(
				clazz.getName()
			).map(
				_modelResourcePermissions::getService
			).flatMap(
				modelResourcePermission -> permissionCheckerTry.map(
					permissionChecker -> modelResourcePermission.contains(
						permissionChecker, identifier, ActionKeys.UPDATE))
			).orElse(
				false
			);
		};
	}

	@Override
	public Try<PermissionChecker> getPermissionCheckerTry(
		Credentials credentials) {

		return Try.success(
			credentials.get()
		).map(
			Long::valueOf
		).map(
			_userService::getUserById
		).map(
			PermissionCheckerFactoryUtil::create
		).recoverWith(
			__ -> Try.fromFallible(PermissionThreadLocal::getPermissionChecker)
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_modelResourcePermissions = openSingleValueMap(
			bundleContext, ModelResourcePermission.class, "model.class.name");
		_portletResourcePermissions = openSingleValueMap(
			bundleContext, PortletResourcePermission.class, "resource.name");
	}

	private ServiceTrackerMap<String, ModelResourcePermission>
		_modelResourcePermissions;
	private ServiceTrackerMap<String, PortletResourcePermission>
		_portletResourcePermissions;

	@Reference
	private UserService _userService;

}