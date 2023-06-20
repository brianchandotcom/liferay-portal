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

package com.liferay.commerce.inventory.internal.permission;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.permission.CommerceInventoryWarehousePermission;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(service = CommerceInventoryWarehousePermission.class)
public class CommerceInventoryWarehousePermissionImpl
	implements CommerceInventoryWarehousePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceInventoryWarehouse commerceInventoryWarehouse,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceInventoryWarehouse, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceInventoryWarehouse.class.getName(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceInventoryWarehouseId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceInventoryWarehouseId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceInventoryWarehouse.class.getName(),
				commerceInventoryWarehouseId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceInventoryWarehouse commerceInventoryWarehouse,
			String actionId)
		throws PortalException {

		if (contains(
				permissionChecker,
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceInventoryWarehouseId, String actionId)
		throws PortalException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouse(commerceInventoryWarehouseId);

		if (commerceInventoryWarehouse == null) {
			return false;
		}

		return _contains(
			permissionChecker, commerceInventoryWarehouse, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceAccountIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceAccountIds)) {
			return false;
		}

		for (long commerceInventoryWarehouseId : commerceAccountIds) {
			if (!contains(
					permissionChecker, commerceInventoryWarehouseId,
					actionId)) {

				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceInventoryWarehouse commerceInventoryWarehouse,
			String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				commerceInventoryWarehouse.getCompanyId()) ||
			permissionChecker.isOmniadmin() ||
			_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), null,
				CommerceInventoryActionKeys.MANAGE_INVENTORY) ||
			_hasSupplierPermission(
				permissionChecker, commerceInventoryWarehouse, actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			actionId);
	}

	private boolean _hasSupplierAccount(
			PermissionChecker permissionChecker,
			CommerceInventoryWarehouse commerceInventoryWarehouse)
		throws PortalException {

		List<CommerceChannel> accountEntryCommerceChannels =
			TransformUtil.transform(
				_accountEntryLocalService.getUserAccountEntries(
					permissionChecker.getUserId(), 0L, StringPool.BLANK,
					new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER},
					QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				accountEntry -> {
					if (_userGroupRoleLocalService.hasUserGroupRole(
							permissionChecker.getUserId(),
							accountEntry.getAccountEntryGroupId(),
							AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER)) {

						List<CommerceChannel> commerceChannels =
							_commerceChannelLocalService.
								getCommerceChannelsByAccountEntryId(
									accountEntry.getAccountEntryId());

						if (ListUtil.isNotEmpty(commerceChannels)) {
							return commerceChannels.get(0);
						}
					}

					return null;
				});

		if (ListUtil.isEmpty(accountEntryCommerceChannels)) {
			return false;
		}

		int commerceChannelRelsCount =
			_commerceChannelRelLocalService.getCommerceChannelRelsCount(
				CommerceInventoryWarehouse.class.getName(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

		if (commerceChannelRelsCount == 0) {
			return true;
		}

		for (CommerceChannel commerceChannel : accountEntryCommerceChannels) {
			CommerceChannelRel commerceChannelRel =
				_commerceChannelRelLocalService.fetchCommerceChannelRel(
					CommerceInventoryWarehouse.class.getName(),
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId(),
					commerceChannel.getCommerceChannelId());

			if (commerceChannelRel != null) {
				return true;
			}
		}

		return false;
	}

	private boolean _hasSupplierPermission(
			PermissionChecker permissionChecker,
			CommerceInventoryWarehouse commerceInventoryWarehouse,
			String actionId)
		throws PortalException {

		if ((actionId.equals(ActionKeys.UPDATE) ||
			 actionId.equals(ActionKeys.VIEW)) &&
			_hasSupplierAccount(
				permissionChecker, commerceInventoryWarehouse)) {

			return true;
		}

		return false;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Reference(
		target = "(resource.name=" + CommerceInventoryConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}