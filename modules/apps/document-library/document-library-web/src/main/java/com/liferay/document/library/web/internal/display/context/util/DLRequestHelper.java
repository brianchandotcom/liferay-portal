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

package com.liferay.document.library.web.internal.display.context.util;

import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.DLGroupServiceSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class DLRequestHelper extends BaseRequestHelper {

	public DLRequestHelper(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);
	}

	public DLGroupServiceSettings getDLGroupServiceSettings() {
		if (_dlGroupServiceSettings != null) {
			return _dlGroupServiceSettings;
		}

		HttpServletRequest httpServletRequest = getRequest();

		_dlGroupServiceSettings =
			(DLGroupServiceSettings)httpServletRequest.getAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_GROUP_SERVICE_SETTINGS);

		if (_dlGroupServiceSettings != null) {
			return _dlGroupServiceSettings;
		}

		String portletResource = getPortletResource();

		try {
			if (Validator.isNotNull(portletResource)) {
				_dlGroupServiceSettings = DLGroupServiceSettings.getInstance(
					getScopeGroupId(), httpServletRequest.getParameterMap());
			}
			else {
				_dlGroupServiceSettings = DLGroupServiceSettings.getInstance(
					getScopeGroupId());
			}
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		httpServletRequest.setAttribute(
			DLWebKeys.DOCUMENT_LIBRARY_GROUP_SERVICE_SETTINGS,
			_dlGroupServiceSettings);

		return _dlGroupServiceSettings;
	}

	public DLPortletInstanceSettings getDLPortletInstanceSettings() {
		if (_dlPortletInstanceSettings != null) {
			return _dlPortletInstanceSettings;
		}

		HttpServletRequest httpServletRequest = getRequest();

		_dlPortletInstanceSettings =
			(DLPortletInstanceSettings)httpServletRequest.getAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_PORTLET_INSTANCE_SETTINGS);

		if (_dlPortletInstanceSettings != null) {
			return _dlPortletInstanceSettings;
		}

		String portletResource = getPortletResource();

		try {
			if (Validator.isNotNull(portletResource)) {
				_dlPortletInstanceSettings =
					DLPortletInstanceSettings.getInstance(
						getLayout(), getResourcePortletId(),
						httpServletRequest.getParameterMap());
			}
			else {
				_dlPortletInstanceSettings =
					DLPortletInstanceSettings.getInstance(
						getLayout(), getPortletId());
			}
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		httpServletRequest.setAttribute(
			DLWebKeys.DOCUMENT_LIBRARY_PORTLET_INSTANCE_SETTINGS,
			_dlPortletInstanceSettings);

		return _dlPortletInstanceSettings;
	}

	private DLGroupServiceSettings _dlGroupServiceSettings;
	private DLPortletInstanceSettings _dlPortletInstanceSettings;

}