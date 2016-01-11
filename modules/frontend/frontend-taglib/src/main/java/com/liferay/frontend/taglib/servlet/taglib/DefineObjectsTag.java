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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Adolfo Pérez
 */
public class DefineObjectsTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		pageContext.setAttribute("resourceBundle", getResourceBundle());

		return SKIP_BODY;
	}

	public void setOverrideResourceBundle(
		ResourceBundle overrideResourceBundle) {

		_overrideResourceBundle = overrideResourceBundle;
	}

	public void setResourceBundleBaseName(String resourceBundleBaseName) {
		_resourceBundleBaseName = resourceBundleBaseName;
	}

	protected ResourceBundle getResourceBundle() {
		if (_overrideResourceBundle != null) {
			return _overrideResourceBundle;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		Locale locale = PortalUtil.getLocale(request);

		return TagResourceBundleUtil.getResourceBundle(
			pageContext, _resourceBundleBaseName, locale);
	}

	private static final String _DEFAULT_RESOURCE_BUNDLE_BASE_NAME =
		"content.Language";

	private ResourceBundle _overrideResourceBundle;
	private String _resourceBundleBaseName = _DEFAULT_RESOURCE_BUNDLE_BASE_NAME;

}