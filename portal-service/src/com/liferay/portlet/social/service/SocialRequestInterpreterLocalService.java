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
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;

/**
 * Provides the local service interface for SocialRequestInterpreter. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestInterpreterLocalServiceUtil
 * @see com.liferay.portlet.social.service.base.SocialRequestInterpreterLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SocialRequestInterpreterLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialRequestInterpreterLocalServiceUtil} to access the social request interpreter local service. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
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
	* Adds the social request interpreter to the list of available
	* interpreters.
	*
	* @param requestInterpreter the social request interpreter
	*/
	public void addRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter);

	/**
	* Removes the social request interpreter from the list of available
	* interpreters.
	*
	* @param requestInterpreter the social request interpreter
	*/
	public void deleteRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter);

	/**
	* Creates a human readable request feed entry for the social request using
	* an available compatible request interpreter.
	*
	* <p>
	* This method finds the appropriate interpreter for the request by going
	* through the available interpreters to find one that can handle the asset
	* type of the request.
	* </p>
	*
	* @param request the social request to be translated to human readable
	form
	* @param themeDisplay the theme display needed by interpreters to create
	links and get localized text fragments
	* @return the social request feed entry
	*/
	public com.liferay.portlet.social.model.SocialRequestFeedEntry interpret(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay);

	/**
	* Processes the confirmation of the social request.
	*
	* <p>
	* Confirmations are handled by finding the appropriate social request
	* interpreter and calling its processConfirmation() method. To find the
	* appropriate interpreter this method goes through the available
	* interpreters to find one that can handle the asset type of the request.
	* </p>
	*
	* @param request the social request being confirmed
	* @param themeDisplay the theme display needed by interpreters to create
	links and get localized text fragments
	*/
	public void processConfirmation(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay);

	/**
	* Processes the rejection of the social request.
	*
	* <p>
	* Rejections are handled by finding the appropriate social request
	* interpreters and calling their processRejection() methods. To find the
	* appropriate interpreters this method goes through the available
	* interpreters and asks them if they can handle the asset type of the
	* request.
	* </p>
	*
	* @param request the social request being rejected
	* @param themeDisplay the theme display needed by interpreters to create
	links and get localized text fragments
	*/
	public void processRejection(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay);
}