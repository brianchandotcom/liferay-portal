/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.spi.history.util;

import com.liferay.change.tracking.spi.constants.CTTimelineKeys;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cheryl Tang
 */
public class CTCollectionTimelineUtil {

	public static void setClassName(
		RenderRequest renderRequest, Class<?> clazz) {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		httpServletRequest.setAttribute(
			CTTimelineKeys.CLASS_NAME, clazz.getName());
	}

	public static void setCTTimelineKeys(
		RenderRequest renderRequest, Class<?> clazz, long classPK) {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		setClassName(renderRequest, clazz);

		httpServletRequest.setAttribute(CTTimelineKeys.CLASS_PK, classPK);
	}

}