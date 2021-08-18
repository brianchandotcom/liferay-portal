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

package com.liferay.osb.portal.instance.provisioning.constants;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Ivica Cardic
 * @author Eduardo García
 */
public enum OSBPortalInstanceProvisioningPortalInstanceStatus {

	ACTIVE(WorkflowConstants.STATUS_APPROVED), CANCELLED(1),
	FAILED(WorkflowConstants.STATUS_INCOMPLETE),
	IN_PROGRESS(WorkflowConstants.STATUS_PENDING);

	public static OSBPortalInstanceProvisioningPortalInstanceStatus parse(
		String statusString) {

		if (statusString == null) {
			return null;
		}

		int status = GetterUtil.getInteger(statusString);

		if (status ==
				OSBPortalInstanceProvisioningPortalInstanceStatus.ACTIVE.
					getStatus()) {

			return OSBPortalInstanceProvisioningPortalInstanceStatus.ACTIVE;
		}
		else if (status ==
					OSBPortalInstanceProvisioningPortalInstanceStatus.CANCELLED.
						getStatus()) {

			return OSBPortalInstanceProvisioningPortalInstanceStatus.CANCELLED;
		}
		else {
			return OSBPortalInstanceProvisioningPortalInstanceStatus.
				IN_PROGRESS;
		}
	}

	public int getStatus() {
		return _status;
	}

	private OSBPortalInstanceProvisioningPortalInstanceStatus(int status) {
		_status = status;
	}

	private final int _status;

}