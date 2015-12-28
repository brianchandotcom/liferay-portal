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

package com.liferay.portlet.social.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialRequestInterpreterLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestInterpreterLocalService
 * @generated
 */
@ProviderType
public class SocialRequestInterpreterLocalServiceWrapper
	implements SocialRequestInterpreterLocalService,
		ServiceWrapper<SocialRequestInterpreterLocalService> {
	public SocialRequestInterpreterLocalServiceWrapper(
		SocialRequestInterpreterLocalService socialRequestInterpreterLocalService) {
		_socialRequestInterpreterLocalService = socialRequestInterpreterLocalService;
	}

	/**
	* Adds the social request interpreter to the list of available
	* interpreters.
	*
	* @param requestInterpreter the social request interpreter
	*/
	@Override
	public void addRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter) {
		_socialRequestInterpreterLocalService.addRequestInterpreter(requestInterpreter);
	}

	/**
	* Removes the social request interpreter from the list of available
	* interpreters.
	*
	* @param requestInterpreter the social request interpreter
	*/
	@Override
	public void deleteRequestInterpreter(
		com.liferay.portlet.social.model.SocialRequestInterpreter requestInterpreter) {
		_socialRequestInterpreterLocalService.deleteRequestInterpreter(requestInterpreter);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _socialRequestInterpreterLocalService.getOSGiServiceIdentifier();
	}

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
	@Override
	public com.liferay.portlet.social.model.SocialRequestFeedEntry interpret(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _socialRequestInterpreterLocalService.interpret(request,
			themeDisplay);
	}

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
	@Override
	public void processConfirmation(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		_socialRequestInterpreterLocalService.processConfirmation(request,
			themeDisplay);
	}

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
	@Override
	public void processRejection(
		com.liferay.portlet.social.model.SocialRequest request,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		_socialRequestInterpreterLocalService.processRejection(request,
			themeDisplay);
	}

	@Override
	public SocialRequestInterpreterLocalService getWrappedService() {
		return _socialRequestInterpreterLocalService;
	}

	@Override
	public void setWrappedService(
		SocialRequestInterpreterLocalService socialRequestInterpreterLocalService) {
		_socialRequestInterpreterLocalService = socialRequestInterpreterLocalService;
	}

	private SocialRequestInterpreterLocalService _socialRequestInterpreterLocalService;
}