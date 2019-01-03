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

package com.liferay.organization.apio.internal.architect.router.base;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.organization.apio.architect.identifier.OrganizationIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Pérez
 */
public abstract class BaseUserAccountOrganizationNestedCollectionRouter
	<T extends Identifier<Long>>
		implements NestedCollectionRouter
			<Organization, Long, OrganizationIdentifier, Long, T> {

	@Override
	public NestedCollectionRoutes<Organization, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Organization, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class
		).build();
	}

	@Reference
	protected UserService userService;

	private PageItems<Organization> _getPageItems(
			Pagination pagination, long userId, ThemeDisplay themeDisplay)
		throws PortalException {

		User user = userService.getUserById(userId);

		List<Organization> organizations = user.getOrganizations();

		int count = organizations.size();

		int endPosition = Math.min(pagination.getEndPosition(), count);

		return new PageItems<>(
			organizations.subList(pagination.getStartPosition(), endPosition),
			count);
	}

}