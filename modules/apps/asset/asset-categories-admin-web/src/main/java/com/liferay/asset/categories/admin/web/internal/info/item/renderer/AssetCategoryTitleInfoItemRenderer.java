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

package com.liferay.asset.categories.admin.web.internal.info.item.renderer;

import com.liferay.asset.categories.admin.web.internal.constants.AssetCategoriesAdminWebKeys;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "service.ranking:Integer=100", service = InfoItemRenderer.class
)
public class AssetCategoryTitleInfoItemRenderer
	implements InfoItemRenderer<AssetCategory> {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "title");
	}

	@Override
	public void render(
		AssetCategory assetCategory, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			httpServletRequest.setAttribute(
				AssetCategoriesAdminWebKeys.ASSET_CATEGORY, assetCategory);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/info/item/renderer/title.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.categories.admin.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _servletContext;

}