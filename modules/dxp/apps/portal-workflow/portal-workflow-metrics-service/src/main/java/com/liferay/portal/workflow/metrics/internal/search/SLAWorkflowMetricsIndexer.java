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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsIndex;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = {Indexer.class, SLAWorkflowMetricsIndexer.class}
)
public class SLAWorkflowMetricsIndexer extends BaseIndexer<Object> {

	@Override
	public String getClassName() {
		return SLAWorkflowMetricsIndexer.class.getName();
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

	protected void doReindex(long companyId) {
		try {
			_slaInstanceResultWorkflowMetricsIndex.clearIndex(companyId);
			_slaTaskResultWorkflowMetricsIndex.clearIndex(companyId);

			_slaInstanceResultWorkflowMetricsIndex.createIndex(companyId);
			_slaTaskResultWorkflowMetricsIndex.createIndex(companyId);

			_slaInstanceResultWorkflowMetricsReindexer.reindex(companyId);
			_slaTaskResultWorkflowMetricsReindexer.reindex(companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
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
		doReindex(GetterUtil.getLong(ids[0]));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SLAWorkflowMetricsIndexer.class);

	@Reference(
		target = "(workflow.metrics.index.entity.name=sla-instance-result)"
	)
	private WorkflowMetricsIndex _slaInstanceResultWorkflowMetricsIndex;

	@Reference(
		target = "(workflow.metrics.index.entity.name=sla-instance-result)"
	)
	private WorkflowMetricsReindexer _slaInstanceResultWorkflowMetricsReindexer;

	@Reference(target = "(workflow.metrics.index.entity.name=sla-task-result)")
	private WorkflowMetricsIndex _slaTaskResultWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=sla-task-result)")
	private WorkflowMetricsReindexer _slaTaskResultWorkflowMetricsReindexer;

}