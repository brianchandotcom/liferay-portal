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

package com.liferay.role.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.role.apio.identifier.RoleIdentifier;
import com.liferay.workflow.apio.architect.identifier.WorkflowTaskIdentifier;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Role">Role </a> resources through a web API. The
 * resources are mapped from the internal model {@code Role}.
 *
 * @author Javier
 * @review
 */
@Component(immediate = true)
public class RoleCollectionResource
	implements CollectionResource<Role, Long, RoleIdentifier> {

	@Override
	public CollectionRoutes<Role, Long> collectionRoutes(
		CollectionRoutes.Builder<Role, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
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
			roleIdentifier -> _roleService.getRole(roleIdentifier)
		).build();
	}

	@Override
	public Representor<Role> representor(
		Representor.Builder<Role, Long> builder) {

		return builder.types(
			"Role"
		).identifier(
			Role::getRoleId
		).addDate(
			"dateCreated", Role::getCreateDate
		).addDate(
			"dateModified", Role::getModifiedDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, Role::getUserId
		).addLocalizedStringByLocale(
			"description", Role::getDescription
		).addRelatedCollection(
			"tasks", WorkflowTaskIdentifier.class
		).addString(
			"name", Role::getName
		).addString(
			"roleType", Role::getTypeLabel
		).addStringList(
			"availableLanguages",
			role -> Arrays.asList(role.getAvailableLanguageIds())
		).build();
	}

	private PageItems<Role> _getPageItems(
		Pagination pagination, Company company) {

		Integer[] roleTypes = {
			RoleConstants.TYPE_ORGANIZATION, RoleConstants.TYPE_REGULAR,
			RoleConstants.TYPE_SITE
		};

		List<Role> roles = _roleService.search(
			company.getCompanyId(), null, roleTypes, null,
			pagination.getStartPosition(), pagination.getEndPosition(), null);
		int count = _roleService.searchCount(
			company.getCompanyId(), null, roleTypes, null);

		return new PageItems<>(roles, count);
	}

	@Reference
	private RoleService _roleService;

}