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

package com.liferay.commerce.product.content.web.internal.render.list;

import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Gianmarco Brunialti Masera
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.product.content.list.renderer.key=" + ProductPublisherCPContentListRenderer.KEY,
		"commerce.product.content.list.renderer.order=" + Integer.MIN_VALUE,
		"commerce.product.content.list.renderer.portlet.name=" + CPPortletKeys.CP_PUBLISHER_WEB,
		"commerce.product.content.list.renderer.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS
	},
	service = CPContentListRenderer.class
)
public class ProductPublisherCPContentListRenderer
	implements CPContentListRenderer {

	public static final String KEY = "list-default";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "product-publisher");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CPDataSourceResult cpDataSourceResult =
			(CPDataSourceResult)httpServletRequest.getAttribute(
				CPWebKeys.CP_DATA_SOURCE_RESULT);

		List<String> skus = new ArrayList<>();

		for (CPCatalogEntry cpCatalogEntry :
				cpDataSourceResult.getCPCatalogEntries()) {

			List<CPSku> cpSkus = cpCatalogEntry.getCPSkus();

			if (cpSkus.size() != 1) {
				continue;
			}

			CPSku cpSku = cpSkus.get(0);

			skus.add(cpSku.getSku());
		}

		long commerceChannelGroupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				_portal.getScopeGroupId(httpServletRequest));

		Map<String, Integer> stockQuantities =
			_commerceInventoryEngine.getStockQuantities(
				_portal.getCompanyId(httpServletRequest),
				commerceChannelGroupId, skus);

		httpServletRequest.setAttribute("stockQuantities", stockQuantities);

		httpServletRequest.setAttribute(
			CPContentWebKeys.CP_CONTENT_HELPER, _cpContentHelper);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/product_publisher/render/list/view.jsp");
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CPContentHelper _cpContentHelper;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.web)"
	)
	private ServletContext _servletContext;

}