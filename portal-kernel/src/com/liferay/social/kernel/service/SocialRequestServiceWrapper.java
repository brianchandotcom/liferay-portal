/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.social.kernel.model.SocialRequest;

/**
 * Provides a wrapper for {@link SocialRequestService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequestService
 * @generated
 */
public class SocialRequestServiceWrapper
	implements ServiceWrapper<SocialRequestService>, SocialRequestService {

	public SocialRequestServiceWrapper() {
		this(null);
	}

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
	public SocialRequest updateRequest(
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