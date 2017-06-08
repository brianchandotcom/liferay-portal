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

package com.liferay.image.editor.capability.brightness;

import com.liferay.image.editor.capability.BaseImageEditorCapability;
import com.liferay.image.editor.capability.ImageEditorCapability;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.image.editor.capability.category=adjust",
		"com.liferay.image.editor.capability.controls=brightness",
		"com.liferay.image.editor.capability.icon=sun",
		"com.liferay.image.editor.capability.name=brightness",
		"com.liferay.image.editor.capability.type=tool"
	},
	service = ImageEditorCapability.class
)
public class ImageEditorCapabilityBrightness extends BaseImageEditorCapability {

	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "brightness");
	}

	public String getName() {
		return "brightness";
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.image.editor.capability.brightness)"
	)
	private ServletContext _servletContext;
}