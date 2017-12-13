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

package com.liferay.commerce.model;

import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderPaymentConstants {

	public static final int STATUS_CANCELLED =
		WorkflowConstants.STATUS_IN_TRASH;

	public static final int STATUS_COMPLETED =
		WorkflowConstants.STATUS_APPROVED;

	public static final int STATUS_FAILED = WorkflowConstants.STATUS_DENIED;

	public static final int STATUS_PENDING = WorkflowConstants.STATUS_PENDING;

}