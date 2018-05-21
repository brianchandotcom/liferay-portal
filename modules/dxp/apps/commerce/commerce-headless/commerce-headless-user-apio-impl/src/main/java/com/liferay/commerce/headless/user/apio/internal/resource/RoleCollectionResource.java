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

package com.liferay.commerce.headless.user.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.commerce.headless.user.apio.identifier.RoleIdentifier;
import com.liferay.commerce.headless.user.apio.identifier.UserIdentifier;
import com.liferay.commerce.headless.user.apio.internal.form.RoleForm;
import com.liferay.commerce.headless.user.apio.internal.security.RolePermissionChecker;
import com.liferay.commerce.headless.user.apio.internal.util.RoleHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class RoleCollectionResource
	implements CollectionResource<Role, Long, RoleIdentifier> {

	@Override
	public CollectionRoutes<Role> collectionRoutes(
		CollectionRoutes.Builder<Role> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).addCreator(
			this::_addRole, _rolePermissionChecker::forAdding,
			RoleForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "roles";
	}

	@Override
	public ItemRoutes<Role, Long> itemRoutes(
		ItemRoutes.Builder<Role, Long> builder) {

		return builder.addGetter(
			this::_getRole
		).addRemover(
			idempotent(_roleLocalService::deleteRole),
			_rolePermissionChecker.forDeleting()
		).addUpdater(
			this::_updateRole, _rolePermissionChecker.forUpdating(),
			RoleForm::buildForm
		).build();
	}

	@Override
	public Representor<Role, Long> representor(
		Representor.Builder<Role, Long> builder) {

		return builder.types(
			"Role"
		).identifier(
			Role::getRoleId
		).addString(
			"name", Role::getName
		).addLocalizedStringByLocale(
			"title", this::_getTitle
		).addLocalizedStringByLocale(
			"description", this::_getDescription
		).addRelatedCollection(
			"users", UserIdentifier.class
		).build();
	}

	private Role _addRole(RoleForm roleForm) throws PortalException {
		Role role = _roleHelper.addRole(
			roleForm.getName(), roleForm.getTitleMap(),
			roleForm.getDescriptionMap());

		_roleHelper.addUser(role, roleForm.getUserIds());

		return role;
	}

	private String _getDescription(Role role, Locale locale) {
		return role.getDescription(locale);
	}

	private PageItems<Role> _getPageItems(
		Pagination pagination, Company company) {

		List<Role> roles = _roleLocalService.getRoles(
			company.getCompanyId(), null);

		return new PageItems<>(roles, roles.size());
	}

	private Role _getRole(Long roleId) throws PortalException {
		return _roleLocalService.getRole(roleId);
	}

	private String _getTitle(Role role, Locale locale) {
		return role.getTitle(locale);
	}

	private Role _updateRole(Long roleId, RoleForm roleForm)
		throws PortalException {

		Role role = _roleHelper.updateRole(
			roleId, roleForm.getName(), roleForm.getTitleMap(),
			roleForm.getDescriptionMap());

		_roleHelper.addUser(role, roleForm.getUserIds());

		return role;
	}

	@Reference
	private RoleHelper _roleHelper;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private RolePermissionChecker _rolePermissionChecker;

}