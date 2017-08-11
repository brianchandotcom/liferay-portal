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
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
public class CategoryCPAttachmentScreenNavigationEntry
	implements ScreenNavigationCategory, ScreenNavigationEntry<AssetCategory> {

	@Override
	public String getCategoryKey() {
		return "images";
	}

	@Override
	public String getEntryKey() {
		return "images";
	}

	@Override
	public String getLabel(Locale locale) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "images");
	}

	@Override
	public String getScreenNavigationKey() {
		return "general";
	}

	@Override
	public boolean isVisible(User user, AssetCategory category) {
		if (category == null) {
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
			"cpAttachmentFileEntryService", cpAttachmentFileEntryService);

		_jspRenderer.renderJSP(_setServletContext, httpServletRequest, httpServletResponse, "/images.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;


	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.asset.categories.web)"
	)
	private ServletContext _setServletContext;


	@Reference
	protected CPAttachmentFileEntryService cpAttachmentFileEntryService;

	private static final Log _log = LogFactoryUtil.getLog(
		CategoryCPAttachmentScreenNavigationEntry.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

}