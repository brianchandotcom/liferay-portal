/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.internal.model;

/**
 * @author Marco Leo
 * @author Luca Pellizzon
 */
public class CurrentCommerceOrderModel {

	public CurrentCommerceOrderModel(
		long orderId, WorkflowStatusModel workflowStatusInfo) {

		_orderId = orderId;
		_workflowStatusInfo = workflowStatusInfo;
	}

	public long getOrderId() {
		return _orderId;
	}

	public WorkflowStatusModel getWorkflowStatusInfo() {
		return _workflowStatusInfo;
	}

	private final long _orderId;
	private final WorkflowStatusModel _workflowStatusInfo;

}