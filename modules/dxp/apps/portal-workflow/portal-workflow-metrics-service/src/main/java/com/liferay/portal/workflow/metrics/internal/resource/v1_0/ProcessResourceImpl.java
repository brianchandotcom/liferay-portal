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

package com.liferay.portal.workflow.metrics.internal.resource.v1_0;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.internal.search.query.WorkflowMetricsInstanceQueryExecutor;
import com.liferay.portal.workflow.metrics.internal.search.query.WorkflowMetricsProcessQueryExecutor;
import com.liferay.portal.workflow.metrics.resource.v1_0.ProcessResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/process.properties",
	scope = ServiceScope.PROTOTYPE, service = ProcessResource.class
)
public class ProcessResourceImpl extends BaseProcessResourceImpl {

	@Override
	public Page<Process> getProcessPage(
			Long companyId, String title, Pagination pagination)
		throws Exception {

		return Page.of(
			getProcesses(
				companyId, title, true, pagination.getStartPosition(),
				pagination.getItemsPerPage()),
			pagination,
			_workflowMetricsProcessQueryExecutor.searchCount(
				companyId, title, true));
	}

	protected Collection<Process> getProcesses(
		long companyId, String title, boolean active, int start, int size) {

		List<Process> processes = new ArrayList<>();

		Map<String, Hits> map = _workflowMetricsProcessQueryExecutor.search(
			companyId, title, active, 0, 1, "name", 1,
			new Sort(Field.getSortableFieldName("date"), true));

		List<Hits> hitsList = ListUtil.subList(
			new ArrayList<>(map.values()), start, start + size);

		for (Hits hits : hitsList) {
			Process process = new Process();

			Document[] documents = hits.getDocs();

			Document document = documents[0];

			process.setInstanceCount(
				_workflowMetricsInstanceQueryExecutor.searchCount(
					companyId,
					_workflowMetricsProcessQueryExecutor.getProcessIds(
						companyId, document.get("name"))));
			process.setTitle(document.get("title"));

			processes.add(process);
		}

		return processes;
	}

	@Reference
	private WorkflowMetricsInstanceQueryExecutor
		_workflowMetricsInstanceQueryExecutor;

	@Reference
	private WorkflowMetricsProcessQueryExecutor
		_workflowMetricsProcessQueryExecutor;

}