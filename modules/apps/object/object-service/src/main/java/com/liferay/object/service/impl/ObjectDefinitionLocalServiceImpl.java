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

package com.liferay.object.service.impl;

import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.exception.DuplicateObjectDefinitionException;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectDefinitionActiveException;
import com.liferay.object.exception.ObjectDefinitionLabelException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectDefinitionPluralLabelException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectDefinitionVersionException;
import com.liferay.object.exception.RequiredObjectDefinitionException;
import com.liferay.object.internal.deployer.ObjectDefinitionDeployerImpl;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.impl.ObjectDefinitionImpl;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.base.ObjectDefinitionLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectEntryPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectDefinition",
	service = AopService.class
)
public class ObjectDefinitionLocalServiceImpl
	extends ObjectDefinitionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition addCustomObjectDefinition(
			long userId, Map<Locale, String> labelMap, String name,
			String panelAppOrder, String panelCategoryKey,
			Map<Locale, String> pluralLabelMap, String scope,
			List<ObjectField> objectFields)
		throws PortalException {

		return _addObjectDefinition(
			userId, null, null, labelMap, name, panelAppOrder, panelCategoryKey,
			null, null, pluralLabelMap, scope, false, 0,
			WorkflowConstants.STATUS_DRAFT, objectFields);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition addOrUpdateSystemObjectDefinition(
			long companyId,
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.fetchByC_N(
				companyId, systemObjectDefinitionMetadata.getName());

		if ((objectDefinition != null) &&
			(objectDefinition.getVersion() ==
				systemObjectDefinitionMetadata.getVersion())) {

			return objectDefinition;
		}

		long userId = _userLocalService.getDefaultUserId(companyId);

		if (objectDefinition == null) {
			Table table = systemObjectDefinitionMetadata.getTable();
			Column<?, Long> primaryKeyColumn =
				systemObjectDefinitionMetadata.getPrimaryKeyColumn();

			return addSystemObjectDefinition(
				userId, systemObjectDefinitionMetadata.getClassName(),
				table.getTableName(),
				systemObjectDefinitionMetadata.getLabelMap(),
				systemObjectDefinitionMetadata.getName(),
				primaryKeyColumn.getName(), primaryKeyColumn.getName(),
				systemObjectDefinitionMetadata.getPluralLabelMap(),
				systemObjectDefinitionMetadata.getScope(),
				systemObjectDefinitionMetadata.getVersion(),
				systemObjectDefinitionMetadata.getObjectFields());
		}

		objectDefinition.setVersion(
			systemObjectDefinitionMetadata.getVersion());

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		List<ObjectField> newObjectFields =
			systemObjectDefinitionMetadata.getObjectFields();

		List<ObjectField> oldObjectFields =
			_objectFieldPersistence.findByODI_DTN(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getDBTableName());

		for (ObjectField oldObjectField : oldObjectFields) {
			if (!_hasObjectField(newObjectFields, oldObjectField)) {
				_objectFieldPersistence.remove(oldObjectField);
			}
		}

		for (ObjectField newObjectField : newObjectFields) {
			ObjectField oldObjectField = _objectFieldPersistence.fetchByODI_N(
				objectDefinition.getObjectDefinitionId(),
				newObjectField.getName());

			if (oldObjectField == null) {
				_objectFieldLocalService.addSystemObjectField(
					userId, objectDefinition.getObjectDefinitionId(),
					newObjectField.getDBColumnName(), false, false, "",
					newObjectField.getLabelMap(), newObjectField.getName(),
					newObjectField.isRequired(), newObjectField.getType());
			}
			else {
				if (!Objects.equals(oldObjectField, newObjectField.getType())) {
					oldObjectField.setRequired(newObjectField.isRequired());
					oldObjectField.setType(newObjectField.getType());

					_objectFieldPersistence.update(oldObjectField);
				}
			}
		}

		return objectDefinition;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition addSystemObjectDefinition(
			long userId, String className, String dbTableName,
			Map<Locale, String> labelMap, String name,
			String pkObjectFieldDBColumnName, String pkObjectFieldName,
			Map<Locale, String> pluralLabelMap, String scope, int version,
			List<ObjectField> objectFields)
		throws PortalException {

		return _addObjectDefinition(
			userId, className, dbTableName, labelMap, name, null, null,
			pkObjectFieldDBColumnName, pkObjectFieldName, pluralLabelMap, scope,
			true, version, WorkflowConstants.STATUS_APPROVED, objectFields);
	}

	@Override
	public void deleteCompanyObjectDefinitions(long companyId)
		throws PortalException {

		List<ObjectDefinition> objectDefinitions =
			objectDefinitionPersistence.findByCompanyId(companyId);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectDefinition deleteObjectDefinition(long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return deleteObjectDefinition(objectDefinition);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectDefinition deleteObjectDefinition(
			ObjectDefinition objectDefinition)
		throws PortalException {

		if (!CompanyThreadLocal.isDeleteInProcess() &&
			!PortalRunMode.isTestMode() && objectDefinition.isApproved()) {

			throw new RequiredObjectDefinitionException();
		}

		if (!objectDefinition.isSystem()) {
			List<ObjectEntry> objectEntries =
				_objectEntryPersistence.findByObjectDefinitionId(
					objectDefinition.getObjectDefinitionId());

			for (ObjectEntry objectEntry : objectEntries) {
				_objectEntryLocalService.deleteObjectEntry(objectEntry);
			}
		}

		_objectFieldPersistence.removeByObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());

		// TODO Deleting an object definition should delete related object
		// relationships

		objectDefinitionPersistence.remove(objectDefinition);

		_resourceLocalService.deleteResource(
			objectDefinition.getCompanyId(), ObjectDefinition.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			objectDefinition.getObjectDefinitionId());

		// TODO Delete object actions and layouts

		if (objectDefinition.isSystem()) {
			_dropTable(objectDefinition.getExtensionDBTableName());
		}
		else if (objectDefinition.isApproved()) {
			for (ResourceAction resourceAction :
					_resourceActionLocalService.getResourceActions(
						objectDefinition.getClassName())) {

				_resourceActionLocalService.deleteResourceAction(
					resourceAction);
			}

			for (ResourceAction resourceAction :
					_resourceActionLocalService.getResourceActions(
						objectDefinition.getPortletId())) {

				_resourceActionLocalService.deleteResourceAction(
					resourceAction);
			}

			for (ResourceAction resourceAction :
					_resourceActionLocalService.getResourceActions(
						objectDefinition.getResourceName())) {

				_resourceActionLocalService.deleteResourceAction(
					resourceAction);
			}

			_dropTable(objectDefinition.getDBTableName());
			_dropTable(objectDefinition.getExtensionDBTableName());

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					objectDefinitionLocalService.undeployObjectDefinition(
						objectDefinition);

					return null;
				});
		}

		return objectDefinition;
	}

	@Clusterable
	@Override
	public void deployObjectDefinition(ObjectDefinition objectDefinition) {
		if (objectDefinition.isSystem()) {
			return;
		}

		undeployObjectDefinition(objectDefinition);

		for (Map.Entry
				<ObjectDefinitionDeployer,
				 Map<Long, List<ServiceRegistration<?>>>> entry :
					_serviceRegistrationsMaps.entrySet()) {

			ObjectDefinitionDeployer objectDefinitionDeployer = entry.getKey();
			Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap =
				entry.getValue();

			serviceRegistrationsMap.computeIfAbsent(
				objectDefinition.getObjectDefinitionId(),
				objectDefinitionId -> objectDefinitionDeployer.deploy(
					objectDefinition));
		}
	}

	@Override
	public ObjectDefinition fetchObjectDefinition(long companyId, String name) {
		return objectDefinitionPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public ObjectDefinition fetchObjectDefinitionByClassName(
			long companyId, String className)
		throws PortalException {

		return objectDefinitionPersistence.fetchByC_C(companyId, className);
	}

	@Override
	public List<ObjectDefinition> getCustomObjectDefinitions(int status) {
		return objectDefinitionPersistence.findByS_S(false, status);
	}

	@Override
	public ObjectDefinition getObjectDefinition(long objectDefinitionId)
		throws PortalException {

		return objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);
	}

	@Override
	public List<ObjectDefinition> getObjectDefinitions(
		long companyId, boolean active, boolean system, int status) {

		return objectDefinitionPersistence.findByC_A_S_S(
			companyId, active, system, status);
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws PortalException {

		return objectDefinitionPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<ObjectDefinition> getSystemObjectDefinitions() {
		return objectDefinitionPersistence.findBySystem(true);
	}

	@Override
	public ObjectDefinition publishCustomObjectDefinition(
			long userId, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (objectDefinition.isSystem()) {
			throw new ObjectDefinitionStatusException();
		}

		objectDefinition.setActive(true);
		objectDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		_createTable(objectDefinition.getDBTableName(), objectDefinition);
		_createTable(
			objectDefinition.getExtensionDBTableName(), objectDefinition);

		ObjectDefinition finalObjectDefinition = objectDefinition;

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				objectDefinitionLocalService.deployObjectDefinition(
					finalObjectDefinition);

				return null;
			});

		return objectDefinition;
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		_addingObjectDefinitionDeployer(
			new ObjectDefinitionDeployerImpl(
				_bundleContext, _dynamicQueryBatchIndexingActionableFactory,
				_listTypeEntryLocalService, _modelSearchRegistrarHelper, this,
				_objectEntryLocalService, _objectFieldLocalService,
				_objectRelationshipLocalService, _objectScopeProviderRegistry,
				_persistedModelLocalServiceRegistry, _resourceActions,
				_workflowStatusModelPreFilterContributor));

		_objectDefinitionDeployerServiceTracker = new ServiceTracker<>(
			_bundleContext, ObjectDefinitionDeployer.class,
			new ServiceTrackerCustomizer
				<ObjectDefinitionDeployer, ObjectDefinitionDeployer>() {

				@Override
				public ObjectDefinitionDeployer addingService(
					ServiceReference<ObjectDefinitionDeployer>
						serviceReference) {

					return _addingObjectDefinitionDeployer(
						_bundleContext.getService(serviceReference));
				}

				@Override
				public void modifiedService(
					ServiceReference<ObjectDefinitionDeployer> serviceReference,
					ObjectDefinitionDeployer objectDefinitionDeployer) {
				}

				@Override
				public void removedService(
					ServiceReference<ObjectDefinitionDeployer> serviceReference,
					ObjectDefinitionDeployer objectDefinitionDeployer) {

					Map<Long, List<ServiceRegistration<?>>>
						serviceRegistrationsMap =
							_serviceRegistrationsMaps.remove(
								objectDefinitionDeployer);

					for (List<ServiceRegistration<?>> serviceRegistrations :
							serviceRegistrationsMap.values()) {

						for (ServiceRegistration<?> serviceRegistration :
								serviceRegistrations) {

							serviceRegistration.unregister();
						}
					}

					_bundleContext.ungetService(serviceReference);
				}

			});

		_objectDefinitionDeployerServiceTracker.open();
	}

	@Clusterable
	@Override
	public void undeployObjectDefinition(ObjectDefinition objectDefinition) {
		if (objectDefinition.isSystem()) {
			return;
		}

		for (Map.Entry
				<ObjectDefinitionDeployer,
				 Map<Long, List<ServiceRegistration<?>>>> entry :
					_serviceRegistrationsMaps.entrySet()) {

			ObjectDefinitionDeployer objectDefinitionDeployer = entry.getKey();

			objectDefinitionDeployer.undeploy(objectDefinition);

			Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap =
				entry.getValue();

			List<ServiceRegistration<?>> serviceRegistrations =
				serviceRegistrationsMap.remove(
					objectDefinition.getObjectDefinitionId());

			if (serviceRegistrations != null) {
				for (ServiceRegistration<?> serviceRegistration :
						serviceRegistrations) {

					serviceRegistration.unregister();
				}
			}
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition updateCustomObjectDefinition(
			Long objectDefinitionId, long descriptionObjectFieldId,
			long titleObjectFieldId, boolean active,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			String scope)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId);

		if (objectDefinition.isSystem()) {
			throw new ObjectDefinitionStatusException();
		}

		return _updateObjectDefinition(
			objectDefinition, descriptionObjectFieldId, titleObjectFieldId,
			active, null, labelMap, name, panelAppOrder, panelCategoryKey, null,
			null, pluralLabelMap, scope);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();

		if (_objectDefinitionDeployerServiceTracker != null) {
			_objectDefinitionDeployerServiceTracker.close();
		}
	}

	private ObjectDefinitionDeployer _addingObjectDefinitionDeployer(
		ObjectDefinitionDeployer objectDefinitionDeployer) {

		Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap =
			new ConcurrentHashMap<>();

		List<ObjectDefinition> objectDefinitions =
			objectDefinitionLocalService.getCustomObjectDefinitions(
				WorkflowConstants.STATUS_APPROVED);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			serviceRegistrationsMap.put(
				objectDefinition.getObjectDefinitionId(),
				objectDefinitionDeployer.deploy(objectDefinition));
		}

		_serviceRegistrationsMaps.put(
			objectDefinitionDeployer, serviceRegistrationsMap);

		return objectDefinitionDeployer;
	}

	private ObjectDefinition _addObjectDefinition(
			long userId, String className, String dbTableName,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, String pkObjectFieldDBColumnName,
			String pkObjectFieldName, Map<Locale, String> pluralLabelMap,
			String scope, boolean system, int version, int status,
			List<ObjectField> objectFields)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		name = _getName(name, system);

		String shortName = ObjectDefinitionImpl.getShortName(name);

		dbTableName = _getDBTableName(
			dbTableName, name, system, user.getCompanyId(), shortName);

		pkObjectFieldName = _getPKObjectFieldName(
			pkObjectFieldName, system, shortName);

		pkObjectFieldDBColumnName = _getPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName, pkObjectFieldName, system);

		_validateLabel(labelMap, LocaleUtil.getSiteDefault());
		_validateName(0, user.getCompanyId(), name, system);
		_validatePluralLabel(pluralLabelMap, LocaleUtil.getSiteDefault());
		_validateScope(scope);
		_validateVersion(system, version);

		ObjectDefinition objectDefinition = objectDefinitionPersistence.create(
			counterLocalService.increment());

		objectDefinition.setCompanyId(user.getCompanyId());
		objectDefinition.setUserId(user.getUserId());
		objectDefinition.setUserName(user.getFullName());
		objectDefinition.setActive(false);
		objectDefinition.setDBTableName(dbTableName);
		objectDefinition.setClassName(
			_getClassName(
				objectDefinition.getObjectDefinitionId(), className, system));
		objectDefinition.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectDefinition.setName(name);
		objectDefinition.setPanelAppOrder(panelAppOrder);
		objectDefinition.setPanelCategoryKey(panelCategoryKey);
		objectDefinition.setPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName);
		objectDefinition.setPKObjectFieldName(pkObjectFieldName);
		objectDefinition.setPluralLabelMap(pluralLabelMap);
		objectDefinition.setScope(scope);
		objectDefinition.setSystem(system);
		objectDefinition.setVersion(version);
		objectDefinition.setStatus(status);

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		_resourceLocalService.addResources(
			objectDefinition.getCompanyId(), 0, objectDefinition.getUserId(),
			ObjectDefinition.class.getName(),
			objectDefinition.getObjectDefinitionId(), false, true, true);

		if (objectFields != null) {
			for (ObjectField objectField : objectFields) {
				if (system) {
					_objectFieldLocalService.addSystemObjectField(
						userId, objectDefinition.getObjectDefinitionId(),
						objectField.getDBColumnName(), objectField.isIndexed(),
						objectField.isIndexedAsKeyword(),
						objectField.getIndexedLanguageId(),
						objectField.getLabelMap(), objectField.getName(),
						objectField.isRequired(), objectField.getType());
				}
				else {
					_objectFieldLocalService.addCustomObjectField(
						userId, objectField.getListTypeDefinitionId(),
						objectDefinition.getObjectDefinitionId(),
						objectField.isIndexed(),
						objectField.isIndexedAsKeyword(),
						objectField.getIndexedLanguageId(),
						objectField.getLabelMap(), objectField.getName(),
						objectField.isRequired(), objectField.getType());
				}
			}
		}

		if (system) {
			_createTable(
				objectDefinition.getExtensionDBTableName(), objectDefinition);
		}

		return objectDefinition;
	}

	private void _createTable(
		String dbTableName, ObjectDefinition objectDefinition) {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			new DynamicObjectDefinitionTable(
				objectDefinition,
				_objectFieldPersistence.findByODI_DTN(
					objectDefinition.getObjectDefinitionId(), dbTableName),
				dbTableName);

		runSQL(dynamicObjectDefinitionTable.getCreateTableSQL());
	}

	private void _deleteWorkflowInstanceLinks(ObjectDefinition objectDefinition)
		throws PortalException {

		List<ObjectEntry> objectEntries =
			_objectEntryPersistence.findByODI_NotS(
				objectDefinition.getObjectDefinitionId(),
				WorkflowConstants.STATUS_APPROVED);

		for (ObjectEntry objectEntry : objectEntries) {
			_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				objectEntry.getCompanyId(), objectEntry.getNonzeroGroupId(),
				objectDefinition.getClassName(),
				objectEntry.getObjectEntryId());
		}
	}

	private void _dropTable(String dbTableName) {
		String sql = "drop table " + dbTableName;

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		runSQL(sql);
	}

	private String _getClassName(
		long objectDefinitionId, String className, boolean system) {

		if (system) {
			return className;
		}

		return "com.liferay.object.model.ObjectDefinition#" +
			objectDefinitionId;
	}

	private String _getDBTableName(
		String dbTableName, String name, boolean system, Long companyId,
		String shortName) {

		if (Validator.isNotNull(dbTableName)) {
			return dbTableName;
		}

		if (system) {
			return name;
		}

		return StringBundler.concat(
			"O_", companyId, StringPool.UNDERLINE, shortName);
	}

	private String _getName(String name, boolean system) {
		name = StringUtil.trim(name);

		if (!system) {
			name = "C_" + name;
		}

		return name;
	}

	private String _getPKObjectFieldDBColumnName(
		String pkObjectFieldDBColumnName, String pkObjectFieldName,
		boolean system) {

		if (Validator.isNotNull(pkObjectFieldDBColumnName)) {
			return pkObjectFieldDBColumnName;
		}

		if (system) {
			return pkObjectFieldName;
		}

		return pkObjectFieldName + StringPool.UNDERLINE;
	}

	private String _getPKObjectFieldName(
		String pkObjectFieldName, boolean system, String shortName) {

		if (Validator.isNotNull(pkObjectFieldName)) {
			return pkObjectFieldName;
		}

		pkObjectFieldName = TextFormatter.format(
			shortName + "Id", TextFormatter.I);

		if (system) {
			return pkObjectFieldName;
		}

		return pkObjectFieldName = "c_" + pkObjectFieldName;
	}

	private boolean _hasObjectField(
		List<ObjectField> newObjectFields, ObjectField oldObjectField) {

		for (ObjectField newObjectField : newObjectFields) {
			if (Objects.equals(
					newObjectField.getName(), oldObjectField.getName())) {

				return true;
			}
		}

		return false;
	}

	private void _startWorkflowInstanceLinks(ObjectDefinition objectDefinition)
		throws PortalException {

		List<ObjectEntry> objectEntries =
			_objectEntryPersistence.findByODI_NotS(
				objectDefinition.getObjectDefinitionId(),
				WorkflowConstants.STATUS_APPROVED);

		for (ObjectEntry objectEntry : objectEntries) {
			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				objectEntry.getCompanyId(), objectEntry.getNonzeroGroupId(),
				objectEntry.getUserId(), objectDefinition.getClassName(),
				objectEntry.getObjectEntryId(), objectEntry,
				ServiceContextThreadLocal.getServiceContext());
		}
	}

	private ObjectDefinition _updateObjectDefinition(
			ObjectDefinition objectDefinition, long descriptionObjectFieldId,
			long titleObjectFieldId, boolean active, String dbTableName,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, String pkObjectFieldDBColumnName,
			String pkObjectFieldName, Map<Locale, String> pluralLabelMap,
			String scope)
		throws PortalException {

		boolean originalActive = objectDefinition.isActive();

		_validateActive(objectDefinition, active);
		_validateObjectFieldId(objectDefinition, descriptionObjectFieldId);
		_validateObjectFieldId(objectDefinition, titleObjectFieldId);
		_validateLabel(labelMap, LocaleUtil.getSiteDefault());
		_validatePluralLabel(pluralLabelMap, LocaleUtil.getSiteDefault());

		objectDefinition.setDescriptionObjectFieldId(descriptionObjectFieldId);
		objectDefinition.setTitleObjectFieldId(titleObjectFieldId);
		objectDefinition.setActive(active);
		objectDefinition.setPanelAppOrder(panelAppOrder);
		objectDefinition.setPanelCategoryKey(panelCategoryKey);
		objectDefinition.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectDefinition.setPluralLabelMap(pluralLabelMap);

		if (objectDefinition.isApproved()) {
			if (!active && originalActive) {
				_deleteWorkflowInstanceLinks(objectDefinition);

				objectDefinitionLocalService.undeployObjectDefinition(
					objectDefinition);
			}
			else if (active) {
				objectDefinitionLocalService.deployObjectDefinition(
					objectDefinition);

				_startWorkflowInstanceLinks(objectDefinition);
			}

			return objectDefinitionPersistence.update(objectDefinition);
		}

		name = _getName(name, objectDefinition.isSystem());

		String shortName = ObjectDefinitionImpl.getShortName(name);

		dbTableName = _getDBTableName(
			dbTableName, name, objectDefinition.isSystem(),
			objectDefinition.getCompanyId(), shortName);

		pkObjectFieldName = _getPKObjectFieldName(
			pkObjectFieldName, objectDefinition.isSystem(), shortName);

		pkObjectFieldDBColumnName = _getPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName, pkObjectFieldName,
			objectDefinition.isSystem());

		_validateName(
			objectDefinition.getObjectDefinitionId(),
			objectDefinition.getCompanyId(), name, objectDefinition.isSystem());
		_validateScope(scope);

		objectDefinition.setDBTableName(dbTableName);
		objectDefinition.setName(name);
		objectDefinition.setPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName);
		objectDefinition.setPKObjectFieldName(pkObjectFieldName);
		objectDefinition.setScope(scope);

		return objectDefinitionPersistence.update(objectDefinition);
	}

	private void _validateActive(
			ObjectDefinition objectDefinition, boolean active)
		throws PortalException {

		if (active && !objectDefinition.isApproved()) {
			throw new ObjectDefinitionActiveException(
				"Object definitions must be published before being activated");
		}

		if (active || !objectDefinition.isActive()) {
			return;
		}

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.getObjectRelationships(
				objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Stream<ObjectRelationship> stream = objectRelationships.stream();

		if (stream.anyMatch(
				objectRelationship -> {
					ObjectDefinition objectDefinition2 =
						objectDefinitionPersistence.fetchByPrimaryKey(
							objectRelationship.getObjectDefinitionId2());

					return (Objects.equals(
						objectRelationship.getType(),
						ObjectRelationshipConstants.TYPE_ONE_TO_MANY) ||
							Objects.equals(
								objectRelationship.getType(),
								ObjectRelationshipConstants.TYPE_ONE_TO_ONE)) &&
						   objectDefinition2.isActive();
				})) {

			throw new ObjectDefinitionActiveException(
				"This object definition has relationships with active object " +
					"definitions");
		}
	}

	private void _validateLabel(
			Map<Locale, String> labelMap, Locale defaultLocale)
		throws PortalException {

		if ((labelMap == null) ||
			Validator.isNull(labelMap.get(defaultLocale))) {

			throw new ObjectDefinitionLabelException(
				"Label is null for locale " + defaultLocale.getDisplayName());
		}
	}

	private void _validateName(
			long objectDefinitionId, long companyId, String name,
			boolean system)
		throws PortalException {

		if (Validator.isNull(name) || (!system && name.equals("C_"))) {
			throw new ObjectDefinitionNameException("Name is null");
		}

		if (system && (name.startsWith("C_") || name.startsWith("c_"))) {
			throw new ObjectDefinitionNameException(
				"System object definition names must not start with \"C_\"");
		}
		else if (!system && !name.startsWith("C_")) {
			throw new ObjectDefinitionNameException(
				"Custom object definition names must start with \"C_\"");
		}

		char[] nameCharArray = name.toCharArray();

		for (int i = 0; i < nameCharArray.length; i++) {
			if (!system) {

				// Skip C_

				if ((i == 0) || (i == 1)) {
					continue;
				}
			}

			char c = nameCharArray[i];

			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ObjectDefinitionNameException(
					"Name must only contain letters and digits");
			}
		}

		if ((system && !Character.isUpperCase(nameCharArray[0])) ||
			(!system && !Character.isUpperCase(nameCharArray[2]))) {

			throw new ObjectDefinitionNameException(
				"The first character of a name must be an upper case letter");
		}

		if ((system && (nameCharArray.length > 41)) ||
			(!system && (nameCharArray.length > 43))) {

			throw new ObjectDefinitionNameException(
				"Names must be less than 41 characters");
		}

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.fetchByC_N(companyId, name);

		if ((objectDefinition != null) &&
			(objectDefinition.getObjectDefinitionId() != objectDefinitionId)) {

			throw new DuplicateObjectDefinitionException(
				"Duplicate name " + name);
		}
	}

	private void _validateObjectFieldId(
			ObjectDefinition objectDefinition, long objectFieldId)
		throws PortalException {

		if (objectFieldId <= 0) {
			return;
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldId);

		if ((objectField == null) ||
			(objectField.getObjectDefinitionId() !=
				objectDefinition.getObjectDefinitionId())) {

			throw new NoSuchObjectFieldException();
		}
	}

	private void _validatePluralLabel(
			Map<Locale, String> pluralLabelMap, Locale defaultLocale)
		throws PortalException {

		if ((pluralLabelMap == null) ||
			Validator.isNull(pluralLabelMap.get(defaultLocale))) {

			throw new ObjectDefinitionPluralLabelException(
				"Plural label is null for locale " +
					defaultLocale.getDisplayName());
		}
	}

	private void _validateScope(String scope) throws PortalException {
		if (Validator.isNull(scope)) {
			throw new ObjectDefinitionScopeException("Scope is null");
		}

		try {
			_objectScopeProviderRegistry.getObjectScopeProvider(scope);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			throw new ObjectDefinitionScopeException(
				illegalArgumentException.getMessage());
		}
	}

	private void _validateVersion(boolean system, int version)
		throws PortalException {

		if (system) {
			if (version <= 0) {
				throw new ObjectDefinitionVersionException(
					"System object definition versions must greater than 0");
			}
		}
		else {
			if (version != 0) {
				throw new ObjectDefinitionVersionException(
					"Custom object definition versions must be 0");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionLocalServiceImpl.class);

	private BundleContext _bundleContext;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ModelSearchRegistrarHelper _modelSearchRegistrarHelper;

	private ServiceTracker<ObjectDefinitionDeployer, ObjectDefinitionDeployer>
		_objectDefinitionDeployerServiceTracker;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryPersistence _objectEntryPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourceLocalService _resourceLocalService;

	private final Map
		<ObjectDefinitionDeployer, Map<Long, List<ServiceRegistration<?>>>>
			_serviceRegistrationsMaps = Collections.synchronizedMap(
				new LinkedHashMap<>());

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Reference(target = "(model.pre.filter.contributor.id=WorkflowStatus)")
	private ModelPreFilterContributor _workflowStatusModelPreFilterContributor;

}