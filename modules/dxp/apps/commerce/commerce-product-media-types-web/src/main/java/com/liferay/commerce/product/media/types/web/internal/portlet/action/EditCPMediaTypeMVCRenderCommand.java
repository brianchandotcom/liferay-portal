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

package com.liferay.commerce.product.media.types.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.exception.NoSuchCPMediaTypeException;
import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.CPMediaTypeService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_MEDIA_TYPES,
		"mvc.command.name=editMediaType"
	},
	service = MVCRenderCommand.class
)
public class EditCPMediaTypeMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			setCPMediaTypeRequestAttribute(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPMediaTypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/edit_media_type.jsp";
	}

	protected void setCPMediaTypeRequestAttribute(RenderRequest renderRequest)
		throws PortalException {

		long cpMediaTypeId = ParamUtil.getLong(renderRequest, "cpMediaTypeId");

		CPMediaType cpMediaType = null;

		if (cpMediaTypeId > 0) {
			cpMediaType = _cpMediaTypeService.getCPMediaType(cpMediaTypeId);
		}

		renderRequest.setAttribute(
			CPWebKeys.COMMERCE_PRODUCT_MEDIA_TYPE, cpMediaType);
	}

	@Reference
	private CPMediaTypeService _cpMediaTypeService;

}