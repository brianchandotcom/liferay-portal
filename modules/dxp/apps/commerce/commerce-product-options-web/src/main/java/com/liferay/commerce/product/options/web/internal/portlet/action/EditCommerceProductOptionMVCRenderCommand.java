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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.constants.CommerceProductWebKeys;
import com.liferay.commerce.product.exception.NoSuchProductOptionException;
import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.service.CommerceProductOptionLocalService;
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
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_OPTIONS,
		"mvc.command.name=editProductOption"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceProductOptionMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			setCommerceProductOptionRequestAttribute(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProductOptionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/edit_product_option.jsp";
	}


	protected void setCommerceProductOptionRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		long commerceProductOptionId = ParamUtil.getLong(
			renderRequest, "commerceProductOptionId");

		CommerceProductOption commerceProductOption = null;

		if (commerceProductOptionId > 0) {
			commerceProductOption =
				_commerceProductOptionLocalService.getCommerceProductOption(
					commerceProductOptionId);
		}

		renderRequest.setAttribute(
			CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION,
			commerceProductOption);
	}

	@Reference
	private CommerceProductOptionLocalService
		_commerceProductOptionLocalService;

	@Reference
	private Portal _portal;

}