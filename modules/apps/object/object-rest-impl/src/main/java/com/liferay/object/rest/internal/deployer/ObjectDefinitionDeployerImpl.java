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

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.graphql.dto.v1_0.ObjectDefinitionGraphQLDTOContributor;
import com.liferay.object.rest.internal.jaxrs.application.ObjectEntryApplication;
import com.liferay.object.rest.internal.jaxrs.context.provider.ObjectDefinitionContextProvider;
import com.liferay.object.rest.internal.jaxrs.exception.mapper.ObjectEntryManagerHttpExceptionMapper;
import com.liferay.object.rest.internal.jaxrs.exception.mapper.ObjectEntryValuesExceptionMapper;
import com.liferay.object.rest.internal.jaxrs.exception.mapper.ObjectValidationRuleEngineExceptionMapper;
import com.liferay.object.rest.internal.jaxrs.exception.mapper.RequiredObjectRelationshipExceptionMapper;
import com.liferay.object.rest.internal.resource.v1_0.BaseObjectEntryResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryRelatedObjectsResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceFactoryImpl;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.cxf.jaxrs.ext.ContextProvider;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = ObjectDefinitionDeployer.class)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	@Override
	public synchronized List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		if (objectDefinition.isSystem()) {
			_initSystemObjectDefinition(
				_systemObjectDefinitionMetadataRegistry.
					getSystemObjectDefinitionMetadata(
						objectDefinition.getName()));

			return Collections.emptyList();
		}

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.get(objectDefinition.getRESTContextPath());

		if (objectDefinitions == null) {
			objectDefinitions = new HashMap<>();

			_objectDefinitionsMap.put(
				objectDefinition.getRESTContextPath(), objectDefinitions);

			_excludeScopedMethods(objectDefinition, objectScopeProvider);
		}

		_initCustomObjectDefinition(objectDefinition);

		objectDefinitions.put(
			objectDefinition.getCompanyId(), objectDefinition);

		return Collections.singletonList(
			_bundleContext.registerService(
				GraphQLDTOContributor.class,
				ObjectDefinitionGraphQLDTOContributor.of(
					_filterPredicateFactory, objectDefinition,
					_objectEntryManagerRegistry.getObjectEntryManager(
						objectDefinition.getStorageType()),
					_objectFieldLocalService, _objectRelationshipLocalService,
					objectScopeProvider),
				HashMapDictionaryBuilder.<String, Object>put(
					"dto.name", objectDefinition.getDBTableName()
				).build()));
	}

	public ObjectDefinition getObjectDefinition(
		long companyId, String restContextPath) {

		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.get(restContextPath);

		ObjectDefinition objectDefinition = null;

		if (objectDefinitions != null) {
			objectDefinition = objectDefinitions.get(companyId);
		}

		if (objectDefinition == null) {
			throw new NotFoundException();
		}

		return objectDefinition;
	}

	@Override
	public synchronized void undeploy(ObjectDefinition objectDefinition) {
		String restContextPath = objectDefinition.getRESTContextPath();

		Map<Long, ObjectDefinition> objectDefinitions =
			_objectDefinitionsMap.get(restContextPath);

		if (objectDefinitions != null) {
			objectDefinitions.remove(objectDefinition.getCompanyId());

			if (objectDefinitions.isEmpty()) {
				_objectDefinitionsMap.remove(restContextPath);
			}
		}

		List<String> companyIds = _restContextPathCompanyIds.get(
			restContextPath);

		if (companyIds != null) {
			companyIds.remove(String.valueOf(objectDefinition.getCompanyId()));

			if (!companyIds.isEmpty()) {
				ServiceRegistration<?> serviceRegistration =
					_applicationServiceRegistrations.get(restContextPath);

				serviceRegistration.setProperties(
					_applicationProperties.get(restContextPath));

				return;
			}
		}

		List<ComponentInstance> componentInstances =
			_componentInstancesMap.remove(restContextPath);

		if (componentInstances != null) {
			for (ComponentInstance componentInstance : componentInstances) {
				componentInstance.dispose();
			}
		}

		ServiceRegistration<?> serviceRegistration1 =
			_applicationServiceRegistrations.remove(restContextPath);

		if (serviceRegistration1 != null) {
			serviceRegistration1.unregister();
		}

		List<ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrationsMap.remove(restContextPath);

		if (serviceRegistrations != null) {
			for (ServiceRegistration<?> serviceRegistration2 :
					serviceRegistrations) {

				serviceRegistration2.unregister();
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private ObjectEntryResourceImpl _createObjectEntryResourceImpl() {
		return new ObjectEntryResourceImpl(
			_filterPredicateFactory, _jsonFactory, _objectActionEngine,
			_objectDefinitionLocalService, _objectEntryLocalService,
			_objectEntryManagerRegistry, _objectFieldLocalService,
			_objectRelationshipService, _objectScopeProviderRegistry,
			_systemObjectDefinitionMetadataRegistry);
	}

	private void _excludeScopedMethods(
		ObjectDefinition objectDefinition,
		ObjectScopeProvider objectScopeProvider) {

		try {
			String factoryPid =
				"com.liferay.portal.vulcan.internal.configuration." +
					"VulcanConfiguration";

			Configuration configuration =
				_configurationAdmin.createFactoryConfiguration(factoryPid, "?");

			Method[] methods = BaseObjectEntryResourceImpl.class.getMethods();

			List<String> excludedOperationIds = new ArrayList<>();

			for (Method method : methods) {
				Path path = method.getAnnotation(Path.class);

				if (path == null) {
					continue;
				}

				String value = path.value();

				boolean groupAware = objectScopeProvider.isGroupAware();
				boolean hasScope = value.contains("scopes");

				if ((!groupAware && hasScope) ||
					(groupAware && !hasScope &&
					 !value.startsWith("/{objectEntryId}"))) {

					excludedOperationIds.add(method.getName());
				}
			}

			configuration.update(
				HashMapDictionaryBuilder.put(
					"excludedOperationIds",
					StringUtil.merge(excludedOperationIds, ",")
				).put(
					"path", objectDefinition.getRESTContextPath()
				).build());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _initCustomObjectDefinition(
		ObjectDefinition objectDefinition) {

		String restContextPath = objectDefinition.getRESTContextPath();

		List<String> companyIds = _restContextPathCompanyIds.computeIfAbsent(
			restContextPath, key -> new ArrayList<>());

		companyIds.add(String.valueOf(objectDefinition.getCompanyId()));

		String osgiJaxRsName = objectDefinition.getOSGiJaxRsName();

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", companyIds
			).put(
				"liferay.jackson", false
			).put(
				"osgi.jaxrs.application.base",
				objectDefinition.getRESTContextPath()
			).put(
				"osgi.jaxrs.extension.select",
				"(osgi.jaxrs.name=Liferay.Vulcan)"
			).put(
				"osgi.jaxrs.name", osgiJaxRsName
			).build();

		_applicationProperties.put(restContextPath, properties);

		ServiceRegistration<Application> applicationServiceRegistration =
			_applicationServiceRegistrations.get(restContextPath);

		if (applicationServiceRegistration == null) {
			_applicationServiceRegistrations.put(
				restContextPath,
				_bundleContext.registerService(
					Application.class,
					new ObjectEntryApplication(_objectEntryOpenAPIResource),
					properties));
		}
		else {
			applicationServiceRegistration.setProperties(properties);
		}

		_serviceRegistrationsMap.computeIfAbsent(
			restContextPath,
			key -> Arrays.asList(
				_bundleContext.registerService(
					ContextProvider.class,
					new ObjectDefinitionContextProvider(this, _portal),
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", "false"
					).put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + osgiJaxRsName + ")"
					).put(
						"osgi.jaxrs.extension", "true"
					).put(
						"osgi.jaxrs.name",
						objectDefinition.getOSGiJaxRsName(
							"ObjectDefinitionContextProvider")
					).build()),
				_bundleContext.registerService(
					ExceptionMapper.class,
					new ObjectEntryManagerHttpExceptionMapper(),
					HashMapDictionaryBuilder.<String, Object>put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + osgiJaxRsName + ")"
					).put(
						"osgi.jaxrs.extension", "true"
					).put(
						"osgi.jaxrs.name",
						objectDefinition.getOSGiJaxRsName(
							"ObjectEntryManagerHttpExceptionMapper")
					).build()),
				_bundleContext.registerService(
					ExceptionMapper.class,
					new ObjectEntryValuesExceptionMapper(),
					HashMapDictionaryBuilder.<String, Object>put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + osgiJaxRsName + ")"
					).put(
						"osgi.jaxrs.extension", "true"
					).put(
						"osgi.jaxrs.name",
						objectDefinition.getOSGiJaxRsName(
							"ObjectEntryValuesExceptionMapper")
					).build()),
				_bundleContext.registerService(
					ExceptionMapper.class,
					new ObjectValidationRuleEngineExceptionMapper(),
					HashMapDictionaryBuilder.<String, Object>put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + osgiJaxRsName + ")"
					).put(
						"osgi.jaxrs.extension", "true"
					).put(
						"osgi.jaxrs.name",
						objectDefinition.getOSGiJaxRsName(
							"ObjectValidationRuleEngineExceptionMapper")
					).build()),
				_bundleContext.registerService(
					ExceptionMapper.class,
					new RequiredObjectRelationshipExceptionMapper(),
					HashMapDictionaryBuilder.<String, Object>put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + osgiJaxRsName + ")"
					).put(
						"osgi.jaxrs.extension", "true"
					).put(
						"osgi.jaxrs.name",
						objectDefinition.getOSGiJaxRsName(
							"RequiredObjectRelationshipExceptionMapper")
					).build()),
				_bundleContext.registerService(
					ObjectEntryRelatedObjectsResourceImpl.class,
					new PrototypeServiceFactory
						<ObjectEntryRelatedObjectsResourceImpl>() {

						@Override
						public ObjectEntryRelatedObjectsResourceImpl getService(
							Bundle bundle,
							ServiceRegistration
								<ObjectEntryRelatedObjectsResourceImpl>
									serviceRegistration) {

							return new ObjectEntryRelatedObjectsResourceImpl(
								_objectDefinitionLocalService,
								_objectEntryManagerRegistry,
								_objectRelationshipService);
						}

						@Override
						public void ungetService(
							Bundle bundle,
							ServiceRegistration
								<ObjectEntryRelatedObjectsResourceImpl>
									serviceRegistration,
							ObjectEntryRelatedObjectsResourceImpl
								objectEntryRelatedObjectsResourceImpl) {
						}

					},
					HashMapDictionaryBuilder.<String, Object>put(
						"api.version", "v1.0"
					).put(
						"entity.class.name",
						ObjectEntry.class.getName() + "#" +
							objectDefinition.getName()
					).put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + osgiJaxRsName + ")"
					).put(
						"osgi.jaxrs.resource", "true"
					).build()),
				_bundleContext.registerService(
					ObjectEntryResource.Factory.class,
					new ObjectEntryResourceFactoryImpl(
						_companyLocalService, _defaultPermissionCheckerFactory,
						_expressionConvert, _filterParserProvider,
						_groupLocalService, objectDefinition,
						this::_createObjectEntryResourceImpl,
						_resourceActionLocalService,
						_resourcePermissionLocalService, _roleLocalService,
						_sortParserProvider, _userLocalService),
					HashMapDictionaryBuilder.<String, Object>put(
						"resource.locator.key",
						objectDefinition.getRESTContextPath() + "/" +
							objectDefinition.getShortName()
					).build()),
				_bundleContext.registerService(
					ObjectEntryResource.class,
					new PrototypeServiceFactory<ObjectEntryResource>() {

						@Override
						public ObjectEntryResource getService(
							Bundle bundle,
							ServiceRegistration<ObjectEntryResource>
								serviceRegistration) {

							return _createObjectEntryResourceImpl();
						}

						@Override
						public void ungetService(
							Bundle bundle,
							ServiceRegistration<ObjectEntryResource>
								serviceRegistration,
							ObjectEntryResource objectEntryResource) {
						}

					},
					HashMapDictionaryBuilder.<String, Object>put(
						"api.version", "v1.0"
					).put(
						"batch.engine.entity.class.name",
						ObjectEntry.class.getName() + "#" + osgiJaxRsName
					).put(
						"batch.engine.task.item.delegate", "true"
					).put(
						"batch.engine.task.item.delegate.name", osgiJaxRsName
					).put(
						"batch.planner.export.enabled", "true"
					).put(
						"batch.planner.import.enabled", "true"
					).put(
						"entity.class.name",
						ObjectEntry.class.getName() + "#" + osgiJaxRsName
					).put(
						"osgi.jaxrs.application.select",
						"(osgi.jaxrs.name=" + osgiJaxRsName + ")"
					).put(
						"osgi.jaxrs.resource", "true"
					).build())));
	}

	private void _initSystemObjectDefinition(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		if (systemObjectDefinitionMetadata == null) {
			return;
		}

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		_componentInstancesMap.computeIfAbsent(
			jaxRsApplicationDescriptor.getRESTContextPath(),
			key -> Arrays.asList(
				_relatedObjectEntryResourceImplComponentFactory.newInstance(
					HashMapDictionaryBuilder.<String, Object>put(
						"api.version", "v1.0"
					).put(
						"osgi.jaxrs.application.select",
						() -> {
							String applicationName =
								jaxRsApplicationDescriptor.getApplicationName();

							return "(osgi.jaxrs.name=" + applicationName + ")";
						}
					).put(
						"osgi.jaxrs.resource", "true"
					).build())));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionDeployerImpl.class);

	private final Map<String, Dictionary<String, Object>>
		_applicationProperties = new HashMap<>();
	private final Map<String, ServiceRegistration<Application>>
		_applicationServiceRegistrations = new HashMap<>();
	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Map<String, List<ComponentInstance>> _componentInstancesMap =
		new HashMap<>();

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private FilterPredicateFactory _filterPredicateFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, Map<Long, ObjectDefinition>>
		_objectDefinitionsMap = new HashMap<>();

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectEntryOpenAPIResource _objectEntryOpenAPIResource;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectRelationshipService _objectRelationshipService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(component.factory=com.liferay.object.rest.internal.resource.v1_0.RelatedObjectEntryResourceImpl)"
	)
	private ComponentFactory _relatedObjectEntryResourceImplComponentFactory;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private final Map<String, List<String>> _restContextPathCompanyIds =
		new HashMap<>();

	@Reference
	private RoleLocalService _roleLocalService;

	private final Map<String, List<ServiceRegistration<?>>>
		_serviceRegistrationsMap = new HashMap<>();

	@Reference
	private SortParserProvider _sortParserProvider;

	@Reference
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	@Reference
	private UserLocalService _userLocalService;

}