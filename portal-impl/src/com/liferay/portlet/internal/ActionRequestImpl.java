/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayActionRequest;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ActionParameters;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
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
		if (getPortletSpecMajorVersion() < 3) {
			throw new UnsupportedOperationException("Requires 3.0 opt-in");
		}

		return _actionParameters;
	}

	@Override
	public String getLifecycle() {
		return PortletRequest.ACTION_PHASE;
	}

	@Override
	public void init(
		HttpServletRequest httpServletRequest, Portlet portlet,
		InvokerPortlet invokerPortlet, PortletContext portletContext,
		WindowState windowState, PortletMode portletMode,
		PortletPreferences preferences, long plid) {

		super.init(
			httpServletRequest, portlet, invokerPortlet, portletContext,
			windowState, portletMode, preferences, plid);

		if (getPortletSpecMajorVersion() >= 3) {
			String portletNamespace = PortalUtil.getPortletNamespace(
				getPortletName());

			_actionParameters = new ActionParametersImpl(
				getPortletParameterMap(httpServletRequest, portletNamespace),
				portletNamespace);
		}
	}

	private ActionParameters _actionParameters;

}