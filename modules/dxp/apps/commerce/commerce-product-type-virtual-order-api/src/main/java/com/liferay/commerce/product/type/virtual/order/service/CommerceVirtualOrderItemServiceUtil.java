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

package com.liferay.commerce.product.type.virtual.order.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceVirtualOrderItem. This utility wraps
 * {@link com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemService
 * @see com.liferay.commerce.product.type.virtual.order.service.base.CommerceVirtualOrderItemServiceBaseImpl
 * @see com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemServiceImpl
 * @generated
 */
@ProviderType
public class CommerceVirtualOrderItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.io.File getFile(long commerceVirtualOrderItemId)
		throws Exception {
		return getService().getFile(commerceVirtualOrderItemId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId, long fileEntryId, String url,
		int activationStatus, long duration, int usages, int maxUsages,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceVirtualOrderItem(commerceVirtualOrderItemId,
			fileEntryId, url, activationStatus, duration, usages, maxUsages,
			active);
	}

	public static CommerceVirtualOrderItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceVirtualOrderItemService, CommerceVirtualOrderItemService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceVirtualOrderItemService.class);

		ServiceTracker<CommerceVirtualOrderItemService, CommerceVirtualOrderItemService> serviceTracker =
			new ServiceTracker<CommerceVirtualOrderItemService, CommerceVirtualOrderItemService>(bundle.getBundleContext(),
				CommerceVirtualOrderItemService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}