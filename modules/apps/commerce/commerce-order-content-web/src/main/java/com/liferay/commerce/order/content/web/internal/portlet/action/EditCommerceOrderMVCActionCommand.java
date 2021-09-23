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

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.exception.CommerceOrderAccountLimitException;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT_MINI,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER_CONTENT,
		"mvc.command.name=/commerce_open_order_content/edit_commerce_order"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderMVCActionCommand extends BaseMVCActionCommand {

	protected CommerceOrder addCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount == null) {
			throw new NoSuchAccountException();
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
				commerceChannelGroupId, commerceAccount.getCommerceAccountId(),
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

	protected void checkoutCommerceOrder(
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

	protected void checkoutOrSubmitCommerceOrder(
			ActionRequest actionRequest, CommerceOrder commerceOrder)
		throws Exception {

		if (commerceOrder.isOpen() && !commerceOrder.isPending()) {
			checkoutCommerceOrder(
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

	protected void deleteCommerceOrders(ActionRequest actionRequest)
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
			_commerceOrderService.deleteCommerceOrder(deleteCommerceOrderId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				CommerceOrder commerceOrder = addCommerceOrder(actionRequest);

				String redirect = _getOrderDetailRedirect(
					commerceOrder, actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceOrder(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceOrders(actionRequest);

				PortletURL openOrdersPortletURL =
					PortletProviderUtil.getPortletURL(
						actionRequest, CommerceOrder.class.getName(),
						PortletProvider.Action.EDIT);

				sendRedirect(
					actionRequest, actionResponse,
					openOrdersPortletURL.toString());
			}
			else if (cmd.equals("reorder")) {
				reorderCommerceOrder(actionRequest);
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
					).setParameter(
						PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE +
							"backURL",
						ParamUtil.getString(actionRequest, "redirect")
					).setParameter(
						"commerceOrderId", commerceOrderId
					).buildString());
			}
			else if (cmd.equals("transition")) {
				executeTransition(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchAccountException ||
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
				throw exception;
			}
		}
	}

	protected void executeTransition(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long workflowTaskId = ParamUtil.getLong(
			actionRequest, "workflowTaskId");
		String transitionName = ParamUtil.getString(
			actionRequest, "transitionName");

		if (workflowTaskId > 0) {
			executeWorkflowTransition(
				actionRequest, commerceOrderId, transitionName, workflowTaskId);
		}
		else if (transitionName.equals("checkout")) {
			checkoutCommerceOrder(actionRequest, commerceOrderId);
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

	protected void executeWorkflowTransition(
			ActionRequest actionRequest, long commerceOrderId,
			String transitionName, long workflowTaskId)
		throws Exception {

		String comment = ParamUtil.getString(actionRequest, "comment");

		_commerceOrderService.executeWorkflowTransition(
			commerceOrderId, workflowTaskId, transitionName, comment);
	}

	protected void reorderCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		reorderCommerceOrder(actionRequest, commerceOrderId);
	}

	protected void reorderCommerceOrder(
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

		checkoutOrSubmitCommerceOrder(actionRequest, commerceOrder);
	}

	protected void setCurrentCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws Exception {

		_commerceOrderHttpHelper.setCurrentCommerceOrder(
			_portal.getHttpServletRequest(actionRequest),
			_commerceOrderService.getCommerceOrder(commerceOrderId));
	}

	protected void updateCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long billingAddressId = ParamUtil.getLong(
			actionRequest, "billingAddressId");
		long shippingAddressId = ParamUtil.getLong(
			actionRequest, "shippingAddressId");
		String purchaseOrderNumber = ParamUtil.getString(
			actionRequest, "purchaseOrderNumber");

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		_commerceOrderService.updateCommerceOrder(
			commerceOrderId, billingAddressId, shippingAddressId,
			commerceOrder.getCommercePaymentMethodKey(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(), purchaseOrderNumber,
			commerceOrder.getSubtotal(), commerceOrder.getShippingAmount(),
			commerceOrder.getTotal(), commerceOrder.getAdvanceStatus(),
			commerceContext);
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

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}