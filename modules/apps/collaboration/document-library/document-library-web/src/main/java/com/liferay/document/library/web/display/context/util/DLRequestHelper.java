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

package com.liferay.document.library.web.display.context.util;

import com.liferay.document.library.web.settings.internal.DLPortletInstanceSettings;
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

	public DLRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public DLGroupServiceSettings getDLGroupServiceSettings() {
		if (_dlGroupServiceSettings != null) {
			return _dlGroupServiceSettings;
		}

		HttpServletRequest request = getRequest();

		_dlGroupServiceSettings = (DLGroupServiceSettings)request.getAttribute(
			_DL_GROUP_SERVICE_SETTINGS_KEY);

		if (_dlGroupServiceSettings != null) {
			return _dlGroupServiceSettings;
		}

		String portletResource = getPortletResource();

		try {
			if (Validator.isNotNull(portletResource)) {
				_dlGroupServiceSettings = DLGroupServiceSettings.getInstance(
					getScopeGroupId(), request.getParameterMap());
			}
			else {
				_dlGroupServiceSettings = DLGroupServiceSettings.getInstance(
					getScopeGroupId());
			}
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		request.setAttribute(
			_DL_GROUP_SERVICE_SETTINGS_KEY, _dlGroupServiceSettings);

		return _dlGroupServiceSettings;
	}

	public DLPortletInstanceSettings getDLPortletInstanceSettings() {
		if (_dlGroupServiceSettings != null) {
			return _dlPortletInstanceSettings;
		}

		HttpServletRequest request = getRequest();

		_dlPortletInstanceSettings =
			(DLPortletInstanceSettings)request.getAttribute(
				_DL_PORTLET_INSTANCE_SETTINGS_KEY);

		if (_dlPortletInstanceSettings != null) {
			return _dlPortletInstanceSettings;
		}

		String portletResource = getPortletResource();

		try {
			if (Validator.isNotNull(portletResource)) {
				_dlPortletInstanceSettings =
					DLPortletInstanceSettings.getInstance(
						getLayout(), getResourcePortletId(),
						request.getParameterMap());
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

		request.setAttribute(
			_DL_PORTLET_INSTANCE_SETTINGS_KEY, _dlPortletInstanceSettings);

		return _dlPortletInstanceSettings;
	}

	private static final String _DL_GROUP_SERVICE_SETTINGS_KEY =
		"DL_GROUP_SERVICE_SETTINGS";

	private static final String _DL_PORTLET_INSTANCE_SETTINGS_KEY =
		"DL_PORTLET_INSTANCE_SETTINGS";

	private DLGroupServiceSettings _dlGroupServiceSettings;
	private DLPortletInstanceSettings _dlPortletInstanceSettings;

}