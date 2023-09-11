/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.KaleoSignaler;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.internal.node.util.NodeExecutorRegistryUtil;
import com.liferay.portal.workflow.kaleo.runtime.internal.petra.executor.GraphWalkerPortalExecutor;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.util.ExecutionContextHelper;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = AopService.class)
@CTAware
@Transactional(
	isolation = Isolation.PORTAL, propagation = Propagation.SUPPORTS,
	rollbackFor = Exception.class
)
public class DefaultKaleoSignaler
	extends BaseKaleoBean implements AopService, KaleoSignaler {

	@Override
	public void signalEntry(
			String transitionName, ExecutionContext executionContext)
		throws PortalException {

		signalEntry(transitionName, executionContext, false);
	}

	@Override
	public void signalEntry(
			String transitionName, ExecutionContext executionContext,
			boolean waitForCompletion)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		executionContext.setTransitionName(transitionName);

		_graphWalkerPortalExecutor.execute(
			new PathElement(
				null, kaleoInstanceToken.getCurrentKaleoNode(),
				executionContext),
			waitForCompletion);
	}

	@Override
	public void signalExecute(
			KaleoNode currentKaleoNode, ExecutionContext executionContext)
		throws PortalException {

		signalExecute(currentKaleoNode, executionContext, false);
	}

	@Override
	@Transactional(
		isolation = Isolation.PORTAL, propagation = Propagation.REQUIRED,
		rollbackFor = Exception.class
	)
	public void signalExecute(
			KaleoNode currentKaleoNode, ExecutionContext executionContext,
			boolean waitForCompletion)
		throws PortalException {

		NodeExecutor nodeExecutor = NodeExecutorRegistryUtil.getNodeExecutor(
			currentKaleoNode.getType());

		List<PathElement> remainingPathElements = new ArrayList<>();

		nodeExecutor.execute(
			currentKaleoNode, executionContext, remainingPathElements);

		_executionContextHelper.checkKaleoInstanceComplete(executionContext);

		for (PathElement remainingPathElement : remainingPathElements) {
			_graphWalkerPortalExecutor.execute(
				remainingPathElement, waitForCompletion);
		}
	}

	@Override
	public void signalExit(
			String transitionName, ExecutionContext executionContext)
		throws PortalException {

		signalExit(transitionName, executionContext, false);
	}

	@Override
	public void signalExit(
			String transitionName, ExecutionContext executionContext,
			boolean waitForCompletion)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		executionContext.setTransitionName(transitionName);

		_graphWalkerPortalExecutor.execute(
			new PathElement(
				kaleoInstanceToken.getCurrentKaleoNode(), null,
				executionContext),
			waitForCompletion);
	}

	@Reference
	private ExecutionContextHelper _executionContextHelper;

	@Reference
	private GraphWalkerPortalExecutor _graphWalkerPortalExecutor;

}