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

package com.liferay.commerce.product.content.search.web.internal.render.list;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.content.list.renderer.key=" + CPPortletKeys.CP_SEARCH_RESULTS,
		"commerce.product.content.list.renderer.order=1000",
		"commerce.product.content.list.renderer.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS
	},
	service = CPContentListRenderer.class
)
public class SearchResultsCPContentListRenderer
	implements CPContentListRenderer {

	@Override
	public String getKey() {
		return CPPortletKeys.CP_SEARCH_RESULTS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "search-results");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/search_results/render/view.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

}