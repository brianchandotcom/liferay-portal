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

import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.internal.graphql.dto.v1_0.ObjectDefinitionGraphQLDTOContributor;
import com.liferay.object.rest.internal.jaxrs.context.provider.ObjectDefinitionContextProvider;
import com.liferay.object.rest.internal.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.ext.ContextProvider;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ObjectDefinitionDeployer.class)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		long objectDefinitionId = objectDefinition.getObjectDefinitionId();

		_componentInstanceMap.put(
			objectDefinitionId,
			Arrays.asList(
				_applicationComponentFactory.newInstance(
					HashMapDictionaryBuilder.<String, Object>put(
						"liferay.jackson", false
					).put(
						"osgi.jaxrs.application.base",
						"/" + objectDefinition.getRESTContextPath()
					).put(
						"osgi.jaxrs.extension.select",
						"(osgi.jaxrs.name=Liferay.Vulcan)"
					).put(
						"osgi.jaxrs.name", objectDefinition.getName()
					).put(
						"objectDefinitionId", objectDefinitionId
					).build()),
				_resourceComponentFactory.newInstance(
					HashMapDictionaryBuilder.<String, Object>put(
						"api.version", "v1.0"
					).put(
						"batch.engine.task.item.delegate", "true"
					).put(
						"osgi.jaxrs.resource", "true"
					).put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + objectDefinition.getName() + ")"
					).build())));

		return Arrays.asList(
			_bundleContext.registerService(
				ContextProvider.class,
				new ObjectDefinitionContextProvider(objectDefinition),
				HashMapDictionaryBuilder.<String, Object>put(
					"osgi.jaxrs.application.select",
					"(osgi.jaxrs.name=" + objectDefinition.getName() + ")"
				).put(
					"osgi.jaxrs.extension", "true"
				).put(
					"batch.engine.task.item.delegate.name",
					objectDefinition.getName()
				).put(
					"enabled", "false"
				).put(
					"osgi.jaxrs.name",
					objectDefinition.getRESTContextPath() +
						"ObjectDefinitionContextProvider"
				).build()),
			_bundleContext.registerService(
				GraphQLDTOContributor.class,
				ObjectDefinitionGraphQLDTOContributor.of(
					objectDefinition, _objectEntryManager,
					_objectFieldLocalService.getObjectFields(
						objectDefinitionId)),
				HashMapDictionaryBuilder.<String, Object>put(
					"dto.name", objectDefinition.getDBTableName()
				).build()));
	}

	@Override
	public void undeploy(long objectDefinitionId) {
		List<ComponentInstance> componentInstances = _componentInstanceMap.get(
			objectDefinitionId);

		for (ComponentInstance componentInstance : componentInstances) {
			componentInstance.dispose();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference(
		target = "(component.factory=com.liferay.object.internal.jaxrs.application.ObjectEntryApplication)"
	)
	private ComponentFactory _applicationComponentFactory;

	private BundleContext _bundleContext;
	private final Map<Long, List<ComponentInstance>> _componentInstanceMap =
		new HashMap<>();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference(
		target = "(component.factory=com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResource)"
	)
	private ComponentFactory _resourceComponentFactory;

}