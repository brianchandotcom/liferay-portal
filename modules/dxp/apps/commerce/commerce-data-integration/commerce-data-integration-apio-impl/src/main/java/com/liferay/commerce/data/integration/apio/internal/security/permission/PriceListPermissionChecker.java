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

package com.liferay.commerce.data.integration.apio.internal.security.permission;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.commerce.data.integration.apio.security.permission.CollectionPermissionChecker;
import com.liferay.commerce.price.list.constants.CommercePriceListActionKeys;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserService;

import java.util.function.BiFunction;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true, service = PriceListPermissionChecker.class)
public class PriceListPermissionChecker implements CollectionPermissionChecker {

	@Override
	public BiFunction<Credentials, Long, Boolean> forAdding() {
		return (credentials, groupId) -> {
			Try<PermissionChecker> permissionCheckerTry =
				_getPermissionCheckerTry(credentials);

			return permissionCheckerTry.map(
				permissionChecker -> _portletResourcePermission.contains(
					permissionChecker, groupId,
					CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS)
			).orElse(
				false
			);
		};
	}

	@Override
	public BiFunction<Credentials, Long, Boolean> forDeleting() {
		return _permissionBridge();
	}

	@Override
	public BiFunction<Credentials, Long, Boolean> forUpdating() {
		return (credentials, identifier) -> {
			Try<PermissionChecker> permissionCheckerTry =
				_getPermissionCheckerTry(credentials);

			Try<CommercePriceList> commercePriceListTry = Try.fromFallible(
				() -> _commercePriceListService.fetchCommercePriceList(
					identifier));

			return permissionCheckerTry.map(
				permissionChecker -> _portletResourcePermission.contains(
					permissionChecker,
					commercePriceListTry.map(
						CommercePriceList::getGroupId
					).orElseThrow(
						() -> new NotFoundException()
					),
					CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS)
			).orElse(
				false
			);
		};
	}

	private Try<PermissionChecker> _getPermissionCheckerTry(
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

	private BiFunction<Credentials, Long, Boolean> _permissionBridge() {
		return forUpdating();
	}

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference(target = "(resource.name=com.liferay.commerce.price.list)")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private UserService _userService;

}