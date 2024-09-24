/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.exception.CommerceOrderStatusException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommerceWorkflowedModelHelper;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartTransition;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartTransitionResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/cart-transition.properties",
	scope = ServiceScope.PROTOTYPE, service = CartTransitionResource.class
)
public class CartTransitionResourceImpl extends BaseCartTransitionResourceImpl {

	@Override
	public Page<CartTransition> getCartCartTransitionsPage(Long cartId)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		if (!commerceOrder.isOpen()) {
			throw new CommerceOrderStatusException(
				"Unable to get order transitions of a placed order");
		}

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		CommerceOrderStatus quoteRequestedCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				CommerceOrderConstants.ORDER_STATUS_QUOTE_REQUESTED);

		if (quoteRequestedCommerceOrderStatus.isTransitionCriteriaMet(
				commerceOrder)) {

			transitionOVPs.add(new ObjectValuePair<>(0L, "request-quote"));
		}

		transitionOVPs.addAll(
			_commerceWorkflowedModelHelper.getWorkflowTransitions(
				contextUser.getUserId(), commerceOrder.getCompanyId(),
				commerceOrder.getModelClassName(),
				commerceOrder.getCommerceOrderId()));

		CommerceOrderStatus inProgressCommerceOrderStatus =
			_commerceOrderStatusRegistry.getCommerceOrderStatus(
				CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS);

		if (inProgressCommerceOrderStatus.isTransitionCriteriaMet(
				commerceOrder)) {

			if (commerceOrder.isApproved()) {
				transitionOVPs.add(new ObjectValuePair<>(0L, "checkout"));
			}
			else if (commerceOrder.isDraft()) {
				transitionOVPs.add(new ObjectValuePair<>(0L, "submit"));
			}
		}

		return Page.of(
			transform(
				transitionOVPs,
				transitionOVP -> _toCartTransition(
					commerceOrder.getCommerceOrderId(), null, transitionOVP)),
			null, transitionOVPs.size());
	}

	@Override
	public CartTransition postCartCartTransition(
			Long cartId, CartTransition cartTransition)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		if (!commerceOrder.isOpen()) {
			throw new CommerceOrderStatusException(
				"Unable to post order transition of a placed order");
		}

		String comment = GetterUtil.getString(cartTransition.getComment());
		String name = GetterUtil.getString(cartTransition.getName());

		long workflowTaskId = GetterUtil.getLong(
			cartTransition.getWorkflowTaskId());

		if (workflowTaskId > 0) {
			_commerceOrderService.executeWorkflowTransition(
				commerceOrder.getCommerceOrderId(), workflowTaskId, name,
				comment);
		}
		else if (name.equals("request-quote")) {
			_commerceOrderEngine.transitionCommerceOrder(
				commerceOrder,
				CommerceOrderConstants.ORDER_STATUS_QUOTE_REQUESTED,
				contextUser.getUserId(), true);
		}
		else if (name.equals("submit")) {
			_commerceOrderEngine.transitionCommerceOrder(
				commerceOrder, CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS,
				contextUser.getUserId(), true);
		}

		return _toCartTransition(
			commerceOrder.getCommerceOrderId(), comment,
			new ObjectValuePair<>(workflowTaskId, name));
	}

	private CartTransition _toCartTransition(
			long commerceOrderId, String comment,
			ObjectValuePair<Long, String> transitionOVP)
		throws Exception {

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, new HashMap<>(), _dtoConverterRegistry,
				contextHttpServletRequest, commerceOrderId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser);

		defaultDTOConverterContext.setAttribute("comment", comment);
		defaultDTOConverterContext.setAttribute("transitionOVP", transitionOVP);

		return _cartTransitionDTOConverter.toDTO(defaultDTOConverterContext);
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.converter.CartTransitionDTOConverter)"
	)
	private DTOConverter<CommerceOrder, CartTransition>
		_cartTransitionDTOConverter;

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

}