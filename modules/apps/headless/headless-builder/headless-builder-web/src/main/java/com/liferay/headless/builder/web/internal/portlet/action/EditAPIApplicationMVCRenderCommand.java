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

package com.liferay.headless.builder.web.internal.portlet.action;

import com.liferay.headless.builder.web.internal.constants.HeadlessBuilderPortletKeys;
import com.liferay.headless.builder.web.internal.display.context.HeadlessBuilderWebDisplayContext;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Montenegro
 */
@Component(
	property = {
		"javax.portlet.name=" + HeadlessBuilderPortletKeys.HEADLESS_BUILDER,
		"mvc.command.name=/headless_builder/edit_api_application"
	},
	service = MVCRenderCommand.class
)
public class EditAPIApplicationMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new HeadlessBuilderWebDisplayContext(
				_editorConfigurationFactory,
				_portal.getHttpServletRequest(renderRequest)));

		return "/headless_builder/edit_api_application.jsp";
	}

	@Reference
	private EditorConfigurationFactory _editorConfigurationFactory;

	@Reference
	private Portal _portal;

}