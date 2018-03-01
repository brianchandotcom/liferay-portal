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

package com.liferay.commerce.vat.web.internal.portlet.action;

import com.liferay.commerce.vat.constants.CommerceVatPortletKeys;
import com.liferay.commerce.vat.service.CommerceVatNumberService;
import com.liferay.commerce.vat.web.internal.display.context.CommerceVatNumberDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceVatPortletKeys.COMMERCE_VAT_NUMBER,
		"mvc.command.name=editCommerceVatNumber"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceVatNumberMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		CommerceVatNumberDisplayContext commerceVatNumberDisplayContext =
			new CommerceVatNumberDisplayContext(
				_commerceVatNumberService, renderRequest, renderResponse);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceVatNumberDisplayContext);

		return "/edit_vat_number.jsp";
	}

	@Reference
	private CommerceVatNumberService _commerceVatNumberService;

}