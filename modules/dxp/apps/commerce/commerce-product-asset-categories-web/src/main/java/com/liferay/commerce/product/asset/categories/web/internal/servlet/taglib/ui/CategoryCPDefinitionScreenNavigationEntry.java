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

package com.liferay.commerce.product.asset.categories.web.internal.servlet.taglib.ui;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.asset.categories.web.internal.util.CPAssetCategoryWebPortletUtil;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=20"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CategoryCPDefinitionScreenNavigationEntry
	implements ScreenNavigationCategory, ScreenNavigationEntry<AssetCategory> {

	@Override
	public String getCategoryKey() {
		return "products";
	}

	@Override
	public String getEntryKey() {
		return "products";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "products");
	}

	@Override
	public String getScreenNavigationKey() {
		return "general";
	}

	@Override
	public boolean isVisible(User user, AssetCategory assetCategory) {
		if (assetCategory == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		AssetCategory assetCategory = null;

		long categoryId = ParamUtil.getLong(httpServletRequest, "categoryId");

		try {
			assetCategory = _assetCategoryService.fetchCategory(categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		httpServletRequest.setAttribute(WebKeys.ASSET_CATEGORY, assetCategory);
		httpServletRequest.setAttribute(
			"cpAssetCategoryWebPortletUtil", cpAssetCategoryWebPortletUtil);
		httpServletRequest.setAttribute(
			"cpDefinitionService", cpDefinitionService);

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/products.jsp");
	}

	@Reference
	protected CPAssetCategoryWebPortletUtil cpAssetCategoryWebPortletUtil;

	@Reference
	protected CPDefinitionService cpDefinitionService;

	private static final Log _log = LogFactoryUtil.getLog(
		CategoryCPDefinitionScreenNavigationEntry.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.asset.categories.web)"
	)
	private ServletContext _setServletContext;

}