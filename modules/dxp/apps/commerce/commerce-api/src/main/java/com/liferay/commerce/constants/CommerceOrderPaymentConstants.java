/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.constants;

import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderPaymentConstants {

	public static final int STATUS_ANY = WorkflowConstants.STATUS_ANY;

	public static final int STATUS_CANCELLED =
		WorkflowConstants.STATUS_IN_TRASH;

	public static final int STATUS_COMPLETED =
		WorkflowConstants.STATUS_APPROVED;

	public static final int STATUS_FAILED = WorkflowConstants.STATUS_DENIED;

	public static final int STATUS_PENDING = WorkflowConstants.STATUS_PENDING;

}