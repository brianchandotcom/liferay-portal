/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.metrics.integration.internal.helper.IndexerHelper;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.metrics.search.index.TransitionWorkflowMetricsIndexer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTransitionModelListener
	extends BaseKaleoModelListener<KaleoTransition> {

	@Override
	public void onAfterCreate(KaleoTransition kaleoTransition) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(
				kaleoTransition.getKaleoDefinitionVersionId());

		if (Objects.isNull(kaleoDefinitionVersion)) {
			return;
		}

		try {
			_transitionWorkflowMetricsIndexer.addTransition(
				_indexerHelper.createAddTransitionRequest(
					kaleoTransition, kaleoDefinitionVersion.getVersion()));
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterRemove(KaleoTransition kaleoTransition) {
		_transitionWorkflowMetricsIndexer.deleteTransition(
			_indexerHelper.createDeleteTransitionRequest(kaleoTransition));
	}

	@Reference
	private IndexerHelper _indexerHelper;

	@Reference
	private TransitionWorkflowMetricsIndexer _transitionWorkflowMetricsIndexer;

}