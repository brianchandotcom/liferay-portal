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
import com.liferay.portal.kernel.editor.Editor;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 * @author Roberto Díaz
 */
@Component(
	property = "name=alloyeditor",
	service = {Editor.class, EditorRenderer.class}
)
public class AlloyEditorEditor implements Editor, EditorRenderer {

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
	public String getName() {
		return _name;
	}

	@Override
	public String getResourcesJspPath() {
		return "/resources.jsp";
	}

	@Override
	public String getResourceType() {
		return PortalWebResourceConstants.RESOURCE_TYPE_EDITOR_ALLOYEDITOR;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_name = (String)properties.get("name");
	}

	private String _name;

}