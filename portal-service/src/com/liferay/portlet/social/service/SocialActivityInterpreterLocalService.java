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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;

/**
 * Provides the local service interface for SocialActivityInterpreter. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityInterpreterLocalServiceUtil
 * @see com.liferay.portlet.social.service.base.SocialActivityInterpreterLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialActivityInterpreterLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SocialActivityInterpreterLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityInterpreterLocalServiceUtil} to access the social activity interpreter local service. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialActivityInterpreterLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
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
	* Adds the activity interpreter to the list of available interpreters.
	*
	* @param activityInterpreter the activity interpreter
	*/
	public void addActivityInterpreter(
		com.liferay.portlet.social.model.SocialActivityInterpreter activityInterpreter);

	/**
	* Removes the activity interpreter from the list of available interpreters.
	*
	* @param activityInterpreter the activity interpreter
	*/
	public void deleteActivityInterpreter(
		com.liferay.portlet.social.model.SocialActivityInterpreter activityInterpreter);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.Map<java.lang.String, java.util.List<com.liferay.portlet.social.model.SocialActivityInterpreter>> getActivityInterpreters();

	/**
	* @deprecated As of 6.2.0, replaced by {@link #interpret(String,
	SocialActivity, ServiceContext)}
	*/
	public com.liferay.portlet.social.model.SocialActivityFeedEntry interpret(
		com.liferay.portlet.social.model.SocialActivity activity,
		com.liferay.portal.theme.ThemeDisplay themeDisplay);

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
	* @param activity the activity to be translated to human readable form
	* @return the activity feed that is a human readable form of the activity
	record or <code>null</code> if a compatible interpreter is not
	found
	*/
	public com.liferay.portlet.social.model.SocialActivityFeedEntry interpret(
		java.lang.String selector,
		com.liferay.portlet.social.model.SocialActivity activity,
		com.liferay.portal.service.ServiceContext serviceContext);

	public com.liferay.portlet.social.model.SocialActivityFeedEntry interpret(
		java.lang.String selector,
		com.liferay.portlet.social.model.SocialActivitySet activitySet,
		com.liferay.portal.service.ServiceContext serviceContext);

	public void updateActivitySet(long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}