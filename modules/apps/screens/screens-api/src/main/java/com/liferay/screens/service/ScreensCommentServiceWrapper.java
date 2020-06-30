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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ScreensCommentService}.
 *
 * @author José Manuel Navarro
 * @see ScreensCommentService
 * @generated
 */
public class ScreensCommentServiceWrapper
	implements ScreensCommentService, ServiceWrapper<ScreensCommentService> {

	public ScreensCommentServiceWrapper(
		ScreensCommentService screensCommentService) {

		_screensCommentService = screensCommentService;
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject addComment(
			String className, long classPK, String body)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensCommentService.addComment(className, classPK, body);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getComment(long commentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensCommentService.getComment(commentId);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getComments(
			String className, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensCommentService.getComments(
			className, classPK, start, end);
	}

	@Override
	public int getCommentsCount(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensCommentService.getCommentsCount(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _screensCommentService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject updateComment(
			long commentId, String body)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _screensCommentService.updateComment(commentId, body);
	}

	@Override
	public ScreensCommentService getWrappedService() {
		return _screensCommentService;
	}

	@Override
	public void setWrappedService(ScreensCommentService screensCommentService) {
		_screensCommentService = screensCommentService;
	}

	private ScreensCommentService _screensCommentService;

}