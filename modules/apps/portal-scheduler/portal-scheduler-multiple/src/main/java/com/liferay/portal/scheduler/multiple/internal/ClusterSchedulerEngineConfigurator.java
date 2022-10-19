/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.scheduler.multiple.internal;

import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Props;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = ClusterSchedulerEngineConfigurator.class)
public class ClusterSchedulerEngineConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		SchedulerEngine schedulerEngine = _schedulerEngine;

		if (_clusterLink.isEnabled()) {
			ClusterSchedulerEngine clusterSchedulerEngine =
				new ClusterSchedulerEngine(schedulerEngine, _triggerFactory);

			clusterSchedulerEngine.setClusterExecutor(_clusterExecutor);
			clusterSchedulerEngine.setClusterMasterExecutor(
				_clusterMasterExecutor);
			clusterSchedulerEngine.setProps(_props);

			_serviceRegistration = bundleContext.registerService(
				IdentifiableOSGiService.class, clusterSchedulerEngine,
				new HashMapDictionary<String, Object>());

			schedulerEngine = ClusterableProxyFactory.createClusterableProxy(
				clusterSchedulerEngine);
		}

		_schedulerEngineServiceRegistration = bundleContext.registerService(
			SchedulerEngine.class, schedulerEngine,
			HashMapDictionaryBuilder.<String, Object>put(
				"scheduler.engine.proxy", Boolean.TRUE
			).build());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		if (_schedulerEngineServiceRegistration != null) {
			_schedulerEngineServiceRegistration.unregister();
		}
	}

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private ClusterLink _clusterLink;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private Props _props;

	@Reference(target = "(scheduler.engine.proxy=false)")
	private SchedulerEngine _schedulerEngine;

	private volatile ServiceRegistration<SchedulerEngine>
		_schedulerEngineServiceRegistration;
	private ServiceRegistration<IdentifiableOSGiService> _serviceRegistration;

	@Reference
	private TriggerFactory _triggerFactory;

}