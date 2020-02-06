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
import com.liferay.portal.workflow.metrics.internal.search.index.SLAInstanceResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.SLATaskResultWorkflowMetricsIndexer;

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

		_slaInstanceResultWorkflowMetricsIndexer.deleteIndex(companyId);
		_slaTaskResultWorkflowMetricsIndexer.deleteIndex(companyId);

		_slaInstanceResultWorkflowMetricsIndexer.createIndex();
		_slaTaskResultWorkflowMetricsIndexer.createIndex();

		_slaInstanceResultWorkflowMetricsIndexer.reindex(companyId);
		_slaTaskResultWorkflowMetricsIndexer.reindex(companyId);
	}

	@Reference
	private SLAInstanceResultWorkflowMetricsIndexer
		_slaInstanceResultWorkflowMetricsIndexer;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

}