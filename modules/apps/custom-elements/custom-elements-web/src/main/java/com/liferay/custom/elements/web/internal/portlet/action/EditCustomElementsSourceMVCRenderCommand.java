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

package com.liferay.custom.elements.web.internal.portlet.action;

import com.liferay.custom.elements.constants.CustomElementsPortletKeys;
import com.liferay.custom.elements.service.CustomElementsSourceLocalService;
import com.liferay.custom.elements.web.internal.constants.CustomElementsWebKeys;
import com.liferay.custom.elements.web.internal.display.context.CustomElementsAdminDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CustomElementsPortletKeys.CUSTOM_ELEMENTS_ADMIN,
		"mvc.command.name=/admin/edit_custom_elements_source"
	},
	service = MVCRenderCommand.class
)
public class EditCustomElementsSourceMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			CustomElementsWebKeys.CUSTOM_ELEMENTS_ADMIN_DISPLAY_CONTEXT,
			new CustomElementsAdminDisplayContext(
				renderRequest, renderResponse));

		try {
			long customElementsSourceId = ParamUtil.getLong(
				renderRequest, "customElementsSourceId");

			if (customElementsSourceId > 0) {
				renderRequest.setAttribute(
					CustomElementsWebKeys.CUSTOM_ELEMENTS_SOURCE,
					_customElementsSourceLocalService.getCustomElementsSource(
						customElementsSourceId));
			}
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return "/admin/edit.jsp";
	}

	@Reference
	private CustomElementsSourceLocalService _customElementsSourceLocalService;

}