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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsIndex;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Activate;
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

	@Activate
	protected void activate() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_companyLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Company company) -> {
				if (_INDEX_ON_STARTUP) {
					return;
				}

				doReindex(company.getCompanyId());

				_slaWorkflowMetricsIndexer.doReindex(company.getCompanyId());
			});

		actionableDynamicQuery.performActions();
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
			_instanceWorkflowMetricsIndex.clearIndex(companyId);
			_nodeWorkflowMetricsIndex.clearIndex(companyId);
			_processWorkflowMetricsIndex.clearIndex(companyId);
			_taskWorkflowMetricsIndex.clearIndex(companyId);
			_transitionWorkflowMetricsIndex.clearIndex(companyId);

			_instanceWorkflowMetricsIndex.createIndex(companyId);
			_nodeWorkflowMetricsIndex.createIndex(companyId);
			_processWorkflowMetricsIndex.createIndex(companyId);
			_taskWorkflowMetricsIndex.createIndex(companyId);
			_transitionWorkflowMetricsIndex.createIndex(companyId);

			_instanceWorkflowMetricsReindexer.reindex(companyId);
			_nodeWorkflowMetricsReindexer.reindex(companyId);
			_processWorkflowMetricsReindexer.reindex(companyId);
			_taskWorkflowMetricsReindexer.reindex(companyId);
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

	private static final boolean _INDEX_ON_STARTUP = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_ON_STARTUP));

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowMetricsIndexer.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsReindexer _instanceWorkflowMetricsReindexer;

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndex _nodeWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsReindexer _nodeWorkflowMetricsReindexer;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndex _processWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsReindexer _processWorkflowMetricsReindexer;

	@Reference
	private SLAWorkflowMetricsIndexer _slaWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndex _taskWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsReindexer _taskWorkflowMetricsReindexer;

	@Reference(target = "(workflow.metrics.index.entity.name=transition)")
	private WorkflowMetricsIndex _transitionWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=transition)")
	private WorkflowMetricsReindexer _transitionWorkflowMetricsReindexer;

}