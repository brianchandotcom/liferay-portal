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

package com.liferay.commerce.channel.web.internal.display.context;

import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.item.selector.criterion.SimpleSiteItemSelectorCriterion;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatusRegistry;
import com.liferay.commerce.product.channel.CommerceChannelTypeRegistry;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alec Sloan
 */
public class SiteCommerceChannelTypeDisplayContext
	extends CommerceChannelDisplayContext {

	public SiteCommerceChannelTypeDisplayContext(
		AccountEntryService accountEntryService,
		CommerceChannelHealthStatusRegistry commerceChannelHealthStatusRegistry,
		ModelResourcePermission<CommerceChannel>
			commerceChannelModelResourcePermission,
		CommerceChannelService commerceChannelService,
		CommerceChannelTypeRegistry commerceChannelTypeRegistry,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		ConfigurationProvider configurationProvider,
		CPTaxCategoryLocalService cpTaxCategoryLocalService,
		DLAppLocalService dlAppLocalService,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		Portal portal,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		WorkflowDefinitionManager workflowDefinitionManager) {

		super(
			accountEntryService, commerceChannelHealthStatusRegistry,
			commerceChannelModelResourcePermission, commerceChannelService,
			commerceChannelTypeRegistry, commerceCurrencyLocalService,
			configurationProvider, cpTaxCategoryLocalService, dlAppLocalService,
			httpServletRequest, itemSelector, portal,
			workflowDefinitionLinkLocalService, workflowDefinitionManager);

		_dlAppLocalService = dlAppLocalService;
		_groupLocalService = groupLocalService;
		_itemSelector = itemSelector;
	}

	public Group getChannelSite() throws PortalException {
		CommerceChannel commerceChannel = getCommerceChannel();

		if (commerceChannel == null) {
			return null;
		}

		return _groupLocalService.fetchGroup(commerceChannel.getSiteGroupId());
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		SimpleSiteItemSelectorCriterion simpleSiteItemSelectorCriterion =
			new SimpleSiteItemSelectorCriterion();

		simpleSiteItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, "sitesSelectItem",
				simpleSiteItemSelectorCriterion));
	}

	private final DLAppLocalService _dlAppLocalService;
	private final GroupLocalService _groupLocalService;
	private final ItemSelector _itemSelector;

}