/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service;

import com.liferay.journal.model.JournalFeed;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for JournalFeed. This utility wraps
 * <code>com.liferay.journal.service.impl.JournalFeedServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedService
 * @generated
 */
public class JournalFeedServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.journal.service.impl.JournalFeedServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static JournalFeed addFeed(
			long groupId, String feedId, boolean autoFeedId, String name,
			String description, long ddmStructureId, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFeed(
			groupId, feedId, autoFeedId, name, description, ddmStructureId,
			ddmTemplateKey, ddmRendererTemplateKey, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, serviceContext);
	}

	public static void deleteFeed(long feedId) throws PortalException {
		getService().deleteFeed(feedId);
	}

	public static void deleteFeed(long groupId, String feedId)
		throws PortalException {

		getService().deleteFeed(groupId, feedId);
	}

	public static JournalFeed getFeed(long feedId) throws PortalException {
		return getService().getFeed(feedId);
	}

	public static JournalFeed getFeed(long groupId, String feedId)
		throws PortalException {

		return getService().getFeed(groupId, feedId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static JournalFeed updateFeed(
			long groupId, String feedId, String name, String description,
			long ddmStructureId, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFeed(
			groupId, feedId, name, description, ddmStructureId, ddmTemplateKey,
			ddmRendererTemplateKey, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, serviceContext);
	}

	public static JournalFeedService getService() {
		return _service;
	}

	public static void setService(JournalFeedService service) {
		_service = service;
	}

	private static volatile JournalFeedService _service;

}