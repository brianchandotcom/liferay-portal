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
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=20"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CategoryCPFriendlyURLScreenNavigationEntry
	implements ScreenNavigationCategory, ScreenNavigationEntry<AssetCategory> {

	@Override
	public String getCategoryKey() {
		return "display.page";
	}

	@Override
	public String getEntryKey() {
		return "display.page";
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
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "category-display-page");
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

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		AssetCategory assetCategory = null;

		long categoryId = ParamUtil.getLong(httpServletRequest, "categoryId");
		long classNameId = _portal.getClassNameId(AssetCategory.class);
		String itemSelectorURL = getItemSelectorUrl(renderRequest);

		String titleMapAsXML = StringPool.BLANK;
		String layoutBreadcrumb = StringPool.BLANK;
		String layoutUuid = StringPool.BLANK;

		try {
			assetCategory = _assetCategoryService.fetchCategory(categoryId);

			if (assetCategory != null) {
				String defaultLanguageId = LocaleUtil.toLanguageId(
					LocaleUtil.getSiteDefault());

				titleMapAsXML =
					_cpFriendlyURLEntryLocalService.getUrlTitleMapAsXML(
						assetCategory.getGroupId(), classNameId, categoryId,
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
							httpServletRequest, selLayout);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		httpServletRequest.setAttribute("assetCategory", assetCategory);
		httpServletRequest.setAttribute("itemSelectorURL", itemSelectorURL);
		httpServletRequest.setAttribute("layoutBreadcrumb", layoutBreadcrumb);
		httpServletRequest.setAttribute("layoutUuid", layoutUuid);
		httpServletRequest.setAttribute("titleMapAsXML", titleMapAsXML);

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/friendly_url.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CategoryCPFriendlyURLScreenNavigationEntry.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.asset.categories.web)"
	)
	private ServletContext _setServletContext;

}