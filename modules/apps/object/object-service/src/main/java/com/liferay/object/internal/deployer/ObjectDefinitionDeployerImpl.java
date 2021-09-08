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
import com.liferay.object.data.provider.RelationshipDataProvider;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.internal.data.provider.ObjectEntryOneToManyRelationshipDataProvider;
import com.liferay.object.internal.info.collection.provider.ObjectEntrySingleFormVariationInfoCollectionProvider;
import com.liferay.object.internal.search.spi.model.index.contributor.ObjectEntryModelDocumentContributor;
import com.liferay.object.internal.search.spi.model.index.contributor.ObjectEntryModelIndexerWriterContributor;
import com.liferay.object.internal.search.spi.model.query.contributor.ObjectEntryKeywordQueryContributor;
import com.liferay.object.internal.search.spi.model.query.contributor.ObjectEntryModelPreFilterContributor;
import com.liferay.object.internal.security.permission.resource.ObjectEntryModelResourcePermission;
import com.liferay.object.internal.security.permission.resource.ObjectEntryPortletResourcePermissionLogic;
import com.liferay.object.internal.workflow.ObjectEntryWorkflowHandler;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Brian Wing Shun Chan
 * @author Marco Leo
 */
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	public ObjectDefinitionDeployerImpl(
		BundleContext bundleContext, DestinationFactory destinationFactory,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		ListTypeEntryLocalService listTypeEntryLocalService,
		MessageBus messageBus,
		ModelSearchRegistrarHelper modelSearchRegistrarHelper,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry,
		ResourceActions resourceActions,
		ModelPreFilterContributor workflowStatusModelPreFilterContributor) {

		_bundleContext = bundleContext;
		_destinationFactory = destinationFactory;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_messageBus = messageBus;
		_modelSearchRegistrarHelper = modelSearchRegistrarHelper;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_persistedModelLocalServiceRegistry =
			persistedModelLocalServiceRegistry;
		_resourceActions = resourceActions;
		_workflowStatusModelPreFilterContributor =
			workflowStatusModelPreFilterContributor;
	}

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

		Destination destination = _destinationFactory.createDestination(
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				objectDefinition.getDestinationName()));
		ObjectEntryModelIndexerWriterContributor
			objectEntryModelIndexerWriterContributor =
				new ObjectEntryModelIndexerWriterContributor(
					_dynamicQueryBatchIndexingActionableFactory,
					_objectEntryLocalService);
		PortletResourcePermission portletResourcePermission =
			PortletResourcePermissionFactory.create(
				objectDefinition.getResourceName(),
				new ObjectEntryPortletResourcePermissionLogic());

		return Arrays.asList(
			_bundleContext.registerService(
				Destination.class, destination,
				_toProperties(
					objectDefinition.getCompanyId(), destination.getName())),
			_bundleContext.registerService(
				InfoCollectionProvider.class,
				new ObjectEntrySingleFormVariationInfoCollectionProvider(
					_listTypeEntryLocalService, objectDefinition,
					_objectEntryLocalService, _objectFieldLocalService,
					_objectScopeProviderRegistry),
				null),
			_bundleContext.registerService(
				KeywordQueryContributor.class,
				new ObjectEntryKeywordQueryContributor(
					_objectFieldLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"indexer.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				ModelDocumentContributor.class,
				new ObjectEntryModelDocumentContributor(
					objectDefinition.getClassName(), _objectEntryLocalService,
					_objectFieldLocalService),
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
				RelationshipDataProvider.class,
				new ObjectEntryOneToManyRelationshipDataProvider(
					objectDefinition, _objectEntryLocalService),
				null),
			_bundleContext.registerService(
				PortletResourcePermission.class, portletResourcePermission,
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.object", "true"
				).put(
					"resource.name", objectDefinition.getResourceName()
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
				modelSearchDefinition ->
					modelSearchDefinition.setModelIndexWriteContributor(
						objectEntryModelIndexerWriterContributor)));
	}

	@Override
	public void undeploy(ObjectDefinition objectDefinition) {
		Destination destination = _messageBus.getDestination(
			objectDefinition.getDestinationName());

		if (destination != null) {
			destination.destroy();
		}

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

	private Dictionary<String, Object> _toProperties(
		long companyId, String destinationName) {

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destinationName
			).put(
				"destination.webhook.event.keys",
				StringUtil.merge(_DESTINATION_WEBHOOK_EVENT_KEYS)
			).put(
				"destination.webhook.required.company.id", companyId
			).build();

		for (String key : _DESTINATION_WEBHOOK_EVENT_KEYS) {
			properties.put(
				"destination.webhook.event.description[" + key + "]",
				StringBundler.concat(
					"destination-webhook-event-description[",
					"liferay-object-event][", key, "]"));
			properties.put(
				"destination.webhook.event.name[" + key + "]",
				"destination-webhook-event-name[liferay-object-event][" + key +
					"]");
		}

		return properties;
	}

	private static final String[] _DESTINATION_WEBHOOK_EVENT_KEYS = {
		"onAfterCreate", "onAfterRemove", "onAfterUpdate", "onBeforeCreate",
		"onBeforeRemove", "onBeforeUpdate"
	};

	private final BundleContext _bundleContext;
	private final DestinationFactory _destinationFactory;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final MessageBus _messageBus;
	private final ModelSearchRegistrarHelper _modelSearchRegistrarHelper;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;
	private final ResourceActions _resourceActions;
	private final ModelPreFilterContributor
		_workflowStatusModelPreFilterContributor;

}