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

package com.liferay.product.navigation.product.menu.theme.contributor.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.product.navigation.product.menu.util.ProductNavigationProductMenuHelper;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = "type=" + TemplateContextContributor.TYPE_THEME,
	service = TemplateContextContributor.class
)
public class ProductMenuTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		if (!_productNavigationProductMenuHelper.isShowProductMenu(
				httpServletRequest)) {

			return;
		}

		String cssClass = GetterUtil.getString(
			contextObjects.get("bodyCssClass"));

		String productMenuState = SessionClicks.get(
			httpServletRequest,
			"com.liferay.product.navigation.product.menu.web_productMenuState",
			"closed");

		if (Objects.equals(productMenuState, "open")) {
			cssClass += StringPool.SPACE + "open product-menu-open";
		}

		contextObjects.put("bodyCssClass", cssClass);

		contextObjects.put("liferay_product_menu_state", productMenuState);
	}

	@Reference
	private ProductNavigationProductMenuHelper
		_productNavigationProductMenuHelper;

}