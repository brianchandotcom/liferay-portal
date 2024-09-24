/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.internal.resource.v1_0;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.exception.CommerceOrderStatusException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommerceWorkflowedModelHelper;
import com.liferay.headless.commerce.delivery.order.dto.v1_0.OrderTransition;
import com.liferay.headless.commerce.delivery.order.resource.v1_0.OrderTransitionResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/order-transition.properties",
	scope = ServiceScope.PROTOTYPE, service = OrderTransitionResource.class
)
public class OrderTransitionResourceImpl
	extends BaseOrderTransitionResourceImpl {

	@Override
	public Page<OrderTransition> getPlacedOrderOrderTransitionsPage(
			Long placedOrderId)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			placedOrderId);

		if (commerceOrder.isOpen()) {
			throw new CommerceOrderStatusException(
				"Unable to get order transitions of an open order");
		}

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		transitionOVPs.addAll(
			_commerceWorkflowedModelHelper.getWorkflowTransitions(
				contextUser.getUserId(), commerceOrder.getCompanyId(),
				commerceOrder.getModelClassName(),
				commerceOrder.getCommerceOrderId()));

		CommerceOrderStatus quoteProcessedCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				CommerceOrderConstants.ORDER_STATUS_QUOTE_PROCESSED);

		if (quoteProcessedCommerceOrderStatus.isTransitionCriteriaMet(
				commerceOrder)) {

			transitionOVPs.add(new ObjectValuePair<>(0L, "process-quote"));
		}

		transitionOVPs.add(new ObjectValuePair<>(0L, "reorder"));

		return Page.of(
			transform(
				transitionOVPs,
				transitionOVP -> _toOrderTransition(
					commerceOrder.getCommerceOrderId(), null, 0,
					transitionOVP)),
			null, transitionOVPs.size());
	}

	@Override
	public OrderTransition postPlacedOrderOrderTransition(
			Long placedOrderId, OrderTransition orderTransition)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			placedOrderId);

		if (commerceOrder.isOpen()) {
			throw new CommerceOrderStatusException(
				"Unable to post order transition of an open order");
		}

		String comment = GetterUtil.getString(orderTransition.getComment());
		String name = GetterUtil.getString(orderTransition.getName());

		long workflowTaskId = GetterUtil.getLong(
			orderTransition.getWorkflowTaskId());

		if (workflowTaskId > 0) {
			_commerceOrderService.executeWorkflowTransition(
				placedOrderId, workflowTaskId, name, comment);
		}
		else if (name.equals("process-quote")) {
			_commerceOrderEngine.transitionCommerceOrder(
				commerceOrder,
				CommerceOrderConstants.ORDER_STATUS_QUOTE_PROCESSED,
				contextUser.getUserId(), true);
		}
		else if (name.equals("reorder")) {
			CommerceOrder newCommerceOrder =
				_commerceOrderService.reorderCommerceOrder(
					commerceOrder.getCommerceOrderId(),
					_commerceContextFactory.create(
						contextCompany.getCompanyId(),
						commerceOrder.getGroupId(), contextUser.getUserId(),
						commerceOrder.getCommerceOrderId(),
						commerceOrder.getCommerceAccountId()));

			return _toOrderTransition(
				commerceOrder.getCommerceOrderId(), comment,
				newCommerceOrder.getCommerceOrderId(),
				new ObjectValuePair<>(workflowTaskId, name));
		}

		return _toOrderTransition(
			commerceOrder.getCommerceOrderId(), comment, 0,
			new ObjectValuePair<>(workflowTaskId, name));
	}

	private OrderTransition _toOrderTransition(
			long commerceOrderId, String comment, long orderId,
			ObjectValuePair<Long, String> transitionOVP)
		throws Exception {

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				_dtoConverterRegistry, commerceOrderId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser);

		defaultDTOConverterContext.setAttribute("comment", comment);
		defaultDTOConverterContext.setAttribute("orderId", orderId);
		defaultDTOConverterContext.setAttribute("transitionOVP", transitionOVP);

		return _orderTransitionDTOConverter.toDTO(defaultDTOConverterContext);
	}

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private CommerceWorkflowedModelHelper _commerceWorkflowedModelHelper;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.order.internal.dto.v1_0.converter.OrderTransitionDTOConverter)"
	)
	private DTOConverter<CommerceOrder, OrderTransition>
		_orderTransitionDTOConverter;

}