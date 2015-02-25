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

package com.liferay.portlet.documentlibrary.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.DLGroupServiceSettings;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class DLRequestHelper extends BaseRequestHelper {

	public DLRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public DLGroupServiceSettings getDLGroupServiceSettings() {
		try {
			if (_dlGroupServiceSettings == null) {
				String portletId = getPortletId();

				if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
					_dlGroupServiceSettings =
						DLGroupServiceSettings.getInstance(
							getScopeGroupId(), getRequest().getParameterMap());
				}
				else {
					_dlGroupServiceSettings =
						DLGroupServiceSettings.getInstance(getScopeGroupId());
				}
			}

			return _dlGroupServiceSettings;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public DLPortletInstanceSettings getDLPortletInstanceSettings() {
		try {
			if (_dlPortletInstanceSettings == null) {
				String portletId = getPortletId();

				if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
					_dlPortletInstanceSettings =
						DLPortletInstanceSettings.getInstance(
							getLayout(), getResourcePortletId(),
							getRequest().getParameterMap());
				}
				else {
					_dlPortletInstanceSettings =
						DLPortletInstanceSettings.getInstance(
							getLayout(), getPortletId());
				}
			}

			return _dlPortletInstanceSettings;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private DLGroupServiceSettings _dlGroupServiceSettings;
	private DLPortletInstanceSettings _dlPortletInstanceSettings;

}