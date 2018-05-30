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

package com.liferay.commerce.wish.list.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceWishListItem. This utility wraps
 * {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemService
 * @see com.liferay.commerce.wish.list.service.base.CommerceWishListItemServiceBaseImpl
 * @see com.liferay.commerce.wish.list.service.impl.CommerceWishListItemServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWishListItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.wish.list.model.CommerceWishListItem addCommerceWishListItem(
		long commerceWishListId, long cpDefinitionId, long cpInstanceId,
		String json,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWishListItem(commerceWishListId, cpDefinitionId,
			cpInstanceId, json, serviceContext);
	}

	public static void deleteCommerceWishListItem(long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceWishListItem(commerceWishListItemId);
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishListItem getCommerceWishListItem(
		long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWishListItem(commerceWishListItemId);
	}

	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishListItem> getCommerceWishListItems(
		long commerceWishListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishListItem> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWishListItems(commerceWishListId, start, end,
			orderByComparator);
	}

	public static int getCommerceWishListItemsCount(long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWishListItemsCount(commerceWishListId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceWishListItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWishListItemService, CommerceWishListItemService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWishListItemService.class);

		ServiceTracker<CommerceWishListItemService, CommerceWishListItemService> serviceTracker =
			new ServiceTracker<CommerceWishListItemService, CommerceWishListItemService>(bundle.getBundleContext(),
				CommerceWishListItemService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}