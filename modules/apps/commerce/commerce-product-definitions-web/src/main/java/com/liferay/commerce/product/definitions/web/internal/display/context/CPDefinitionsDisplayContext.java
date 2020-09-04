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

import com.liferay.commerce.account.item.selector.criterion.CommerceAccountGroupItemSelectorCriterion;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.item.selector.criterion.CommerceChannelItemSelectorCriterion;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.type.CPType;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionsDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionsDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CommerceAccountGroupRelService commerceAccountGroupRelService,
		CommerceCatalogService commerceCatalogService,
		CommerceChannelRelService commerceChannelRelService,
		CPDefinitionService cpDefinitionService, ItemSelector itemSelector) {

		super(actionHelper, httpServletRequest);

		_commerceAccountGroupRelService = commerceAccountGroupRelService;
		_commerceCatalogService = commerceCatalogService;
		_commerceChannelRelService = commerceChannelRelService;
		_cpDefinitionService = cpDefinitionService;
		_itemSelector = itemSelector;
	}

	public String getAccountGroupItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		CommerceAccountGroupItemSelectorCriterion
			commerceAccountGroupItemSelectorCriterion =
				new CommerceAccountGroupItemSelectorCriterion();

		commerceAccountGroupItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "accountGroupSelectItem",
			commerceAccountGroupItemSelectorCriterion);

		String checkedCommerceAccountGroupIds = StringUtil.merge(
			getCommerceAccountGroupRelCommerceAccountGroupIds());

		itemSelectorURL.setParameter(
			"checkedCommerceAccountGroupIds", checkedCommerceAccountGroupIds);

		return itemSelectorURL.toString();
	}

	public CreationMenu getAccountGroupsCreationMenu() throws PortalException {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				CPDefinition cpDefinition = getCPDefinition();

				String cpDefinitionName = StringPool.BLANK;

				if (cpDefinition != null) {
					cpDefinitionName = cpDefinition.getName(
						LocaleUtil.toLanguageId(cpRequestHelper.getLocale()));
				}

				dropdownItem.setHref(
					liferayPortletResponse.getNamespace() +
						"selectCommerceAccountGroup");
				dropdownItem.setLabel(
					LanguageUtil.format(
						httpServletRequest, "add-account-group-relation-to-x",
						cpDefinitionName));
				dropdownItem.setTarget("event");
			}
		).build();
	}

	public String getChannelItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		CommerceChannelItemSelectorCriterion
			commerceChannelItemSelectorCriterion =
				new CommerceChannelItemSelectorCriterion();

		commerceChannelItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "channelSelectItem",
			commerceChannelItemSelectorCriterion);

		String checkedCommerceChannelIds = StringUtil.merge(
			getCommerceChannelRelCommerceChannelIds());

		itemSelectorURL.setParameter(
			"checkedCommerceChannelIds", checkedCommerceChannelIds);

		return itemSelectorURL.toString();
	}

	public CreationMenu getChannelsCreationMenu() throws PortalException {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				String cpDefinitionName = StringPool.BLANK;

				CPDefinition cpDefinition = getCPDefinition();

				if (cpDefinition != null) {
					cpDefinitionName = cpDefinition.getName(
						LocaleUtil.toLanguageId(cpRequestHelper.getLocale()));
				}

				dropdownItem.setHref(
					liferayPortletResponse.getNamespace() +
						"selectCommerceChannel");
				dropdownItem.setLabel(
					LanguageUtil.format(
						httpServletRequest, "add-channel-relation-to-x",
						cpDefinitionName));
				dropdownItem.setTarget("event");
			}
		).build();
	}

	public List<ClayDataSetActionDropdownItem>
			getClayDataSetActionDropdownItems()
		throws PortalException {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CPDefinition.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter("cpDefinitionId", "{id}");
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS);

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				portletURL.toString(), "view", "view",
				LanguageUtil.get(httpServletRequest, "view"), "get", null,
				null));

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				"/o/headless-commerce-admin-catalog/v1.0/products/{productId}",
				"trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete", "async"));

		return clayDataSetActionDropdownItems;
	}

	public long[] getCommerceAccountGroupRelCommerceAccountGroupIds()
		throws PortalException {

		List<CommerceAccountGroupRel> commerceAccountGroupRels =
			_commerceAccountGroupRelService.getCommerceAccountGroupRels(
				CPDefinition.class.getName(), getCPDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<CommerceAccountGroupRel> stream =
			commerceAccountGroupRels.stream();

		return stream.mapToLong(
			CommerceAccountGroupRel::getCommerceAccountGroupId
		).toArray();
	}

	public List<CommerceCatalog> getCommerceCatalogs() throws PortalException {
		return _commerceCatalogService.searchCommerceCatalogs(
			cpRequestHelper.getCompanyId(), null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public long[] getCommerceChannelRelCommerceChannelIds()
		throws PortalException {

		List<CommerceChannelRel> commerceChannelRels =
			_commerceChannelRelService.getCommerceChannelRels(
				CPDefinition.class.getName(), getCPDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<CommerceChannelRel> stream = commerceChannelRels.stream();

		return stream.mapToLong(
			CommerceChannelRel::getCommerceChannelId
		).toArray();
	}

	public String getCPDefinitionThumbnailURL() throws Exception {
		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return StringPool.BLANK;
		}

		return cpDefinition.getDefaultImageThumbnailSrc();
	}

	public CProduct getCProduct() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return null;
		}

		return cpDefinition.getCProduct();
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		RenderURL renderURL = liferayPortletResponse.createRenderURL();

		renderURL.setParameter("mvcRenderCommandName", "addProductDefinition");
		renderURL.setParameter("backURL", cpRequestHelper.getCurrentURL());
		renderURL.setWindowState(LiferayWindowState.POP_UP);

		for (CPType cpType : getCPTypes()) {
			renderURL.setParameter("productTypeName", cpType.getName());

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(renderURL.toString());
					dropdownItem.setLabel(
						cpType.getLabel(cpRequestHelper.getLocale()));
					dropdownItem.setTarget("modal");
				});
		}

		return creationMenu;
	}

	public List<DropdownItem> getDropdownItems() throws Exception {
		List<DropdownItem> dropdownItems = new ArrayList<>();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			cpRequestHelper.getRenderRequest(), cpRequestHelper.getPortletId(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "duplicateCPDefinition");
		portletURL.setParameter(
			"cpDefinitionId",
			ParamUtil.getString(httpServletRequest, "cpDefinitionId"));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		DropdownItem dropdownItem = new DropdownItem();

		dropdownItem.setHref(portletURL.toString());
		dropdownItem.setLabel(
			LanguageUtil.get(httpServletRequest, "duplicate"));
		dropdownItem.setTarget("modal");

		dropdownItems.add(dropdownItem);

		return dropdownItems;
	}

	public List<HeaderActionModel> getHeaderActionModels()
		throws PortalException {

		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		RenderResponse renderResponse = cpRequestHelper.getRenderResponse();

		RenderURL cancelURL = renderResponse.createRenderURL();

		HeaderActionModel cancelHeaderActionModel = new HeaderActionModel(
			null, cancelURL.toString(), null, "cancel");

		headerActionModels.add(cancelHeaderActionModel);

		CPDefinition cpDefinition = getCPDefinition();

		ActionURL actionURL = renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME, "editProductDefinition");

		String saveButtonLabel = "save";

		if ((cpDefinition == null) || cpDefinition.isDraft() ||
			cpDefinition.isApproved() || cpDefinition.isExpired() ||
			cpDefinition.isScheduled()) {

			saveButtonLabel = "save-as-draft";
		}

		HeaderActionModel saveAsDraftHeaderActionModel = new HeaderActionModel(
			null, renderResponse.getNamespace() + "fm", actionURL.toString(),
			null, saveButtonLabel);

		headerActionModels.add(saveAsDraftHeaderActionModel);

		String publishButtonLabel = "publish";

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				cpRequestHelper.getCompanyId(),
				cpRequestHelper.getScopeGroupId(),
				CPDefinition.class.getName())) {

			publishButtonLabel = "submit-for-publication";
		}

		String additionalClasses = "btn-primary";

		if ((cpDefinition != null) && cpDefinition.isPending()) {
			additionalClasses = additionalClasses + " disabled";
		}

		HeaderActionModel publishHeaderActionModel = new HeaderActionModel(
			additionalClasses, renderResponse.getNamespace() + "fm",
			actionURL.toString(),
			renderResponse.getNamespace() + "publishButton",
			publishButtonLabel);

		headerActionModels.add(publishHeaderActionModel);

		return headerActionModels;
	}

	public String getUrlTitleMapAsXML() throws PortalException {
		long cpDefinitionId = getCPDefinitionId();

		if (cpDefinitionId <= 0) {
			return StringPool.BLANK;
		}

		return _cpDefinitionService.getUrlTitleMapAsXML(cpDefinitionId);
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(), CPDefinition.class.getName(),
			getCPDefinitionId(), null);
	}

	public boolean isSelectedCatalog(CommerceCatalog commerceCatalog)
		throws PortalException {

		CPDefinition cpDefinition = getCPDefinition();

		if (commerceCatalog.getGroupId() == cpDefinition.getGroupId()) {
			return true;
		}

		return false;
	}

	private final CommerceAccountGroupRelService
		_commerceAccountGroupRelService;
	private final CommerceCatalogService _commerceCatalogService;
	private final CommerceChannelRelService _commerceChannelRelService;
	private final CPDefinitionService _cpDefinitionService;
	private final ItemSelector _itemSelector;

}