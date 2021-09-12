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

/**
 * Provides the local service utility for SocialRequestInterpreter. This utility wraps
 * <code>com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestInterpreterLocalService
 * @generated
 */
public class SocialRequestInterpreterLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	public static com.liferay.social.kernel.model.SocialRequestFeedEntry
		interpret(
			com.liferay.social.kernel.model.SocialRequest request,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		return getService().interpret(request, themeDisplay);
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
	public static void processConfirmation(
		com.liferay.social.kernel.model.SocialRequest request,
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		getService().processConfirmation(request, themeDisplay);
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
	public static void processRejection(
		com.liferay.social.kernel.model.SocialRequest request,
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		getService().processRejection(request, themeDisplay);
	}

	public static SocialRequestInterpreterLocalService getService() {
		return _service;
	}

	private static volatile SocialRequestInterpreterLocalService _service;

}