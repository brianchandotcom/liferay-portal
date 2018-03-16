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

package com.liferay.screens.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for ScreensComment. This utility wraps
 * {@link com.liferay.screens.service.impl.ScreensCommentServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensCommentService
 * @see com.liferay.screens.service.base.ScreensCommentServiceBaseImpl
 * @see com.liferay.screens.service.impl.ScreensCommentServiceImpl
 * @generated
 */
@ProviderType
public class ScreensCommentServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.screens.service.impl.ScreensCommentServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.json.JSONObject addComment(
		java.lang.String className, long classPK, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addComment(className, classPK, body);
	}

	public static com.liferay.portal.kernel.json.JSONObject getComment(
		long commentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getComment(commentId);
	}

	public static com.liferay.portal.kernel.json.JSONArray getComments(
		java.lang.String className, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getComments(className, classPK, start, end);
	}

	public static int getCommentsCount(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommentsCount(className, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.json.JSONObject updateComment(
		long commentId, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateComment(commentId, body);
	}

	public static ScreensCommentService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ScreensCommentService, ScreensCommentService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ScreensCommentService.class);

		ServiceTracker<ScreensCommentService, ScreensCommentService> serviceTracker =
			new ServiceTracker<ScreensCommentService, ScreensCommentService>(bundle.getBundleContext(),
				ScreensCommentService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}