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
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.internal.info.collection.provider.ObjectEntrySingleFormVariationInfoCollectionProvider;
import com.liferay.object.internal.search.spi.model.index.contributor.ObjectEntryModelDocumentContributor;
import com.liferay.object.internal.search.spi.model.index.contributor.ObjectEntryModelIndexerWriterContributor;
import com.liferay.object.internal.search.spi.model.query.contributor.ObjectEntryKeywordQueryContributor;
import com.liferay.object.internal.search.spi.model.query.contributor.ObjectEntryModelPreFilterContributor;
import com.liferay.object.internal.security.permission.resource.ObjectEntryModelResourcePermission;
import com.liferay.object.internal.security.permission.resource.ObjectEntryPortletResourcePermissionLogic;
import com.liferay.object.internal.workflow.ObjectEntryWorkflowHandler;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
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
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = ObjectDefinitionDeployer.class
)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		try {
			_readResourceActions(objectDefinition);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}

		PortletResourcePermission portletResourcePermission =
			PortletResourcePermissionFactory.create(
				objectDefinition.getResourceName(),
				new ObjectEntryPortletResourcePermissionLogic());

		ObjectEntryModelIndexerWriterContributor
			objectEntryModelIndexerWriterContributor =
				new ObjectEntryModelIndexerWriterContributor(
					_dynamicQueryBatchIndexingActionableFactory,
					_objectEntryLocalService);

		_persistedModelLocalServiceRegistry.register(
			objectDefinition.getClassName(), _objectEntryLocalService);

		return Arrays.asList(
			_bundleContext.registerService(
				InfoCollectionProvider.class,
				new ObjectEntrySingleFormVariationInfoCollectionProvider(
					objectDefinition, _objectEntryLocalService),
				null),
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
				KeywordQueryContributor.class,
				new ObjectEntryKeywordQueryContributor(
					_objectFieldLocalService),
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
			_modelSearchRegistrarHelper.register(
				objectDefinition.getClassName(), _bundleContext,
				modelSearchDefinition ->
					modelSearchDefinition.setModelIndexWriteContributor(
						objectEntryModelIndexerWriterContributor)),
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
				).build()));
	}

	@Override
	public void undeploy(ObjectDefinition objectDefinition) {
		_persistedModelLocalServiceRegistry.unregister(
			objectDefinition.getClassName());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
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

	private BundleContext _bundleContext;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private ModelSearchRegistrarHelper _modelSearchRegistrarHelper;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	@Reference
	private ResourceActions _resourceActions;

	@Reference(target = "(model.pre.filter.contributor.id=WorkflowStatus)")
	private ModelPreFilterContributor _workflowStatusModelPreFilterContributor;

}