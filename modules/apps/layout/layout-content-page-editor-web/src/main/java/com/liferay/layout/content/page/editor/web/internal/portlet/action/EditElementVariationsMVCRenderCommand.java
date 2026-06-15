/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/edit_element_variations"
	},
	service = MVCRenderCommand.class
)
public class EditElementVariationsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return "/edit_element_variations.jsp";
	}

}
