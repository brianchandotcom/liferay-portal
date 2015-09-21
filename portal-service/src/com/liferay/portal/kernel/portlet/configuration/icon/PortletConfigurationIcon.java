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

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public interface PortletConfigurationIcon {

	public String getAlt();

	public String getAriaRole();

	public String getCssClass();

	public Map<String, Object> getData();

	public String getIconCssClass();

	public String getId();

	public String getImage();

	public String getImageHover();

	public String getLang();

	public String getLinkCssClass();

	public String getMessage();

	public String getMethod();

	public String getOnClick();

	public String getSrc();

	public String getSrcHover();

	public String getTarget();

	public String getURL();

	public boolean isLabel();

	public boolean isLocalizeMessage();

	public boolean isShow();

	public boolean isToolTip();

	public boolean isUseDialog();

}