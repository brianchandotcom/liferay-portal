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

package com.liferay.commerce.media;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class CommerceMediaResolverUtil {

	public static String getDefaultURL(long groupId) {
		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getDefaultURL(groupId);
	}

	public static String getDownloadURL(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getDownloadURL(
			commerceAccountId, cpAttachmentFileEntryId);
	}

	public static String getThumbnailURL(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getThumbnailURL(
			commerceAccountId, cpAttachmentFileEntryId);
	}

	public static String getURL(
			long commerceAccountId, long cpAttachmentFileEntryId)
		throws PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getURL(
			commerceAccountId, cpAttachmentFileEntryId);
	}

	private static final ServiceTracker<?, CommerceMediaResolver>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(CommerceMediaResolverUtil.class),
			CommerceMediaResolver.class);

}