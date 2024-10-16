/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.helper;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.frontend.helper.CommerceOrderStepTrackerHelper;
import com.liferay.commerce.frontend.model.StepModel;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = CommerceOrderStepTrackerHelper.class)
public class CommerceOrderStepTrackerHelperImpl
	implements CommerceOrderStepTrackerHelper {

	@Override
	public List<StepModel> getCommerceOrderSteps(
			boolean admin, CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		List<StepModel> stepModels = new ArrayList<>();

		CommerceOrderStatus currentCommerceOrderStatus =
			_commerceOrderEngine.getCurrentCommerceOrderStatus(commerceOrder);

		if ((currentCommerceOrderStatus == null) ||
			(admin && (currentCommerceOrderStatus.getPriority() == -1))) {

			return stepModels;
		}

		if ((currentCommerceOrderStatus != null) &&
			currentCommerceOrderStatus.isWorkflowEnabled(commerceOrder)) {

			return _getWorkflowSteps(commerceOrder, locale);
		}

		if (admin &&
			ArrayUtil.contains(
				CommerceOrderConstants.ORDER_STATUSES_OPEN,
				commerceOrder.getOrderStatus())) {

			return stepModels;
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
				(admin &&
				 (ArrayUtil.contains(
					 CommerceOrderConstants.ORDER_STATUSES_OPEN,
					 commerceOrderStatus.getKey()) ||
				  (commerceOrderStatus.getPriority() == -1))) ||
				(!admin &&
				 (((currentCommerceOrderStatus.getPriority() == -1) &&
				   (currentCommerceOrderStatus.getKey() !=
					   commerceOrderStatus.getKey())) ||
				  ((currentCommerceOrderStatus.getPriority() != -1) &&
				   (commerceOrderStatus.getPriority() == -1))))) {

				continue;
			}

			StepModel stepModel = new StepModel();

			stepModel.setId(
				CommerceOrderConstants.getOrderStatusLabel(
					commerceOrderStatus.getKey()));
			stepModel.setLabel(commerceOrderStatus.getLabel(locale));

			if (commerceOrderStatus.equals(currentCommerceOrderStatus) &&
				(commerceOrderStatus.getKey() !=
					CommerceOrderConstants.ORDER_STATUS_COMPLETED) &&
				(commerceOrderStatus.getKey() !=
					CommerceOrderConstants.ORDER_STATUS_QUOTE_PROCESSED)) {

				stepModel.setState("active");
			}
			else if ((currentCommerceOrderStatus != null) &&
					 (commerceOrderStatus.getPriority() <=
						 currentCommerceOrderStatus.getPriority()) &&
					 commerceOrderStatus.isComplete(commerceOrder)) {

				stepModel.setState("completed");
			}
			else {
				stepModel.setState("inactive");
			}

			stepModels.add(stepModel);
		}

		return stepModels;
	}

	private List<StepModel> _getWorkflowSteps(
		CommerceOrder commerceOrder, Locale locale) {

		List<StepModel> stepModels = new ArrayList<>();

		int[] workflowStatuses = {
			WorkflowConstants.STATUS_DRAFT, WorkflowConstants.STATUS_PENDING,
			WorkflowConstants.STATUS_APPROVED
		};

		for (int workflowStatus : workflowStatuses) {
			StepModel stepModel = new StepModel();

			String workflowStatusLabel = WorkflowConstants.getStatusLabel(
				workflowStatus);

			stepModel.setId(workflowStatusLabel);
			stepModel.setLabel(_language.get(locale, workflowStatusLabel));

			if (commerceOrder.getStatus() == workflowStatus) {
				stepModel.setState("active");
			}
			else if (commerceOrder.getStatus() < workflowStatus) {
				stepModel.setState("completed");
			}
			else {
				stepModel.setState("inactive");
			}

			stepModels.add(stepModel);
		}

		return stepModels;
	}

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private Language _language;

}