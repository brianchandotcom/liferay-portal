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

package com.liferay.depot.internal.instance.lifecycle;

import com.liferay.depot.internal.constants.DepotRolesConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.AbstractMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class DepotRolesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company)
		throws PortalException {

		for (String name : _DEPOT_ROLE_NAMES) {
			_getOrCreateRole(
				company.getCompanyId(), name, _getDescriptionMap(name));
		}
	}

	private String _getDescription(Locale locale, String name) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		if (Objects.equals(DepotRolesConstants.DEPOT_ADMINISTRATOR, name)) {
			return _language.get(
				resourceBundle,
				"repository-administrators-are-super-users-of-their-" +
					"repository-but-cannot-make-other-users-into-repository-" +
						"administrators");
		}
		else if (Objects.equals(DepotRolesConstants.DEPOT_MEMBER, name)) {
			return _language.get(
				resourceBundle,
				"all-users-who-belong-to-a-repository-have-this-role-within-" +
					"that-repository");
		}
		else if (Objects.equals(DepotRolesConstants.DEPOT_OWNER, name)) {
			return _language.get(
				resourceBundle,
				"repository-owners-are-super-users-of-their-repository-and-" +
					"can-assign-reposiitory-roles-to-users");
		}

		return StringPool.BLANK;
	}

	private Map<Locale, String> _getDescriptionMap(String name) {
		Set<Locale> availableLocales = _language.getAvailableLocales();

		Stream<Locale> stream = availableLocales.stream();

		return stream.map(
			locale -> new AbstractMap.SimpleEntry<>(
				locale, _getDescription(locale, name))
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);
	}

	private Role _getOrCreateRole(
			long companyId, String name, Map<Locale, String> descriptionMap)
		throws PortalException {

		try {
			Role role = _roleLocalService.getRole(companyId, name);

			if (!Objects.equals(descriptionMap, role.getDescriptionMap())) {
				role.setDescriptionMap(descriptionMap);

				return _roleLocalService.updateRole(role);
			}

			return role;
		}
		catch (NoSuchRoleException noSuchRoleException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchRoleException, noSuchRoleException);
			}

			boolean addResource = PermissionThreadLocal.isAddResource();

			try {
				PermissionThreadLocal.setAddResource(false);

				User user = _userLocalService.getDefaultUser(companyId);

				return _roleLocalService.addRole(
					user.getUserId(), null, 0, name, null, descriptionMap,
					RoleConstants.TYPE_DEPOT, null, null);
			}
			finally {
				PermissionThreadLocal.setAddResource(addResource);
			}
		}
	}

	private static final String[] _DEPOT_ROLE_NAMES = {
		DepotRolesConstants.DEPOT_ADMINISTRATOR,
		DepotRolesConstants.DEPOT_MEMBER, DepotRolesConstants.DEPOT_OWNER
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DepotRolesPortalInstanceLifecycleListener.class);

	@Reference
	private Language _language;

	@Reference(target = "(bundle.symbolic.name=com.liferay.depot.service)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}