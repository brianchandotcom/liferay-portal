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

package com.liferay.frontend.editor.alloyeditor.web.internal;

import com.liferay.portal.kernel.editor.Editor;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(service = Editor.class)
public class AlloyEditorCreoleEditor implements Editor {

	@Override
	public String[] getJavaScriptModules() {
		return new String[] {"liferay-alloy-editor"};
	}

	@Override
	public String getJspPath() {
		return "/alloyeditor.jsp";
	}

	@Override
	public String getName() {
		return "alloyeditor_creole";
	}

	@Override
	public String getResourceType() {
		return PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR;
	}

}