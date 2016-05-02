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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.shopping.constants.ShoppingPortletKeys;
import com.liferay.shopping.exception.NoSuchCouponException;
import com.liferay.shopping.model.ShoppingCoupon;
import com.liferay.shopping.service.ShoppingCouponLocalService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN,
		"mvc.command.name=/shopping/view_coupon"
	},
	service = MVCRenderCommand.class
)
public class ViewCouponMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long couponId = ParamUtil.getLong(renderRequest, "couponId");

			String code = ParamUtil.getString(renderRequest, "code");

			ShoppingCoupon coupon = null;

			if (couponId > 0) {
				coupon = _shoppingCouponLocalService.getCoupon(couponId);
			}
			else {
				coupon = _shoppingCouponLocalService.getCoupon(code);
			}

			renderRequest.setAttribute(WebKeys.SHOPPING_COUPON, coupon);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCouponException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/view_coupon.jsp";
	}

	@Reference(unbind = "-")
	protected void setShoppingCouponLocalService(
		ShoppingCouponLocalService shoppingCouponLocalService) {

		_shoppingCouponLocalService = shoppingCouponLocalService;
	}

	private ShoppingCouponLocalService _shoppingCouponLocalService;

}