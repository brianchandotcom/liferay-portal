/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for UserNotificationInterpreter. This utility wraps
 * {@link com.liferay.portal.service.impl.UserNotificationInterpreterLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationInterpreterLocalService
 * @see com.liferay.portal.service.base.UserNotificationInterpreterLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserNotificationInterpreterLocalServiceImpl
 * @generated
 */
public class UserNotificationInterpreterLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.UserNotificationInterpreterLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds the use notification interpreter to the list of available
	* interpreters.
	*
	* @param userNotificationInterpreter the user notification interpreter
	*/
	public static void addUserNotificationInterpreter(
		com.liferay.portal.kernel.notifications.UserNotificationInterpreter userNotificationInterpreter) {
		getService().addUserNotificationInterpreter(userNotificationInterpreter);
	}

	/**
	* Removes the user notification interpreter from the list of available
	* interpreters.
	*
	* @param userNotificationInterpreter the user notification interpreter
	*/
	public static void deleteUserNotificationInterpreter(
		com.liferay.portal.kernel.notifications.UserNotificationInterpreter userNotificationInterpreter) {
		getService()
			.deleteUserNotificationInterpreter(userNotificationInterpreter);
	}

	public static java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.liferay.portal.kernel.notifications.UserNotificationInterpreter>> getUserNotificationInterpreters() {
		return getService().getUserNotificationInterpreters();
	}

	/**
	* Creates a human readable user notification feed entry for the user
	* notification using an available compatible user notification interpreter.
	*
	* <p>
	* This method finds the appropriate interpreter for the user notification
	* by going through the available interpreters and asking them if they can
	* handle the user notidication based on the portlet.
	* </p>
	*
	* @param userNotificationEvent the user notification event to be
	translated to human readable form
	* @return the user notification feed that is a human readable form of the
	user notification record or <code>null</code> if a compatible
	interpreter is not found
	*/
	public static com.liferay.portal.kernel.notifications.UserNotificationFeedEntry interpret(
		java.lang.String selector,
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .interpret(selector, userNotificationEvent, serviceContext);
	}

	public static UserNotificationInterpreterLocalService getService() {
		if (_service == null) {
			_service = (UserNotificationInterpreterLocalService)PortalBeanLocatorUtil.locate(UserNotificationInterpreterLocalService.class.getName());

			ReferenceRegistry.registerReference(UserNotificationInterpreterLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(UserNotificationInterpreterLocalService service) {
	}

	private static UserNotificationInterpreterLocalService _service;
}