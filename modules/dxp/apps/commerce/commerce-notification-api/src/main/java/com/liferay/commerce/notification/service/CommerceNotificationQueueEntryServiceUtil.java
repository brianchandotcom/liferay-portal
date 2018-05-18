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

package com.liferay.commerce.notification.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceNotificationQueueEntry. This utility wraps
 * {@link com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryService
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationQueueEntryServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryServiceImpl
 * @generated
 */
@ProviderType
public class CommerceNotificationQueueEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationQueueEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry> getCommerceNotificationQueueEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceNotificationQueueEntries(groupId, start, end,
			orderByComparator);
	}

	public static int getCommerceNotificationQueueEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceNotificationQueueEntriesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationQueueEntry resendCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .resendCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	public static CommerceNotificationQueueEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceNotificationQueueEntryService, CommerceNotificationQueueEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceNotificationQueueEntryService.class);

		ServiceTracker<CommerceNotificationQueueEntryService, CommerceNotificationQueueEntryService> serviceTracker =
			new ServiceTracker<CommerceNotificationQueueEntryService, CommerceNotificationQueueEntryService>(bundle.getBundleContext(),
				CommerceNotificationQueueEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}