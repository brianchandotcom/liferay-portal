/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoInstanceImpl extends KaleoInstanceBaseImpl {

	@Override
	public KaleoInstanceToken getRootKaleoInstanceToken(
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		return KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(
			getKaleoInstanceId(), workflowContext, serviceContext);
	}

	@Override
	public KaleoInstanceToken getRootKaleoInstanceToken(
			ServiceContext serviceContext)
		throws PortalException {

		return getRootKaleoInstanceToken(
			WorkflowContextUtil.convert(getWorkflowContext()), serviceContext);
	}

}