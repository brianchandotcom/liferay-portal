/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTaskInstanceTokenImpl extends KaleoTaskInstanceTokenBaseImpl {

	@Override
	public KaleoTaskAssignmentInstance getFirstKaleoTaskAssignmentInstance() {
		return KaleoTaskAssignmentInstanceLocalServiceUtil.
			fetchFirstKaleoTaskAssignmentInstance(
				getKaleoTaskInstanceTokenId(), null);
	}

	@Override
	public KaleoInstanceToken getKaleoInstanceToken() throws PortalException {
		return KaleoInstanceTokenLocalServiceUtil.getKaleoInstanceToken(
			getKaleoInstanceTokenId());
	}

	@Override
	public KaleoTask getKaleoTask() throws PortalException {
		return KaleoTaskLocalServiceUtil.getKaleoTask(getKaleoTaskId());
	}

	@Override
	public List<KaleoTaskAssignmentInstance> getKaleoTaskAssignmentInstances() {
		return KaleoTaskAssignmentInstanceLocalServiceUtil.
			getKaleoTaskAssignmentInstances(getKaleoTaskInstanceTokenId());
	}

}