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

package com.liferay.portal.workflow.kaleo.action.executor.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.action.executor.ActionExecutorFactory;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.ActionExecutorManager;
import com.liferay.portal.workflow.kaleo.runtime.util.ClassLoaderUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoActionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true)
public class ActionExecutorManagerImpl implements ActionExecutorManager {

	@Override
	public void executeKaleoActions(
			String kaleoClassName, long kaleoClassPK,
			ExecutionType executionType, ExecutionContext executionContext)
		throws PortalException {

		List<KaleoAction> kaleoActions =
			_kaleoActionLocalService.getKaleoActions(
				kaleoClassName, kaleoClassPK, executionType.getValue());

		for (KaleoAction kaleoAction : kaleoActions) {
			long startTime = System.currentTimeMillis();

			String comment = _COMMENT_ACTION_SUCCESS;

			try {
				String[] scriptRequiredContexts = StringUtil.split(
					kaleoAction.getScriptRequiredContexts());

				ClassLoader[] classLoaders = ClassLoaderUtil.getClassLoaders(
					scriptRequiredContexts);

				ActionExecutor actionExecutor = null;

				String scriptLanguage = kaleoAction.getScriptLanguage();

				if (scriptLanguage.equals(_LANGUAGE_JAVA)) {
					actionExecutor = _actionExecutorFactory.getActionExecutor(
						kaleoAction.getScriptLanguage(),
						kaleoAction.getScript());
				}
				else {
					actionExecutor = _actionExecutorFactory.getActionExecutor(
						kaleoAction.getScriptLanguage());
				}

				actionExecutor.execute(
					kaleoAction, executionContext, classLoaders);

				KaleoInstanceToken kaleoInstanceToken =
					executionContext.getKaleoInstanceToken();

				_kaleoInstanceLocalService.updateKaleoInstance(
					kaleoInstanceToken.getKaleoInstanceId(),
					executionContext.getWorkflowContext(),
					executionContext.getServiceContext());
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}

				comment = e.getMessage();
			}
			finally {
				_kaleoLogLocalService.addActionExecutionKaleoLog(
					executionContext.getKaleoInstanceToken(), kaleoAction,
					startTime, System.currentTimeMillis(), comment,
					executionContext.getServiceContext());
			}
		}
	}

	@Reference(unbind = "-")
	protected void setActionExecutorFactory(
		ActionExecutorFactory actionExecutorFactory) {

		_actionExecutorFactory = actionExecutorFactory;
	}

	@Reference(unbind = "-")
	protected void setKaleoActionLocalService(
		KaleoActionLocalService kaleoActionLocalService) {

		_kaleoActionLocalService = kaleoActionLocalService;
	}

	@Reference(unbind = "-")
	protected void setKaleoInstanceLocalService(
		KaleoInstanceLocalService kaleoInstanceLocalService) {

		_kaleoInstanceLocalService = kaleoInstanceLocalService;
	}

	@Reference(unbind = "-")
	protected void setKaleoLogLocalService(
		KaleoLogLocalService kaleoLogLocalService) {

		_kaleoLogLocalService = kaleoLogLocalService;
	}

	private static final String _COMMENT_ACTION_SUCCESS =
		"Action completed successfully.";

	private static final String _LANGUAGE_JAVA = "java";

	private static final Log _log = LogFactoryUtil.getLog(
		ActionExecutorManagerImpl.class);

	private ActionExecutorFactory _actionExecutorFactory;
	private KaleoActionLocalService _kaleoActionLocalService;
	private KaleoInstanceLocalService _kaleoInstanceLocalService;
	private KaleoLogLocalService _kaleoLogLocalService;

}