/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.search.index.reindexer;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexer;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexerRegistry;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jiaxu Wei
 */
@Component(service = WorkflowMetricsReindexerRegistry.class)
public class WorkflowMetricsReindexerRegistryImpl
	implements WorkflowMetricsReindexerRegistry {

	@Override
	public boolean containsKey(String indexEntityName) {
		return _serviceTrackerMap.containsKey(indexEntityName);
	}

	@Override
	public Set<String> getIndexEntityNames() {
		return _serviceTrackerMap.keySet();
	}

	@Override
	public WorkflowMetricsReindexer getWorkflowMetricsReindexer(
		String indexEntityName) {

		return _serviceTrackerMap.getService(indexEntityName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, WorkflowMetricsReindexer.class,
			"workflow.metrics.index.entity.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, WorkflowMetricsReindexer>
		_serviceTrackerMap;

}