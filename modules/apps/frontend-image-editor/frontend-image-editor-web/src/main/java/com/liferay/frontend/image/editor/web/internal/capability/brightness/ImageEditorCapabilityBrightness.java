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

package com.liferay.frontend.image.editor.web.internal.capability.brightness;

import com.liferay.frontend.image.editor.capability.BaseImageEditorCapability;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

/**
 * @author Bruno Basto
 */
public class ImageEditorCapabilityBrightness extends BaseImageEditorCapability {

	public ImageEditorCapabilityBrightness(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "brightness");
	}

	@Override
	public String getName() {
		return "brightness";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private final ServletContext _servletContext;

}