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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.item.selector.criterion.CPDefinitionItemSelectorCriterion;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDisplayLayoutDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionDisplayLayoutDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CommerceChannelLocalService commerceChannelLocalService,
		CPDefinitionService cpDefinitionService,
		CPDisplayLayoutService cpDisplayLayoutService,
		GroupLocalService groupLocalService, ItemSelector itemSelector) {

		super(actionHelper, httpServletRequest);

		_commerceChannelLocalService = commerceChannelLocalService;
		_cpDefinitionService = cpDefinitionService;
		_cpDisplayLayoutService = cpDisplayLayoutService;
		_groupLocalService = groupLocalService;
		_itemSelector = itemSelector;
	}

	public String getAddProductDisplayPageURL() throws Exception {
		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceChannel.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDisplayLayout");
		portletURL.setParameter(
			"commerceChannelId", String.valueOf(getCommerceChannelId()));

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
				dropdownItem.setHref(getAddProductDisplayPageURL());
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-display-layout"));
				dropdownItem.setTarget("sidePanel");
			}
		).build();
	}

	public String getDisplayPageItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

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

	public String getProductItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		CPDefinitionItemSelectorCriterion cpDefinitionItemSelectorCriterion =
			new CPDefinitionItemSelectorCriterion();

		cpDefinitionItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "productDefinitionsSelectItem",
			cpDefinitionItemSelectorCriterion);

		itemSelectorURL.setParameter("singleSelection", Boolean.toString(true));

		return itemSelectorURL.toString();
	}

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CPDefinitionService _cpDefinitionService;
	private CPDisplayLayout _cpDisplayLayout;
	private final CPDisplayLayoutService _cpDisplayLayoutService;
	private final GroupLocalService _groupLocalService;
	private final ItemSelector _itemSelector;

}