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

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.constants.CommerceProductWebKeys;
import com.liferay.commerce.product.exception.NoSuchProductOptionValueException;
import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.service.CommerceProductOptionValueLocalService;
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
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_OPTIONS,
		"mvc.command.name=editProductOptionValue"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceProductOptionValueMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			setCommerceProductOptionValueRequestAttribute(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProductOptionValueException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/edit_product_option_value.jsp";
	}

	protected void setCommerceProductOptionValueRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		long commerceProductOptionValueId = ParamUtil.getLong(
			renderRequest, "commerceProductOptionValueId");

		CommerceProductOptionValue commerceProductOptionValue = null;

		if (commerceProductOptionValueId > 0) {
			commerceProductOptionValue =
				_commerceProductOptionValueLocalService.
					getCommerceProductOptionValue(commerceProductOptionValueId);
		}

		renderRequest.setAttribute(
			CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION_VALUE,
			commerceProductOptionValue);
	}

	@Reference
	private CommerceProductOptionValueLocalService
		_commerceProductOptionValueLocalService;

}