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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialRequestService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestService
 * @generated
 */
public class SocialRequestServiceWrapper
	implements ServiceWrapper<SocialRequestService>, SocialRequestService {

	public SocialRequestServiceWrapper(
		SocialRequestService socialRequestService) {

		_socialRequestService = socialRequestService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _socialRequestService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.social.kernel.model.SocialRequest updateRequest(
			long requestId, int status,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialRequestService.updateRequest(
			requestId, status, themeDisplay);
	}

	@Override
	public SocialRequestService getWrappedService() {
		return _socialRequestService;
	}

	@Override
	public void setWrappedService(SocialRequestService socialRequestService) {
		_socialRequestService = socialRequestService;
	}

	private SocialRequestService _socialRequestService;

}