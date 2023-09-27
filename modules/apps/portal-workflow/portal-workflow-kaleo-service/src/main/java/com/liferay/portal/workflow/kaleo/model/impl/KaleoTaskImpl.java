/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTaskImpl extends KaleoTaskBaseImpl {

	@Override
	public KaleoNode getKaleoNode() throws PortalException {
		return KaleoNodeLocalServiceUtil.getKaleoNode(getKaleoNodeId());
	}

	@Override
	public List<KaleoTaskAssignment> getKaleoTaskAssignments() {
		return KaleoTaskAssignmentLocalServiceUtil.getKaleoTaskAssignments(
			KaleoTask.class.getName(), getKaleoTaskId());
	}

}