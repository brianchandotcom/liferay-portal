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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 */
public class NavigationItem extends HashMap<String, Object> {

	public NavigationItem() {
		_request = null;
	}

	public NavigationItem(HttpServletRequest request) {
		_request = request;
	}

	public void setActive(boolean active) {
		put("active", active);
	}

	public void setDisabled(boolean disabled) {
		put("disabled", disabled);
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

				if (Validator.isNotNull(parameterValue)) {
					portletURL.setParameter(parameterName, parameterValue);
				}
				else {
					portletURL.setParameter(parameterName, (String)null);
				}
			}
		}

		setHref(portletURL.toString());
	}

	public void setHref(String href) {
		put("href", href);
	}

	public void setLabel(String label) {
		if (Validator.isNotNull(_request)) {
			label = LanguageUtil.get(_request, label);
		}

		put("label", label);
	}

	private final HttpServletRequest _request;

}