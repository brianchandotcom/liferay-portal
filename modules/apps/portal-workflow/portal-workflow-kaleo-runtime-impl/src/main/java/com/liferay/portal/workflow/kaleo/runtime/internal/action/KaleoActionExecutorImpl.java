/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.ActionExecutorManager;
import com.liferay.portal.workflow.kaleo.runtime.action.KaleoActionExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoActionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = KaleoActionExecutor.class)
public class KaleoActionExecutorImpl implements KaleoActionExecutor {

	@Override
	public void executeKaleoActions(
			String kaleoClassName, long kaleoClassPK,
			ExecutionType executionType, ExecutionContext executionContext)
		throws PortalException {

		ServiceContext serviceContext = executionContext.getServiceContext();

		List<KaleoAction> kaleoActions = _getKaleoActions(
			kaleoClassName, kaleoClassPK, executionType, executionContext,
			serviceContext);

		for (KaleoAction kaleoAction : kaleoActions) {
			long startTime = System.currentTimeMillis();

			String comment = _COMMENT_ACTION_SUCCESS;

			try {
				actionExecutorManager.executeKaleoAction(
					kaleoAction, executionContext);

				KaleoInstanceToken kaleoInstanceToken =
					executionContext.getKaleoInstanceToken();

				_kaleoInstanceLocalService.updateKaleoInstance(
					kaleoInstanceToken.getKaleoInstanceId(),
					executionContext.getWorkflowContext());
			}
			catch (Exception exception) {
				_log.error(exception);

				comment = exception.getMessage();
			}
			finally {
				_kaleoLogLocalService.addActionExecutionKaleoLog(
					executionContext.getKaleoInstanceToken(), kaleoAction,
					startTime, System.currentTimeMillis(), comment,
					serviceContext);
			}
		}
	}

	@Reference
	protected ActionExecutorManager actionExecutorManager;

	private List<KaleoAction> _getKaleoActions(
		String kaleoClassName, long kaleoClassPK, ExecutionType executionType,
		ExecutionContext executionContext, ServiceContext serviceContext) {

		if (!Objects.equals(KaleoNode.class.getName(), kaleoClassName)) {
			return _kaleoActionLocalService.getKaleoActions(
				serviceContext.getCompanyId(), kaleoClassName, kaleoClassPK,
				executionType.getValue());
		}

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoInstanceToken.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion == null) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			kaleoDefinitionVersion.getKaleoNodeKaleoActions(kaleoClassPK),
			kaleoAction -> Objects.equals(
				executionType.getValue(), kaleoAction.getExecutionType()));
	}

	private static final String _COMMENT_ACTION_SUCCESS =
		"Action completed successfully.";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoActionExecutorImpl.class);

	@Reference
	private KaleoActionLocalService _kaleoActionLocalService;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

}