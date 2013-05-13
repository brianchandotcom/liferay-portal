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

package com.liferay.portlet.expando.model;

import com.liferay.portal.theme.ThemeDisplay;

/**
 * @author Jorge Ferrer
 */
public abstract class BaseCustomAttributesDisplay
	implements CustomAttributesDisplay {

	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	private long _classNameId;
	private String _portletId;

}