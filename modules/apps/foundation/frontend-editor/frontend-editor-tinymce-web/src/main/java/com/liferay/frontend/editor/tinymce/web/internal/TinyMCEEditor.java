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

package com.liferay.frontend.editor.tinymce.web.internal;

import com.liferay.portal.kernel.editor.Editor;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 * @author Roberto Díaz
 */
@Component(service = Editor.class)
public class TinyMCEEditor implements Editor {

	@Override
	public String[] getJavaScriptModules() {
		return new String[0];
	}

	@Override
	public String getJspPath() {
		return "/tinymce.jsp";
	}

	@Override
	public String getName() {
		return "tinymce";
	}

	@Override
	public String getResourceType() {
		return PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_TINYMCEEDITOR;
	}

}