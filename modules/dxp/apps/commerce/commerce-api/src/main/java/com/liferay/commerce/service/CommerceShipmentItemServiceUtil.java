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
 * Provides the remote service utility for CommerceShipmentItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemService
 * @see com.liferay.commerce.service.base.CommerceShipmentItemServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl
 * @generated
 */
@ProviderType
public class CommerceShipmentItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceShipmentItem addCommerceShipmentItem(
		long commerceShipmentId, long commerceOrderItemId,
		long commerceWarehouseId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceShipmentItem(commerceShipmentId,
			commerceOrderItemId, commerceWarehouseId, quantity, serviceContext);
	}

	public static void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceShipmentItem(commerceShipmentItemId);
	}

	public static com.liferay.commerce.model.CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentItemId) {
		return getService().fetchCommerceShipmentItem(commerceShipmentItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		long commerceOrderItemId) {
		return getService().getCommerceShipmentItems(commerceOrderItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipmentItem> orderByComparator) {
		return getService()
				   .getCommerceShipmentItems(commerceShipmentId, start, end,
			orderByComparator);
	}

	public static int getCommerceShipmentItemsCount(long commerceShipmentId) {
		return getService().getCommerceShipmentItemsCount(commerceShipmentId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceShipmentItem updateCommerceShipmentItem(
		long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceShipmentItem(commerceShipmentItemId, quantity);
	}

	public static CommerceShipmentItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShipmentItemService, CommerceShipmentItemService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceShipmentItemService.class);

		ServiceTracker<CommerceShipmentItemService, CommerceShipmentItemService> serviceTracker =
			new ServiceTracker<CommerceShipmentItemService, CommerceShipmentItemService>(bundle.getBundleContext(),
				CommerceShipmentItemService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}