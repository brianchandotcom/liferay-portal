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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.frontend.model.StepModel;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.servlet.taglib.ui.constants.CommerceOrderScreenNavigationConstants;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderURL;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 * @author Fabio Diego Mastrorilli
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderEditDisplayContext {

	public CommerceOrderEditDisplayContext(
			CommerceChannelLocalService commerceChannelLocalService,
			CommerceNotificationQueueEntryLocalService
				commerceNotificationQueueEntryLocalService,
			CommerceOrderEngine commerceOrderEngine,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderNoteService commerceOrderNoteService,
			CommerceOrderService commerceOrderService,
			CommerceOrderStatusRegistry commerceOrderStatusRegistry,
			CommercePaymentMethodGroupRelLocalService
				commercePaymentMethodGroupRelLocalService,
			CommerceShipmentService commerceShipmentService,
			RenderRequest renderRequest)
		throws PortalException {

		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceNotificationQueueEntryLocalService =
			commerceNotificationQueueEntryLocalService;
		_commerceOrderEngine = commerceOrderEngine;
		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commerceOrderService = commerceOrderService;
		_commerceOrderStatusRegistry = commerceOrderStatusRegistry;
		_commercePaymentMethodGroupRelLocalService =
			commercePaymentMethodGroupRelLocalService;
		_commerceShipmentService = commerceShipmentService;

		long commerceOrderId = ParamUtil.getLong(
			renderRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			_commerceOrder = _commerceOrderService.getCommerceOrder(
				commerceOrderId);
		}
		else {
			_commerceOrder = null;
		}

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDateTime =
			FastDateFormatFactoryUtil.getDateTime(
				DateFormat.SHORT, DateFormat.SHORT, themeDisplay.getLocale(),
				themeDisplay.getTimeZone());
	}

	public String getCommerceAccountThumbnailURL() throws PortalException {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		CommerceAccount commerceAccount = _commerceOrder.getCommerceAccount();

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/organization_logo?img_id=");
		sb.append(commerceAccount.getLogoId());

		if (commerceAccount.getLogoId() > 0) {
			sb.append("&t=");
			sb.append(
				WebServerServletTokenUtil.getToken(
					commerceAccount.getLogoId()));
		}

		return sb.toString();
	}

	public CreationMenu getCommerceAddressCreationMenu(
			String mvcRenderCommandName)
		throws Exception {

		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		RenderURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
		portletURL.setParameter(Constants.CMD, Constants.ADD);
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(getCommerceOrderId()));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(portletURL.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_commerceOrderRequestHelper.getRequest(),
						"add-new-address"));
			}
		).build();
	}

	public String getCommerceChannelName() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		return commerceChannel.getName();
	}

	public PortletURL getCommerceNotificationQueueEntriesPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_commerceOrderRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/commerce_order/edit_commerce_order"
		).setParameter(
			"commerceOrderId", getCommerceOrderId()
		).setParameter(
			"screenNavigationCategoryKey",
			CommerceOrderScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_ORDER_EMAILS
		).build();

		String redirect = ParamUtil.getString(
			_commerceOrderRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		return portletURL;
	}

	public CommerceNotificationQueueEntry getCommerceNotificationQueueEntry()
		throws PortalException {

		long commerceNotificationQueueEntryId = ParamUtil.getLong(
			_commerceOrderRequestHelper.getRequest(),
			"commerceNotificationQueueEntryId");

		if (commerceNotificationQueueEntryId > 0) {
			return _commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntry(
					commerceNotificationQueueEntryId);
		}

		return null;
	}

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public String getCommerceOrderDateTime(Date date) {
		if ((_commerceOrder == null) || (date == null)) {
			return StringPool.BLANK;
		}

		return _commerceOrderDateFormatDateTime.format(date);
	}

	public long getCommerceOrderId() {
		if (_commerceOrder == null) {
			return 0;
		}

		return _commerceOrder.getCommerceOrderId();
	}

	public CommerceOrderItem getCommerceOrderItem() throws PortalException {
		if (_commerceOrderItem != null) {
			return _commerceOrderItem;
		}

		long commerceOrderItemId = ParamUtil.getLong(
			_commerceOrderRequestHelper.getRequest(), "commerceOrderItemId");

		if (commerceOrderItemId > 0) {
			_commerceOrderItem = _commerceOrderItemService.getCommerceOrderItem(
				commerceOrderItemId);
		}

		return _commerceOrderItem;
	}

	public PortletURL getCommerceOrderItemsPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_commerceOrderRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/commerce_order/edit_commerce_order"
		).setParameter(
			"commerceOrderId", getCommerceOrderId()
		).setParameter(
			"screenNavigationCategoryKey",
			CommerceOrderScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_ORDER_GENERAL
		).build();

		String redirect = ParamUtil.getString(
			_commerceOrderRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		return portletURL;
	}

	public List<CommerceOrderNote> getCommerceOrderNotes()
		throws PortalException {

		long commerceOrderId = getCommerceOrderId();

		if (commerceOrderId <= 0) {
			return Collections.emptyList();
		}

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public String getCommerceOrderPaymentMethodDescription()
		throws PortalException {

		if ((_commerceOrder == null) ||
			Validator.isNull(_commerceOrder.getCommercePaymentMethodKey())) {

			return StringPool.BLANK;
		}

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			getCommercePaymentMethodGroupRel();

		return commercePaymentMethodGroupRel.getDescription(
			_commerceOrderRequestHelper.getLocale());
	}

	public String getCommerceOrderPaymentMethodName() throws PortalException {
		if ((_commerceOrder == null) ||
			Validator.isNull(_commerceOrder.getCommercePaymentMethodKey())) {

			return StringPool.BLANK;
		}

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			getCommercePaymentMethodGroupRel();

		return commercePaymentMethodGroupRel.getName(
			_commerceOrderRequestHelper.getLocale());
	}

	public PortletURL getCommerceOrderPaymentsPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_commerceOrderRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/commerce_order/edit_commerce_order"
		).setParameter(
			"commerceOrderId", getCommerceOrderId()
		).setParameter(
			"screenNavigationCategoryKey",
			CommerceOrderScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_ORDER_PAYMENTS
		).build();

		String redirect = ParamUtil.getString(
			_commerceOrderRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		return portletURL;
	}

	public CommercePaymentMethodGroupRel getCommercePaymentMethodGroupRel()
		throws PortalException {

		return _commercePaymentMethodGroupRelLocalService.
			getCommercePaymentMethodGroupRel(
				_commerceOrder.getGroupId(),
				_commerceOrder.getCommercePaymentMethodKey());
	}

	public CommerceShipment getCommerceShipment() throws PortalException {
		if (_commerceShipment != null) {
			return _commerceShipment;
		}

		long commerceShipmentId = ParamUtil.getLong(
			_commerceOrderRequestHelper.getRequest(), "commerceShipmentId");

		if (commerceShipmentId > 0) {
			_commerceShipment = _commerceShipmentService.getCommerceShipment(
				commerceShipmentId);
		}

		return _commerceShipment;
	}

	public PortletURL getCommerceShipmentsPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_commerceOrderRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/commerce_order/edit_commerce_order"
		).setParameter(
			"commerceOrderId", getCommerceOrderId()
		).setParameter(
			"screenNavigationCategoryKey",
			CommerceOrderScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_ORDER_SHIPMENTS
		).build();

		String redirect = ParamUtil.getString(
			_commerceOrderRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		return portletURL;
	}

	public String getDescriptiveCommerceAddress(CommerceAddress commerceAddress)
		throws PortalException {

		if (commerceAddress == null) {
			return StringPool.BLANK;
		}

		Region region = commerceAddress.getRegion();

		StringBundler sb = new StringBundler((region == null) ? 6 : 8);

		sb.append(commerceAddress.getStreet1());
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);

		if (!Validator.isBlank(commerceAddress.getStreet2())) {
			sb.append(commerceAddress.getStreet2());
			sb.append(StringPool.COMMA);
			sb.append(StringPool.SPACE);
		}

		if (!Validator.isBlank(commerceAddress.getStreet3())) {
			sb.append(commerceAddress.getStreet3());
			sb.append(StringPool.COMMA);
			sb.append(StringPool.SPACE);
		}

		sb.append(commerceAddress.getCity());
		sb.append(StringPool.NEW_LINE);

		if (region != null) {
			sb.append(region.getRegionCode());
			sb.append(StringPool.SPACE);
		}

		sb.append(commerceAddress.getZip());

		return sb.toString();
	}

	public List<HeaderActionModel> getHeaderActionModels()
		throws PortalException {

		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(_commerceOrder);

		if ((_commerceOrder == null) || (currentCommerceOrderStatus == null) ||
			!currentCommerceOrderStatus.isComplete(_commerceOrder) ||
			(currentCommerceOrderStatus.getKey() ==
				CommerceOrderConstants.ORDER_STATUS_CANCELLED) ||
			(currentCommerceOrderStatus.getKey() ==
				CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS)) {

			return headerActionModels;
		}

		List<CommerceOrderStatus> commerceOrderStatuses =
			_commerceOrderEngine.getNextCommerceOrderStatuses(_commerceOrder);

		PortletURL portletURL = getTransitionOrderPortletURL();

		for (CommerceOrderStatus commerceOrderStatus : commerceOrderStatuses) {
			if ((commerceOrderStatus.getKey() ==
					CommerceOrderConstants.ORDER_STATUS_SHIPPED) ||
				!commerceOrderStatus.isValidForOrder(_commerceOrder) ||
				!commerceOrderStatus.isTransitionCriteriaMet(_commerceOrder)) {

				continue;
			}

			String label = CommerceOrderConstants.getOrderStatusLabel(
				commerceOrderStatus.getKey());

			if (commerceOrderStatus.getKey() ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) {

				label = "create-shipment";
			}
			else if (commerceOrderStatus.getKey() ==
						CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS) {

				label = "check-out";

				if (!_commerceOrder.isApproved()) {
					label = "submit";
				}
			}
			else if (commerceOrderStatus.getKey() ==
						CommerceOrderConstants.ORDER_STATUS_PROCESSING) {

				label = "accept-order";
			}
			else if (commerceOrderStatus.getKey() ==
						CommerceOrderConstants.ORDER_STATUS_CANCELLED) {

				label = "cancel";
			}
			else if (commerceOrderStatus.getKey() ==
						CommerceOrderConstants.ORDER_STATUS_ON_HOLD) {

				if (currentCommerceOrderStatus.getKey() ==
						CommerceOrderConstants.ORDER_STATUS_ON_HOLD) {

					label = "release-hold";
				}
				else {
					label = "hold";
				}
			}

			if (Validator.isNull(label)) {
				label = commerceOrderStatus.getLabel(
					_commerceOrderRequestHelper.getLocale());
			}

			String buttonCssClass = "btn-primary";

			if (commerceOrderStatus.getPriority() ==
					CommerceOrderConstants.ORDER_STATUS_ANY) {

				buttonCssClass = "btn-secondary";
			}

			portletURL.setParameter(
				"transitionName", String.valueOf(commerceOrderStatus.getKey()));

			headerActionModels.add(
				new HeaderActionModel(
					buttonCssClass, null, portletURL.toString(), null, label));
		}

		return headerActionModels;
	}

	public List<StepModel> getOrderSteps() throws PortalException {
		List<StepModel> steps = new ArrayList<>();

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(_commerceOrder);

		if ((_commerceOrder == null) || (currentCommerceOrderStatus == null) ||
			(currentCommerceOrderStatus.getPriority() == -1)) {

			return steps;
		}

		if ((currentCommerceOrderStatus != null) &&
			currentCommerceOrderStatus.isWorkflowEnabled(_commerceOrder)) {

			return _getWorkflowSteps();
		}

		if (ArrayUtil.contains(
				CommerceOrderConstants.ORDER_STATUSES_OPEN,
				_commerceOrder.getOrderStatus())) {

			return steps;
		}

		List<CommerceOrderStatus> commerceOrderStatuses =
			_commerceOrderStatusRegistry.getCommerceOrderStatuses();

		for (CommerceOrderStatus commerceOrderStatus : commerceOrderStatuses) {
			if (((commerceOrderStatus.getKey() ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) &&
				 (_commerceOrder.getOrderStatus() !=
					 CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED)) ||
				!commerceOrderStatus.isValidForOrder(_commerceOrder) ||
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
				commerceOrderStatus.getLabel(
					_commerceOrderRequestHelper.getLocale()));

			if (commerceOrderStatus.equals(currentCommerceOrderStatus) &&
				(commerceOrderStatus.getKey() !=
					CommerceOrderConstants.ORDER_STATUS_COMPLETED)) {

				step.setState("active");
			}
			else if ((currentCommerceOrderStatus != null) &&
					 (commerceOrderStatus.getPriority() <=
						 currentCommerceOrderStatus.getPriority()) &&
					 commerceOrderStatus.isComplete(_commerceOrder)) {

				step.setState("completed");
			}
			else {
				step.setState("inactive");
			}

			steps.add(step);
		}

		return steps;
	}

	public PortletURL getTransitionOrderPortletURL() {
		return PortletURLBuilder.createActionURL(
			_commerceOrderRequestHelper.getLiferayPortletResponse()
		).setActionName(
			"/commerce_order/edit_commerce_order"
		).setCMD(
			"transition"
		).setRedirect(
			_commerceOrderRequestHelper.getCurrentURL()
		).setParameter(
			"commerceOrderId", _commerceOrder.getCommerceOrderId()
		).build();
	}

	private List<StepModel> _getWorkflowSteps() {
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
					_commerceOrderRequestHelper.getLocale(),
					workflowStatusLabel));

			if (_commerceOrder.getStatus() == workflowStatus) {
				step.setState("active");
			}
			else if (_commerceOrder.getStatus() < workflowStatus) {
				step.setState("completed");
			}
			else {
				step.setState("inactive");
			}

			steps.add(step);
		}

		return steps;
	}

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;
	private final CommerceOrder _commerceOrder;
	private final Format _commerceOrderDateFormatDateTime;
	private final CommerceOrderEngine _commerceOrderEngine;
	private CommerceOrderItem _commerceOrderItem;
	private final CommerceOrderItemService _commerceOrderItemService;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final CommerceOrderService _commerceOrderService;
	private final CommerceOrderStatusRegistry _commerceOrderStatusRegistry;
	private final CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;
	private CommerceShipment _commerceShipment;
	private final CommerceShipmentService _commerceShipmentService;

}