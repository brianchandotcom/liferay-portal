/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class ExecutionContext implements Serializable {

	public ExecutionContext(
		KaleoInstanceToken kaleoInstanceToken,
		KaleoTaskInstanceToken kaleoTaskInstanceToken,
		Map<String, Serializable> workflowContext,
		ServiceContext serviceContext) {

		this(kaleoInstanceToken, workflowContext, serviceContext);

		_kaleoTaskInstanceToken = kaleoTaskInstanceToken;
	}

	public ExecutionContext(
		KaleoInstanceToken kaleoInstanceToken,
		KaleoTimerInstanceToken kaleoTimerInstanceToken,
		Map<String, Serializable> workflowContext,
		ServiceContext serviceContext) {

		this(kaleoInstanceToken, workflowContext, serviceContext);

		_kaleoTimerInstanceToken = kaleoTimerInstanceToken;
	}

	public ExecutionContext(
		KaleoInstanceToken kaleoInstanceToken,
		Map<String, Serializable> workflowContext,
		ServiceContext serviceContext) {

		_kaleoInstanceToken = kaleoInstanceToken;
		_serviceContext = serviceContext;

		_workflowContext = new HashMap<>(workflowContext);
	}

	public KaleoInstanceToken getKaleoInstanceToken() {
		return _kaleoInstanceToken;
	}

	public KaleoTaskInstanceToken getKaleoTaskInstanceToken() {
		return _kaleoTaskInstanceToken;
	}

	public KaleoTimerInstanceToken getKaleoTimerInstanceToken() {
		return _kaleoTimerInstanceToken;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public String getTransitionName() {
		return _transitionName;
	}

	public Map<String, Serializable> getWorkflowContext() {
		return _workflowContext;
	}

	public void setKaleoTaskInstanceToken(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		_kaleoTaskInstanceToken = kaleoTaskInstanceToken;
	}

	public void setKaleoTimerInstanceToken(
		KaleoTimerInstanceToken kaleoTimerInstanceToken) {

		_kaleoTimerInstanceToken = kaleoTimerInstanceToken;
	}

	public void setTransitionName(String transitionName) {
		_transitionName = transitionName;
	}

	private final KaleoInstanceToken _kaleoInstanceToken;
	private KaleoTaskInstanceToken _kaleoTaskInstanceToken;
	private KaleoTimerInstanceToken _kaleoTimerInstanceToken;
	private final ServiceContext _serviceContext;
	private String _transitionName;
	private final Map<String, Serializable> _workflowContext;

}