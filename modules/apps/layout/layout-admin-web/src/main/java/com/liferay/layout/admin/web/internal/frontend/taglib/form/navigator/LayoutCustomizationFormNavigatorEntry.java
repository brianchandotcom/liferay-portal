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

package com.liferay.layout.admin.web.internal.frontend.taglib.form.navigator;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorConstants;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "form.navigator.entry.order:Integer=100",
	service = FormNavigatorEntry.class
)
public class LayoutCustomizationFormNavigatorEntry
	extends BaseLayoutFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_LAYOUT_LOOK_AND_FEEL;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT_DESIGN;
	}

	@Override
	public String getKey() {
		return "customization";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	protected String getJspPath() {
		return "/layout/customization.jsp";
	}

	@Reference(target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)")
	private ServletContext _servletContext;

}