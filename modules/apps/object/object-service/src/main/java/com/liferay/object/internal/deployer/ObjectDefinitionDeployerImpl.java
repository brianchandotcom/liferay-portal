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

package com.liferay.object.internal.deployer;

import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.internal.info.collection.provider.ObjectEntrySingleFormVariationInfoCollectionProvider;
import com.liferay.object.internal.language.ObjectResourceBundle;
import com.liferay.object.internal.related.models.ObjectEntry1to1ObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.related.models.ObjectEntry1toMObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.related.models.ObjectEntryMtoMObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.rest.context.path.RESTContextPathResolverImpl;
import com.liferay.object.internal.search.spi.model.index.contributor.ObjectEntryModelDocumentContributor;
import com.liferay.object.internal.search.spi.model.index.contributor.ObjectEntryModelIndexerWriterContributor;
import com.liferay.object.internal.search.spi.model.query.contributor.ObjectEntryKeywordQueryContributor;
import com.liferay.object.internal.search.spi.model.query.contributor.ObjectEntryModelPreFilterContributor;
import com.liferay.object.internal.search.spi.model.result.contributor.ObjectEntryModelSummaryContributor;
import com.liferay.object.internal.security.permission.resource.ObjectEntryModelResourcePermission;
import com.liferay.object.internal.security.permission.resource.ObjectEntryPortletResourcePermissionLogic;
import com.liferay.object.internal.workflow.ObjectEntryWorkflowHandler;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.rest.context.path.RESTContextPathResolver;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Brian Wing Shun Chan
 * @author Marco Leo
 */
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	public ObjectDefinitionDeployerImpl(
		BundleContext bundleContext,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ModelSearchRegistrarHelper modelSearchRegistrarHelper,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		ObjectViewLocalService objectViewLocalService,
		PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry,
		ResourceActions resourceActions,
		ModelPreFilterContributor workflowStatusModelPreFilterContributor) {

		_bundleContext = bundleContext;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_modelSearchRegistrarHelper = modelSearchRegistrarHelper;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_objectViewLocalService = objectViewLocalService;
		_persistedModelLocalServiceRegistry =
			persistedModelLocalServiceRegistry;
		_resourceActions = resourceActions;
		_workflowStatusModelPreFilterContributor =
			workflowStatusModelPreFilterContributor;
	}

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		_persistedModelLocalServiceRegistry.register(
			objectDefinition.getClassName(), _objectEntryLocalService);

		try {
			_readResourceActions(objectDefinition);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}

		ObjectEntryModelIndexerWriterContributor
			objectEntryModelIndexerWriterContributor =
				new ObjectEntryModelIndexerWriterContributor(
					_dynamicQueryBatchIndexingActionableFactory,
					_objectEntryLocalService);
		ObjectEntryModelSummaryContributor objectEntryModelSummaryContributor =
			new ObjectEntryModelSummaryContributor();

		PortletResourcePermission portletResourcePermission =
			PortletResourcePermissionFactory.create(
				objectDefinition.getResourceName(),
				new ObjectEntryPortletResourcePermissionLogic());

		List<ServiceRegistration<?>> serviceRegistrations = ListUtil.fromArray(
			_bundleContext.registerService(
				InfoCollectionProvider.class,
				new ObjectEntrySingleFormVariationInfoCollectionProvider(
					_listTypeEntryLocalService, objectDefinition,
					_objectEntryLocalService, _objectFieldLocalService,
					_objectScopeProviderRegistry),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				KeywordQueryContributor.class,
				new ObjectEntryKeywordQueryContributor(
					_objectFieldLocalService, _objectViewLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"indexer.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				ModelDocumentContributor.class,
				new ObjectEntryModelDocumentContributor(
					objectDefinition.getClassName(),
					_objectDefinitionLocalService, _objectEntryLocalService,
					_objectFieldLocalService,
					_persistedModelLocalServiceRegistry),
				HashMapDictionaryBuilder.<String, Object>put(
					"indexer.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				ModelIndexerWriterContributor.class,
				objectEntryModelIndexerWriterContributor,
				HashMapDictionaryBuilder.<String, Object>put(
					"indexer.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				ModelPreFilterContributor.class,
				new ObjectEntryModelPreFilterContributor(
					_workflowStatusModelPreFilterContributor),
				HashMapDictionaryBuilder.<String, Object>put(
					"indexer.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				ModelResourcePermission.class,
				new ObjectEntryModelResourcePermission(
					objectDefinition.getClassName(), _objectEntryLocalService,
					portletResourcePermission),
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.object", "true"
				).put(
					"model.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				ObjectRelatedModelsProvider.class,
				new ObjectEntry1to1ObjectRelatedModelsProviderImpl(
					objectDefinition, _objectEntryLocalService,
					_objectFieldLocalService, _objectRelationshipLocalService),
				null),
			_bundleContext.registerService(
				ObjectRelatedModelsProvider.class,
				new ObjectEntry1toMObjectRelatedModelsProviderImpl(
					objectDefinition, _objectEntryLocalService,
					_objectFieldLocalService, _objectRelationshipLocalService),
				null),
			_bundleContext.registerService(
				ObjectRelatedModelsProvider.class,
				new ObjectEntryMtoMObjectRelatedModelsProviderImpl(
					objectDefinition, _objectEntryLocalService,
					_objectRelationshipLocalService),
				null),
			_bundleContext.registerService(
				PortletResourcePermission.class, portletResourcePermission,
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.object", "true"
				).put(
					"resource.name", objectDefinition.getResourceName()
				).build()),
			_bundleContext.registerService(
				RESTContextPathResolver.class,
				new RESTContextPathResolverImpl(
					"/o/" + objectDefinition.getRESTContextPath(),
					_objectScopeProviderRegistry.getObjectScopeProvider(
						objectDefinition.getScope()),
					false),
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				WorkflowHandler.class,
				new ObjectEntryWorkflowHandler(
					objectDefinition, _objectEntryLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", objectDefinition.getClassName()
				).build()),
			_modelSearchRegistrarHelper.register(
				objectDefinition.getClassName(), _bundleContext,
				modelSearchDefinition -> {
					modelSearchDefinition.setModelIndexWriteContributor(
						objectEntryModelIndexerWriterContributor);
					modelSearchDefinition.setModelSummaryContributor(
						objectEntryModelSummaryContributor);
				}));

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			serviceRegistrations.add(
				_bundleContext.registerService(
					ResourceBundle.class,
					new ObjectResourceBundle(locale, objectDefinition),
					MapUtil.singletonDictionary(
						"language.id", LocaleUtil.toLanguageId(locale))));
		}

		return serviceRegistrations;
	}

	@Override
	public void undeploy(ObjectDefinition objectDefinition) {
		_persistedModelLocalServiceRegistry.unregister(
			objectDefinition.getClassName());
	}

	private void _readResourceActions(ObjectDefinition objectDefinition)
		throws Exception {

		_resourceActions.populateModelResources(
			SAXReaderUtil.read(
				StringUtil.replace(
					StringUtil.read(
						ObjectDefinitionDeployerImpl.class.getClassLoader(),
						"resource-actions/resource-actions.xml.tpl"),
					new String[] {
						"[$MODEL_NAME$]", "[$PORTLET_NAME$]",
						"[$RESOURCE_NAME$]"
					},
					new String[] {
						objectDefinition.getClassName(),
						objectDefinition.getPortletId(),
						objectDefinition.getResourceName()
					})));
	}

	private final BundleContext _bundleContext;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ModelSearchRegistrarHelper _modelSearchRegistrarHelper;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final ObjectViewLocalService _objectViewLocalService;
	private final PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;
	private final ResourceActions _resourceActions;
	private final ModelPreFilterContributor
		_workflowStatusModelPreFilterContributor;

}