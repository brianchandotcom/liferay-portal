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
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.DLSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class DLRequestHelper extends BaseRequestHelper {

	public DLRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public DLPortletInstanceSettings getDLPortletInstanceSettings() {
		try {
			if (_dlPortletInstanceSettings == null) {
				if (getPortletId().equals(PortletKeys.PORTLET_CONFIGURATION)) {
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

	public DLSettings getDLSettings() {
		try {
			if (_dlSettings == null) {
				if (getPortletId().equals(PortletKeys.PORTLET_CONFIGURATION)) {
					_dlSettings = DLSettings.getInstance(
						getScopeGroupId(), getRequest().getParameterMap());
				}
				else {
					_dlSettings = DLSettings.getInstance(getScopeGroupId());
				}
			}

			return _dlSettings;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private DLPortletInstanceSettings _dlPortletInstanceSettings;
	private DLSettings _dlSettings;

}