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

package com.liferay.redirect.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for RedirectEntry. This utility wraps
 * <code>com.liferay.redirect.service.impl.RedirectEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see RedirectEntryService
 * @generated
 */
public class RedirectEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.redirect.service.impl.RedirectEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.redirect.model.RedirectEntry addRedirectEntry(
			long groupId, String destinationURL, java.util.Date expirationDate,
			boolean permanent, String sourceURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRedirectEntry(
			groupId, destinationURL, expirationDate, permanent, sourceURL,
			serviceContext);
	}

	public static com.liferay.redirect.model.RedirectEntry deleteRedirectEntry(
			long redirectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRedirectEntry(redirectEntryId);
	}

	public static com.liferay.redirect.model.RedirectEntry fetchRedirectEntry(
			long redirectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchRedirectEntry(redirectEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.redirect.model.RedirectEntry>
			getRedirectEntries(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.redirect.model.RedirectEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRedirectEntries(groupId, start, end, obc);
	}

	public static int getRedirectEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRedirectEntriesCount(groupId);
	}

	public static void updateRedirectEntriesReferences(
			long groupId, String destinationURL, String groupBaseURL,
			boolean updateReferences, String sourceURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateRedirectEntriesReferences(
			groupId, destinationURL, groupBaseURL, updateReferences, sourceURL);
	}

	public static com.liferay.redirect.model.RedirectEntry updateRedirectEntry(
			long redirectEntryId, String destinationURL,
			java.util.Date expirationDate, boolean permanent, String sourceURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRedirectEntry(
			redirectEntryId, destinationURL, expirationDate, permanent,
			sourceURL);
	}

	public static RedirectEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<RedirectEntryService, RedirectEntryService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(RedirectEntryService.class);

		ServiceTracker<RedirectEntryService, RedirectEntryService>
			serviceTracker =
				new ServiceTracker<RedirectEntryService, RedirectEntryService>(
					bundle.getBundleContext(), RedirectEntryService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}