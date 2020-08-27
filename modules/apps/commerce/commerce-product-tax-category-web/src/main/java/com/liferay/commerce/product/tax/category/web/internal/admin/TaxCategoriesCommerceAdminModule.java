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

package com.liferay.commerce.product.tax.category.web.internal.admin;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.tax.category.web.internal.display.context.CPTaxCategoryDisplayContext;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.admin.module.key=" + TaxCategoriesCommerceAdminModule.KEY,
	service = CommerceAdminModule.class
)
public class TaxCategoriesCommerceAdminModule implements CommerceAdminModule {

	public static final String KEY = "tax-categories";

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, KEY);
	}

	@Override
	public PortletURL getSearchURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return null;
	}

	@Override
	public boolean isVisible(long groupId) throws PortalException {
		return PortalPermissionUtil.contains(
			PermissionThreadLocal.getPermissionChecker(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_TAX_CATEGORIES);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		CPTaxCategoryDisplayContext cpTaxCategoryDisplayContext =
			new CPTaxCategoryDisplayContext(
				_commerceTaxMethodService, _cpTaxCategoryService, renderRequest,
				renderResponse);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, cpTaxCategoryDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, _portal.getHttpServletRequest(renderRequest),
			httpServletResponse, "/view.jsp");
	}

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

	@Reference
	private CPTaxCategoryService _cpTaxCategoryService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.tax.category.web)"
	)
	private ServletContext _servletContext;

}