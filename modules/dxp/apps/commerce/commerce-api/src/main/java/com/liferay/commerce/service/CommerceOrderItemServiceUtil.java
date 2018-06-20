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
 * Provides the remote service utility for CommerceOrderItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceOrderItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItemService
 * @see com.liferay.commerce.service.base.CommerceOrderItemServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderItemServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceOrderItem addCommerceOrderItem(
		long commerceOrderId, long cpInstanceId, int quantity,
		int shippedQuantity, String json, java.math.BigDecimal price,
		com.liferay.commerce.context.CommerceContext commerceContext,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceOrderItem(commerceOrderId, cpInstanceId,
			quantity, shippedQuantity, json, price, commerceContext,
			serviceContext);
	}

	public static void deleteCommerceOrderItem(long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceOrderItem(commerceOrderItemId);
	}

	public static com.liferay.commerce.model.CommerceOrderItem fetchCommerceOrderItem(
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceOrderItem(commerceOrderItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem> getAvailableForShipmentCommerceOrderItems(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getAvailableForShipmentCommerceOrderItems(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrderItem getCommerceOrderItem(
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderItem(commerceOrderItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderItems(commerceOrderId, start, end);
	}

	public static int getCommerceOrderItemsCount(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderItemsCount(commerceOrderId);
	}

	public static int getCommerceOrderItemsQuantity(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderItemsQuantity(commerceOrderId);
	}

	public static int getCommerceWarehouseItemQuantity(
		long commerceOrderItemId, long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWarehouseItemQuantity(commerceOrderItemId,
			commerceWarehouseId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		long commerceOrderId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().search(commerceOrderId, keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		long commerceOrderId, String sku, String name, boolean andOperator,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(commerceOrderId, sku, name, andOperator, start, end,
			sort);
	}

	public static com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		long commerceOrderItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrderItem(commerceOrderItemId, quantity);
	}

	public static com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		long commerceOrderItemId, int quantity, String json,
		java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrderItem(commerceOrderItemId, quantity,
			json, price);
	}

	public static CommerceOrderItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderItemService, CommerceOrderItemService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrderItemService.class);

		ServiceTracker<CommerceOrderItemService, CommerceOrderItemService> serviceTracker =
			new ServiceTracker<CommerceOrderItemService, CommerceOrderItemService>(bundle.getBundleContext(),
				CommerceOrderItemService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}