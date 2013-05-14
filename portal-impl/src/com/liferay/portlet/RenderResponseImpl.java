/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Collection;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class RenderResponseImpl
	extends MimeResponseImpl implements LiferayRenderResponse {

	@Override
	public String getLifecycle() {
		return PortletRequest.RENDER_PHASE;
	}

	public String getResourceName() {
		return _resourceName;
	}

	public String getTitle() {
		return _title;
	}

	public Boolean getUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	@Override
	public void setNextPossiblePortletModes(
		Collection<PortletMode> portletModes) {
	}

	@Override
	public void setResourceName(String resourceName) {
		_resourceName = resourceName;
	}

	@Override
	public void setTitle(String title) {
		_title = title;

		// See LEP-2188

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_portletRequestImpl.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setTitle(_title);
	}

	public void setUseDefaultTemplate(Boolean useDefaultTemplate) {
		_useDefaultTemplate = useDefaultTemplate;
	}

	@Override
	protected void init(
		PortletRequestImpl portletRequestImpl, HttpServletResponse response,
		String portletName, long companyId, long plid) {

		super.init(portletRequestImpl, response, portletName, companyId, plid);

		_portletRequestImpl = portletRequestImpl;
	}

	private PortletRequestImpl _portletRequestImpl;
	private String _resourceName;
	private String _title;
	private Boolean _useDefaultTemplate;

}