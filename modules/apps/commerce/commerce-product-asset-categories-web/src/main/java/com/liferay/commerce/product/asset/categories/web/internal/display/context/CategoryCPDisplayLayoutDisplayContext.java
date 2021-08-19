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

package com.liferay.commerce.product.asset.categories.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CategoryCPDisplayLayoutDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CategoryCPDisplayLayoutDisplayContext(
		ActionHelper actionHelper,
		AssetCategoryLocalService assetCategoryLocalService,
		HttpServletRequest httpServletRequest,
		CommerceChannelLocalService commerceChannelLocalService,
		CPDisplayLayoutService cpDisplayLayoutService,
		GroupLocalService groupLocalService, ItemSelector itemSelector,
		LayoutLocalService layoutLocalService) {

		super(actionHelper, httpServletRequest);

		_assetCategoryLocalService = assetCategoryLocalService;
		_commerceChannelLocalService = commerceChannelLocalService;
		_cpDisplayLayoutService = cpDisplayLayoutService;
		_groupLocalService = groupLocalService;
		_itemSelector = itemSelector;
		_layoutLocalService = layoutLocalService;
	}

	public String getAddCategoryDisplayPageURL() throws Exception {
		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceChannel.class.getName(),
				PortletProvider.Action.MANAGE)
		).setMVCRenderCommandName(
			"/commerce_channels/edit_asset_category_cp_display_layout"
		).setParameter(
			"commerceChannelId", getCommerceChannelId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public AssetCategory getAssetCategory(long assetCategoryId)
		throws PortalException {

		return _assetCategoryLocalService.getAssetCategory(assetCategoryId);
	}

	public String getCategorySelectorURL(RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, AssetCategory.class.getName(),
			PortletProvider.Action.BROWSE);

		if (portletURL == null) {
			return null;
		}

		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				themeDisplay.getCompanyGroupId());

		portletURL.setParameter(
			"eventName", renderResponse.getNamespace() + "selectCategory");
		portletURL.setParameter("singleSelect", "true");
		portletURL.setParameter(
			"vocabularyIds",
			ListUtil.toString(
				vocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public CommerceChannel getCommerceChannel() {
		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		return _commerceChannelLocalService.fetchCommerceChannel(
			commerceChannelId);
	}

	public long getCommerceChannelId() {
		CommerceChannel commerceChannel = getCommerceChannel();

		if (commerceChannel == null) {
			return 0;
		}

		return commerceChannel.getCommerceChannelId();
	}

	public CPDisplayLayout getCPDisplayLayout() throws PortalException {
		if (_cpDisplayLayout != null) {
			return _cpDisplayLayout;
		}

		long cpDisplayLayoutId = ParamUtil.getLong(
			cpRequestHelper.getRequest(), "cpDisplayLayoutId");

		_cpDisplayLayout = _cpDisplayLayoutService.fetchCPDisplayLayout(
			cpDisplayLayoutId);

		return _cpDisplayLayout;
	}

	public CreationMenu getCreationMenu() throws Exception {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(getAddCategoryDisplayPageURL());
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-display-layout"));
				dropdownItem.setTarget("sidePanel");
			}
		).build();
	}

	public Layout getDefaultAssetCategoryLayout() throws Exception {
		CommerceChannel commerceChannel = getCommerceChannel();

		CPDisplayLayoutConfiguration cpDisplayLayoutConfiguration =
			ConfigurationProviderUtil.getConfiguration(
				CPDisplayLayoutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

		String layoutUuid =
			cpDisplayLayoutConfiguration.assetCategoryLayoutUuid();

		Layout layout = null;

		if (Validator.isNotNull(layoutUuid)) {
			layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				layoutUuid, commerceChannel.getSiteGroupId(), false);

			if (layout == null) {
				layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
					layoutUuid, commerceChannel.getSiteGroupId(), true);
			}
		}

		return layout;
	}

	public String getItemSelectorUrl(RenderRequest renderRequest)
		throws PortalException {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(renderRequest);

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		layoutItemSelectorCriterion.setShowHiddenPages(true);
		layoutItemSelectorCriterion.setShowPrivatePages(true);
		layoutItemSelectorCriterion.setShowPublicPages(true);

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		CommerceChannel commerceChannel = getCommerceChannel();

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory,
			_groupLocalService.getGroup(commerceChannel.getSiteGroupId()),
			commerceChannel.getSiteGroupId(), "selectDisplayPage",
			layoutItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public String getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler((4 * ancestors.size()) + 5);

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
	public PortletURL getPortletURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		portletURL.setParameter(
			"commerceChannelId", String.valueOf(getCommerceChannelId()));

		return portletURL;
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private CPDisplayLayout _cpDisplayLayout;
	private final CPDisplayLayoutService _cpDisplayLayoutService;
	private final GroupLocalService _groupLocalService;
	private final ItemSelector _itemSelector;
	private final LayoutLocalService _layoutLocalService;

}