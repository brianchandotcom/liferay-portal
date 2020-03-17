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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.petra.process.ProcessExecutor;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.sidecar.Sidecar;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
	enabled = false, immediate = true, service = {}
)
public class SidecarElasticsearchConnectionManager {

	@Activate
	protected void activate(ComponentContext componentContext) {
		ElasticsearchConfiguration elasticsearchConfiguration =
			ConfigurableUtil.createConfigurable(
				ElasticsearchConfiguration.class,
				componentContext.getProperties());

		ElasticsearchConnection elasticsearchConnection;

		if (elasticsearchConfiguration.operationMode() ==
				com.liferay.portal.search.elasticsearch7.configuration.
					OperationMode.EMBEDDED) {

			elasticsearchConnection = new SidecarElasticsearchConnection(
				new Sidecar(
					componentContext,
					SidecarElasticsearchConnectionManager.class.getName(),
					elasticsearchConfiguration, _file, _processExecutor,
					_props));
		}
		else {
			elasticsearchConnection = ProxyFactory.newDummyInstance(
				ElasticsearchConnection.class);
		}

		BundleContext bundleContext = componentContext.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			ElasticsearchConnection.class, elasticsearchConnection,
			MapUtil.singletonDictionary(
				"operation.mode", String.valueOf(OperationMode.EMBEDDED)));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private File _file;

	@Reference
	private ProcessExecutor _processExecutor;

	@Reference
	private Props _props;

	private ServiceRegistration<ElasticsearchConnection> _serviceRegistration;

}