/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.redirect.model.RedirectEntry;

import java.util.List;

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
	public static RedirectEntry addRedirectEntry(
			long groupId, String destinationURL, java.util.Date expirationDate,
			boolean permanent, String sourceURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addRedirectEntry(
			groupId, destinationURL, expirationDate, permanent, sourceURL,
			serviceContext);
	}

	public static RedirectEntry addRedirectEntry(
			long groupId, String destinationURL, java.util.Date expirationDate,
			String groupBaseURL, boolean permanent, String sourceURL,
			boolean updateChainedRedirectEntries,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addRedirectEntry(
			groupId, destinationURL, expirationDate, groupBaseURL, permanent,
			sourceURL, updateChainedRedirectEntries, serviceContext);
	}

	public static RedirectEntry deleteRedirectEntry(long redirectEntryId)
		throws PortalException {

		return getService().deleteRedirectEntry(redirectEntryId);
	}

	public static RedirectEntry fetchRedirectEntry(long redirectEntryId)
		throws PortalException {

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

	public static List<RedirectEntry> getRedirectEntries(
			long groupId, int start, int end,
			OrderByComparator<RedirectEntry> orderByComparator)
		throws PortalException {

		return getService().getRedirectEntries(
			groupId, start, end, orderByComparator);
	}

	public static int getRedirectEntriesCount(long groupId)
		throws PortalException {

		return getService().getRedirectEntriesCount(groupId);
	}

	public static RedirectEntry updateRedirectEntry(
			long redirectEntryId, String destinationURL,
			java.util.Date expirationDate, boolean permanent, String sourceURL)
		throws PortalException {

		return getService().updateRedirectEntry(
			redirectEntryId, destinationURL, expirationDate, permanent,
			sourceURL);
	}

	public static RedirectEntry updateRedirectEntry(
			long redirectEntryId, String destinationURL,
			java.util.Date expirationDate, String groupBaseURL,
			boolean permanent, String sourceURL,
			boolean updateChainedRedirectEntries)
		throws PortalException {

		return getService().updateRedirectEntry(
			redirectEntryId, destinationURL, expirationDate, groupBaseURL,
			permanent, sourceURL, updateChainedRedirectEntries);
	}

	public static RedirectEntryService getService() {
		return _service;
	}

	public static void setService(RedirectEntryService service) {
		_service = service;
	}

	private static volatile RedirectEntryService _service;

}