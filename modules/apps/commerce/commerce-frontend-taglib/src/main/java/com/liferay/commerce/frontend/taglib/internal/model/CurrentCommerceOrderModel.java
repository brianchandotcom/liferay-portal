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