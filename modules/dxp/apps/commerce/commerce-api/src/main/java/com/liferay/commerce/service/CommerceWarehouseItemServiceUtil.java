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
 * Provides the remote service utility for CommerceWarehouseItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceWarehouseItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseItemService
 * @see com.liferay.commerce.service.base.CommerceWarehouseItemServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceWarehouseItemServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWarehouseItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceWarehouseItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceWarehouseItem addCommerceWarehouseItem(
		long commerceWarehouseId, long cpInstanceId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWarehouseItem(commerceWarehouseId, cpInstanceId,
			quantity, serviceContext);
	}

	public static void deleteCommerceWarehouseItem(long commerceWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceWarehouseItem(commerceWarehouseItemId);
	}

	public static com.liferay.commerce.model.CommerceWarehouseItem fetchCommerceWarehouseItem(
		long commerceWarehouseId, long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCommerceWarehouseItem(commerceWarehouseId, cpInstanceId);
	}

	public static com.liferay.commerce.model.CommerceWarehouseItem getCommerceWarehouseItem(
		long commerceWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouseItem(commerceWarehouseItemId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouseItems(cpInstanceId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouseItem> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWarehouseItems(cpInstanceId, start, end,
			orderByComparator);
	}

	public static int getCommerceWarehouseItemsCount(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouseItemsCount(cpInstanceId);
	}

	public static int getCPInstanceQuantity(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstanceQuantity(cpInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceWarehouseItem updateCommerceWarehouseItem(
		long commerceWarehouseItemId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceWarehouseItem(commerceWarehouseItemId,
			quantity, serviceContext);
	}

	public static CommerceWarehouseItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWarehouseItemService, CommerceWarehouseItemService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWarehouseItemService.class);

		ServiceTracker<CommerceWarehouseItemService, CommerceWarehouseItemService> serviceTracker =
			new ServiceTracker<CommerceWarehouseItemService, CommerceWarehouseItemService>(bundle.getBundleContext(),
				CommerceWarehouseItemService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}