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

import com.liferay.commerce.account.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.channel.web.internal.display.context.helper.CommerceChannelRequestHelper;
import com.liferay.commerce.configuration.CommerceOrderCheckoutConfiguration;
import com.liferay.commerce.configuration.CommerceOrderFieldsConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatus;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatusRegistry;
import com.liferay.commerce.product.channel.CommerceChannelType;
import com.liferay.commerce.product.channel.CommerceChannelTypeRegistry;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.tax.configuration.CommerceShippingTaxConfiguration;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelDisplayContext
	extends BaseCommerceChannelDisplayContext {

	public CommerceChannelDisplayContext(
		CommerceChannelHealthStatusRegistry commerceChannelHealthStatusRegistry,
		ModelResourcePermission<CommerceChannel>
			commerceChannelModelResourcePermission,
		CommerceChannelService commerceChannelService,
		CommerceChannelTypeRegistry commerceChannelTypeRegistry,
		CommerceCurrencyService commerceCurrencyService,
		ConfigurationProvider configurationProvider,
		CPTaxCategoryLocalService cpTaxCategoryLocalService,
		DLAppLocalService dlAppLocalService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		Portal portal,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		WorkflowDefinitionManager workflowDefinitionManager) {

		super(httpServletRequest);

		_commerceChannelHealthStatusRegistry =
			commerceChannelHealthStatusRegistry;
		_commerceChannelModelResourcePermission =
			commerceChannelModelResourcePermission;
		_commerceChannelService = commerceChannelService;
		_commerceChannelTypeRegistry = commerceChannelTypeRegistry;
		_commerceCurrencyService = commerceCurrencyService;
		_configurationProvider = configurationProvider;
		_cpTaxCategoryLocalService = cpTaxCategoryLocalService;
		_dlAppLocalService = dlAppLocalService;
		_itemSelector = itemSelector;
		_portal = portal;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
		_workflowDefinitionManager = workflowDefinitionManager;

		_commerceChannelRequestHelper = new CommerceChannelRequestHelper(
			httpServletRequest);
	}

	public FileEntry fetchFileEntry() throws PortalException {
		return _dlAppLocalService.fetchFileEntryByExternalReferenceCode(
			getCommerceChannel().getGroupId(), "ORDER_PRINT_TEMPLATE");
	}

	public int getAccountCartMaxAllowed() throws PortalException {
		CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
			_getCommerceOrderFieldsConfiguration();

		int maxAllowed =
			commerceOrderFieldsConfiguration.accountCartMaxAllowed();

		if (maxAllowed < 0) {
			return 0;
		}

		return maxAllowed;
	}

	public CPTaxCategory getActiveShippingTaxCategory() throws PortalException {
		CommerceChannel commerceChannel = getCommerceChannel();

		CommerceShippingTaxConfiguration commerceShippingTaxConfiguration =
			_configurationProvider.getConfiguration(
				CommerceShippingTaxConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_TAX));

		return _cpTaxCategoryLocalService.fetchCPTaxCategory(
			commerceShippingTaxConfiguration.taxCategoryId());
	}

	public List<WorkflowDefinition> getActiveWorkflowDefinitions()
		throws PortalException {

		return _workflowDefinitionManager.getActiveWorkflowDefinitions(
			_commerceChannelRequestHelper.getCompanyId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public String getAddChannelURL() throws Exception {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_channels/add_commerce_channel"
		).setRedirect(
			getPortletURL()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getAddPaymentMethodURL(String commercePaymentMethodEngineKey)
		throws Exception {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest,
				CommercePaymentMethodGroupRel.class.getName(),
				PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/commerce_payment_methods/edit_commerce_payment_method_group_rel"
		).setParameter(
			"commerceChannelId", getCommerceChannelId()
		).setParameter(
			"commercePaymentMethodEngineKey", commercePaymentMethodEngineKey
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getChannelURL(CommerceChannel commerceChannel)
		throws PortalException {

		if (commerceChannel == null) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("backURL", portletURL.toString());

		portletURL.setParameter(
			"commerceChannelId",
			String.valueOf(commerceChannel.getCommerceChannelId()));
		portletURL.setParameter(
			"mvcRenderCommandName", "/commerce_channels/edit_commerce_channel");

		return portletURL.toString();
	}

	public CommerceChannel getCommerceChannel() throws PortalException {
		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		if (commerceChannelId == 0) {
			return null;
		}

		return _commerceChannelService.fetchCommerceChannel(commerceChannelId);
	}

	public long getCommerceChannelId() throws PortalException {
		CommerceChannel commerceChannel = getCommerceChannel();

		if (commerceChannel == null) {
			return 0;
		}

		return commerceChannel.getCommerceChannelId();
	}

	public List<CommerceChannelType> getCommerceChannelTypes() {
		return _commerceChannelTypeRegistry.getCommerceChannelTypes();
	}

	public List<CommerceCurrency> getCommerceCurrencies()
		throws PortalException {

		return _commerceCurrencyService.getCommerceCurrencies(
			cpRequestHelper.getCompanyId(), true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public int getCommerceSiteType() throws PortalException {
		CommerceAccountGroupServiceConfiguration
			commerceAccountGroupServiceConfiguration =
				_getCommerceAccountGroupServiceConfiguration();

		return commerceAccountGroupServiceConfiguration.commerceSiteType();
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (hasAddChannelPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getAddChannelURL());
					dropdownItem.setLabel(
						LanguageUtil.get(httpServletRequest, "add-channel"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	public PortletURL getEditCommerceChannelRenderURL() {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				cpRequestHelper.getRequest(), CPPortletKeys.COMMERCE_CHANNELS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_channels/edit_commerce_channel"
		).buildPortletURL();
	}

	public List<HeaderActionModel> getHeaderActionModels() {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		LiferayPortletResponse liferayPortletResponse =
			_commerceChannelRequestHelper.getLiferayPortletResponse();

		RenderURL renderURL = liferayPortletResponse.createRenderURL();

		headerActionModels.add(
			new HeaderActionModel(null, renderURL.toString(), null, "cancel"));

		headerActionModels.add(
			new HeaderActionModel(
				"btn-primary", liferayPortletResponse.getNamespace() + "fm",
				null, null, "save"));

		return headerActionModels;
	}

	public String getImageItemSelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		FileItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new FileEntryItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "addFileEntry",
			fileItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		String filterFields = ParamUtil.getString(
			httpServletRequest, "filterFields");

		if (Validator.isNotNull(filterFields)) {
			portletURL.setParameter("filterFields", filterFields);
		}

		String filtersLabels = ParamUtil.getString(
			httpServletRequest, "filtersLabels");

		if (Validator.isNotNull(filtersLabels)) {
			portletURL.setParameter("filtersLabels", filtersLabels);
		}

		String filtersValues = ParamUtil.getString(
			httpServletRequest, "filtersValues");

		if (Validator.isNotNull(filtersValues)) {
			portletURL.setParameter("filtersValues", filtersValues);
		}

		return portletURL;
	}

	public List<CPTaxCategory> getTaxCategories() {
		return _cpTaxCategoryLocalService.getCPTaxCategories(
			_commerceChannelRequestHelper.getCompanyId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public WorkflowDefinitionLink getWorkflowDefinitionLink(long typePK)
		throws PortalException {

		WorkflowDefinitionLink workflowDefinitionLink = null;

		CommerceChannel commerceChannel = getCommerceChannel();

		try {
			workflowDefinitionLink =
				_workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
					_commerceChannelRequestHelper.getCompanyId(),
					commerceChannel.getGroupId(), CommerceOrder.class.getName(),
					0, typePK, true);
		}
		catch (NoSuchWorkflowDefinitionLinkException
					noSuchWorkflowDefinitionLinkException) {

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchWorkflowDefinitionLinkException);
			}
		}

		return workflowDefinitionLink;
	}

	public boolean hasAddChannelPermission() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletResourcePermission portletResourcePermission =
			_commerceChannelModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), null,
			CPActionKeys.ADD_COMMERCE_CHANNEL);
	}

	public boolean hasPermission(long commerceChannelId, String actionId)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceChannelModelResourcePermission.contains(
			themeDisplay.getPermissionChecker(), commerceChannelId, actionId);
	}

	public boolean hasUnsatisfiedCommerceHealthChecks() throws PortalException {
		List<CommerceChannelHealthStatus> commerceChannelHealthStatuses =
			_commerceChannelHealthStatusRegistry.
				getCommerceChannelHealthStatuses();

		for (CommerceChannelHealthStatus commerceChannelHealthStatus :
				commerceChannelHealthStatuses) {

			if (!commerceChannelHealthStatus.isFixed(
					_commerceChannelRequestHelper.getCompanyId(),
					getCommerceChannelId())) {

				return true;
			}
		}

		return false;
	}

	public boolean isCheckoutRequestedDeliveryDateEnabled()
		throws PortalException {

		CommerceChannel commerceChannel = getCommerceChannel();

		CommerceOrderCheckoutConfiguration commerceOrderCheckoutConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderCheckoutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		return commerceOrderCheckoutConfiguration.
			checkoutRequestedDeliveryDateEnabled();
	}

	public boolean isGuestCheckoutEnabled() throws PortalException {
		CommerceChannel commerceChannel = getCommerceChannel();

		CommerceOrderCheckoutConfiguration commerceOrderCheckoutConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderCheckoutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		return commerceOrderCheckoutConfiguration.guestCheckoutEnabled();
	}

	public boolean isHideShippingPriceZero() throws PortalException {
		CommerceChannel commerceChannel = getCommerceChannel();

		CommerceOrderCheckoutConfiguration commerceOrderCheckoutConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderCheckoutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		return commerceOrderCheckoutConfiguration.hideShippingPriceZero();
	}

	public boolean isShowPurchaseOrderNumber() throws PortalException {
		CommerceChannel commerceChannel = getCommerceChannel();

		CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderFieldsConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		return commerceOrderFieldsConfiguration.showPurchaseOrderNumber();
	}

	private CommerceAccountGroupServiceConfiguration
			_getCommerceAccountGroupServiceConfiguration()
		throws PortalException {

		if (_commerceAccountGroupServiceConfiguration != null) {
			return _commerceAccountGroupServiceConfiguration;
		}

		CommerceChannel commerceChannel = getCommerceChannel();

		_commerceAccountGroupServiceConfiguration =
			_configurationProvider.getConfiguration(
				CommerceAccountGroupServiceConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceAccountConstants.SERVICE_NAME));

		return _commerceAccountGroupServiceConfiguration;
	}

	private CommerceOrderFieldsConfiguration
			_getCommerceOrderFieldsConfiguration()
		throws PortalException {

		if (_commerceOrderFieldsConfiguration != null) {
			return _commerceOrderFieldsConfiguration;
		}

		CommerceChannel commerceChannel = getCommerceChannel();

		_commerceOrderFieldsConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderFieldsConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER_FIELDS));

		return _commerceOrderFieldsConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelDisplayContext.class);

	private CommerceAccountGroupServiceConfiguration
		_commerceAccountGroupServiceConfiguration;
	private final CommerceChannelHealthStatusRegistry
		_commerceChannelHealthStatusRegistry;
	private final ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;
	private final CommerceChannelRequestHelper _commerceChannelRequestHelper;
	private final CommerceChannelService _commerceChannelService;
	private final CommerceChannelTypeRegistry _commerceChannelTypeRegistry;
	private final CommerceCurrencyService _commerceCurrencyService;
	private CommerceOrderFieldsConfiguration _commerceOrderFieldsConfiguration;
	private final ConfigurationProvider _configurationProvider;
	private final CPTaxCategoryLocalService _cpTaxCategoryLocalService;
	private final DLAppLocalService _dlAppLocalService;
	private final ItemSelector _itemSelector;
	private final Portal _portal;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private final WorkflowDefinitionManager _workflowDefinitionManager;

}