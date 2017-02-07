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

package com.liferay.frontend.editor.alloyeditor.link.browse.web.internal.servlet.taglib;

import com.liferay.portal.kernel.servlet.taglib.TagDynamicIdFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	immediate = true,
	property = {"tagClassName=com.liferay.taglib.ui.InputEditorTag"},
	service = TagDynamicIdFactory.class
)
public class AlloyEditorLinkBrowseInputEditorTagDynamicIdFactory
	implements TagDynamicIdFactory {

	@Override
	public String getTagDynamicId(
		HttpServletRequest request, HttpServletResponse response, Object tag) {

		String editorName = GetterUtil.getString(
			(String)request.getAttribute("liferay-ui:input-editor:editorName"));

		return editorName;
	}

}