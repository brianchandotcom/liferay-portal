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

package com.liferay.portal.workflow.kaleo.runtime.internal.node;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.node.BaseNodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "node.type=JOIN_XOR",
	service = NodeExecutor.class
)
public class JoinXorNodeExecutor extends BaseNodeExecutor {

	@Override
	protected boolean doEnter(
			KaleoNode currentKaleoNode, ExecutionContext executionContext)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		kaleoInstanceToken =
			_kaleoInstanceTokenLocalService.getKaleoInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId());

		if (kaleoInstanceToken.isCompleted()) {
			return false;
		}

		kaleoInstanceToken =
			_kaleoInstanceTokenLocalService.completeKaleoInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId());

		KaleoInstanceToken parentKaleoInstanceToken =
			kaleoInstanceToken.getParentKaleoInstanceToken();

		if (!parentKaleoInstanceToken.
				hasIncompleteChildrenKaleoInstanceToken()) {

			return false;
		}

		List<KaleoInstanceToken> childrenKaleoInstanceTokens =
			parentKaleoInstanceToken.getChildrenKaleoInstanceTokens();

		for (KaleoInstanceToken childrenKaleoInstanceToken :
				childrenKaleoInstanceTokens) {

			_kaleoInstanceTokenLocalService.completeKaleoInstanceToken(
				childrenKaleoInstanceToken.getKaleoInstanceTokenId());
		}

		_completeChildrenKaleoTaskInstanceTokens(
			parentKaleoInstanceToken, currentKaleoNode,
			executionContext.getServiceContext());

		return true;
	}

	@Override
	protected void doExecute(
			KaleoNode currentKaleoNode, ExecutionContext executionContext,
			List<PathElement> remainingPathElements)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		KaleoInstanceToken parentKaleoInstanceToken =
			kaleoInstanceToken.getParentKaleoInstanceToken();

		if (parentKaleoInstanceToken.getCurrentKaleoNodeId() ==
				currentKaleoNode.getKaleoNodeId()) {

			return;
		}

		parentKaleoInstanceToken =
			_kaleoInstanceTokenLocalService.updateKaleoInstanceToken(
				parentKaleoInstanceToken.getKaleoInstanceTokenId(),
				currentKaleoNode.getKaleoNodeId());

		KaleoTransition kaleoTransition =
			currentKaleoNode.getDefaultKaleoTransition();

		ExecutionContext newExecutionContext = new ExecutionContext(
			parentKaleoInstanceToken, executionContext.getWorkflowContext(),
			executionContext.getServiceContext());

		PathElement pathElement = new PathElement(
			currentKaleoNode, kaleoTransition.getTargetKaleoNode(),
			newExecutionContext);

		remainingPathElements.add(pathElement);
	}

	@Override
	protected void doExecuteTimer(
		KaleoNode currentKaleoNode, KaleoTimer kaleoTimer,
		ExecutionContext executionContext) {
	}

	@Override
	protected void doExit(
		KaleoNode currentKaleoNode, ExecutionContext executionContext,
		List<PathElement> remainingPathElements) {
	}

	private void _completeChildrenKaleoTaskInstanceTokens(
			KaleoInstanceToken kaleoInstanceToken, KaleoNode stopKaleoNode,
			ServiceContext serviceContext)
		throws PortalException {

		List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
			_getChildrentKaleoTaskInstanceTokens(
				kaleoInstanceToken, stopKaleoNode, serviceContext);

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				kaleoTaskInstanceTokens) {

			_completeKaleoTaskInstanceToken(
				kaleoTaskInstanceToken, serviceContext);
		}
	}

	private void _completeKaleoTaskInstanceToken(
			KaleoTaskInstanceToken kaleoTaskInstanceToken,
			ServiceContext serviceContext)
		throws PortalException {

		_kaleoTaskAssignmentInstanceLocalService.
			assignKaleoTaskAssignmentInstance(
				kaleoTaskInstanceToken, User.class.getName(),
				serviceContext.getUserId(), serviceContext);

		long kaleoTaskInstanceTokenId =
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId();

		_kaleoTaskAssignmentInstanceLocalService.completeKaleoTaskInstanceToken(
			kaleoTaskInstanceTokenId, serviceContext);

		_kaleoTaskInstanceTokenLocalService.completeKaleoTaskInstanceToken(
			kaleoTaskInstanceTokenId, serviceContext);
	}

	private List<KaleoTaskInstanceToken> _getChildrentKaleoTaskInstanceTokens(
			KaleoInstanceToken currentKaleoInstanceToken,
			KaleoNode stopKaleoNode, ServiceContext serviceContext)
		throws PortalException {

		if (_isFinalKaleoInstanceToken(
				currentKaleoInstanceToken, stopKaleoNode)) {

			return Collections.emptyList();
		}

		KaleoNode currentKaleoNode =
			currentKaleoInstanceToken.getCurrentKaleoNode();

		List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
			new ArrayList<>();

		String type = currentKaleoNode.getType();

		if (type.equals("TASK")) {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				_getKaleoTaksInstanceToken(
					currentKaleoNode, currentKaleoInstanceToken);

			kaleoTaskInstanceTokens.add(kaleoTaskInstanceToken);
		}

		for (KaleoInstanceToken childKaleoInstanceToken :
				currentKaleoInstanceToken.getChildrenKaleoInstanceTokens()) {

			kaleoTaskInstanceTokens.addAll(
				_getChildrentKaleoTaskInstanceTokens(
					childKaleoInstanceToken, stopKaleoNode, serviceContext));
		}

		return kaleoTaskInstanceTokens;
	}

	private KaleoTaskInstanceToken _getKaleoTaksInstanceToken(
			KaleoNode kaleoNode, KaleoInstanceToken kaleoInstanceToken)
		throws PortalException {

		long kaleoNodeId = kaleoNode.getKaleoNodeId();

		KaleoTask kaleoTask = _kaleoTaskLocalService.getKaleoNodeKaleoTask(
			kaleoNodeId);

		long kaleoTaskId = kaleoTask.getKaleoTaskId();

		long kaleoInstanceId = kaleoInstanceToken.getKaleoInstanceId();

		return _kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
			kaleoInstanceId, kaleoTaskId);
	}

	private boolean _isFinalKaleoInstanceToken(
			KaleoInstanceToken kaleoInstanceToken, KaleoNode stopKaleoNode)
		throws PortalException {

		if (kaleoInstanceToken.getCurrentKaleoNodeId() ==
				stopKaleoNode.getKaleoNodeId()) {

			return true;
		}

		KaleoNode kaleoNode = kaleoInstanceToken.getCurrentKaleoNode();

		if (kaleoNode.isTerminal()) {
			return true;
		}

		return false;
	}

	@Reference
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Reference
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

}