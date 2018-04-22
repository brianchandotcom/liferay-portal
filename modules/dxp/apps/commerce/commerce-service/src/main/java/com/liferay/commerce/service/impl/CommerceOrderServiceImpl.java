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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.internal.security.permission.CommerceOrderWorkflowPermissionChecker;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.base.CommerceOrderServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
public class CommerceOrderServiceImpl extends CommerceOrderServiceBaseImpl {

	@Override
	public CommerceOrder addOrganizationCommerceOrder(
			long groupId, long siteGroupId, long orderOrganizationId,
			long shippingAddressId, String purchaseOrderNumber)
		throws PortalException {

		return commerceOrderLocalService.addOrganizationCommerceOrder(
			groupId, getUserId(), siteGroupId, orderOrganizationId,
			shippingAddressId, purchaseOrderNumber);
	}

	@Override
	public CommerceOrder addUserCommerceOrder(long groupId)
		throws PortalException {

		return commerceOrderLocalService.addUserCommerceOrder(
			groupId, getGuestOrUserId());
	}

	@Override
	public CommerceOrder addUserCommerceOrder(long groupId, long orderUserId)
		throws PortalException {

		return commerceOrderLocalService.addUserCommerceOrder(
			groupId, getGuestOrUserId(), orderUserId);
	}

	@Override
	public CommerceOrder approveCommerceOrder(long commerceOrderId)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceOrderActionKeys.APPROVE_COMMERCE_ORDER);

		return commerceOrderLocalService.approveCommerceOrder(
			getUserId(), commerceOrderId);
	}

	@Override
	public CommerceOrder cancelCommerceOrderPayment(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER);

		return commerceOrderLocalService.cancelCommerceOrderPayment(
			commerceOrderId, serviceContext);
	}

	@Override
	public CommerceOrder checkoutCommerceOrder(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER);

		return commerceOrderLocalService.checkoutCommerceOrder(
			commerceOrderId, serviceContext);
	}

	@Override
	public CommerceOrder completeCommerceOrderPayment(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER);

		return commerceOrderLocalService.completeCommerceOrderPayment(
			commerceOrderId, serviceContext);
	}

	@Override
	public void deleteCommerceOrder(long commerceOrderId)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.DELETE);

		commerceOrderLocalService.deleteCommerceOrder(commerceOrderId);
	}

	@Override
	public CommerceOrder executeWorkflowTransition(
			long commerceOrderId, long workflowTaskId, String transitionName,
			String comment)
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder(commerceOrderId);

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			commerceOrder.getCompanyId(), workflowTaskId);

		if (!_commerceOrderWorkflowPermissionChecker.hasPermission(
				commerceOrder, workflowTask, getPermissionChecker())) {

			throw new PrincipalException();
		}

		return commerceOrderLocalService.executeWorkflowTransition(
			getUserId(), commerceOrderId, workflowTaskId, transitionName,
			comment);
	}

	@Override
	public CommerceOrder fetchCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		if (commerceOrder != null) {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrder, ActionKeys.VIEW);
		}

		return commerceOrder;
	}

	@Override
	public CommerceOrder fetchCommerceOrder(long groupId, int orderStatus)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.fetchCommerceOrder(
				groupId, getGuestOrUserId(), orderStatus);

		if (commerceOrder != null) {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrder, ActionKeys.VIEW);
		}

		return commerceOrder;
	}

	@Override
	public CommerceOrder fetchCommerceOrder(String uuid, long groupId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.fetchCommerceOrderByUuidAndGroupId(
				uuid, groupId);

		if (commerceOrder != null) {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrder, ActionKeys.VIEW);
		}

		return commerceOrder;
	}

	@Override
	public int[] getAvailableOrderStatuses(long commerceOrderId)
		throws PortalException {

		if (commerceOrderId > 0) {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);
		}

		return commerceOrderLocalService.getAvailableOrderStatuses(
			commerceOrderId);
	}

	@Override
	public CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrder, ActionKeys.VIEW);

		return commerceOrder;
	}

	@Override
	public CommerceOrder getCommerceOrderByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrderByUuidAndGroupId(
				uuid, groupId);

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrder, ActionKeys.VIEW);

		return commerceOrder;
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
			long groupId, long orderUserId, int start, int end,
			OrderByComparator<CommerceOrder> orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((orderUserId != permissionChecker.getUserId()) &&
			!CommercePermission.contains(
				permissionChecker, groupId,
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS)) {

			throw new PrincipalException();
		}

		return commerceOrderLocalService.getCommerceOrders(
			groupId, orderUserId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrdersCount(long groupId, long orderUserId)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((orderUserId != permissionChecker.getUserId()) &&
			!CommercePermission.contains(
				permissionChecker, groupId,
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS)) {

			throw new PrincipalException();
		}

		return commerceOrderLocalService.getCommerceOrdersCount(
			groupId, orderUserId);
	}

	@Override
	public void mergeGuestCommerceOrder(
			long guestCommerceOrderId, long userCommerceOrderId,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), guestCommerceOrderId, ActionKeys.VIEW);
		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), userCommerceOrderId, ActionKeys.UPDATE);

		commerceOrderLocalService.mergeGuestCommerceOrder(
			guestCommerceOrderId, userCommerceOrderId, serviceContext);
	}

	@Override
	public CommerceOrder reorderCommerceOrder(long commerceOrderId)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.VIEW);

		return commerceOrderLocalService.reorderCommerceOrder(
			getUserId(), commerceOrderId);
	}

	@Override
	public String startCommerceOrderPayment(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER);

		return commerceOrderLocalService.startCommerceOrderPayment(
			commerceOrderId, serviceContext);
	}

	@Override
	public CommerceOrder submitCommerceOrder(long commerceOrderId)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.submitCommerceOrder(
			getUserId(), commerceOrderId);
	}

	@Override
	public CommerceOrder updateBillingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateBillingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	@Override
	public CommerceOrder updateCommerceOrder(
			long commerceOrderId, long billingAddressId, long shippingAddressId,
			long commercePaymentMethodId, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			BigDecimal subtotal, BigDecimal shippingPrice, BigDecimal total,
			String advanceStatus)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateCommerceOrder(
			commerceOrderId, billingAddressId, shippingAddressId,
			commercePaymentMethodId, commerceShippingMethodId,
			shippingOptionName, purchaseOrderNumber, subtotal, shippingPrice,
			total, advanceStatus);
	}

	@Override
	public CommerceOrder updateOrderStatus(
			long commerceOrderId, int orderStatus)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateOrderStatus(
			commerceOrderId, orderStatus);
	}

	@Override
	public CommerceOrder updatePaymentStatus(
			long commerceOrderId, int paymentStatus,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updatePaymentStatus(
			commerceOrderId, paymentStatus, serviceContext);
	}

	@Override
	public CommerceOrder updatePurchaseOrderNumber(
			long commerceOrderId, String purchaseOrderNumber)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updatePurchaseOrderNumber(
			commerceOrderId, purchaseOrderNumber);
	}

	@Override
	public CommerceOrder updateShippingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateShippingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	@Override
	public CommerceOrder updateUser(long commerceOrderId, long userId)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateUser(commerceOrderId, userId);
	}

	private static volatile ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceOrderServiceImpl.class,
				"_commerceOrderModelResourcePermission", CommerceOrder.class);

	@ServiceReference(type = CommerceOrderWorkflowPermissionChecker.class)
	private CommerceOrderWorkflowPermissionChecker
		_commerceOrderWorkflowPermissionChecker;

	@ServiceReference(type = WorkflowTaskManager.class)
	private WorkflowTaskManager _workflowTaskManager;

}