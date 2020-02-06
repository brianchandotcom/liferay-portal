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
import com.liferay.portal.workflow.metrics.internal.search.index.InstanceWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.NodeWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.ProcessWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.TokenWorkflowMetricsIndexer;

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

		_instanceWorkflowMetricsIndexer.deleteIndex(companyId);
		_nodeWorkflowMetricsIndexer.deleteIndex(companyId);
		_processWorkflowMetricsIndexer.deleteIndex(companyId);
		_tokenWorkflowMetricsIndexer.deleteIndex(companyId);

		_instanceWorkflowMetricsIndexer.createIndex();
		_nodeWorkflowMetricsIndexer.createIndex();
		_processWorkflowMetricsIndexer.createIndex();
		_tokenWorkflowMetricsIndexer.createIndex();

		_instanceWorkflowMetricsIndexer.reindex(companyId);
		_nodeWorkflowMetricsIndexer.reindex(companyId);
		_processWorkflowMetricsIndexer.reindex(companyId);
		_tokenWorkflowMetricsIndexer.reindex(companyId);
	}

	@Reference
	private InstanceWorkflowMetricsIndexer _instanceWorkflowMetricsIndexer;

	@Reference
	private NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;

	@Reference
	private ProcessWorkflowMetricsIndexer _processWorkflowMetricsIndexer;

	@Reference
	private TokenWorkflowMetricsIndexer _tokenWorkflowMetricsIndexer;

}