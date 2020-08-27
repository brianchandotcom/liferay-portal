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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alec Sloan
 */
public class CommerceMediaResolverUtil {

	public static String getDefaultUrl(long groupId) {
		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getDefaultUrl(groupId);
	}

	public static String getDownloadUrl(long cpAttachmentFileEntryId)
		throws PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getDownloadUrl(cpAttachmentFileEntryId);
	}

	public static byte[] getMediaBytes(HttpServletRequest httpServletRequest)
		throws IOException, PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getMediaBytes(httpServletRequest);
	}

	public static String getThumbnailUrl(long cpAttachmentFileEntryId)
		throws PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getThumbnailUrl(cpAttachmentFileEntryId);
	}

	public static String getUrl(long cpAttachmentFileEntryId)
		throws PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		return commerceMediaResolver.getUrl(cpAttachmentFileEntryId);
	}

	public static void sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		commerceMediaResolver.sendMediaBytes(
			httpServletRequest, httpServletResponse);
	}

	public static void sendMediaBytes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String download)
		throws IOException, PortalException {

		CommerceMediaResolver commerceMediaResolver =
			_serviceTracker.getService();

		commerceMediaResolver.sendMediaBytes(
			httpServletRequest, httpServletResponse, download);
	}

	private static final ServiceTracker<?, CommerceMediaResolver>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(CommerceMediaResolverUtil.class),
			CommerceMediaResolver.class);

}