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
import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationUtil;

import java.util.Collection;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;

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

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public boolean getUseDefaultTemplate() {
		if (_useDefaultTemplate == null) {
			Portlet portlet = getPortlet();

			return portlet.isUseDefaultTemplate();
		}

		return _useDefaultTemplate;
	}

	@Override
	public void setNextPossiblePortletModes(
		Collection<? extends PortletMode> portletModes) {
	}

	@Override
	public void setResourceName(String resourceName) {
		_resourceName = resourceName;
	}

	@Override
	public void setTitle(String title) {

		// See LEP-2188

		ThemeDisplay themeDisplay =
			(ThemeDisplay)portletRequestImpl.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String localizedCustomTitle = PortletConfigurationUtil.getPortletTitle(
			portletDisplay.getPortletSetup(), themeDisplay.getLanguageId());

		if (portletDisplay.isActive() &&
			Validator.isNull(localizedCustomTitle)) {

			_title = title;
		}
		else {
			_title = localizedCustomTitle;
		}

		portletDisplay.setTitle(_title);
	}

	@Override
	public void setUseDefaultTemplate(Boolean useDefaultTemplate) {
		_useDefaultTemplate = useDefaultTemplate;
	}

	private String _resourceName;
	private String _title;
	private Boolean _useDefaultTemplate;

}