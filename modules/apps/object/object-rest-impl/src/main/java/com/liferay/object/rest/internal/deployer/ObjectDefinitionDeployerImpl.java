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
import com.liferay.object.rest.internal.jaxrs.exception.mapper.RequiredObjectFieldExceptionMapper;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ext.ExceptionMapper;

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

	@Override
	public synchronized List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		if (!_objectDefinitionsMap.containsKey(
				objectDefinition.getRESTContextPath())) {

			_componentInstancesMap.put(
				objectDefinition.getRESTContextPath(),
				Arrays.asList(
					_objectEntryApplicationComponentFactory.newInstance(
						HashMapDictionaryBuilder.<String, Object>put(
							"liferay.jackson", false
						).put(
							"liferay.object.definition.id",
							objectDefinition.getObjectDefinitionId()
						).put(
							"osgi.jaxrs.application.base",
							"/" + objectDefinition.getRESTContextPath()
						).put(
							"osgi.jaxrs.extension.select",
							"(osgi.jaxrs.name=Liferay.Vulcan)"
						).put(
							"osgi.jaxrs.name", objectDefinition.getShortName()
						).build()),
					_objectEntryResourceComponentFactory.newInstance(
						HashMapDictionaryBuilder.<String, Object>put(
							"api.version", "v1.0"
						).put(
							"batch.engine.task.item.delegate", "true"
						).put(
							"batch.engine.task.item.delegate.name",
							objectDefinition.getShortName()
						).put(
							"osgi.jaxrs.application.select",
							"(osgi.jaxrs.name=" +
								objectDefinition.getShortName() + ")"
						).put(
							"osgi.jaxrs.resource", "true"
						).build())));

			Collections.addAll(
				serviceRegistrations,
				_bundleContext.registerService(
					ContextProvider.class,
					new ObjectDefinitionContextProvider(this, _portal),
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", "false"
					).put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + objectDefinition.getShortName() +
							")"
					).put(
						"osgi.jaxrs.extension", "true"
					).put(
						"osgi.jaxrs.name",
						objectDefinition.getRESTContextPath() +
							"ObjectDefinitionContextProvider"
					).build()),
				_bundleContext.registerService(
					ExceptionMapper.class,
					new RequiredObjectFieldExceptionMapper(),
					HashMapDictionaryBuilder.<String, Object>put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + objectDefinition.getShortName() +
							")"
					).put(
						"osgi.jaxrs.extension", "true"
					).put(
						"osgi.jaxrs.name",
						objectDefinition.getRESTContextPath() +
							"RequiredObjectFieldExceptionMapper"
					).build()));
		}

		serviceRegistrations.add(
			_bundleContext.registerService(
				GraphQLDTOContributor.class,
				ObjectDefinitionGraphQLDTOContributor.of(
					objectDefinition, _objectEntryManager,
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId())),
				HashMapDictionaryBuilder.<String, Object>put(
					"dto.name", objectDefinition.getDBTableName()
				).build()));

		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.get(objectDefinition.getRESTContextPath());

		if (objectDefinitions == null) {
			objectDefinitions = new HashMap<>();

			_objectDefinitionsMap.put(
				objectDefinition.getRESTContextPath(), objectDefinitions);
		}

		objectDefinitions.put(
			objectDefinition.getCompanyId(), objectDefinition);

		return serviceRegistrations;
	}

	public ObjectDefinition getObjectDefinition(
		long companyId, String restContextPath) {

		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.get(restContextPath);

		if (objectDefinitions != null) {
			return objectDefinitions.get(companyId);
		}

		return null;
	}

	@Override
	public synchronized void undeploy(ObjectDefinition objectDefinition) {
		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.get(objectDefinition.getRESTContextPath());

		if (objectDefinitions != null) {
			objectDefinitions.remove(objectDefinition.getCompanyId());

			if (objectDefinitions.isEmpty()) {
				_objectDefinitionsMap.remove(
					objectDefinition.getRESTContextPath());
			}
		}

		if (!_objectDefinitionsMap.containsKey(
				objectDefinition.getRESTContextPath())) {

			List<ComponentInstance> componentInstances =
				_componentInstancesMap.get(
					objectDefinition.getRESTContextPath());

			if (componentInstances != null) {
				for (ComponentInstance componentInstance : componentInstances) {
					componentInstance.dispose();
				}
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;
	private final Map<String, List<ComponentInstance>> _componentInstancesMap =
		new HashMap<>();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, Map<Long, ObjectDefinition>>
		_objectDefinitionsMap = new HashMap<>();

	@Reference(
		target = "(component.factory=com.liferay.object.internal.jaxrs.application.ObjectEntryApplication)"
	)
	private ComponentFactory _objectEntryApplicationComponentFactory;

	@Reference
	private ObjectEntryManager _objectEntryManager;

	@Reference(
		target = "(component.factory=com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResource)"
	)
	private ComponentFactory _objectEntryResourceComponentFactory;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private Portal _portal;

}