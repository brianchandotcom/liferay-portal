/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceOrder. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceOrderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderService
 * @see com.liferay.commerce.service.base.CommerceOrderServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceOrder addOrganizationCommerceOrder(
		long groupId, long siteGroupId, long orderOrganizationId,
		long shippingAddressId, String purchaseOrderNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOrganizationCommerceOrder(groupId, siteGroupId,
			orderOrganizationId, shippingAddressId, purchaseOrderNumber);
	}

	public static com.liferay.commerce.model.CommerceOrder addUserCommerceOrder(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addUserCommerceOrder(groupId);
	}

	public static com.liferay.commerce.model.CommerceOrder addUserCommerceOrder(
		long groupId, long orderUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addUserCommerceOrder(groupId, orderUserId);
	}

	public static com.liferay.commerce.model.CommerceOrder approveCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().approveCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder cancelCommerceOrderPayment(
		long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .cancelCommerceOrderPayment(commerceOrderId, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder checkoutCommerceOrder(
		long commerceOrderId,
		com.liferay.commerce.context.CommerceContext commerceContext,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .checkoutCommerceOrder(commerceOrderId, commerceContext,
			serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder completeCommerceOrderPayment(
		long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .completeCommerceOrderPayment(commerceOrderId, serviceContext);
	}

	public static void deleteCommerceOrder(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder executeWorkflowTransition(
		long commerceOrderId, long workflowTaskId, String transitionName,
		String comment)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .executeWorkflowTransition(commerceOrderId, workflowTaskId,
			transitionName, comment);
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		long groupId, int orderStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceOrder(groupId, orderStatus);
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceOrder(uuid, groupId);
	}

	public static int[] getAvailableOrderStatuses(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAvailableOrderStatuses(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder getCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder getCommerceOrderByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrders(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceOrders(groupId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrders(
		long siteGroupId, int[] orderStatuses)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrders(siteGroupId, orderStatuses);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrders(
		long groupId, long orderUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceOrders(groupId, orderUserId, start, end,
			orderByComparator);
	}

	public static int getCommerceOrdersCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrdersCount(groupId);
	}

	public static int getCommerceOrdersCount(long groupId, long orderUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrdersCount(groupId, orderUserId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void mergeGuestCommerceOrder(long guestCommerceOrderId,
		long userCommerceOrderId,
		com.liferay.commerce.context.CommerceContext commerceContext,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.mergeGuestCommerceOrder(guestCommerceOrderId, userCommerceOrderId,
			commerceContext, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder reorderCommerceOrder(
		long commerceOrderId,
		com.liferay.commerce.context.CommerceContext commerceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .reorderCommerceOrder(commerceOrderId, commerceContext);
	}

	public static String startCommerceOrderPayment(long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .startCommerceOrderPayment(commerceOrderId, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder submitCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().submitCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder updateBillingAddress(
		long commerceOrderId, String name, String description, String street1,
		String street2, String street3, String city, String zip,
		long commerceRegionId, long commerceCountryId, String phoneNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateBillingAddress(commerceOrderId, name, description,
			street1, street2, street3, city, zip, commerceRegionId,
			commerceCountryId, phoneNumber, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder updateCommerceOrder(
		long commerceOrderId, long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		String shippingOptionName, String purchaseOrderNumber,
		java.math.BigDecimal subtotal, java.math.BigDecimal shippingPrice,
		java.math.BigDecimal total, String advanceStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrder(commerceOrderId, billingAddressId,
			shippingAddressId, commercePaymentMethodId,
			commerceShippingMethodId, shippingOptionName, purchaseOrderNumber,
			subtotal, shippingPrice, total, advanceStatus);
	}

	public static com.liferay.commerce.model.CommerceOrder updateOrderStatus(
		long commerceOrderId, int orderStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateOrderStatus(commerceOrderId, orderStatus);
	}

	public static com.liferay.commerce.model.CommerceOrder updatePaymentStatus(
		long commerceOrderId, int paymentStatus,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePaymentStatus(commerceOrderId, paymentStatus,
			serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder updatePurchaseOrderNumber(
		long commerceOrderId, String purchaseOrderNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePurchaseOrderNumber(commerceOrderId,
			purchaseOrderNumber);
	}

	public static com.liferay.commerce.model.CommerceOrder updateShippingAddress(
		long commerceOrderId, String name, String description, String street1,
		String street2, String street3, String city, String zip,
		long commerceRegionId, long commerceCountryId, String phoneNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateShippingAddress(commerceOrderId, name, description,
			street1, street2, street3, city, zip, commerceRegionId,
			commerceCountryId, phoneNumber, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder updateUser(
		long commerceOrderId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateUser(commerceOrderId, userId);
	}

	public static CommerceOrderService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderService, CommerceOrderService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrderService.class);

		ServiceTracker<CommerceOrderService, CommerceOrderService> serviceTracker =
			new ServiceTracker<CommerceOrderService, CommerceOrderService>(bundle.getBundleContext(),
				CommerceOrderService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}