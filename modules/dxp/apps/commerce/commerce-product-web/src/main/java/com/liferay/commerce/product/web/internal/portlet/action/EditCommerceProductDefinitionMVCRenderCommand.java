/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.web.internal.portlet.action;

import com.liferay.commerce.product.exception.NoSuchProductDefinitionException;
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.CommerceProductDefinitionLocalService;
import com.liferay.commerce.product.web.internal.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.web.internal.constants.CommerceProductWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

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
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=/commerce_product_definitions/edit_product_definition"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceProductDefinitionMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			setCommerceProductDefinitionRequestAttribute(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProductDefinitionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/commerce_product_definitions/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/commerce_product_definitions/edit_product_definition.jsp";
	}

	@Reference(unbind = "-")
	protected void setCommerceProductDefinitionLocalService(
		CommerceProductDefinitionLocalService
			commerceProductDefinitionLocalService) {

		_commerceProductDefinitionLocalService =
			commerceProductDefinitionLocalService;
	}

	protected void setCommerceProductDefinitionRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		long commerceProductDefinitionId = ParamUtil.getLong(
			renderRequest, "commerceProductDefinitionId");

		CommerceProductDefinition commerceProductDefinition = null;

		if (commerceProductDefinitionId > 0) {
			commerceProductDefinition =
				_commerceProductDefinitionLocalService.
					getCommerceProductDefinition(commerceProductDefinitionId);
		}

		renderRequest.setAttribute(
			CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION,
			commerceProductDefinition);
	}

	private CommerceProductDefinitionLocalService
		_commerceProductDefinitionLocalService;

	@Reference
	private Portal _portal;

}