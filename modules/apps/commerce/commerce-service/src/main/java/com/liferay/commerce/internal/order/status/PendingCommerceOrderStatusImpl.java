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

package com.liferay.commerce.internal.order.status;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.HashMap;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.order.status.key=" + PendingCommerceOrderStatusImpl.KEY,
		"commerce.order.status.priority:Integer=" + PendingCommerceOrderStatusImpl.PRIORITY
	},
	service = CommerceOrderStatus.class
)
public class PendingCommerceOrderStatusImpl implements CommerceOrderStatus {

	public static final int KEY = CommerceOrderConstants.ORDER_STATUS_PENDING;

	public static final int PRIORITY = 30;

	@Override
	public CommerceOrder doTransition(CommerceOrder commerceOrder, long userId)
		throws PortalException {

		commerceOrder.setOrderStatus(KEY);

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		if (isWorkflowEnabled(commerceOrder)) {

			// Commerce order

			commerceOrder.setStatus(WorkflowConstants.STATUS_PENDING);

			// Workflow

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setScopeGroupId(commerceOrder.getGroupId());
			serviceContext.setUserId(userId);
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			commerceOrder = WorkflowHandlerRegistryUtil.startWorkflowInstance(
				commerceOrder.getCompanyId(), commerceOrder.getScopeGroupId(),
				userId, CommerceOrder.class.getName(),
				commerceOrder.getCommerceOrderId(), commerceOrder,
				serviceContext, new HashMap<>());
		}

		return _commerceOrderLocalService.updateCommerceOrder(commerceOrder);
	}

	@Override
	public int getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale, CommerceOrderConstants.getOrderStatusLabel(KEY));
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public boolean isComplete(CommerceOrder commerceOrder) {
		if (!commerceOrder.isOpen() && commerceOrder.isApproved()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTransitionCriteriaMet(CommerceOrder commerceOrder)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentMethodRegistry.getCommercePaymentMethod(
				commerceOrder.getCommercePaymentMethodKey());

		if (commercePaymentMethod == null) {
			return true;
		}

		if ((commerceOrder.getPaymentStatus() ==
				CommerceOrderConstants.PAYMENT_STATUS_PAID) ||
			(commercePaymentMethod.getPaymentType() ==
				CommercePaymentConstants.
					COMMERCE_PAYMENT_METHOD_TYPE_OFFLINE)) {

			return _commerceOrderValidatorRegistry.isValid(
				LocaleUtil.getSiteDefault(), commerceOrder);
		}

		return false;
	}

	@Override
	public boolean isWorkflowEnabled(CommerceOrder commerceOrder)
		throws PortalException {

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				commerceOrder.getCompanyId(), commerceOrder.getGroupId(),
				CommerceOrder.class.getName(), 0,
				CommerceOrderConstants.TYPE_PK_FULFILLMENT, true);

		if (workflowDefinitionLink != null) {
			return true;
		}

		return false;
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CommercePaymentMethodRegistry _commercePaymentMethodRegistry;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}