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

package com.liferay.social.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for SocialActivityInterpreter. This utility wraps
 * <code>com.liferay.portlet.social.service.impl.SocialActivityInterpreterLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityInterpreterLocalService
 * @generated
 */
public class SocialActivityInterpreterLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.social.service.impl.SocialActivityInterpreterLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Map
		<String,
		 List<com.liferay.social.kernel.model.SocialActivityInterpreter>>
			getActivityInterpreters() {

		return getService().getActivityInterpreters();
	}

	public static List
		<com.liferay.social.kernel.model.SocialActivityInterpreter>
			getActivityInterpreters(String selector) {

		return getService().getActivityInterpreters(selector);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * Creates a human readable activity feed entry for the activity using an
	 * available compatible activity interpreter.
	 *
	 * <p>
	 * This method finds the appropriate interpreter for the activity by going
	 * through the available interpreters and asking them if they can handle the
	 * asset type of the activity.
	 * </p>
	 *
	 * @param selector the context in which the activity interpreter is used
	 * @param activity the activity to be translated to human readable form
	 * @param serviceContext the service context to be applied
	 * @return the activity feed that is a human readable form of the activity
	 record or <code>null</code> if a compatible interpreter is not
	 found
	 */
	public static com.liferay.social.kernel.model.SocialActivityFeedEntry
		interpret(
			String selector,
			com.liferay.social.kernel.model.SocialActivity activity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return getService().interpret(selector, activity, serviceContext);
	}

	public static com.liferay.social.kernel.model.SocialActivityFeedEntry
		interpret(
			String selector,
			com.liferay.social.kernel.model.SocialActivitySet activitySet,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return getService().interpret(selector, activitySet, serviceContext);
	}

	public static void updateActivitySet(long activityId)
		throws PortalException {

		getService().updateActivitySet(activityId);
	}

	public static SocialActivityInterpreterLocalService getService() {
		return _service;
	}

	private static volatile SocialActivityInterpreterLocalService _service;

}