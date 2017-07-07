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
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "form.navigator.entry.order:Integer=300",
	service = FormNavigatorEntry.class
)
public class CategoryCPFriendlyURLFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<AssetCategory> {

	@Override
	public String getCategoryKey() {
		return "details";
	}

	@Override
	public String getFormNavigatorId() {
		return "edit.category.form";
	}

	public String getItemSelectorUrl(RenderRequest renderRequest) {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(renderRequest);

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "selectDisplayPage",
			layoutItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	@Override
	public String getKey() {
		return "details";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "details");
	}

	public String getLayoutBreadcrumb(
			HttpServletRequest httpServletRequest, Layout layout)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(httpServletRequest, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(httpServletRequest, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		RenderRequest renderRequest = (RenderRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetCategory assetCategory = null;

		long categoryId = ParamUtil.getLong(request, "categoryId");
		long classNameId = _portal.getClassNameId(AssetCategory.class);
		String itemSelectorURL = getItemSelectorUrl(renderRequest);

		String titleMapAsXML = null;
		String layoutBreadcrumb = StringPool.BLANK;
		String layoutUuid = null;

		try {
			assetCategory = _assetCategoryService.fetchCategory(categoryId);

			if (assetCategory != null) {
				String defaultLanguageId = LocaleUtil.toLanguageId(
					LocaleUtil.getSiteDefault());

				titleMapAsXML =
					_cpFriendlyURLEntryLocalService.getUrlTitleMapAsXML(
						assetCategory.getGroupId(),
						assetCategory.getCompanyId(), classNameId, categoryId,
						defaultLanguageId);

				CPDisplayLayout cpDisplayLayout =
					_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
						AssetCategory.class, categoryId);

				if ((cpDisplayLayout != null) &&
					Validator.isNotNull(cpDisplayLayout.getLayoutUuid())) {

					layoutUuid = cpDisplayLayout.getLayoutUuid();

					Layout selLayout =
						_layoutLocalService.fetchLayoutByUuidAndGroupId(
							layoutUuid, themeDisplay.getSiteGroupId(), false);

					if (selLayout == null) {
						selLayout =
							_layoutLocalService.fetchLayoutByUuidAndGroupId(
								layoutUuid, themeDisplay.getSiteGroupId(),
								true);
					}

					if (selLayout != null) {
						layoutBreadcrumb = getLayoutBreadcrumb(
							request, selLayout);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		request.setAttribute("itemSelectorURL", itemSelectorURL);
		request.setAttribute("layoutBreadcrumb", layoutBreadcrumb);
		request.setAttribute("layoutUuid", layoutUuid);
		request.setAttribute("titleMapAsXML", titleMapAsXML);

		super.include(request, response);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.asset.categories.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/details.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CategoryCPFriendlyURLFormNavigatorEntry.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}