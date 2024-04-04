/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceReturn;
import com.liferay.commerce.model.CommerceReturnItem;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommerceQuantityFormatter;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionService;
import com.liferay.list.type.service.ListTypeEntryService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gianmarco Brunialti Masera
 * @author Alessio Antonio Rendina
 */
public class CommerceReturnContentDisplayContext {

	public CommerceReturnContentDisplayContext(
		AccountEntryLocalService accountEntryLocalService,
		CommerceOrderItemService commerceOrderItemService,
		CommerceOrderService commerceOrderService,
		CommercePaymentMethodGroupRelLocalService
			commercePaymentMethodGroupRelLocalService,
		CommercePriceFormatter commercePriceFormatter,
		CommerceQuantityFormatter commerceQuantityFormatter, Language language,
		ListTypeDefinitionService listTypeDefinitionService,
		ListTypeEntryService listTypeEntryService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		HttpServletRequest httpServletRequest) {

		_accountEntryLocalService = accountEntryLocalService;
		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderService = commerceOrderService;
		_commercePaymentMethodGroupRelLocalService =
			commercePaymentMethodGroupRelLocalService;
		_commercePriceFormatter = commercePriceFormatter;
		_commerceQuantityFormatter = commerceQuantityFormatter;
		_language = language;
		_listTypeDefinitionService = listTypeDefinitionService;
		_listTypeEntryService = listTypeEntryService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;

		_commerceContext = (CommerceContext)httpServletRequest.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getAccountEntryName() throws PortalException {
		CommerceReturn commerceReturn = getCommerceReturn();

		if (commerceReturn == null) {
			return StringPool.BLANK;
		}

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			commerceReturn.getAccountId());

		return accountEntry.getName();
	}

	public String getAPIURL() {
		long commerceChannelId = getCommerceChannelId();

		if (commerceChannelId == 0) {
			return StringPool.BLANK;
		}

		String encodedFilter = URLCodec.encodeURL(
			StringBundler.concat(
				"'channelId' eq '", commerceChannelId, StringPool.APOSTROPHE),
			true);

		return "/o/commerce-returns?filter=" + encodedFilter;
	}

	public long getCommerceChannelId() {
		try {
			return _commerceContext.getCommerceChannelId();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return 0;
	}

	public CommerceOrder getCommerceOrder() throws PortalException {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		CommerceReturn commerceReturn = getCommerceReturn();

		if (commerceReturn == null) {
			return _commerceOrder;
		}

		_commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceReturn.getOrderId());

		return _commerceOrder;
	}

	public long getCommerceOrderId() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return 0;
		}

		return commerceOrder.getCommerceOrderId();
	}

	public CommerceReturn getCommerceReturn() {
		if (_commerceReturn != null) {
			return _commerceReturn;
		}

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			getCommerceReturnId());

		if (objectEntry == null) {
			return _commerceReturn;
		}

		_commerceReturn = new CommerceReturn(objectEntry);

		return _commerceReturn;
	}

	public long getCommerceReturnId() {
		if (_commerceReturnId > 0) {
			return _commerceReturnId;
		}

		_commerceReturnId = ParamUtil.getLong(
			_cpRequestHelper.getRequest(), "commerceReturnId");

		return _commerceReturnId;
	}

	public CommerceReturnItem getCommerceReturnItem() {
		if (_commerceReturnItem != null) {
			return _commerceReturnItem;
		}

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			getCommerceReturnItemId());

		if (objectEntry == null) {
			return _commerceReturnItem;
		}

		_commerceReturnItem = new CommerceReturnItem(objectEntry);

		return _commerceReturnItem;
	}

	public CreationMenu getCommerceReturnItemCreationMenu() {
		CreationMenu creationMenu = new CreationMenu();

		CommerceReturn commerceReturn = getCommerceReturn();

		if (commerceReturn == null) {
			return creationMenu;
		}

		if (Objects.equals(commerceReturn.getReturnStatus(), "draft")) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						PortletURLBuilder.create(
							PortletProviderUtil.getPortletURL(
								_cpRequestHelper.getRequest(),
								CommerceOrder.class.getName(),
								PortletProvider.Action.VIEW)
						).setMVCRenderCommandName(
							"/commerce_order_content" +
								"/view_returnable_commerce_order_items"
						).setParameter(
							"commerceOrderId", getCommerceOrderId()
						).setParameter(
							"commerceOrderItemIds", _getCommerceOrderItemIds()
						).setParameter(
							"commerceReturnId", commerceReturn.getId()
						).setWindowState(
							LiferayWindowState.POP_UP
						).buildPortletURL());
					dropdownItem.setLabel(
						_language.get(
							_cpRequestHelper.getRequest(), "add-return-item"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	public List<FDSActionDropdownItem>
			getCommerceReturnItemFDSActionDropdownItems()
		throws PortalException {

		HttpServletRequest httpServletRequest = _cpRequestHelper.getRequest();

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						httpServletRequest, CommerceReturn.class.getName(),
						PortletProvider.Action.EDIT)
				).setMVCRenderCommandName(
					"/commerce_return_content/edit_commerce_return_item"
				).setParameter(
					"commerceReturnItemId", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				null, "edit", _language.get(httpServletRequest, "edit"), "get",
				"get", "sidePanel"),
			new FDSActionDropdownItem(
				null, null, "delete",
				_language.get(httpServletRequest, "delete"), "delete", "delete",
				"headless"));
	}

	public long getCommerceReturnItemId() {
		if (_commerceReturnItemId > 0) {
			return _commerceReturnItemId;
		}

		_commerceReturnItemId = ParamUtil.getLong(
			_cpRequestHelper.getRequest(), "commerceReturnItemId");

		return _commerceReturnItemId;
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws PortalException {

		HttpServletRequest httpServletRequest = _cpRequestHelper.getRequest();

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						httpServletRequest, CommerceReturn.class.getName(),
						PortletProvider.Action.EDIT)
				).setMVCRenderCommandName(
					"/commerce_return_content/view_commerce_return"
				).setParameter(
					"commerceReturnId", "{id}"
				).buildString(),
				null, "view", _language.get(httpServletRequest, "view"), "get",
				"get", null));
	}

	public String getFormattedQuantity() throws PortalException {
		CommerceReturnItem commerceReturnItem = getCommerceReturnItem();

		if (commerceReturnItem == null) {
			return StringPool.BLANK;
		}

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(
				commerceReturnItem.getCommerceOrderItemId());

		BigDecimal formattedQuantity = _commerceQuantityFormatter.format(
			_cpRequestHelper.getCompanyId(), commerceReturnItem.getQuantity(),
			commerceOrderItem.getSku(),
			commerceOrderItem.getUnitOfMeasureKey());

		return formattedQuantity.toString();
	}

	public List<HeaderActionModel> getHeaderActionModels() throws Exception {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		CommerceReturn commerceReturn = getCommerceReturn();

		if ((commerceReturn != null) &&
			!Objects.equals(commerceReturn.getReturnStatus(), "draft")) {

			return headerActionModels;
		}

		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		HeaderActionModel headerActionModel = new HeaderActionModel(
			"btn-primary", liferayPortletResponse.getNamespace() + "fm", null,
			liferayPortletResponse.getNamespace() + "submitReturnRequestButton",
			"submit-return-request");

		if (commerceReturn.getRequestedItems() <= 0) {
			headerActionModel.setAdditionalClasses("disabled");
		}

		headerActionModels.add(headerActionModel);

		return headerActionModels;
	}

	public String getListTypeEntriesByExternalReferenceCodeURL() {
		return StringBundler.concat(
			"/o/headless-admin-list-type/v1.0/list-type-definitions",
			"/by-external-reference-code/L_COMMERCE_RETURN_REASONS",
			"/list-type-entries");
	}

	public String getOrderDate() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		return dateTimeFormat.format(commerceOrder.getOrderDate());
	}

	public String getPaymentMethod() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return StringPool.BLANK;
		}

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				fetchCommercePaymentMethodGroupRel(
					commerceOrder.getGroupId(),
					commerceOrder.getCommercePaymentMethodKey());

		if (commercePaymentMethodGroupRel == null) {
			return StringPool.BLANK;
		}

		return commercePaymentMethodGroupRel.getName(
			_cpRequestHelper.getLocale());
	}

	public String getRefundSubtotal() throws PortalException {
		return _getTotalAmount();
	}

	public String getReturnItemsAPIURL() {
		long commerceReturnId = getCommerceReturnId();

		if (commerceReturnId == 0) {
			return StringPool.BLANK;
		}

		String encodedFilter = URLCodec.encodeURL(
			StringBundler.concat(
				"'r_commerceReturnToCommerceReturnItems_c_commerceReturnId' ",
				"eq '", commerceReturnId, StringPool.APOSTROPHE),
			true);

		return "/o/commerce-return-items" +
			"?nestedFields=commerceOrderItemToCommerceReturnItems&filter=" +
				encodedFilter;
	}

	public String getReturnReasonName() throws PortalException {
		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"L_COMMERCE_RETURN_REASONS",
					_cpRequestHelper.getCompanyId());

		if (listTypeDefinition == null) {
			return StringPool.BLANK;
		}

		CommerceReturnItem commerceReturnItem = getCommerceReturnItem();

		if (commerceReturnItem == null) {
			return StringPool.BLANK;
		}

		for (ListTypeEntry listTypeEntry :
				_listTypeEntryService.getListTypeEntries(
					listTypeDefinition.getListTypeDefinitionId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			if (Objects.equals(
					listTypeEntry.getKey(),
					commerceReturnItem.getReturnReason())) {

				return listTypeEntry.getName(_cpRequestHelper.getLocale());
			}
		}

		return StringPool.BLANK;
	}

	public String getReturnStatus() {
		CommerceReturn commerceReturn = getCommerceReturn();

		if (commerceReturn == null) {
			return "draft";
		}

		return commerceReturn.getReturnStatus();
	}

	public String getReturnStatusDisplayType() {
		String returnStatus = getReturnStatus();

		if (returnStatus.equals("draft")) {
			return "info";
		}
		else if (returnStatus.equals("completed")) {
			return "success";
		}

		return StringPool.BLANK;
	}

	public String getShippingAddress() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return StringPool.BLANK;
		}

		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();

		StringBundler sb = new StringBundler(5);

		sb.append(shippingAddress.getCity());
		sb.append(StringPool.COMMA_AND_SPACE);

		try {
			Region region = shippingAddress.getRegion();

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

		sb.append(shippingAddress.getZip());

		return sb.toString();
	}

	public String getTotalEstimatedRefund() throws PortalException {
		return _getTotalAmount();
	}

	public String getUnitsReturned() {
		CommerceReturn commerceReturn = getCommerceReturn();

		if (commerceReturn == null) {
			return StringPool.BLANK;
		}

		int requestedItems = commerceReturn.getRequestedItems();

		return _language.format(
			_cpRequestHelper.getRequest(),
			(requestedItems == 1) ? "x-item" : "x-items", requestedItems,
			false);
	}

	private String _getCommerceOrderItemIds() throws PortalException {
		CommerceReturn commerceReturn = getCommerceReturn();

		if (commerceReturn == null) {
			return StringPool.BLANK;
		}

		ObjectEntry objectEntry = commerceReturn.getObjectEntry();

		ObjectRelationship commerceReturnToCommerceReturnItems =
			_objectRelationshipLocalService.getObjectRelationship(
				objectEntry.getObjectDefinitionId(),
				"commerceReturnToCommerceReturnItems");

		return StringUtil.merge(
			TransformUtil.transformToLongArray(
				_objectEntryLocalService.getOneToManyObjectEntries(
					objectEntry.getGroupId(),
					commerceReturnToCommerceReturnItems.
						getObjectRelationshipId(),
					commerceReturn.getId(), true, null, -1, -1),
				curObjectEntry -> {
					Map<String, Serializable> values =
						curObjectEntry.getValues();

					return (long)values.get(
						"r_commerceOrderItemToCommerceReturnItems_" +
							"commerceOrderItemId");
				}));
	}

	private String _getTotalAmount() throws PortalException {
		CommerceReturn commerceReturn = getCommerceReturn();

		if (commerceReturn == null) {
			return StringPool.BLANK;
		}

		CommerceCurrency commerceCurrency = null;

		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder != null) {
			commerceCurrency = commerceOrder.getCommerceCurrency();
		}

		return _commercePriceFormatter.format(
			commerceCurrency, commerceReturn.getTotalAmount(),
			_cpRequestHelper.getLocale());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnContentDisplayContext.class);

	private final AccountEntryLocalService _accountEntryLocalService;
	private final CommerceContext _commerceContext;
	private CommerceOrder _commerceOrder;
	private final CommerceOrderItemService _commerceOrderItemService;
	private final CommerceOrderService _commerceOrderService;
	private final CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CommerceQuantityFormatter _commerceQuantityFormatter;
	private CommerceReturn _commerceReturn;
	private long _commerceReturnId;
	private CommerceReturnItem _commerceReturnItem;
	private long _commerceReturnItemId;
	private final CPRequestHelper _cpRequestHelper;
	private final Language _language;
	private final ListTypeDefinitionService _listTypeDefinitionService;
	private final ListTypeEntryService _listTypeEntryService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}