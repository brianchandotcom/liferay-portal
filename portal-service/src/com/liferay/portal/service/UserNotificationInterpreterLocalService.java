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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for UserNotificationInterpreter. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationInterpreterLocalServiceUtil
 * @see com.liferay.portal.service.base.UserNotificationInterpreterLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserNotificationInterpreterLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface UserNotificationInterpreterLocalService
	extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserNotificationInterpreterLocalServiceUtil} to access the user notification interpreter local service. Add custom service methods to {@link com.liferay.portal.service.impl.UserNotificationInterpreterLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Adds the use notification interpreter to the list of available
	* interpreters.
	*
	* @param userNotificationInterpreter the user notification interpreter
	*/
	public void addUserNotificationInterpreter(
		com.liferay.portal.kernel.notifications.UserNotificationInterpreter userNotificationInterpreter);

	/**
	* Removes the user notification interpreter from the list of available
	* interpreters.
	*
	* @param userNotificationInterpreter the user notification interpreter
	*/
	public void deleteUserNotificationInterpreter(
		com.liferay.portal.kernel.notifications.UserNotificationInterpreter userNotificationInterpreter);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.liferay.portal.kernel.notifications.UserNotificationInterpreter>> getUserNotificationInterpreters();

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
	public com.liferay.portal.kernel.notifications.UserNotificationFeedEntry interpret(
		java.lang.String selector,
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;
}