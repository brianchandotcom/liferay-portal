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

package com.liferay.address.apio.internal.architect.router.base;

import com.liferay.address.apio.architect.identifier.AddressIdentifier;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.CommonPermissionUtil;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Perez
 */
public abstract class BaseUserAccountAddressNestedCollectionRouter
	<T extends Identifier<Long>> implements
		NestedCollectionRouter<Address, Long, AddressIdentifier, Long, T> {

	@Override
	public NestedCollectionRoutes<Address, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Address, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Credentials.class
		).build();
	}

	@Reference
	protected AddressLocalService addressLocalService;

	@Reference
	protected UserService userService;

	private PageItems<Address> _getPageItems(
			Pagination pagination, long personId, Credentials credentials)
		throws PortalException {

		User user = userService.getUserById(personId);

		String className = user.getModelClassName();

		CommonPermissionUtil.check(
			(PermissionChecker)credentials.get(), className, user.getUserId(),
			ActionKeys.VIEW);

		List<Address> addresses = addressLocalService.getAddresses(
			user.getCompanyId(), Contact.class.getName(), user.getContactId());

		int count = addresses.size();

		int endPosition = Math.min(count, pagination.getEndPosition());

		return new PageItems<>(
			addresses.subList(pagination.getStartPosition(), endPosition),
			count);
	}

}