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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DynamicServletRequestUtil {

	public static HttpServletRequest createDynamicServletRequest(
		HttpServletRequest httpServletRequest, Portlet portlet,
		Map<String, String[]> parameterMap, boolean mergeParameters) {

		DynamicServletRequest dynamicServletRequest = null;

		if (portlet.isPrivateRequestAttributes()) {
			String portletNamespace = PortalUtil.getPortletNamespace(
				portlet.getPortletName());

			dynamicServletRequest = new NamespaceServletRequest(
				httpServletRequest, portletNamespace, portletNamespace);
		}
		else {
			dynamicServletRequest = new DynamicServletRequest(
				httpServletRequest);
		}

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();

			String[] values = entry.getValue();

			String[] oldValues = dynamicServletRequest.getParameterValues(name);

			if (mergeParameters && (oldValues != null)) {
				values = ArrayUtil.append(values, oldValues);
			}

			dynamicServletRequest.setParameterValues(name, values);
		}

		return dynamicServletRequest;
	}

}