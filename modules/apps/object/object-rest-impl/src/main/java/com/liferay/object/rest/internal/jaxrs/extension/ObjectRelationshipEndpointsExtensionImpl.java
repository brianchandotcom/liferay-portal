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

package com.liferay.object.rest.internal.jaxrs.extension;

import com.liferay.object.related.models.ObjectRelationshipEndpointsExtension;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = ObjectRelationshipEndpointsExtension.class)
public class ObjectRelationshipEndpointsExtensionImpl
	implements ObjectRelationshipEndpointsExtension {

	@Override
	public void addSystemObjectRelationshipsEndpoints(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		Set<String> basePaths = _applicationNames.keySet();

		Stream<String> basePathsStream = basePaths.stream();

		String systemObjectBasePath = basePathsStream.filter(
			applicationBasePath -> {
				String restContextPath =
					systemObjectDefinitionMetadata.getRESTContextPath();

				String basePath = "/" + restContextPath.split("/")[0];

				return applicationBasePath.contains(basePath);
			}
		).findFirst(
		).orElse(
			""
		);

		String jaxrsApplicationName = _applicationNames.get(
			systemObjectBasePath);

		if (_componentInstancesMap.get(jaxrsApplicationName) == null) {
			_componentInstancesMap.put(
				jaxrsApplicationName,
				_relationshipsResourceComponentFactory.newInstance(
					HashMapDictionaryBuilder.<String, Object>put(
						"api.version", "v1.0"
					).put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + jaxrsApplicationName + ")"
					).put(
						"osgi.jaxrs.resource", "true"
					).build()));
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerApplication = ServiceTrackerListFactory.open(
			bundleContext, Application.class, null,
			new ServiceTrackerCustomizer<Application, Application>() {

				@Override
				public Application addingService(
					ServiceReference<Application> serviceReference) {

					String basePath = (String)serviceReference.getProperty(
						"osgi.jaxrs.application.base");
					String jaxrsName = (String)serviceReference.getProperty(
						"osgi.jaxrs.name");

					if ((basePath != null) && (jaxrsName != null)) {
						_applicationNames.put(basePath, jaxrsName);
					}

					return bundleContext.getService(serviceReference);
				}

				@Override
				public void modifiedService(
					ServiceReference<Application> serviceReference,
					Application o) {
				}

				@Override
				public void removedService(
					ServiceReference<Application> serviceReference,
					Application o) {

					String jaxrsName = (String)serviceReference.getProperty(
						"osgi.jaxrs.name");

					ComponentInstance componentInstance =
						_componentInstancesMap.get(jaxrsName);

					if (componentInstance != null) {
						componentInstance.dispose();
					}
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		for (ComponentInstance componentInstance :
				_componentInstancesMap.values()) {

			componentInstance.dispose();
		}

		_serviceTrackerApplication.close();
	}

	private final Map<String, String> _applicationNames = new HashMap<>();
	private final Map<String, ComponentInstance> _componentInstancesMap =
		new HashMap<>();

	@Reference(
		target = "(component.factory=com.liferay.object.rest.internal.resource.v1_0.ObjectRelationshipsResource)"
	)
	private ComponentFactory _relationshipsResourceComponentFactory;

	private ServiceTrackerList<Application> _serviceTrackerApplication;

}