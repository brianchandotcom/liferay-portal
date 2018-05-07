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

package com.liferay.portal.workflow.kaleo.runtime.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.util.ExecutionContextHelper;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = ExecutionContextHelper.class)
public class ExecutionContextHelperImpl implements ExecutionContextHelper {

	@Override
	public void checkKaleoInstanceComplete(ExecutionContext executionContext)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		if (!kaleoInstanceToken.isCompleted()) {
			return;
		}

		KaleoInstance kaleoInstance = kaleoInstanceToken.getKaleoInstance();

		if (!kaleoInstance.isCompleted()) {
			return;
		}

		_kaleoLogLocalService.addWorkflowInstanceEndKaleoLog(
			kaleoInstanceToken, executionContext.getServiceContext());
	}

	@Override
	public void completeKaleoTimerInstances(ExecutionContext executionContext)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		List<KaleoTimerInstanceToken> kaleoTimerInstanceTokens =
			_kaleoTimerInstanceTokenLocalService.getKaleoTimerInstanceTokens(
				kaleoInstanceToken.getKaleoInstanceTokenId(), false, false,
				executionContext.getServiceContext());

		_kaleoTimerInstanceTokenLocalService.completeKaleoTimerInstanceTokens(
			kaleoTimerInstanceTokens, executionContext.getServiceContext());
	}

	@Override
	public String convert(ExecutionContext executionContext) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		if (kaleoInstanceToken != null) {
			jsonObject.put(
				"kaleoInstanceTokenId",
				kaleoInstanceToken.getKaleoInstanceTokenId());
		}

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			executionContext.getKaleoTaskInstanceToken();

		if (kaleoTaskInstanceToken != null) {
			jsonObject.put(
				"kaleoTaskInstanceTokenId",
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
		}

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		jsonObject.put(
			"workflowContext", WorkflowContextUtil.convert(workflowContext));

		ServiceContext serviceContext = executionContext.getServiceContext();

		jsonObject.put(
			"serviceContext", _jsonFactory.serialize(serviceContext));

		jsonObject.put("transitionName", executionContext.getTransitionName());

		return jsonObject.toString();
	}

	@Override
	public ExecutionContext convert(String json) throws Exception {
		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		KaleoInstanceToken kaleoInstanceToken = null;

		long kaleoInstanceTokenId = jsonObject.getLong("kaleoInstanceTokenId");

		if (kaleoInstanceTokenId > 0) {
			kaleoInstanceToken =
				_kaleoInstanceTokenLocalService.getKaleoInstanceToken(
					kaleoInstanceTokenId);
		}

		KaleoTaskInstanceToken kaleoTaskInstanceToken = null;

		long kaleoTaskInstanceTokenId = jsonObject.getLong(
			"kaleoTaskInstanceTokenId");

		if (kaleoTaskInstanceTokenId > 0) {
			kaleoTaskInstanceToken =
				_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
					kaleoTaskInstanceTokenId);
		}

		Map<String, Serializable> workflowContext = WorkflowContextUtil.convert(
			jsonObject.getString("workflowContext"));

		ServiceContext serviceContext =
			(ServiceContext)_jsonFactory.deserialize(
				jsonObject.getString("serviceContext"));

		return new ExecutionContext(
			kaleoInstanceToken, kaleoTaskInstanceToken, workflowContext,
			serviceContext);
	}

	@Override
	public boolean isKaleoInstanceBlocked(ExecutionContext executionContext) {
		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		int count =
			_kaleoTimerInstanceTokenLocalService.
				getKaleoTimerInstanceTokensCount(
					kaleoInstanceToken.getKaleoInstanceTokenId(), true, false,
					executionContext.getServiceContext());

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoTimerInstanceTokenLocalService
		_kaleoTimerInstanceTokenLocalService;

}