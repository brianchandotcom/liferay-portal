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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayActionRequest;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionParameters;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderParameters;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class ActionRequestImpl
	extends ClientDataRequestImpl implements LiferayActionRequest {

	@Override
	public ActionParameters getActionParameters() {
		return _actionParameters;
	}

	@Override
	public String getLifecycle() {
		return PortletRequest.ACTION_PHASE;
	}

	@Override
	public void init(
		HttpServletRequest request, Portlet portlet,
		InvokerPortlet invokerPortlet, PortletContext portletContext,
		WindowState windowState, PortletMode portletMode,
		PortletPreferences preferences, long plid) {

		super.init(
			request, portlet, invokerPortlet, portletContext, windowState,
			portletMode, preferences, plid);

		Map<String, String[]> actionParameterMap = new LinkedHashMap<>();
		Map<String, String[]> parameterMap = getParameterMap();
		String portletNamespace = PortalUtil.getPortletNamespace(
			getPortletName());
		Map<String, String[]> servletRequestParameterMap =
			request.getParameterMap();
		RenderParameters renderParameters = getRenderParameters();

		Set<String> renderParameterNames = renderParameters.getNames();

		for (Map.Entry<String, String[]> mapEntry : parameterMap.entrySet()) {
			String name = mapEntry.getKey();

			// If the parameter name is not a public/private render parameter,
			// then it is an action parameter. Also, if the parameter name is
			// prefixed with the portlet namespace in the original request, then
			// it is to be regarded as an action parameter (even if it has the
			// same name as a public render parameter). See: TCK
			// V3PortletParametersTests_SPEC11_3_getNames

			if (!renderParameterNames.contains(name)) {
				actionParameterMap.put(name, mapEntry.getValue());
			}
			else {
				String namespacedParameter = portletNamespace + name;

				if (renderParameterNames.contains(name) &&
					servletRequestParameterMap.containsKey(
						namespacedParameter)) {

					actionParameterMap.put(
						name,
						servletRequestParameterMap.get(namespacedParameter));
				}
			}
		}

		_actionParameters = new ActionParametersImpl(
			actionParameterMap, portletNamespace);
	}

	private ActionParameters _actionParameters;

}