/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.graph;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.graph.GraphWalker;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.internal.BaseKaleoBean;
import com.liferay.portal.workflow.kaleo.runtime.internal.node.util.NodeExecutorRegistryUtil;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.util.ExecutionContextHelper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = AopService.class)
@CTAware
@Transactional(
	isolation = Isolation.PORTAL, propagation = Propagation.REQUIRES_NEW,
	rollbackFor = Exception.class
)
public class DefaultGraphWalker
	extends BaseKaleoBean implements AopService, GraphWalker {

	@Override
	public void follow(
			KaleoNode sourceKaleoNode, KaleoNode targetKaleoNode,
			List<PathElement> remainingPathElements,
			ExecutionContext executionContext)
		throws PortalException {

		if (sourceKaleoNode != null) {
			NodeExecutor nodeExecutor =
				NodeExecutorRegistryUtil.getNodeExecutor(
					sourceKaleoNode.getType());

			nodeExecutor.exit(
				sourceKaleoNode, executionContext, remainingPathElements);
		}

		if (targetKaleoNode != null) {
			kaleoLogLocalService.addNodeEntryKaleoLog(
				executionContext.getKaleoInstanceToken(), sourceKaleoNode,
				targetKaleoNode, executionContext.getServiceContext());

			NodeExecutor nodeExecutor =
				NodeExecutorRegistryUtil.getNodeExecutor(
					targetKaleoNode.getType());

			boolean performExecute = nodeExecutor.enter(
				targetKaleoNode, executionContext);

			if (performExecute) {
				nodeExecutor.execute(
					targetKaleoNode, executionContext, remainingPathElements);
			}
		}

		_executionContextHelper.checkKaleoInstanceComplete(executionContext);
	}

	@Reference
	private ExecutionContextHelper _executionContextHelper;

}