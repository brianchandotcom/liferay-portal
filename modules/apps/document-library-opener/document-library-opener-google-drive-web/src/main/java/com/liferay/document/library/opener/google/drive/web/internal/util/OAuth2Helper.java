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

package com.liferay.document.library.opener.google.drive.web.internal.util;

import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveWebConstants;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = OAuth2Helper.class)
public class OAuth2Helper {

	public String getRedirectURI(HttpServletRequest httpServletRequest) {
		return _getRedirectURI(_portal.getPortalURL(httpServletRequest));
	}

	public String getRedirectURI(PortletRequest portletRequest) {
		return _getRedirectURI(_portal.getPortalURL(portletRequest));
	}

	private String _getRedirectURI(String portalURL) {
		return portalURL + Portal.PATH_MODULE +
			DLOpenerGoogleDriveWebConstants.GOOGLE_DRIVE_SERVLET_PATH;
	}

	@Reference
	private Portal _portal;

}