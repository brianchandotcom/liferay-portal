/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTimerImpl extends KaleoTimerBaseImpl {

	@Override
	public List<KaleoTaskAssignment> getKaleoTaskReassignments() {
		return KaleoTaskAssignmentLocalServiceUtil.getKaleoTaskAssignments(
			KaleoTimer.class.getName(), getKaleoTimerId());
	}

	@Override
	public boolean isRecurring() {
		if (Validator.isNotNull(getRecurrenceScale())) {
			return true;
		}

		return false;
	}

}