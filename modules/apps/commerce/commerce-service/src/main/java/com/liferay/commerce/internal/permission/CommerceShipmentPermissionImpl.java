/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.permission;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.permission.CommerceShipmentPermission;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = CommerceShipmentPermission.class)
public class CommerceShipmentPermissionImpl
	implements CommerceShipmentPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceShipment commerceShipment, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceShipment, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceShipment.class.getName(),
				commerceShipment.getCommerceShipmentId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceShipmentId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceShipmentId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceShipment.class.getName(),
				commerceShipmentId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceShipment commerceShipment, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, commerceShipment.getCommerceShipmentId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceShipmentId,
			String actionId)
		throws PortalException {

		CommerceShipment commerceShipment =
			_commerceShipmentLocalService.fetchCommerceShipment(
				commerceShipmentId);

		if (commerceShipment == null) {
			return false;
		}

		return _contains(permissionChecker, commerceShipment, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceShipmentIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(commerceShipmentIds)) {
			return false;
		}

		for (long commerceShipmentId : commerceShipmentIds) {
			if (!contains(permissionChecker, commerceShipmentId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceShipment commerceShipment, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(commerceShipment.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				commerceShipment.getCompanyId(),
				CommerceShipment.class.getName(),
				commerceShipment.getCommerceShipmentId(),
				commerceShipment.getUserId(), actionId)) {

			return true;
		}

		if (Objects.equals(actionId, ActionKeys.VIEW) &&
			_containsCommerceOrderViewPermission(
				commerceShipment.getCommerceShipmentId(), permissionChecker)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CommerceShipment.class.getName(),
			commerceShipment.getCommerceShipmentId(), actionId);
	}

	private boolean _containsCommerceOrderViewPermission(
			long commerceShipmentId, PermissionChecker permissionChecker)
		throws PortalException {

		Set<Long> commerceOrderIds = new HashSet<>(
			TransformUtil.transform(
				_commerceShipmentItemLocalService.getCommerceShipmentItems(
					commerceShipmentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null),
				commerceShipmentItem -> {
					CommerceOrderItem commerceOrderItem =
						_commerceOrderItemLocalService.fetchCommerceOrderItem(
							commerceShipmentItem.getCommerceOrderItemId());

					if (commerceOrderItem == null) {
						return null;
					}

					return commerceOrderItem.getCommerceOrderId();
				}));

		if (commerceOrderIds.isEmpty()) {
			return false;
		}

		for (long commerceOrderId : commerceOrderIds) {
			if (!_commerceOrderModelResourcePermission.contains(
					permissionChecker, commerceOrderId, ActionKeys.VIEW)) {

				return false;
			}
		}

		return true;
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	@Reference
	private CommerceShipmentLocalService _commerceShipmentLocalService;

}