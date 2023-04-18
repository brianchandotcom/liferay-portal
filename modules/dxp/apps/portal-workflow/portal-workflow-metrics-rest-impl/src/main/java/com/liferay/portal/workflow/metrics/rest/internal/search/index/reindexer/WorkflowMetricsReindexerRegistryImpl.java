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