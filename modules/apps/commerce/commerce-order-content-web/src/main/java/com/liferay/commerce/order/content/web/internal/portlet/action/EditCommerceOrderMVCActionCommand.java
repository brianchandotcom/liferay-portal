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

package com.liferay.commerce.order.content.web.internal.portlet.action;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.exception.CommerceOrderAccountLimitException;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT_MINI,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER_CONTENT,
		"mvc.command.name=/commerce_open_order_content/edit_commerce_order"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				CommerceOrder commerceOrder = _addCommerceOrder(actionRequest);

				String redirect = _getOrderDetailRedirect(
					commerceOrder, actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceOrders(actionRequest);

				PortletURL openOrdersPortletURL =
					PortletProviderUtil.getPortletURL(
						actionRequest, CommerceOrder.class.getName(),
						PortletProvider.Action.EDIT);

				sendRedirect(
					actionRequest, actionResponse,
					openOrdersPortletURL.toString());
			}
			else if (cmd.equals(Constants.UPDATE)) {
				_updateCommerceOrder(actionRequest);
			}
			else if (cmd.equals("addBillingAddress")) {
				_addBillingAddress(actionRequest);
			}
			else if (cmd.equals("addShippingAddress")) {
				_addShippingAddress(actionRequest);
			}
			else if (cmd.equals("purchaseOrderNumber")) {
				_updatePurchaseOrderNumber(actionRequest);
			}
			else if (cmd.equals("requestedDeliveryDate")) {
				_updateRequestedDeliveryDate(actionRequest);
			}
			else if (cmd.equals("reorder")) {
				_reorderCommerceOrder(actionRequest);
			}
			else if (cmd.equals("requestedQuote")) {
				_updateRequestedQuoteCommerceOrder(actionRequest);
			}
			else if (cmd.equals("selectBillingAddress")) {
				_selectBillingAddress(actionRequest);
			}
			else if (cmd.equals("selectShippingAddress")) {
				_selectShippingAddress(actionRequest);
			}
			else if (cmd.equals("setCurrent")) {
				long commerceOrderId = ParamUtil.getLong(
					actionRequest, "commerceOrderId");

				setCurrentCommerceOrder(actionRequest, commerceOrderId);

				hideDefaultSuccessMessage(actionRequest);

				sendRedirect(
					actionRequest, actionResponse,
					PortletURLBuilder.create(
						PortletProviderUtil.getPortletURL(
							actionRequest, CommerceOrder.class.getName(),
							PortletProvider.Action.EDIT)
					).setMVCRenderCommandName(
						"/commerce_open_order_content/edit_commerce_order"
					).setBackURL(
						ParamUtil.getString(actionRequest, "redirect")
					).setParameter(
						"commerceOrderId", commerceOrderId
					).buildString());
			}
			else if (cmd.equals("transition")) {
				_executeTransition(actionRequest);
			}
			else if (cmd.equals("updateBillingAddress")) {
				_updateBillingAddress(actionRequest);
			}
			else if (cmd.equals("updateShippingAddress")) {
				_updateShippingAddress(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof NoSuchOrderException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof CommerceOrderValidatorException) {
				CommerceOrderValidatorException
					commerceOrderValidatorException =
						(CommerceOrderValidatorException)exception;

				SessionErrors.add(
					actionRequest, commerceOrderValidatorException.getClass(),
					commerceOrderValidatorException);

				hideDefaultErrorMessage(actionRequest);
			}
			else {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
	}

	protected void setCurrentCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws Exception {

		CommerceOrder currentCommerceOrder =
			_commerceOrderHttpHelper.getCurrentCommerceOrder(
				_portal.getHttpServletRequest(actionRequest));

		if ((currentCommerceOrder == null) ||
			(commerceOrderId != currentCommerceOrder.getCommerceOrderId())) {

			_commerceOrderHttpHelper.setCurrentCommerceOrder(
				_portal.getHttpServletRequest(actionRequest),
				_commerceOrderService.getCommerceOrder(commerceOrderId));
		}
	}

	private void _addBillingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long regionId = ParamUtil.getLong(actionRequest, "regionId");
		long countryId = ParamUtil.getLong(actionRequest, "countryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAddress.class.getName(), actionRequest);

		CommerceAddress commerceAddress =
			_commerceAddressService.addCommerceAddress(
				AccountEntry.class.getName(),
				commerceOrder.getCommerceAccountId(), name, description,
				street1, street2, street3, city, zip, regionId, countryId,
				phoneNumber, CommerceAddressConstants.ADDRESS_TYPE_BILLING,
				serviceContext);

		_commerceOrderService.updateBillingAddress(
			commerceOrder.getCommerceOrderId(),
			commerceAddress.getCommerceAddressId());
	}

	private CommerceOrder _addCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		AccountEntry accountEntry = commerceContext.getAccountEntry();

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commerceCurrencyId = 0;

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		if (commerceCurrency != null) {
			commerceCurrencyId = commerceCurrency.getCommerceCurrencyId();
		}

		long commerceChannelGroupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				themeDisplay.getScopeGroupId());

		long commerceOrderTypeId = ParamUtil.getLong(
			actionRequest, "commerceOrderTypeId");

		if (commerceOrderTypeId == 0) {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByGroupId(
					commerceChannelGroupId);

			List<CommerceOrderType> commerceOrderTypes =
				_commerceOrderTypeService.getCommerceOrderTypes(
					CommerceChannel.class.getName(),
					commerceChannel.getCommerceChannelId(), true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			if (!commerceOrderTypes.isEmpty()) {
				CommerceOrderType commerceOrderType = commerceOrderTypes.get(0);

				commerceOrderTypeId =
					commerceOrderType.getCommerceOrderTypeId();
			}
		}

		try {
			return _commerceOrderService.addCommerceOrder(
				commerceChannelGroupId, accountEntry.getAccountEntryId(),
				commerceCurrencyId, commerceOrderTypeId);
		}
		catch (Exception exception) {
			if (exception instanceof CommerceOrderAccountLimitException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				return null;
			}

			throw exception;
		}
	}

	private void _addShippingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long regionId = ParamUtil.getLong(actionRequest, "regionId");
		long countryId = ParamUtil.getLong(actionRequest, "countryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAddress.class.getName(), actionRequest);

		CommerceAddress commerceAddress =
			_commerceAddressService.addCommerceAddress(
				AccountEntry.class.getName(),
				commerceOrder.getCommerceAccountId(), name, description,
				street1, street2, street3, city, zip, regionId, countryId,
				phoneNumber, CommerceAddressConstants.ADDRESS_TYPE_SHIPPING,
				serviceContext);

		_commerceOrderService.updateShippingAddress(
			commerceOrder.getCommerceOrderId(),
			commerceAddress.getCommerceAddressId());
	}

	private void _checkoutCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		_commerceAccountHelper.setCurrentCommerceAccount(
			_portal.getHttpServletRequest(actionRequest),
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				_portal.getScopeGroupId(actionRequest)),
			commerceOrder.getCommerceAccountId());

		actionRequest.setAttribute(
			WebKeys.REDIRECT,
			PortletURLBuilder.create(
				_commerceOrderHttpHelper.getCommerceCheckoutPortletURL(
					_portal.getHttpServletRequest(actionRequest))
			).setParameter(
				"commerceOrderId", commerceOrderId
			).buildString());
	}

	private void _checkoutOrSubmitCommerceOrder(
			ActionRequest actionRequest, CommerceOrder commerceOrder)
		throws Exception {

		if (commerceOrder.isOpen() && !commerceOrder.isPending()) {
			_checkoutCommerceOrder(
				actionRequest, commerceOrder.getCommerceOrderId());

			return;
		}

		PortletURL portletURL = null;

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		long plid = _portal.getPlidFromPortletId(
			groupId, CommercePortletKeys.COMMERCE_ORDER_CONTENT);

		if (plid > 0) {
			portletURL = _portletURLFactory.create(
				httpServletRequest, CommercePortletKeys.COMMERCE_ORDER_CONTENT,
				plid, PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = _portletURLFactory.create(
				httpServletRequest, CommercePortletKeys.COMMERCE_ORDER_CONTENT,
				PortletRequest.RENDER_PHASE);
		}

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
	}

	private void _deleteCommerceOrders(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceOrderIds = null;

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			deleteCommerceOrderIds = new long[] {commerceOrderId};
		}
		else {
			deleteCommerceOrderIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceOrderIds"),
				0L);
		}

		for (long deleteCommerceOrderId : deleteCommerceOrderIds) {
			_commerceOrderHttpHelper.deleteCommerceOrder(
				actionRequest, deleteCommerceOrderId);
		}
	}

	private void _executeTransition(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long workflowTaskId = ParamUtil.getLong(
			actionRequest, "workflowTaskId");
		String transitionName = ParamUtil.getString(
			actionRequest, "transitionName");

		if (workflowTaskId > 0) {
			_executeWorkflowTransition(
				actionRequest, commerceOrderId, transitionName, workflowTaskId);
		}
		else if (transitionName.equals("checkout")) {
			_checkoutCommerceOrder(actionRequest, commerceOrderId);
		}
		else {
			CommerceOrder commerceOrder =
				_commerceOrderService.getCommerceOrder(commerceOrderId);

			int orderStatus = GetterUtil.getInteger(
				transitionName, commerceOrder.getOrderStatus());

			if (transitionName.equals("submit")) {
				orderStatus = CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS;
			}

			_commerceOrderEngine.transitionCommerceOrder(
				commerceOrder, orderStatus, _portal.getUserId(actionRequest));
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	private void _executeWorkflowTransition(
			ActionRequest actionRequest, long commerceOrderId,
			String transitionName, long workflowTaskId)
		throws Exception {

		String comment = ParamUtil.getString(actionRequest, "comment");

		_commerceOrderService.executeWorkflowTransition(
			commerceOrderId, workflowTaskId, transitionName, comment);
	}

	private String _getOrderDetailRedirect(
			CommerceOrder commerceOrder, ActionRequest actionRequest)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, CommerceOrder.class.getName(),
			PortletProvider.Action.EDIT);

		if (commerceOrder != null) {
			portletURL.setParameter(
				"mvcRenderCommandName",
				"/commerce_open_order_content/edit_commerce_order");
			portletURL.setParameter(
				"commerceOrderId",
				String.valueOf(commerceOrder.getCommerceOrderId()));

			String backURL = ParamUtil.getString(actionRequest, "backURL");

			portletURL.setParameter("backURL", backURL);
		}

		return portletURL.toString();
	}

	private void _reorderCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		_reorderCommerceOrder(actionRequest, commerceOrderId);
	}

	private void _reorderCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceOrder commerceOrder =
			_commerceOrderService.reorderCommerceOrder(
				commerceOrderId, commerceContext);

		_commerceAccountHelper.setCurrentCommerceAccount(
			_portal.getHttpServletRequest(actionRequest),
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				_portal.getScopeGroupId(actionRequest)),
			commerceOrder.getCommerceAccountId());
		_commerceOrderHttpHelper.setCurrentCommerceOrder(
			_portal.getHttpServletRequest(actionRequest), commerceOrder);

		_checkoutOrSubmitCommerceOrder(actionRequest, commerceOrder);
	}

	private void _selectBillingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long addressId = ParamUtil.getLong(actionRequest, "addressId");

		_commerceOrderService.updateBillingAddress(commerceOrderId, addressId);
	}

	private void _selectShippingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long addressId = ParamUtil.getLong(actionRequest, "addressId");

		_commerceOrderService.updateShippingAddress(commerceOrderId, addressId);
	}

	private void _updateBillingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long regionId = ParamUtil.getLong(actionRequest, "regionId");
		long countryId = ParamUtil.getLong(actionRequest, "countryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateBillingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, regionId, countryId, phoneNumber, serviceContext);
	}

	private void _updateCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		long billingAddressId = ParamUtil.getLong(
			actionRequest, "billingAddressId");
		long shippingAddressId = ParamUtil.getLong(
			actionRequest, "shippingAddressId");
		String purchaseOrderNumber = ParamUtil.getString(
			actionRequest, "purchaseOrderNumber");

		_commerceOrderService.updateCommerceOrder(
			commerceOrder.getExternalReferenceCode(), commerceOrderId,
			billingAddressId, commerceOrder.getCommerceShippingMethodId(),
			shippingAddressId, commerceOrder.getAdvanceStatus(),
			commerceOrder.getCommercePaymentMethodKey(), purchaseOrderNumber,
			commerceOrder.getShippingAmount(),
			commerceOrder.getShippingOptionName(), commerceOrder.getSubtotal(),
			commerceOrder.getTotal(), commerceContext);
	}

	private void _updateCommerceOrderNote(ActionRequest actionRequest)
		throws Exception {

		String content = ParamUtil.getString(actionRequest, "content");

		if (Validator.isNotNull(content)) {
			boolean restricted = ParamUtil.getBoolean(
				actionRequest, "restricted");

			long commerceOrderId = ParamUtil.getLong(
				actionRequest, "commerceOrderId");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceOrderNote.class.getName(), actionRequest);

			_commerceOrderNoteService.addCommerceOrderNote(
				commerceOrderId, content, restricted, serviceContext);
		}
	}

	private void _updatePurchaseOrderNumber(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");
		String purchaseOrderNumber = ParamUtil.getString(
			actionRequest, "purchaseOrderNumber");

		_commerceOrderService.updatePurchaseOrderNumber(
			commerceOrderId, purchaseOrderNumber);
	}

	private void _updateRequestedDeliveryDate(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		int requestedDeliveryDateMonth = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateMonth");
		int requestedDeliveryDateDay = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateDay");
		int requestedDeliveryDateYear = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateYear");
		int requestedDeliveryDateHour = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateHour");
		int requestedDeliveryDateMinute = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateMinute");
		int requestedDeliveryDateAmPm = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateAmPm");

		if (requestedDeliveryDateAmPm == Calendar.PM) {
			requestedDeliveryDateHour += 12;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateInfo(
			commerceOrder.getCommerceOrderId(), commerceOrder.getPrintedNote(),
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear, requestedDeliveryDateHour,
			requestedDeliveryDateMinute, serviceContext);
	}

	private void _updateRequestedQuoteCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		_updateCommerceOrderNote(actionRequest);

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		_commerceOrderEngine.transitionCommerceOrder(
			commerceOrder, CommerceOrderConstants.ORDER_STATUS_QUOTE_REQUESTED,
			_portal.getUserId(actionRequest));

		actionRequest.setAttribute(
			WebKeys.REDIRECT,
			PortletURLBuilder.create(
				_commerceOrderHttpHelper.getCommerceCartPortletURL(
					_portal.getHttpServletRequest(actionRequest), commerceOrder)
			).buildString());
	}

	private void _updateShippingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long regionId = ParamUtil.getLong(actionRequest, "regionId");
		long countryId = ParamUtil.getLong(actionRequest, "countryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateShippingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, regionId, countryId, phoneNumber, serviceContext);
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}