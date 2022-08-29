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

package com.liferay.object.rest.internal.deployer;

import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.util.PropsUtil;

import java.util.HashMap;
import java.util.Map;

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
@Component(immediate = true, service = {})
public class ObjectRelationshipEndpointDeployer {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_systemObjectDefinitionMetadatas = ServiceTrackerListFactory.open(
			bundleContext, SystemObjectDefinitionMetadata.class, null,
			new ServiceTrackerCustomizer
				<SystemObjectDefinitionMetadata,
				 SystemObjectDefinitionMetadata>() {

				@Override
				public SystemObjectDefinitionMetadata addingService(
					ServiceReference<SystemObjectDefinitionMetadata>
						serviceReference) {

					SystemObjectDefinitionMetadata
						systemObjectDefinitionMetadata =
							bundleContext.getService(serviceReference);

					_addSystemObjectRelationshipsEndpoints(
						systemObjectDefinitionMetadata);

					return systemObjectDefinitionMetadata;
				}

				@Override
				public void modifiedService(
					ServiceReference<SystemObjectDefinitionMetadata>
						serviceReference,
					SystemObjectDefinitionMetadata
						systemObjectDefinitionMetadata) {
				}

				@Override
				public void removedService(
					ServiceReference<SystemObjectDefinitionMetadata>
						serviceReference,
					SystemObjectDefinitionMetadata
						systemObjectDefinitionMetadata) {

					ComponentInstance componentInstance =
						_componentInstancesMap.get(
							systemObjectDefinitionMetadata.
								getJaxRsApplicationName());

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

		_systemObjectDefinitionMetadatas.close();
	}

	private void _addSystemObjectRelationshipsEndpoints(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-153324"))) {
			return;
		}

		String jaxRsApplicationName =
			systemObjectDefinitionMetadata.getJaxRsApplicationName();

		if (_componentInstancesMap.get(jaxRsApplicationName) == null) {
			_componentInstancesMap.put(
				jaxRsApplicationName,
				_relationshipsResourceComponentFactory.newInstance(
					HashMapDictionaryBuilder.<String, Object>put(
						"api.version", "v1.0"
					).put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + jaxRsApplicationName + ")"
					).put(
						"osgi.jaxrs.resource", "true"
					).build()));
		}
	}

	private final Map<String, ComponentInstance> _componentInstancesMap =
		new HashMap<>();

	@Reference(
		target = "(component.factory=com.liferay.object.rest.internal.resource.v1_0.ObjectRelationshipResource)"
	)
	private ComponentFactory _relationshipsResourceComponentFactory;

	private ServiceTrackerList<SystemObjectDefinitionMetadata>
		_systemObjectDefinitionMetadatas;

}