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

/**
 * Provides a wrapper for {@link UserNotificationInterpreterLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationInterpreterLocalService
 * @generated
 */
public class UserNotificationInterpreterLocalServiceWrapper
	implements UserNotificationInterpreterLocalService,
		ServiceWrapper<UserNotificationInterpreterLocalService> {
	public UserNotificationInterpreterLocalServiceWrapper(
		UserNotificationInterpreterLocalService userNotificationInterpreterLocalService) {
		_userNotificationInterpreterLocalService = userNotificationInterpreterLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _userNotificationInterpreterLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_userNotificationInterpreterLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds the use notification interpreter to the list of available
	* interpreters.
	*
	* @param userNotificationInterpreter the user notification interpreter
	*/
	@Override
	public void addUserNotificationInterpreter(
		com.liferay.portal.kernel.notifications.UserNotificationInterpreter userNotificationInterpreter) {
		_userNotificationInterpreterLocalService.addUserNotificationInterpreter(userNotificationInterpreter);
	}

	/**
	* Removes the user notification interpreter from the list of available
	* interpreters.
	*
	* @param userNotificationInterpreter the user notification interpreter
	*/
	@Override
	public void deleteUserNotificationInterpreter(
		com.liferay.portal.kernel.notifications.UserNotificationInterpreter userNotificationInterpreter) {
		_userNotificationInterpreterLocalService.deleteUserNotificationInterpreter(userNotificationInterpreter);
	}

	@Override
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.liferay.portal.kernel.notifications.UserNotificationInterpreter>> getUserNotificationInterpreters() {
		return _userNotificationInterpreterLocalService.getUserNotificationInterpreters();
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
	@Override
	public com.liferay.portal.kernel.notifications.UserNotificationFeedEntry interpret(
		java.lang.String selector,
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userNotificationInterpreterLocalService.interpret(selector,
			userNotificationEvent, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public UserNotificationInterpreterLocalService getWrappedUserNotificationInterpreterLocalService() {
		return _userNotificationInterpreterLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedUserNotificationInterpreterLocalService(
		UserNotificationInterpreterLocalService userNotificationInterpreterLocalService) {
		_userNotificationInterpreterLocalService = userNotificationInterpreterLocalService;
	}

	@Override
	public UserNotificationInterpreterLocalService getWrappedService() {
		return _userNotificationInterpreterLocalService;
	}

	@Override
	public void setWrappedService(
		UserNotificationInterpreterLocalService userNotificationInterpreterLocalService) {
		_userNotificationInterpreterLocalService = userNotificationInterpreterLocalService;
	}

	private UserNotificationInterpreterLocalService _userNotificationInterpreterLocalService;
}