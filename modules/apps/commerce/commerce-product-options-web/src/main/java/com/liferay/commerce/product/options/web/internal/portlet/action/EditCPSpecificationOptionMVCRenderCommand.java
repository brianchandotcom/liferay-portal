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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
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
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_SPECIFICATION_OPTIONS,
		"mvc.command.name=/cp_specification_options/edit_cp_specification_option"
	},
	service = MVCRenderCommand.class
)
public class EditCPSpecificationOptionMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			_setCPSpecificationOptionRequestAttribute(renderRequest);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCPSpecificationOptionException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(renderRequest, exception.getClass());

				return "/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/edit_cp_specification_option.jsp";
	}

	private void _setCPSpecificationOptionRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		long cpSpecificationOptionId = ParamUtil.getLong(
			renderRequest, "cpSpecificationOptionId");

		CPSpecificationOption cpSpecificationOption = null;

		if (cpSpecificationOptionId > 0) {
			cpSpecificationOption =
				_cpSpecificationOptionService.getCPSpecificationOption(
					cpSpecificationOptionId);
		}

		renderRequest.setAttribute(
			CPWebKeys.CP_SPECIFICATION_OPTION, cpSpecificationOption);
	}

	@Reference
	private CPSpecificationOptionService _cpSpecificationOptionService;

}