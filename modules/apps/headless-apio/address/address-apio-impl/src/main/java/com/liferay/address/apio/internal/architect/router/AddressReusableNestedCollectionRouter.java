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

package com.liferay.address.apio.internal.architect.router;

import com.liferay.address.apio.architect.identifier.AddressIdentifier;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/address">Address</a> resources of a <a
 * href="http://schema.org/Person">Person</a> through a web API. The resources
 * are mapped from the internal model {@code Address}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true, service = ReusableNestedCollectionRouter.class)
public class AddressReusableNestedCollectionRouter
	implements ReusableNestedCollectionRouter
		<Address, Long, AddressIdentifier, ClassNameClassPK> {

	@Override
	public NestedCollectionRoutes
		<Address, Long, ClassNameClassPK> collectionRoutes(
			NestedCollectionRoutes.Builder<Address, Long, ClassNameClassPK>
				builder) {

		return builder.addGetter(
			this::_getPageItems, Credentials.class
		).build();
	}

	@Reference
	protected AddressLocalService addressLocalService;

	@Reference
	protected UserService userService;

	private PageItems<Address> _getPageItems(
			Pagination pagination, ClassNameClassPK classNameClassPK,
			Credentials credentials)
		throws PortalException {

		User user = userService.getUserById(classNameClassPK.getClassPK());

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