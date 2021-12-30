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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.constants.CPInstanceConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPDefinitionExpirationDateException;
import com.liferay.commerce.product.exception.CPDefinitionMetaDescriptionException;
import com.liferay.commerce.product.exception.CPDefinitionMetaKeywordsException;
import com.liferay.commerce.product.exception.CPDefinitionMetaTitleException;
import com.liferay.commerce.product.exception.CPDefinitionNameDefaultLanguageException;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.exception.NoSuchCatalogException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.servlet.taglib.ui.constants.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.service.CPDAvailabilityEstimateService;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cp_definition"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CPDefinition cpDefinition = _updateCPDefinition(actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, cpDefinition,
					CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCPDefinitions(actionRequest);
			}
			else if (cmd.equals("deleteAccountGroup")) {
				_deleteAccountGroup(actionRequest);
			}
			else if (cmd.equals("deleteChannel")) {
				_deleteChannel(actionRequest);
			}
			else if (cmd.equals("updateConfiguration")) {
				Callable<Object> cpDefinitionConfigurationCallable =
					new CPDefinitionConfigurationCallable(actionRequest);

				TransactionInvokerUtil.invoke(
					_transactionConfig, cpDefinitionConfigurationCallable);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals("updateSubscriptionInfo")) {
				updateSubscriptionInfo(actionRequest);
			}
			else if (cmd.equals("updateVisibility")) {
				Callable<Object> cpDefinitionVisibilityCallable =
					new CPDefinitionVisibilityCallable(actionRequest);

				TransactionInvokerUtil.invoke(
					_transactionConfig, cpDefinitionVisibilityCallable);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof NoSuchCPDefinitionException ||
				throwable instanceof PrincipalException) {

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (throwable instanceof AssetCategoryException ||
					 throwable instanceof AssetTagException ||
					 throwable instanceof CPDefinitionExpirationDateException ||
					 throwable instanceof
						 CPDefinitionMetaDescriptionException ||
					 throwable instanceof CPDefinitionMetaKeywordsException ||
					 throwable instanceof CPDefinitionMetaTitleException ||
					 throwable instanceof
						 CPDefinitionNameDefaultLanguageException ||
					 throwable instanceof FriendlyURLLengthException ||
					 throwable instanceof NoSuchCatalogException ||
					 throwable instanceof
						 NoSuchCPDefinitionInventoryException ||
					 throwable instanceof NumberFormatException) {

				SessionErrors.add(
					actionRequest, throwable.getClass(), throwable);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				_log.error(throwable, throwable);

				throw new Exception(throwable);
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CPDefinition cpDefinition,
			String screenNavigationCategoryKey)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				actionRequest, themeDisplay.getScopeGroup(),
				CPDefinition.class.getName(), PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/cp_definitions/edit_cp_definition"
		).setParameter(
			"cpDefinitionId", cpDefinition.getCPDefinitionId()
		).setParameter(
			"screenNavigationCategoryKey", screenNavigationCategoryKey
		).buildString();
	}

	protected void updateShippingInfo(ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinition.class.getName(), actionRequest);

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		boolean shippable = ParamUtil.getBoolean(actionRequest, "shippable");
		boolean freeShipping = ParamUtil.getBoolean(
			actionRequest, "freeShipping");
		boolean shipSeparately = ParamUtil.getBoolean(
			actionRequest, "shipSeparately");
		double shippingExtraPrice = ParamUtil.getDouble(
			actionRequest, "shippingExtraPrice");
		double width = ParamUtil.getDouble(actionRequest, "width");
		double height = ParamUtil.getDouble(actionRequest, "height");
		double depth = ParamUtil.getDouble(actionRequest, "depth");
		double weight = ParamUtil.getDouble(actionRequest, "weight");

		_cpDefinitionService.updateShippingInfo(
			cpDefinitionId, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, serviceContext);
	}

	protected void updateSubscriptionInfo(ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		boolean subscriptionEnabled = ParamUtil.getBoolean(
			actionRequest, "subscriptionEnabled");
		int subscriptionLength = ParamUtil.getInteger(
			actionRequest, "subscriptionLength");
		String subscriptionType = ParamUtil.getString(
			actionRequest, "subscriptionType");
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "subscriptionTypeSettings--");
		long maxSubscriptionCycles = ParamUtil.getLong(
			actionRequest, "maxSubscriptionCycles");
		boolean deliverySubscriptionEnabled = ParamUtil.getBoolean(
			actionRequest, "deliverySubscriptionEnabled");
		int deliverySubscriptionLength = ParamUtil.getInteger(
			actionRequest, "deliverySubscriptionLength");
		String deliverySubscriptionType = ParamUtil.getString(
			actionRequest, "deliverySubscriptionType");
		UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "deliverySubscriptionTypeSettings--");
		long deliveryMaxSubscriptionCycles = ParamUtil.getLong(
			actionRequest, "deliveryMaxSubscriptionCycles");

		_cpDefinitionService.updateSubscriptionInfo(
			cpDefinitionId, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, deliverySubscriptionEnabled,
			deliverySubscriptionLength, deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles);
	}

	private void _deleteAccountGroup(ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		long commerceAccountGroupRelId = ParamUtil.getLong(
			actionRequest, "commerceAccountGroupRelId");

		_commerceAccountGroupRelService.deleteCommerceAccountGroupRel(
			commerceAccountGroupRelId);

		_reindexCPDefinition(cpDefinitionId);
	}

	private void _deleteChannel(ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		long commerceChannelRelId = ParamUtil.getLong(
			actionRequest, "commerceChannelRelId");

		_commerceChannelRelService.deleteCommerceChannelRel(
			commerceChannelRelId);

		_reindexCPDefinition(cpDefinitionId);
	}

	private void _deleteCPDefinitions(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPDefinitionIds = null;

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		if (cpDefinitionId > 0) {
			deleteCPDefinitionIds = new long[] {cpDefinitionId};
		}
		else {
			deleteCPDefinitionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "id"), 0L);
		}

		for (long deleteCPDefinitionId : deleteCPDefinitionIds) {
			_cpDefinitionService.deleteCPDefinition(deleteCPDefinitionId);
		}
	}

	private void _reindexCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.reindex(cpDefinition);
	}

	private CPDefinition _updateCPDefinition(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "nameMapAsXML");
		Map<Locale, String> shortDescriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "shortDescriptionMapAsXML");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "descriptionMapAsXML");
		Map<Locale, String> urlTitleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "urlTitleMapAsXML");
		Map<Locale, String> metaTitleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "metaTitleMapAsXML");
		Map<Locale, String> metaDescriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "metaDescriptionMapAsXML");
		Map<Locale, String> metaKeywordsMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "metaKeywordsMapAsXML");
		boolean published = ParamUtil.getBoolean(actionRequest, "published");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinition.class.getName(), actionRequest);

		CPDefinition cpDefinition = null;

		if (cpDefinitionId <= 0) {
			long commerceCatalogGroupId = ParamUtil.getLong(
				actionRequest, "commerceCatalogGroupId");

			CommerceCatalog commerceCatalog =
				_commerceCatalogService.fetchCommerceCatalogByGroupId(
					commerceCatalogGroupId);

			if (commerceCatalog == null) {
				throw new NoSuchCatalogException();
			}

			Locale defaultLocale = LocaleUtil.fromLanguageId(
				commerceCatalog.getCatalogDefaultLanguageId());

			if (Validator.isNull(nameMap.get(defaultLocale))) {
				throw new CPDefinitionNameDefaultLanguageException();
			}

			String productTypeName = ParamUtil.getString(
				actionRequest, "productTypeName");

			// Add commerce product definition

			cpDefinition = _cpDefinitionService.addCPDefinition(
				null, commerceCatalogGroupId, nameMap, shortDescriptionMap,
				descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
				metaKeywordsMap, productTypeName, true, true, false, false, 0D,
				0D, 0D, 0D, 0D, 0L, false, false, null, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire,
				CPInstanceConstants.DEFAULT_SKU, false, 1, null, null, 0L,
				serviceContext);
		}
		else {

			// Update commerce product definition

			CPDefinition oldCPDefinition = _cpDefinitionService.getCPDefinition(
				cpDefinitionId);

			cpDefinition = _cpDefinitionService.updateCPDefinition(
				cpDefinitionId, nameMap, shortDescriptionMap, descriptionMap,
				urlTitleMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap,
				oldCPDefinition.isIgnoreSKUCombinations(), null, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);
		}

		return cpDefinition;
	}

	private void _updateCPDefinitionInventory(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionInventoryId = ParamUtil.getLong(
			actionRequest, "cpDefinitionInventoryId");

		long cpdAvailabilityEstimateEntryId = ParamUtil.getLong(
			actionRequest, "cpdAvailabilityEstimateEntryId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		String cpDefinitionInventoryEngine = ParamUtil.getString(
			actionRequest, "CPDefinitionInventoryEngine");
		String lowStockActivity = ParamUtil.getString(
			actionRequest, "lowStockActivity");
		long commerceAvailabilityEstimateId = ParamUtil.getLong(
			actionRequest, "commerceAvailabilityEstimateId");
		boolean displayAvailability = ParamUtil.getBoolean(
			actionRequest, "displayAvailability");
		boolean displayStockQuantity = ParamUtil.getBoolean(
			actionRequest, "displayStockQuantity");
		boolean backOrders = ParamUtil.getBoolean(actionRequest, "backOrders");
		int minStockQuantity = ParamUtil.getInteger(
			actionRequest, "minStockQuantity");
		int minOrderQuantity = ParamUtil.getInteger(
			actionRequest, "minOrderQuantity");
		int maxOrderQuantity = ParamUtil.getInteger(
			actionRequest, "maxOrderQuantity");
		int multipleOrderQuantity = ParamUtil.getInteger(
			actionRequest, "multipleOrderQuantity");
		String allowedOrderQuantities = ParamUtil.getString(
			actionRequest, "allowedOrderQuantities");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionInventory.class.getName(), actionRequest);

		if (cpDefinitionInventoryId <= 0) {
			_cpDefinitionInventoryService.addCPDefinitionInventory(
				cpDefinitionId, cpDefinitionInventoryEngine, lowStockActivity,
				displayAvailability, displayStockQuantity, minStockQuantity,
				backOrders, minOrderQuantity, maxOrderQuantity,
				allowedOrderQuantities, multipleOrderQuantity);
		}
		else {
			_cpDefinitionInventoryService.updateCPDefinitionInventory(
				cpDefinitionInventoryId, cpDefinitionInventoryEngine,
				lowStockActivity, displayAvailability, displayStockQuantity,
				minStockQuantity, backOrders, minOrderQuantity,
				maxOrderQuantity, allowedOrderQuantities,
				multipleOrderQuantity);
		}

		_cpdAvailabilityEstimateService.updateCPDAvailabilityEstimate(
			cpdAvailabilityEstimateEntryId, cpDefinitionId,
			commerceAvailabilityEstimateId, serviceContext);
	}

	private void _updateTaxCategoryInfo(ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "cpTaxCategoryId");
		boolean taxExempt = ParamUtil.getBoolean(actionRequest, "taxExempt");
		boolean telcoOrElectronics = ParamUtil.getBoolean(
			actionRequest, "telcoOrElectronics");

		_cpDefinitionService.updateTaxCategoryInfo(
			cpDefinitionId, cpTaxCategoryId, taxExempt, telcoOrElectronics);
	}

	private void _updateVisibility(ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		// Commerce account group rels

		long[] commerceAccountGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "commerceAccountGroupIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAccountGroupRel.class.getName(), actionRequest);

		for (long commerceAccountGroupId : commerceAccountGroupIds) {
			if (commerceAccountGroupId == 0) {
				continue;
			}

			_commerceAccountGroupRelService.addCommerceAccountGroupRel(
				CPDefinition.class.getName(), cpDefinitionId,
				commerceAccountGroupId, serviceContext);
		}

		// Commerce channel rels

		long[] commerceChannelIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "commerceChannelIds"), 0L);

		for (long commerceChannelId : commerceChannelIds) {
			if (commerceChannelId == 0) {
				continue;
			}

			_commerceChannelRelService.addCommerceChannelRel(
				CPDefinition.class.getName(), cpDefinitionId, commerceChannelId,
				serviceContext);
		}

		// Filters

		boolean accountGroupFilterEnabled = ParamUtil.getBoolean(
			actionRequest, "accountGroupFilterEnabled");
		boolean channelFilterEnabled = ParamUtil.getBoolean(
			actionRequest, "channelFilterEnabled");

		_cpDefinitionService.updateCPDefinitionAccountGroupFilter(
			cpDefinitionId, accountGroupFilterEnabled);
		_cpDefinitionService.updateCPDefinitionChannelFilter(
			cpDefinitionId, channelFilterEnabled);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPDefinitionMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CPDAvailabilityEstimateService _cpdAvailabilityEstimateService;

	@Reference
	private CPDefinitionInventoryService _cpDefinitionInventoryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	private class CPDefinitionConfigurationCallable
		implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			_updateCPDefinitionInventory(_actionRequest);
			updateShippingInfo(_actionRequest);
			_updateTaxCategoryInfo(_actionRequest);

			return null;
		}

		private CPDefinitionConfigurationCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

	private class CPDefinitionVisibilityCallable implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			_updateVisibility(_actionRequest);

			return null;
		}

		private CPDefinitionVisibilityCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}