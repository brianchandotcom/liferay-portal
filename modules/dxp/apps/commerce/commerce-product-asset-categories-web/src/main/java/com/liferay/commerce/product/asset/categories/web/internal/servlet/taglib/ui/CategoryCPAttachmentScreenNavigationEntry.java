/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.asset.categories.web.internal.servlet.taglib.ui;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
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
		return LanguageUtil.get(locale, "images");
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
			"cpAttachmentFileEntryService", cpAttachmentFileEntryService);

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/images.jsp");
	}

	@Reference
	protected CPAttachmentFileEntryService cpAttachmentFileEntryService;

	private static final Log _log = LogFactoryUtil.getLog(
		CategoryCPAttachmentScreenNavigationEntry.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.asset.categories.web)"
	)
	private ServletContext _setServletContext;

}