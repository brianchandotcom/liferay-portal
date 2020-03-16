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

package com.liferay.portal.workflow.metrics.internal.search;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsIndex;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, service = {Indexer.class, WorkflowMetricsIndexer.class}
)
public class WorkflowMetricsIndexer extends BaseIndexer<Object> {

	@Override
	public String getClassName() {
		return WorkflowMetricsIndexer.class.getName();
	}

	@Override
	protected final void doDelete(Object t) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final Document doGetDocument(Object object) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected final void doReindex(Object object) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final void doReindex(String className, long classPK)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_instanceWorkflowMetricsIndex.deleteIndex(companyId);
		_nodeWorkflowMetricsIndex.deleteIndex(companyId);
		_processWorkflowMetricsIndex.deleteIndex(companyId);
		_taskWorkflowMetricsIndex.deleteIndex(companyId);
		_transitionWorkflowMetricsIndex.deleteIndex(companyId);

		_instanceWorkflowMetricsIndex.createIndex();
		_nodeWorkflowMetricsIndex.createIndex();
		_processWorkflowMetricsIndex.createIndex();
		_taskWorkflowMetricsIndex.createIndex();
		_transitionWorkflowMetricsIndex.createIndex();

		_instanceWorkflowMetricsIndex.reindex(companyId);
		_nodeWorkflowMetricsIndex.reindex(companyId);
		_processWorkflowMetricsIndex.reindex(companyId);
		_taskWorkflowMetricsIndex.reindex(companyId);
		_transitionWorkflowMetricsIndex.reindex(companyId);
	}

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndex _nodeWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndex _processWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndex _taskWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=transition)")
	private WorkflowMetricsIndex _transitionWorkflowMetricsIndex;

}