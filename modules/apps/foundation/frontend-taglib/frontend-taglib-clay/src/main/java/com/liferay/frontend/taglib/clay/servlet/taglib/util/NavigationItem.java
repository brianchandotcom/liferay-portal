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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.io.Serializable;

import javax.portlet.PortletURL;

/**
 * @author Chema Balsas
 */
public class NavigationItem implements Serializable {

	public String getHref() {
		return _href;
	}

	public String getLabel() {
		return _label;
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setHref(PortletURL portletURL, Object... parameters) {
		if (parameters != null) {
			if ((parameters.length % 2) != 0) {
				throw new IllegalArgumentException(
					"Parameters length is not an even number");
			}

			for (int i = 0; i < parameters.length; i += 2) {
				String parameterName = String.valueOf(parameters[i]);
				String parameterValue = String.valueOf(parameters[i + 1]);

				portletURL.setParameter(parameterName, parameterValue);
			}
		}

		_href = portletURL.toString();
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setLabel(String label) {
		_label = label;
	}

	private boolean _active;
	private boolean _disabled;
	private String _href;
	private String _label;

}