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

import com.liferay.frontend.editor.EditorRenderer;
import com.liferay.frontend.editor.alloyeditor.web.internal.constants.AlloyEditorConstants;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = {
		"name=" + AlloyEditorBBCodeEditor.EDITOR_NAME,
		"name=" + AlloyEditorCreoleEditor.EDITOR_NAME,
		"name=" + AlloyEditorEditor.EDITOR_NAME
	},
	service = EditorRenderer.class
)
public class AlloyEditorRenderer implements EditorRenderer {

	@Override
	public String getAttributeNamespace() {
		return AlloyEditorConstants.ATTRIBUTE_NAMESPACE;
	}

	@Override
	public String[] getJavaScriptModules() {
		return new String[] {"liferay-alloy-editor"};
	}

	@Override
	public String getJspPath() {
		return "/alloyeditor.jsp";
	}

	@Override
	public String getResourcesJspPath() {
		return "/resources.jsp";
	}

	@Override
	public String getResourceType() {
		return PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR;
	}

}