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

package com.liferay.portal.kernel.portlet.configuration.icon;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public abstract class BasePortletConfigurationIcon
	implements PortletConfigurationIcon {

	public BasePortletConfigurationIcon(PortletRequest portletRequest) {
		this.portletRequest = portletRequest;

		themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getAlt() {
		return null;
	}

	@Override
	public String getAriaRole() {
		return null;
	}

	@Override
	public String getCssClass() {
		return null;
	}

	@Override
	public Map<String, Object> getData() {
		return null;
	}

	@Override
	public String getIconCssClass() {
		return null;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getImage() {
		return null;
	}

	@Override
	public String getImageHover() {
		return null;
	}

	@Override
	public String getLang() {
		return null;
	}

	@Override
	public String getLinkCssClass() {
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getMethod() {
		return null;
	}

	@Override
	public String getOnClick() {
		return null;
	}

	@Override
	public String getSrc() {
		return null;
	}

	@Override
	public String getSrcHover() {
		return null;
	}

	@Override
	public String getTarget() {
		return null;
	}

	@Override
	public String getURL() {
		return null;
	}

	@Override
	public boolean isLabel() {
		return false;
	}

	@Override
	public boolean isLocalizeMessage() {
		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return false;
	}

	protected PortletRequest portletRequest;
	protected ThemeDisplay themeDisplay;

}