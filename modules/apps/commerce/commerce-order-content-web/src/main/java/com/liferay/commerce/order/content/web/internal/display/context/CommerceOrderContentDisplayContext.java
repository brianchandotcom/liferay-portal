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

package com.liferay.commerce.order.content.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.configuration.CommerceOrderFieldsConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.constants.CommercePaymentMethodConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.frontend.model.StepModel;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.content.web.internal.portlet.configuration.CommerceOrderContentPortletInstanceConfiguration;
import com.liferay.commerce.order.content.web.internal.portlet.configuration.OpenCommerceOrderContentPortletInstanceConfiguration;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterType;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterTypeRegistry;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.InputStream;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderContentDisplayContext {

	public CommerceOrderContentDisplayContext(
			CommerceAddressService commerceAddressService,
			CommerceChannelLocalService commerceChannelLocalService,
			CommerceOrderEngine commerceOrderEngine,
			CommerceOrderHttpHelper commerceOrderHttpHelper,
			CommerceOrderImporterTypeRegistry commerceOrderImporterTypeRegistry,
			CommerceOrderNoteService commerceOrderNoteService,
			CommerceOrderPriceCalculation commerceOrderPriceCalculation,
			CommerceOrderService commerceOrderService,
			CommerceOrderStatusRegistry commerceOrderStatusRegistry,
			CommerceOrderTypeService commerceOrderTypeService,
			CommercePaymentMethodGroupRelLocalService
				commercePaymentMethodGroupRelLocalService,
			CommercePaymentMethodRegistry commercePaymentMethodRegistry,
			CommerceShipmentItemService commerceShipmentItemService,
			CommerceTermEntryService commerceTermEntryService,
			ConfigurationProvider configurationProvider,
			DLAppLocalService dlAppLocalService,
			HttpServletRequest httpServletRequest, ItemSelector itemSelector,
			ModelResourcePermission<CommerceOrder> modelResourcePermission,
			PercentageFormatter percentageFormatter,
			PortletResourcePermission portletResourcePermission)
		throws PortalException {

		_commerceAddressService = commerceAddressService;
		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceOrderEngine = commerceOrderEngine;
		_commerceOrderHttpHelper = commerceOrderHttpHelper;
		_commerceOrderImporterTypeRegistry = commerceOrderImporterTypeRegistry;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commerceOrderPriceCalculation = commerceOrderPriceCalculation;
		_commerceOrderService = commerceOrderService;
		_commerceOrderStatusRegistry = commerceOrderStatusRegistry;
		_commerceOrderTypeService = commerceOrderTypeService;
		_commercePaymentMethodGroupRelLocalService =
			commercePaymentMethodGroupRelLocalService;
		_commercePaymentMethodRegistry = commercePaymentMethodRegistry;
		_commerceShipmentItemService = commerceShipmentItemService;
		_commerceTermEntryService = commerceTermEntryService;
		_configurationProvider = configurationProvider;
		_dlAppLocalService = dlAppLocalService;
		_httpServletRequest = httpServletRequest;
		_itemSelector = itemSelector;
		_modelResourcePermission = modelResourcePermission;
		_percentageFormatter = percentageFormatter;
		_portletResourcePermission = portletResourcePermission;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		_portletDisplay = _cpRequestHelper.getPortletDisplay();

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDate = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
		_commerceOrderDateFormatTime = FastDateFormatFactoryUtil.getTime(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		_commerceContext = (CommerceContext)httpServletRequest.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);

		_accountEntry = _commerceContext.getAccountEntry();

		_commerceOrderNoteId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderNoteId");
	}

	public CommerceChannel fetchCommerceChannel() {
		return _commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
			_cpRequestHelper.getScopeGroupId());
	}

	public String formatCommerceOrderDate(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		}

		return _commerceOrderDateFormatDate.format(date);
	}

	public AccountEntry getAccountEntry() {
		return _accountEntry;
	}

	public List<CommerceAddress> getBillingCommerceAddresses(
			long commerceAccountId)
		throws PortalException {

		return _commerceAddressService.getBillingCommerceAddresses(
			_commerceContext.getCommerceChannelId(),
			AccountEntry.class.getName(), commerceAccountId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public long getCommerceAccountId() {
		long accountEntryId = 0;

		if (_accountEntry != null) {
			accountEntryId = _accountEntry.getAccountEntryId();
		}

		return accountEntryId;
	}

	public String getCommerceAccountThumbnailURL() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return StringPool.BLANK;
		}

		AccountEntry accountEntry = commerceOrder.getAccountEntry();

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/organization_logo?img_id=");
		sb.append(accountEntry.getLogoId());

		if (accountEntry.getLogoId() > 0) {
			sb.append("&t=");
			sb.append(
				WebServerServletTokenUtil.getToken(accountEntry.getLogoId()));
		}

		return sb.toString();
	}

	public CreationMenu getCommerceAddressCreationMenu(
			String mvcRenderCommandName)
		throws Exception {

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_cpRequestHelper.getLiferayPortletResponse()
					).setMVCRenderCommandName(
						mvcRenderCommandName
					).setCMD(
						Constants.ADD
					).setParameter(
						"commerceOrderId", getCommerceOrderId()
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_cpRequestHelper.getRequest(), "add-new-address"));
			}
		).build();
	}

	public List<CommerceOrderImporterType> getCommerceImporterTypes(
			CommerceOrder commerceOrder)
		throws PortalException {

		return _commerceOrderImporterTypeRegistry.getCommerceOrderImporterTypes(
			commerceOrder);
	}

	public CommerceOrder getCommerceOrder() throws PortalException {
		long commerceOrderId = getCommerceOrderId();

		if (commerceOrderId > 0) {
			return _commerceOrderService.fetchCommerceOrder(
				getCommerceOrderId());
		}

		return _commerceOrderService.fetchCommerceOrder(
			ParamUtil.getString(_httpServletRequest, "commerceOrderUuid"),
			_cpRequestHelper.getCommerceChannelGroupId());
	}

	public String getCommerceOrderDate(CommerceOrder commerceOrder) {
		Date orderDate = commerceOrder.getCreateDate();

		if (commerceOrder.getOrderDate() != null) {
			orderDate = commerceOrder.getOrderDate();
		}

		return _commerceOrderDateFormatDate.format(orderDate);
	}

	public long getCommerceOrderId() {
		return ParamUtil.getLong(_httpServletRequest, "commerceOrderId");
	}

	public CommerceOrderImporterType getCommerceOrderImporterType(String key) {
		return _commerceOrderImporterTypeRegistry.getCommerceOrderImporterType(
			key);
	}

	public CommerceOrderNote getCommerceOrderNote() throws PortalException {
		if ((_commerceOrderNote == null) && (_commerceOrderNoteId > 0)) {
			_commerceOrderNote = _commerceOrderNoteService.getCommerceOrderNote(
				_commerceOrderNoteId);
		}

		return _commerceOrderNote;
	}

	public List<CommerceOrderNote> getCommerceOrderNotes(
			CommerceOrder commerceOrder)
		throws PortalException {

		if (hasModelPermission(
				commerceOrder,
				CommerceOrderActionKeys.
					MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES)) {

			return _commerceOrderNoteService.getCommerceOrderNotes(
				commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrder.getCommerceOrderId(), false);
	}

	public int getCommerceOrderNotesCount(CommerceOrder commerceOrder)
		throws PortalException {

		if (hasModelPermission(commerceOrder, ActionKeys.UPDATE_DISCUSSION)) {
			return _commerceOrderNoteService.getCommerceOrderNotesCount(
				commerceOrder.getCommerceOrderId());
		}

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrder.getCommerceOrderId(), false);
	}

	public CommerceOrderPrice getCommerceOrderPrice() throws PortalException {
		return _commerceOrderPriceCalculation.getCommerceOrderPrice(
			getCommerceOrder(), _commerceContext);
	}

	public String getCommerceOrderStatus(CommerceOrder commerceOrder) {
		return LanguageUtil.get(
			_httpServletRequest,
			CommerceOrderConstants.getOrderStatusLabel(
				commerceOrder.getOrderStatus()));
	}

	public String getCommerceOrderTime(CommerceOrder commerceOrder) {
		Date orderDate = commerceOrder.getCreateDate();

		if (commerceOrder.getOrderDate() != null) {
			orderDate = commerceOrder.getOrderDate();
		}

		return _commerceOrderDateFormatTime.format(orderDate);
	}

	public String getCommerceOrderTypeName(String languageId)
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.fetchCommerceOrderType(
				commerceOrder.getCommerceOrderTypeId());

		if (commerceOrderType == null) {
			return StringPool.BLANK;
		}

		return commerceOrderType.getName(languageId);
	}

	public List<CommerceOrderType> getCommerceOrderTypes()
		throws PortalException {

		CommerceChannel commerceChannel = fetchCommerceChannel();

		if (commerceChannel == null) {
			return Collections.emptyList();
		}

		return _commerceOrderTypeService.getCommerceOrderTypes(
			CommerceChannel.class.getName(),
			commerceChannel.getCommerceChannelId(), true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public int getCommerceOrderTypesCount() throws PortalException {
		CommerceChannel commerceChannel = fetchCommerceChannel();

		if (commerceChannel == null) {
			return 0;
		}

		return _commerceOrderTypeService.getCommerceOrderTypesCount(
			CommerceChannel.class.getName(),
			commerceChannel.getCommerceChannelId(), true);
	}

	public String getCommercePriceDisplayType() {
		CommerceChannel commerceChannel = fetchCommerceChannel();

		return commerceChannel.getPriceDisplayType();
	}

	public List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceOrderItemId)
		throws PortalException {

		return _commerceShipmentItemService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	public String getCommerceShipmentStatusLabel(int status) {
		return LanguageUtil.get(
			_httpServletRequest,
			CommerceShipmentConstants.getShipmentStatusLabel(status));
	}

	public String getCSVTemplateDownloadURL() throws Exception {
		FileEntry fileEntry =
			_dlAppLocalService.fetchFileEntryByExternalReferenceCode(
				_cpRequestHelper.getScopeGroupId(), "CSV_TEMPLATE_ERC");

		if (fileEntry == null) {
			Class<?> clazz = getClass();

			InputStream inputStream = clazz.getResourceAsStream(
				"dependencies/csv_template.csv");

			File file = FileUtil.createTempFile(inputStream);

			fileEntry = _dlAppLocalService.addFileEntry(
				"CSV_TEMPLATE_ERC", _cpRequestHelper.getUserId(),
				_cpRequestHelper.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "csv_template.csv",
				MimeTypesUtil.getContentType(file), "csv_template",
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, file,
				null, null,
				ServiceContextFactory.getInstance(
					_cpRequestHelper.getRequest()));

			FileUtil.delete(file);
		}

		return DLURLHelperUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(),
			_cpRequestHelper.getThemeDisplay(), StringPool.BLANK, false, true);
	}

	public String getDescriptiveAddress(CommerceAddress commerceAddress) {
		StringBundler sb = new StringBundler(5);

		sb.append(commerceAddress.getCity());
		sb.append(StringPool.COMMA_AND_SPACE);

		try {
			Region region = commerceAddress.getRegion();

			if (region != null) {
				sb.append(region.getName());
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		sb.append(commerceAddress.getZip());

		return sb.toString();
	}

	public String getDisplayStyle(String portletId)
		throws ConfigurationException {

		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}
		else if (portletId.equals(
					CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT)) {

			OpenCommerceOrderContentPortletInstanceConfiguration
				openCommerceOrderContentPortletInstanceConfiguration =
					_portletDisplay.getPortletInstanceConfiguration(
						OpenCommerceOrderContentPortletInstanceConfiguration.
							class);

			return openCommerceOrderContentPortletInstanceConfiguration.
				displayStyle();
		}
		else if (portletId.equals(CommercePortletKeys.COMMERCE_ORDER_CONTENT)) {
			CommerceOrderContentPortletInstanceConfiguration
				commerceOrderContentPortletInstanceConfiguration =
					_portletDisplay.getPortletInstanceConfiguration(
						CommerceOrderContentPortletInstanceConfiguration.class);

			return commerceOrderContentPortletInstanceConfiguration.
				displayStyle();
		}

		return StringPool.BLANK;
	}

	public long getDisplayStyleGroupId(String portletId)
		throws ConfigurationException {

		if (Validator.isNull(portletId)) {
			return _cpRequestHelper.getScopeGroupId();
		}
		else if (portletId.equals(
					CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT)) {

			OpenCommerceOrderContentPortletInstanceConfiguration
				openCommerceOrderContentPortletInstanceConfiguration =
					_portletDisplay.getPortletInstanceConfiguration(
						OpenCommerceOrderContentPortletInstanceConfiguration.
							class);

			return openCommerceOrderContentPortletInstanceConfiguration.
				displayStyleGroupId();
		}
		else if (portletId.equals(CommercePortletKeys.COMMERCE_ORDER_CONTENT)) {
			CommerceOrderContentPortletInstanceConfiguration
				commerceOrderContentPortletInstanceConfiguration =
					_portletDisplay.getPortletInstanceConfiguration(
						CommerceOrderContentPortletInstanceConfiguration.class);

			return commerceOrderContentPortletInstanceConfiguration.
				displayStyleGroupId();
		}

		return _cpRequestHelper.getScopeGroupId();
	}

	public List<DropdownItem> getDropdownItems() throws Exception {
		List<DropdownItem> dropdownItems = new ArrayList<>();

		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder.isOpen()) {
			for (CommerceOrderImporterType commerceOrderImporterType :
					getCommerceImporterTypes(commerceOrder)) {

				dropdownItems.add(
					DropdownItemBuilder.setHref(
						PortletURLBuilder.create(
							PortletURLFactoryUtil.create(
								_cpRequestHelper.getRenderRequest(),
								_cpRequestHelper.getPortletId(),
								PortletRequest.RENDER_PHASE)
						).setMVCRenderCommandName(
							"/commerce_open_order_content" +
								"/view_commerce_order_importer_type"
						).setRedirect(
							PortalUtil.getCurrentURL(
								_cpRequestHelper.getRequest())
						).setParameter(
							"commerceOrderId", getCommerceOrderId()
						).setParameter(
							"commerceOrderImporterTypeKey",
							commerceOrderImporterType.getKey()
						).setWindowState(
							LiferayWindowState.POP_UP
						).buildString()
					).setLabel(
						LanguageUtil.get(
							_cpRequestHelper.getRequest(),
							commerceOrderImporterType.getLabel(
								_cpRequestHelper.getLocale()))
					).setTarget(
						"modal"
					).build());
			}
		}

		if (commerceOrder.isOpen() &&
			hasModelPermission(commerceOrder, ActionKeys.DELETE)) {

			dropdownItems.add(
				DropdownItemBuilder.setHref(
					PortletURLBuilder.createActionURL(
						_cpRequestHelper.getLiferayPortletResponse()
					).setActionName(
						"/commerce_open_order_content/edit_commerce_order"
					).setCMD(
						Constants.DELETE
					).setRedirect(
						PortalUtil.getCurrentURL(_cpRequestHelper.getRequest())
					).setParameter(
						"commerceOrderId", getCommerceOrderId()
					).buildString()
				).setLabel(
					LanguageUtil.get(_cpRequestHelper.getRequest(), "delete")
				).build());

			dropdownItems.add(
				DropdownItemBuilder.setHref(
					PortletURLBuilder.createActionURL(
						_cpRequestHelper.getLiferayPortletResponse()
					).setActionName(
						"/commerce_open_order_content/edit_commerce_order_item"
					).setCMD(
						Constants.RESET
					).setRedirect(
						PortalUtil.getCurrentURL(_cpRequestHelper.getRequest())
					).setParameter(
						"commerceOrderId", getCommerceOrderId()
					).buildString()
				).setLabel(
					LanguageUtil.get(
						_cpRequestHelper.getRequest(), "remove-all-items")
				).build());
		}

		if (!commerceOrder.isOpen()) {
			dropdownItems.add(
				DropdownItemBuilder.setHref(
					ResourceURLBuilder.createResourceURL(
						_cpRequestHelper.getLiferayPortletResponse(),
						CommercePortletKeys.COMMERCE_ORDER_CONTENT
					).setParameter(
						"commerceOrderId", getCommerceOrderId()
					).setResourceID(
						"/commerce_order_content/export_commerce_order_report"
					).buildString()
				).setLabel(
					LanguageUtil.get(_cpRequestHelper.getRequest(), "print")
				).build());
		}

		return dropdownItems;
	}

	public String getExportCommerceOrderReportURL() {
		return ResourceURLBuilder.createResourceURL(
			_cpRequestHelper.getLiferayPortletResponse(),
			CommercePortletKeys.COMMERCE_ORDER_CONTENT
		).setParameter(
			"commerceOrderId", getCommerceOrderId()
		).setResourceID(
			"/commerce_order_content/export_commerce_order_report"
		).buildString();
	}

	public List<HeaderActionModel> getHeaderActionModels()
		throws PortalException {

		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		CommerceOrder commerceOrder = getCommerceOrder();

		headerActionModels.add(
			new HeaderActionModel(
				"btn-secondary", null,
				PortletURLBuilder.createActionURL(
					_cpRequestHelper.getLiferayPortletResponse()
				).setActionName(
					"/commerce_open_order_content/edit_commerce_order"
				).setCMD(
					Constants.UPDATE
				).setRedirect(
					_cpRequestHelper.getCurrentURL()
				).setParameter(
					"commerceOrderId", commerceOrder.getCommerceOrderId()
				).buildPortletURL(
				).toString(),
				null, "save"));

		if (isShowRetryPayment()) {
			headerActionModels.add(
				new HeaderActionModel(
					"btn-primary", null, getRetryPaymentURL(), null,
					"retry-payment"));
		}

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(commerceOrder);

		if ((currentCommerceOrderStatus == null) ||
			!currentCommerceOrderStatus.isComplete(commerceOrder) ||
			(currentCommerceOrderStatus.getKey() ==
				CommerceOrderConstants.ORDER_STATUS_CANCELLED) ||
			(currentCommerceOrderStatus.getKey() ==
				CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS)) {

			return headerActionModels;
		}

		if (!commerceOrder.isOpen()) {
			String portletURL = PortletURLBuilder.createActionURL(
				_cpRequestHelper.getLiferayPortletResponse()
			).setActionName(
				"/commerce_open_order_content/edit_commerce_order"
			).setCMD(
				"reorder"
			).setParameter(
				"commerceOrderId", commerceOrder.getCommerceOrderId()
			).buildString();

			headerActionModels.add(
				new HeaderActionModel(
					"btn-primary", null, portletURL, null, "reorder"));
		}

		List<CommerceOrderStatus> commerceOrderStatuses =
			_commerceOrderEngine.getNextCommerceOrderStatuses(commerceOrder);

		for (CommerceOrderStatus commerceOrderStatus : commerceOrderStatuses) {
			if ((commerceOrderStatus.getKey() ==
					CommerceOrderConstants.ORDER_STATUS_SHIPPED) ||
				!commerceOrderStatus.isValidForOrder(commerceOrder) ||
				!commerceOrderStatus.isTransitionCriteriaMet(commerceOrder)) {

				continue;
			}

			String label;
			String transitionName;

			if (commerceOrderStatus.getKey() ==
					CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS) {

				if (!hasPermission(
						CommerceOrderActionKeys.
							CHECKOUT_OPEN_COMMERCE_ORDERS)) {

					continue;
				}

				label = "checkout";
				transitionName = "checkout";

				if (!commerceOrder.isApproved()) {
					label = "submit";
					transitionName = "submit";
				}
			}
			else if (commerceOrderStatus.getKey() ==
						CommerceOrderConstants.ORDER_STATUS_PROCESSING) {

				if (!hasPermission(
						CommerceOrderActionKeys.APPROVE_OPEN_COMMERCE_ORDERS)) {

					continue;
				}

				label = "accept-order";
				transitionName = String.valueOf(commerceOrderStatus.getKey());
			}
			else {
				continue;
			}

			String buttonCssClass = "btn-primary";

			if (commerceOrderStatus.getPriority() ==
					CommerceOrderConstants.ORDER_STATUS_ANY) {

				buttonCssClass = "btn-secondary";
			}

			String transitionOrderPortletURLString = PortletURLBuilder.create(
				getTransitionOrderPortletURL()
			).setParameter(
				"transitionName", transitionName
			).buildString();

			headerActionModels.add(
				new HeaderActionModel(
					buttonCssClass, null, transitionOrderPortletURLString, null,
					label));
		}

		return headerActionModels;
	}

	public String getLocalizedPercentage(BigDecimal percentage, Locale locale)
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		return _percentageFormatter.getLocalizedPercentage(
			locale, commerceCurrency.getMaxFractionDigits(),
			commerceCurrency.getMinFractionDigits(), percentage);
	}

	public List<StepModel> getOrderSteps() throws PortalException {
		List<StepModel> steps = new ArrayList<>();

		CommerceOrder commerceOrder = getCommerceOrder();

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(commerceOrder);

		if ((commerceOrder == null) || (currentCommerceOrderStatus == null) ||
			(currentCommerceOrderStatus.getPriority() == -1)) {

			return steps;
		}

		if ((currentCommerceOrderStatus != null) &&
			currentCommerceOrderStatus.isWorkflowEnabled(commerceOrder)) {

			return _getWorkflowSteps(commerceOrder);
		}

		if (ArrayUtil.contains(
				CommerceOrderConstants.ORDER_STATUSES_OPEN,
				commerceOrder.getOrderStatus())) {

			return steps;
		}

		List<CommerceOrderStatus> commerceOrderStatuses =
			_commerceOrderStatusRegistry.getCommerceOrderStatuses(
				commerceOrder);

		for (CommerceOrderStatus commerceOrderStatus : commerceOrderStatuses) {
			if (((commerceOrderStatus.getKey() ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) &&
				 (commerceOrder.getOrderStatus() !=
					 CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED)) ||
				!commerceOrderStatus.isValidForOrder(commerceOrder) ||
				ArrayUtil.contains(
					CommerceOrderConstants.ORDER_STATUSES_OPEN,
					commerceOrderStatus.getKey()) ||
				(commerceOrderStatus.getPriority() == -1)) {

				continue;
			}

			StepModel step = new StepModel();

			step.setId(
				CommerceOrderConstants.getOrderStatusLabel(
					commerceOrderStatus.getKey()));
			step.setLabel(
				commerceOrderStatus.getLabel(_cpRequestHelper.getLocale()));

			if (commerceOrderStatus.equals(currentCommerceOrderStatus) &&
				(commerceOrderStatus.getKey() !=
					CommerceOrderConstants.ORDER_STATUS_COMPLETED)) {

				step.setState("active");
			}
			else if ((currentCommerceOrderStatus != null) &&
					 (commerceOrderStatus.getPriority() <=
						 currentCommerceOrderStatus.getPriority()) &&
					 commerceOrderStatus.isComplete(commerceOrder)) {

				step.setState("completed");
			}
			else {
				step.setState("inactive");
			}

			steps.add(step);
		}

		return steps;
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public String getRetryPaymentURL() throws PortalException {
		return PortletURLBuilder.create(
			_commerceOrderHttpHelper.getCommerceCheckoutPortletURL(
				_cpRequestHelper.getRequest())
		).setParameter(
			"checkoutStepName", "payment-process"
		).setParameter(
			"commerceOrderUuid",
			() -> {
				CommerceOrder commerceOrder = getCommerceOrder();

				return commerceOrder.getUuid();
			}
		).buildString();
	}

	public SearchContainer<CommerceOrder> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_cpRequestHelper.getLiferayPortletRequest(), getPortletURL(), null,
			"no-orders-were-found");

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		try {
			if (isOpenOrderContentPortlet()) {
				_searchContainer.setResultsAndTotal(
					() -> _commerceOrderService.getUserPendingCommerceOrders(
						_cpRequestHelper.getCompanyId(),
						_cpRequestHelper.getCommerceChannelGroupId(), keywords,
						_searchContainer.getStart(), _searchContainer.getEnd()),
					(int)
						_commerceOrderService.getUserPendingCommerceOrdersCount(
							_cpRequestHelper.getCompanyId(),
							_cpRequestHelper.getCommerceChannelGroupId(),
							keywords));
			}
			else {
				_searchContainer.setResultsAndTotal(
					() -> _commerceOrderService.getUserPlacedCommerceOrders(
						_cpRequestHelper.getCompanyId(),
						_cpRequestHelper.getCommerceChannelGroupId(), keywords,
						_searchContainer.getStart(), _searchContainer.getEnd()),
					(int)_commerceOrderService.getUserPlacedCommerceOrdersCount(
						_cpRequestHelper.getCompanyId(),
						_cpRequestHelper.getCommerceChannelGroupId(),
						keywords));
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return _searchContainer;
	}

	public List<CommerceAddress> getShippingCommerceAddresses(
			long commerceAccountId)
		throws PortalException {

		return _commerceAddressService.getShippingCommerceAddresses(
			_commerceContext.getCommerceChannelId(),
			AccountEntry.class.getName(), commerceAccountId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	public PortletURL getTransitionOrderPortletURL() throws PortalException {
		return PortletURLBuilder.createActionURL(
			_cpRequestHelper.getLiferayPortletResponse()
		).setActionName(
			"/commerce_open_order_content/edit_commerce_order"
		).setCMD(
			"transition"
		).setRedirect(
			_cpRequestHelper.getCurrentURL()
		).setParameter(
			"commerceOrderId", getCommerceOrderId()
		).buildPortletURL();
	}

	public boolean hasManageCommerceOrderDeliveryTermsPermission() {
		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		return _portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_DELIVERY_TERMS);
	}

	public boolean hasManageCommerceOrderPaymentTermsPermission() {
		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		return _portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_PAYMENT_TERMS);
	}

	public boolean hasModelPermission(
			CommerceOrder commerceOrder, String actionId)
		throws PortalException {

		return _modelResourcePermission.contains(
			_cpRequestHelper.getPermissionChecker(), commerceOrder, actionId);
	}

	public boolean hasModelPermission(long commerceOrderId, String actionId)
		throws PortalException {

		return _modelResourcePermission.contains(
			_cpRequestHelper.getPermissionChecker(), commerceOrderId, actionId);
	}

	public boolean hasPermission(AccountEntry accountEntry, String actionId)
		throws PortalException {

		if (accountEntry == null) {
			return false;
		}

		return _portletResourcePermission.contains(
			_cpRequestHelper.getPermissionChecker(),
			_accountEntry.getAccountEntryGroupId(), actionId);
	}

	public boolean hasPermission(String actionId) {
		return _portletResourcePermission.contains(
			_cpRequestHelper.getPermissionChecker(),
			_cpRequestHelper.getScopeGroupId(), actionId);
	}

	public boolean hasViewBillingAddressPermission(
			PermissionChecker permissionChecker, AccountEntry accountEntry)
		throws PortalException {

		if (accountEntry.isGuestAccount() || accountEntry.isPersonalAccount() ||
			_portletResourcePermission.contains(
				permissionChecker, accountEntry.getAccountEntryGroup(),
				CommerceOrderActionKeys.VIEW_BILLING_ADDRESS)) {

			return true;
		}

		return false;
	}

	public boolean isCommerceSiteTypeB2C() {
		if (_commerceContext.getCommerceSiteType() ==
				CommerceChannelConstants.SITE_TYPE_B2C) {

			return true;
		}

		return false;
	}

	public boolean isOpenOrderContentPortlet() {
		String portletName = _cpRequestHelper.getPortletName();

		return portletName.equals(
			CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);
	}

	public boolean isRequestQuoteEnabled() throws PortalException {
		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11028")) {
			return false;
		}

		CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
			_getCommerceOrderFieldsConfiguration();

		if (commerceOrderFieldsConfiguration == null) {
			return false;
		}

		return commerceOrderFieldsConfiguration.requestQuoteEnabled();
	}

	public boolean isShowCommerceOrderCreateTime() throws PortalException {
		CommerceOrderContentPortletInstanceConfiguration
			commerceOrderContentPortletInstanceConfiguration =
				_portletDisplay.getPortletInstanceConfiguration(
					CommerceOrderContentPortletInstanceConfiguration.class);

		return commerceOrderContentPortletInstanceConfiguration.
			showCommerceOrderCreateTime();
	}

	public boolean isShowPurchaseOrderNumber() throws PortalException {
		try {
			CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CommerceOrderFieldsConfiguration.class,
					new GroupServiceSettingsLocator(
						_cpRequestHelper.getCommerceChannelGroupId(),
						CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

			return commerceOrderFieldsConfiguration.showPurchaseOrderNumber();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return true;
	}

	public boolean isShowRetryPayment() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (_hasOrderStatusInProgress(commerceOrder.getOrderStatus()) &&
			_hasPaymentStatusRetryPayment(commerceOrder.getPaymentStatus()) &&
			_isCommercePaymentMethodOnline(
				commerceOrder.getCommercePaymentMethodKey()) &&
			_isCommercePaymentMethodActive(
				commerceOrder.getCommercePaymentMethodKey(),
				commerceOrder.getGroupId())) {

			return true;
		}

		return false;
	}

	public boolean isValidCommerceOrder() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return false;
		}

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		if (commerceOrderItems.isEmpty()) {
			return false;
		}

		return true;
	}

	private CommerceOrderFieldsConfiguration
			_getCommerceOrderFieldsConfiguration()
		throws PortalException {

		if (_commerceOrderFieldsConfiguration != null) {
			return _commerceOrderFieldsConfiguration;
		}

		CommerceChannel commerceChannel = fetchCommerceChannel();

		if (commerceChannel == null) {
			return null;
		}

		_commerceOrderFieldsConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderFieldsConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER_FIELDS));

		return _commerceOrderFieldsConfiguration;
	}

	private List<StepModel> _getWorkflowSteps(CommerceOrder commerceOrder) {
		List<StepModel> steps = new ArrayList<>();

		int[] workflowStatuses = {
			WorkflowConstants.STATUS_DRAFT, WorkflowConstants.STATUS_PENDING,
			WorkflowConstants.STATUS_APPROVED
		};

		for (int workflowStatus : workflowStatuses) {
			StepModel step = new StepModel();

			String workflowStatusLabel = WorkflowConstants.getStatusLabel(
				workflowStatus);

			step.setId(workflowStatusLabel);
			step.setLabel(
				LanguageUtil.get(
					_cpRequestHelper.getLocale(), workflowStatusLabel));

			if (commerceOrder.getStatus() == workflowStatus) {
				step.setState("active");
			}
			else if (commerceOrder.getStatus() < workflowStatus) {
				step.setState("completed");
			}
			else {
				step.setState("inactive");
			}

			steps.add(step);
		}

		return steps;
	}

	private boolean _hasOrderStatusInProgress(int orderStatus) {
		if (CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS == orderStatus) {
			return true;
		}

		return false;
	}

	private boolean _hasPaymentStatusRetryPayment(int paymentStatus) {
		return ArrayUtil.contains(
			CommerceOrderPaymentConstants.STATUSES_RETRY_PAYMENT,
			paymentStatus);
	}

	private boolean _isCommercePaymentMethodActive(
			String commercePaymentMethodKey, long groupId)
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRel(
					groupId, commercePaymentMethodKey);

		return commercePaymentMethodGroupRel.isActive();
	}

	private boolean _isCommercePaymentMethodOnline(
		String commercePaymentMethodKey) {

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentMethodRegistry.getCommercePaymentMethod(
				commercePaymentMethodKey);

		return ArrayUtil.contains(
			CommercePaymentMethodConstants.TYPES_ONLINE,
			commercePaymentMethod.getPaymentType());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderContentDisplayContext.class);

	private final AccountEntry _accountEntry;
	private final CommerceAddressService _commerceAddressService;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceContext _commerceContext;
	private final Format _commerceOrderDateFormatDate;
	private final Format _commerceOrderDateFormatTime;
	private final CommerceOrderEngine _commerceOrderEngine;
	private CommerceOrderFieldsConfiguration _commerceOrderFieldsConfiguration;
	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private final CommerceOrderImporterTypeRegistry
		_commerceOrderImporterTypeRegistry;
	private CommerceOrderNote _commerceOrderNote;
	private final long _commerceOrderNoteId;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderPriceCalculation _commerceOrderPriceCalculation;
	private final CommerceOrderService _commerceOrderService;
	private final CommerceOrderStatusRegistry _commerceOrderStatusRegistry;
	private final CommerceOrderTypeService _commerceOrderTypeService;
	private final CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;
	private final CommercePaymentMethodRegistry _commercePaymentMethodRegistry;
	private final CommerceShipmentItemService _commerceShipmentItemService;
	private final CommerceTermEntryService _commerceTermEntryService;
	private final ConfigurationProvider _configurationProvider;
	private final CPRequestHelper _cpRequestHelper;
	private final DLAppLocalService _dlAppLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final ModelResourcePermission<CommerceOrder>
		_modelResourcePermission;
	private final PercentageFormatter _percentageFormatter;
	private final PortletDisplay _portletDisplay;
	private final PortletResourcePermission _portletResourcePermission;
	private SearchContainer<CommerceOrder> _searchContainer;

}