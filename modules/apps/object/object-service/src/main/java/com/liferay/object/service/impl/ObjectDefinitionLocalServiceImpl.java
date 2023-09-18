/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.definition.tree.Edge;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.definition.util.ObjectDefinitionUtil;
import com.liferay.object.deployer.InactiveObjectDefinitionDeployer;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectDefinitionAccountEntryRestrictedException;
import com.liferay.object.exception.ObjectDefinitionAccountEntryRestrictedObjectFieldIdException;
import com.liferay.object.exception.ObjectDefinitionActiveException;
import com.liferay.object.exception.ObjectDefinitionEnableCategorizationException;
import com.liferay.object.exception.ObjectDefinitionEnableCommentsException;
import com.liferay.object.exception.ObjectDefinitionEnableObjectEntryHistoryException;
import com.liferay.object.exception.ObjectDefinitionExternalReferenceCodeException;
import com.liferay.object.exception.ObjectDefinitionLabelException;
import com.liferay.object.exception.ObjectDefinitionModifiableException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectDefinitionPanelCategoryKeyException;
import com.liferay.object.exception.ObjectDefinitionPluralLabelException;
import com.liferay.object.exception.ObjectDefinitionPortletException;
import com.liferay.object.exception.ObjectDefinitionRootObjectDefinitionIdException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectDefinitionSystemException;
import com.liferay.object.exception.ObjectDefinitionVersionException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.RequiredObjectDefinitionException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.internal.dao.db.ObjectDBManagerUtil;
import com.liferay.object.internal.deployer.InactiveObjectDefinitionDeployerImpl;
import com.liferay.object.internal.deployer.ObjectDefinitionDeployerImpl;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionLocalizationTableFactory;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.impl.ObjectDefinitionImpl;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFolderItemLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectLayoutTabLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.base.ObjectDefinitionLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectEntryPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectFolderPersistence;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
			long userId, long objectFolderId, boolean enableComments,
			boolean enableLocalization, boolean enableObjectEntryDraft,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, Map<Locale, String> pluralLabelMap,
			boolean portlet, String scope, String storageType,
			List<ObjectField> objectFields)
		throws PortalException {

		return _addObjectDefinition(
			null, userId, objectFolderId, null, null, enableComments,
			enableLocalization, enableObjectEntryDraft, labelMap, true, name,
			panelAppOrder, panelCategoryKey, null, null, pluralLabelMap,
			portlet, scope, storageType, false, null, 0,
			WorkflowConstants.STATUS_DRAFT, objectFields);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition addObjectDefinition(
			String externalReferenceCode, long userId, long objectFolderId,
			boolean modifiable, boolean system)
		throws PortalException {

		_validateExternalReferenceCode(
			true, externalReferenceCode, modifiable, StringPool.BLANK, system);

		ObjectDefinition objectDefinition = objectDefinitionPersistence.create(
			counterLocalService.increment());

		objectDefinition.setExternalReferenceCode(externalReferenceCode);

		User user = _userLocalService.getUser(userId);

		objectDefinition.setCompanyId(user.getCompanyId());
		objectDefinition.setUserId(user.getUserId());
		objectDefinition.setUserName(user.getFullName());
		objectDefinition.setObjectFolderId(
			_getObjectFolderId(user.getCompanyId(), objectFolderId));

		objectDefinition.setActive(false);
		objectDefinition.setLabel(externalReferenceCode);
		objectDefinition.setModifiable(modifiable);
		objectDefinition.setName(externalReferenceCode);
		objectDefinition.setPluralLabel(externalReferenceCode);
		objectDefinition.setScope(ObjectDefinitionConstants.SCOPE_COMPANY);
		objectDefinition.setStorageType(
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT);
		objectDefinition.setSystem(system);
		objectDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);

		if (objectDefinition.isUnmodifiableSystemObject() || !modifiable) {
			throw new ObjectDefinitionModifiableException.MustBeModifiable();
		}

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		_resourceLocalService.addResources(
			objectDefinition.getCompanyId(), 0, objectDefinition.getUserId(),
			ObjectDefinition.class.getName(),
			objectDefinition.getObjectDefinitionId(), false, true, true);

		_addSystemObjectFields(
			ObjectEntryTable.INSTANCE.getTableName(), objectDefinition,
			ObjectEntryTable.INSTANCE.objectEntryId.getName(), userId);

		return _updateTitleObjectFieldId(objectDefinition, null);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition addOrUpdateSystemObjectDefinition(
			long companyId, long objectFolderId,
			SystemObjectDefinitionManager systemObjectDefinitionManager)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.fetchByC_N(
				companyId, systemObjectDefinitionManager.getName());

		long userId = _userLocalService.getGuestUserId(companyId);

		if (objectDefinition == null) {
			Table table = systemObjectDefinitionManager.getTable();
			Column<?, Long> primaryKeyColumn =
				systemObjectDefinitionManager.getPrimaryKeyColumn();

			objectDefinition = addSystemObjectDefinition(
				systemObjectDefinitionManager.getExternalReferenceCode(),
				userId, objectFolderId,
				systemObjectDefinitionManager.getModelClassName(),
				table.getTableName(), false,
				systemObjectDefinitionManager.getLabelMap(), false,
				systemObjectDefinitionManager.getName(), null, null,
				primaryKeyColumn.getName(), primaryKeyColumn.getName(),
				systemObjectDefinitionManager.getPluralLabelMap(),
				systemObjectDefinitionManager.getScope(),
				systemObjectDefinitionManager.getTitleObjectFieldName(),
				systemObjectDefinitionManager.getVersion(),
				WorkflowConstants.STATUS_APPROVED,
				systemObjectDefinitionManager.getObjectFields());

			_addOrUpdateObjectActions(
				userId, objectDefinition.getObjectDefinitionId(),
				systemObjectDefinitionManager.getObjectActions());

			return objectDefinition;
		}

		objectDefinition.setObjectFolderId(objectFolderId);
		objectDefinition.setVersion(systemObjectDefinitionManager.getVersion());

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		List<ObjectField> newObjectFields =
			systemObjectDefinitionManager.getObjectFields();

		List<ObjectField> oldObjectFields =
			_objectFieldPersistence.findByODI_DTN(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getDBTableName());

		for (ObjectField oldObjectField : oldObjectFields) {
			if (oldObjectField.isSystem() &&
				!ObjectFieldUtil.isMetadata(oldObjectField.getName()) &&
				!_hasObjectField(newObjectFields, oldObjectField)) {

				_objectFieldPersistence.remove(oldObjectField);
			}
		}

		for (ObjectField newObjectField : newObjectFields) {
			ObjectField oldObjectField = _objectFieldPersistence.fetchByODI_N(
				objectDefinition.getObjectDefinitionId(),
				newObjectField.getName());

			if (oldObjectField == null) {
				_objectFieldLocalService.addSystemObjectField(
					newObjectField.getExternalReferenceCode(), userId,
					newObjectField.getListTypeDefinitionId(),
					objectDefinition.getObjectDefinitionId(),
					newObjectField.getBusinessType(),
					newObjectField.getDBColumnName(),
					objectDefinition.getDBTableName(),
					newObjectField.getDBType(), false, false, "",
					newObjectField.getLabelMap(), newObjectField.getName(),
					newObjectField.isRequired(), newObjectField.isState(),
					newObjectField.getObjectFieldSettings());
			}
			else {
				if (!Objects.equals(
						oldObjectField.getDBType(),
						newObjectField.getDBType()) ||
					!Objects.equals(
						oldObjectField.isRequired(),
						newObjectField.isRequired())) {

					oldObjectField.setBusinessType(
						newObjectField.getBusinessType());
					oldObjectField.setDBType(newObjectField.getDBType());
					oldObjectField.setRequired(newObjectField.isRequired());

					_objectFieldPersistence.update(oldObjectField);
				}
			}
		}

		_addOrUpdateObjectActions(
			userId, objectDefinition.getObjectDefinitionId(),
			systemObjectDefinitionManager.getObjectActions());

		return objectDefinition;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition addSystemObjectDefinition(
			String externalReferenceCode, long userId, long objectFolderId,
			String className, String dbTableName, boolean enableComments,
			Map<Locale, String> labelMap, boolean modifiable, String name,
			String panelAppOrder, String panelCategoryKey,
			String pkObjectFieldDBColumnName, String pkObjectFieldName,
			Map<Locale, String> pluralLabelMap, String scope,
			String titleObjectFieldName, int version, int status,
			List<ObjectField> objectFields)
		throws PortalException {

		return _addObjectDefinition(
			externalReferenceCode, userId, objectFolderId, className,
			dbTableName, enableComments, false, false, labelMap, modifiable,
			name, panelAppOrder, panelCategoryKey, pkObjectFieldDBColumnName,
			pkObjectFieldName, pluralLabelMap, false, scope,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, true,
			titleObjectFieldName, version, status, objectFields);
	}

	@Override
	public void bindObjectDefinitions(long[] objectRelationshipIds)
		throws PortalException {

		ObjectRelationship rootObjectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipIds[objectRelationshipIds.length - 1]);

		long rootObjectDefinitionId =
			rootObjectRelationship.getObjectDefinitionId1();

		ObjectDefinition rootObjectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				rootObjectDefinitionId);

		if (rootObjectDefinition.getRootObjectDefinitionId() == 0) {
			objectDefinitionLocalService.updateRootObjectDefinitionId(
				rootObjectDefinitionId, rootObjectDefinitionId);
		}

		for (long objectRelationshipId : objectRelationshipIds) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					objectRelationshipId);

			if (objectRelationship.isEdge()) {
				continue;
			}

			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(),
				objectRelationship.getParameterObjectFieldId(),
				objectRelationship.getDeletionType(), true,
				objectRelationship.getLabelMap());

			ObjectDefinition objectDefinition1 =
				objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			if (objectDefinition1.getRootObjectDefinitionId() == 0) {
				objectDefinitionLocalService.updateRootObjectDefinitionId(
					objectDefinition1.getObjectDefinitionId(),
					rootObjectDefinitionId);
			}

			ObjectDefinition objectDefinition2 =
				objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId2());

			objectDefinitionLocalService.updateRootObjectDefinitionId(
				objectDefinition2.getObjectDefinitionId(),
				rootObjectDefinitionId);
		}
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

		if (!PortalInstances.isCurrentCompanyInDeletionProcess() &&
			!PortalRunMode.isTestMode() &&
			objectDefinition.isUnmodifiableSystemObject()) {

			throw new RequiredObjectDefinitionException();
		}

		if (objectDefinition.getRootObjectDefinitionId() != 0) {
			throw new ObjectDefinitionRootObjectDefinitionIdException(
				"Object definitions that belong to a hierarchical structure " +
					"cannot be deleted");
		}

		if (objectDefinition.isSystem() &&
			!ObjectDefinitionUtil.isInvokerBundleAllowed()) {

			throw new ObjectDefinitionSystemException(
				"Only allowed bundles can delete system object definitions");
		}

		_objectActionLocalService.deleteObjectActions(
			objectDefinition.getObjectDefinitionId());

		if (!objectDefinition.isUnmodifiableSystemObject()) {
			List<ObjectEntry> objectEntries =
				_objectEntryPersistence.findByObjectDefinitionId(
					objectDefinition.getObjectDefinitionId());

			for (ObjectEntry objectEntry : objectEntries) {
				_objectEntryLocalService.deleteObjectEntry(objectEntry);
			}
		}

		for (ObjectRelationship objectRelationship :
				_objectRelationshipPersistence.findByODI1_R(
					objectDefinition.getObjectDefinitionId(), false)) {

			_objectRelationshipLocalService.deleteObjectRelationship(
				objectRelationship);
		}

		for (ObjectRelationship objectRelationship :
				_objectRelationshipPersistence.findByODI2_R(
					objectDefinition.getObjectDefinitionId(), false)) {

			_objectRelationshipLocalService.deleteObjectRelationship(
				objectRelationship);
		}

		_objectFieldLocalService.deleteObjectFieldByObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());

		_objectFolderItemLocalService.
			deleteObjectFolderItemByObjectDefinitionId(
				objectDefinition.getObjectDefinitionId());

		_objectLayoutLocalService.deleteObjectLayouts(
			objectDefinition.getObjectDefinitionId());

		_objectValidationRuleLocalService.deleteObjectValidationRules(
			objectDefinition.getObjectDefinitionId());

		_objectViewLocalService.deleteObjectViews(
			objectDefinition.getObjectDefinitionId());

		objectDefinitionPersistence.remove(objectDefinition);

		_resourceLocalService.deleteResource(
			objectDefinition.getCompanyId(), ObjectDefinition.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			objectDefinition.getObjectDefinitionId());

		if (objectDefinition.isUnmodifiableSystemObject()) {
			_dropTable(objectDefinition.getExtensionDBTableName());
		}
		else if (objectDefinition.isApproved()) {
			try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
					objectDefinition.getCompanyId())) {

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
			}

			_dropTable(objectDefinition.getDBTableName());
			_dropTable(objectDefinition.getExtensionDBTableName());

			if (objectDefinition.isEnableLocalization()) {
				_dropTable(objectDefinition.getLocalizationDBTableName());
			}

			undeployObjectDefinition(objectDefinition);

			_registerTransactionCallbackForCluster(
				_undeployObjectDefinitionMethodKey, objectDefinition);
		}

		return objectDefinition;
	}

	@Override
	public void deployInactiveObjectDefinition(
		ObjectDefinition objectDefinition) {

		undeployObjectDefinition(objectDefinition);

		for (Map.Entry
				<InactiveObjectDefinitionDeployer,
				 Map<Long, List<ServiceRegistration<?>>>> entry :
					_inactiveObjectDefinitionsServiceRegistrationsMaps.
						entrySet()) {

			InactiveObjectDefinitionDeployer inactiveObjectDefinitionDeployer =
				entry.getKey();
			Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap =
				entry.getValue();

			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setWithSafeCloseable(
						objectDefinition.getCompanyId())) {

				serviceRegistrationsMap.computeIfAbsent(
					objectDefinition.getObjectDefinitionId(),
					objectDefinitionId ->
						inactiveObjectDefinitionDeployer.deploy(
							objectDefinition));
			}
		}
	}

	@Override
	public void deployObjectDefinition(ObjectDefinition objectDefinition) {
		undeployObjectDefinition(objectDefinition);

		for (Map.Entry
				<ObjectDefinitionDeployer,
				 Map<Long, List<ServiceRegistration<?>>>> entry :
					_serviceRegistrationsMaps.entrySet()) {

			ObjectDefinitionDeployer objectDefinitionDeployer = entry.getKey();
			Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap =
				entry.getValue();

			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setWithSafeCloseable(
						objectDefinition.getCompanyId())) {

				serviceRegistrationsMap.computeIfAbsent(
					objectDefinition.getObjectDefinitionId(),
					objectDefinitionId -> objectDefinitionDeployer.deploy(
						objectDefinition));
			}
		}
	}

	@Override
	public ObjectDefinition enableAccountEntryRestricted(
			ObjectRelationship objectRelationship)
		throws PortalException {

		ObjectDefinition objectDefinition1 = getObjectDefinition(
			objectRelationship.getObjectDefinitionId1());

		if (!Objects.equals(objectDefinition1.getShortName(), "AccountEntry")) {
			throw new ObjectDefinitionAccountEntryRestrictedException(
				"Custom object definitions can only be restricted by account " +
					"entry");
		}

		ObjectDefinition objectDefinition2 = getObjectDefinition(
			objectRelationship.getObjectDefinitionId2());

		if (objectDefinition2.isAccountEntryRestricted()) {
			return objectDefinition2;
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		objectDefinition2.setAccountEntryRestrictedObjectFieldId(
			objectField.getObjectFieldId());

		objectDefinition2.setAccountEntryRestricted(true);

		return objectDefinitionPersistence.update(objectDefinition2);
	}

	@Override
	public ObjectDefinition
			enableAccountEntryRestrictedForNondefaultStorageType(
				ObjectField objectField)
		throws PortalException {

		if (!objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_INTEGER) &&
			!objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER) &&
			!objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT)) {

			throw new ObjectDefinitionAccountEntryRestrictedException(
				"Custom object definitions can only be restricted by an " +
					"integer, long integer, or text field");
		}

		ObjectDefinition objectDefinition = getObjectDefinition(
			objectField.getObjectDefinitionId());

		if (objectDefinition.isDefaultStorageType()) {
			throw new UnsupportedOperationException();
		}

		objectDefinition.setAccountEntryRestricted(true);
		objectDefinition.setAccountEntryRestrictedObjectFieldId(
			objectField.getObjectFieldId());

		return objectDefinitionPersistence.update(objectDefinition);
	}

	@Override
	public ObjectDefinition fetchObjectDefinition(long companyId, String name) {
		return objectDefinitionPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public ObjectDefinition fetchObjectDefinitionByClassName(
		long companyId, String className) {

		return objectDefinitionPersistence.fetchByC_C(companyId, className);
	}

	public ObjectDefinition fetchSystemObjectDefinition(String name) {
		for (ObjectDefinition systemObjectDefinition :
				getSystemObjectDefinitions()) {

			if (Objects.equals(systemObjectDefinition.getName(), name)) {
				return systemObjectDefinition;
			}
		}

		return null;
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
	public List<ObjectDefinition> getObjectDefinitions(
		long companyId, boolean active, int status) {

		return objectDefinitionPersistence.findByC_A_S(
			companyId, active, status);
	}

	@Override
	public List<ObjectDefinition> getObjectDefinitions(
		long companyId, int status) {

		return objectDefinitionPersistence.findByC_S(companyId, status);
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws PortalException {

		return objectDefinitionPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<ObjectDefinition> getObjectFolderObjectDefinitions(
		long objectFolderId) {

		return objectDefinitionPersistence.findByObjectFolderId(objectFolderId);
	}

	@Override
	public List<ObjectDefinition> getSystemObjectDefinitions() {
		return objectDefinitionPersistence.findBySystem(true);
	}

	@Override
	public boolean hasObjectRelationship(long objectDefinitionId) {
		int countByObjectDefinitionId1 =
			_objectRelationshipPersistence.countByObjectDefinitionId1(
				objectDefinitionId);
		int countByObjectDefinitionId2 =
			_objectRelationshipPersistence.countByObjectDefinitionId2(
				objectDefinitionId);

		if ((countByObjectDefinitionId1 > 0) ||
			(countByObjectDefinitionId2 > 0)) {

			return true;
		}

		return false;
	}

	@Override
	public ObjectDefinition publishCustomObjectDefinition(
			long userId, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (objectDefinition.isUnmodifiableSystemObject()) {
			throw new ObjectDefinitionStatusException();
		}

		if (objectDefinition.getRootObjectDefinitionId() == 0) {
			return _publishObjectDefinition(userId, objectDefinition);
		}

		if (objectDefinition.isRootDescendantNode()) {
			throw new ObjectDefinitionStatusException(
				"Nonroot object definitions within a hierarchical structure " +
					"are ineligible for publication");
		}

		Tree tree = _treeFactory.create(objectDefinitionId);

		Iterator<Node> iterator = tree.iterator();

		while (iterator.hasNext()) {
			Node node = iterator.next();

			_publishObjectDefinition(
				userId, getObjectDefinition(node.getObjectDefinitionId()));
		}

		return getObjectDefinition(objectDefinitionId);
	}

	@Override
	public ObjectDefinition publishSystemObjectDefinition(
			long userId, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return _publishObjectDefinition(userId, objectDefinition);
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		Map<Long, List<ServiceRegistration<?>>> activeServiceRegistrationsMap =
			new ConcurrentHashMap<>();
		InactiveObjectDefinitionDeployer inactiveObjectDefinitionDeployer =
			new InactiveObjectDefinitionDeployerImpl(
				_bundleContext, _objectEntryService, _objectFieldLocalService,
				_objectRelationshipLocalService);
		Map<Long, List<ServiceRegistration<?>>>
			inactiveServiceRegistrationsMap = new ConcurrentHashMap<>();
		ObjectDefinitionDeployer objectDefinitionDeployer =
			new ObjectDefinitionDeployerImpl(
				_accountEntryLocalService,
				_accountEntryOrganizationRelLocalService,
				_assetEntryLocalService, _bundleContext,
				_dynamicQueryBatchIndexingActionableFactory, _groupLocalService,
				_listTypeLocalService, _modelSearchRegistrarHelper,
				_objectActionLocalService, this, _objectEntryLocalService,
				_objectEntryService, _objectFieldLocalService,
				_objectLayoutLocalService, _objectLayoutTabLocalService,
				_objectRelationshipLocalService, _objectScopeProviderRegistry,
				_objectViewLocalService, _organizationLocalService,
				_persistedModelLocalServiceRegistry, _ploEntryLocalService,
				_portal, _portletLocalService, _resourceActions, _treeFactory,
				_userLocalService, _resourcePermissionLocalService,
				_workflowStatusModelPreFilterContributor,
				_userGroupRoleLocalService);

		_companyLocalService.forEachCompanyId(
			companyId -> {
				for (ObjectDefinition objectDefinition :
						objectDefinitionLocalService.getObjectDefinitions(
							companyId, WorkflowConstants.STATUS_APPROVED)) {

					if (objectDefinition.isActive()) {
						activeServiceRegistrationsMap.put(
							objectDefinition.getObjectDefinitionId(),
							objectDefinitionDeployer.deploy(objectDefinition));
					}
					else {
						inactiveServiceRegistrationsMap.put(
							objectDefinition.getObjectDefinitionId(),
							inactiveObjectDefinitionDeployer.deploy(
								objectDefinition));
					}
				}
			});

		_inactiveObjectDefinitionsServiceRegistrationsMaps.put(
			inactiveObjectDefinitionDeployer, inactiveServiceRegistrationsMap);
		_serviceRegistrationsMaps.put(
			objectDefinitionDeployer, activeServiceRegistrationsMap);

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

					_companyLocalService.forEachCompanyId(
						companyId -> {
							for (ObjectDefinition objectDefinition :
									objectDefinitionLocalService.
										getObjectDefinitions(
											companyId,
											WorkflowConstants.
												STATUS_APPROVED)) {

								if (objectDefinition.isActive()) {
									objectDefinitionDeployer.undeploy(
										objectDefinition);
								}
							}
						});

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

		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				_objectDefinitionDeployerServiceTracker.open();

				return null;
			});
	}

	@Override
	public void unbindObjectDefinition(long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		Tree tree = _treeFactory.create(
			objectDefinition.getRootObjectDefinitionId());

		Iterator<Node> iterator = tree.iterator(
			objectDefinition.getObjectDefinitionId());

		while (iterator.hasNext()) {
			Node node = iterator.next();

			objectDefinitionLocalService.updateRootObjectDefinitionId(
				node.getObjectDefinitionId(), 0);

			if (node.isRoot()) {
				continue;
			}

			Edge edge = node.getEdge();

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					edge.getObjectRelationshipId());

			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(),
				objectRelationship.getParameterObjectFieldId(),
				objectRelationship.getDeletionType(), false,
				objectRelationship.getLabelMap());
		}
	}

	@Override
	public void undeployObjectDefinition(ObjectDefinition objectDefinition) {
		if (objectDefinition.isUnmodifiableSystemObject()) {
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

		_invalidatePortalCache(objectDefinition);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition updateCustomObjectDefinition(
			String externalReferenceCode, long objectDefinitionId,
			long accountEntryRestrictedObjectFieldId,
			long descriptionObjectFieldId, long objectFolderId,
			long titleObjectFieldId, boolean accountEntryRestricted,
			boolean active, boolean enableCategorization,
			boolean enableComments, boolean enableLocalization,
			boolean enableObjectEntryDraft, boolean enableObjectEntryHistory,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, boolean portlet,
			Map<Locale, String> pluralLabelMap, String scope)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (objectDefinition.isUnmodifiableSystemObject()) {
			throw new ObjectDefinitionStatusException(
				"Object definition " + objectDefinition);
		}

		if (objectDefinition.isRootDescendantNode()) {
			String errorMessage =
				"cannot be changed when the object definition is a root " +
					"descendant node";

			if (!Objects.equals(
					objectDefinition.getAccountEntryRestrictedObjectFieldId(),
					accountEntryRestrictedObjectFieldId)) {

				throw new ObjectDefinitionAccountEntryRestrictedObjectFieldIdException(
					"Account entry restriction object field ID " +
						errorMessage);
			}
			else if (!Objects.equals(
						objectDefinition.isAccountEntryRestricted(),
						accountEntryRestricted)) {

				throw new ObjectDefinitionAccountEntryRestrictedException(
					"Account entry restriction " + errorMessage);
			}
			else if (!Objects.equals(
						objectDefinition.getPanelCategoryKey(),
						panelCategoryKey)) {

				throw new ObjectDefinitionPanelCategoryKeyException(
					"Panel category key " + errorMessage);
			}
			else if (!Objects.equals(objectDefinition.isPortlet(), portlet)) {
				throw new ObjectDefinitionPortletException(
					"Portlet " + errorMessage);
			}
			else if (!Objects.equals(objectDefinition.getScope(), scope)) {
				throw new ObjectDefinitionScopeException(
					"Scope " + errorMessage);
			}
		}

		return _updateObjectDefinition(
			externalReferenceCode, objectDefinition,
			accountEntryRestrictedObjectFieldId, descriptionObjectFieldId,
			objectFolderId, titleObjectFieldId, accountEntryRestricted, active,
			null, enableCategorization, enableComments, enableLocalization,
			enableObjectEntryDraft, enableObjectEntryHistory, labelMap, name,
			panelAppOrder, panelCategoryKey, portlet, null, null,
			pluralLabelMap, scope);
	}

	@Override
	public ObjectDefinition updateExternalReferenceCode(
			long objectDefinitionId, String externalReferenceCode)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		_validateExternalReferenceCode(
			false, externalReferenceCode, objectDefinition.isModifiable(),
			objectDefinition.getName(), objectDefinition.isSystem());

		objectDefinition.setExternalReferenceCode(externalReferenceCode);

		return objectDefinitionPersistence.update(objectDefinition);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition updateObjectFolderId(
			long objectDefinitionId, long objectFolderId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		long oldObjectFolderId = objectDefinition.getObjectFolderId();

		objectDefinition.setObjectFolderId(
			_getObjectFolderId(
				objectDefinition.getCompanyId(), objectFolderId));

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		_objectFolderItemLocalService.updateObjectFolderObjectFolderItem(
			objectDefinitionId, objectDefinition.getObjectFolderId(),
			oldObjectFolderId);

		return objectDefinition;
	}

	@Override
	public ObjectDefinition updateRootObjectDefinitionId(
			long objectDefinitionId, long rootObjectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (rootObjectDefinitionId == 0) {
			objectDefinition.setRootObjectDefinitionId(0);

			return objectDefinitionPersistence.update(objectDefinition);
		}

		ObjectDefinition rootObjectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(
				rootObjectDefinitionId);

		if ((objectDefinitionId != rootObjectDefinitionId) &&
			(rootObjectDefinition.getRootObjectDefinitionId() == 0)) {

			throw new ObjectDefinitionRootObjectDefinitionIdException(
				"Object definition " + rootObjectDefinitionId +
					" is not a root object definition");
		}

		objectDefinition.setRootObjectDefinitionId(rootObjectDefinitionId);

		return objectDefinitionPersistence.update(objectDefinition);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition updateSystemObjectDefinition(
			String externalReferenceCode, long objectDefinitionId,
			long objectFolderId, long titleObjectFieldId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId);

		_validateExternalReferenceCode(
			false, externalReferenceCode, objectDefinition.isModifiable(),
			objectDefinition.getName(), objectDefinition.isSystem());
		_validateObjectFieldId(objectDefinition, titleObjectFieldId);

		long oldObjectFolderId = objectDefinition.getObjectFolderId();

		objectDefinition.setExternalReferenceCode(externalReferenceCode);
		objectDefinition.setObjectFolderId(
			_getObjectFolderId(
				objectDefinition.getCompanyId(), objectFolderId));
		objectDefinition.setTitleObjectFieldId(titleObjectFieldId);

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		_objectFolderItemLocalService.updateObjectFolderObjectFolderItem(
			objectDefinitionId, objectDefinition.getObjectFolderId(),
			oldObjectFolderId);

		return objectDefinition;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectDefinition updateTitleObjectFieldId(
			long objectDefinitionId, long titleObjectFieldId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId);

		_validateObjectFieldId(objectDefinition, titleObjectFieldId);

		objectDefinition.setTitleObjectFieldId(titleObjectFieldId);

		return objectDefinitionPersistence.update(objectDefinition);
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

		_companyLocalService.forEachCompanyId(
			companyId -> {
				for (ObjectDefinition objectDefinition :
						objectDefinitionLocalService.getObjectDefinitions(
							companyId, WorkflowConstants.STATUS_APPROVED)) {

					if (objectDefinition.isActive()) {
						serviceRegistrationsMap.put(
							objectDefinition.getObjectDefinitionId(),
							objectDefinitionDeployer.deploy(objectDefinition));
					}
				}
			});

		_serviceRegistrationsMaps.put(
			objectDefinitionDeployer, serviceRegistrationsMap);

		return objectDefinitionDeployer;
	}

	private ObjectDefinition _addObjectDefinition(
			String externalReferenceCode, long userId, long objectFolderId,
			String className, String dbTableName, boolean enableComments,
			boolean enableLocalization, boolean enableObjectEntryDraft,
			Map<Locale, String> labelMap, boolean modifiable, String name,
			String panelAppOrder, String panelCategoryKey,
			String pkObjectFieldDBColumnName, String pkObjectFieldName,
			Map<Locale, String> pluralLabelMap, boolean portlet, String scope,
			String storageType, boolean system, String titleObjectFieldName,
			int version, int status, List<ObjectField> objectFields)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		name = _getName(name, system);

		String shortName = ObjectDefinitionImpl.getShortName(name);

		dbTableName = _getDBTableName(
			dbTableName, modifiable, name, system, user.getCompanyId(),
			shortName);

		pkObjectFieldName = _getPKObjectFieldName(
			pkObjectFieldName, modifiable, system, shortName);

		pkObjectFieldDBColumnName = _getPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName, pkObjectFieldName, modifiable, system);

		storageType = Validator.isNotNull(storageType) ? storageType :
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT;

		_validateExternalReferenceCode(
			true, externalReferenceCode, modifiable, name, system);
		_validateEnableComments(
			enableComments, modifiable, storageType, system);
		_validateLabel(labelMap);
		_validateName(0, user.getCompanyId(), modifiable, name, system);
		_validatePluralLabel(pluralLabelMap);
		_validateScope(scope, storageType);
		_validateVersion(system, version);

		ObjectDefinition objectDefinition = objectDefinitionPersistence.create(
			counterLocalService.increment());

		objectDefinition.setExternalReferenceCode(externalReferenceCode);
		objectDefinition.setCompanyId(user.getCompanyId());
		objectDefinition.setUserId(user.getUserId());
		objectDefinition.setUserName(user.getFullName());
		objectDefinition.setObjectFolderId(
			_getObjectFolderId(user.getCompanyId(), objectFolderId));
		objectDefinition.setActive(
			_isUnmodifiableSystemObject(modifiable, system));
		objectDefinition.setDBTableName(dbTableName);
		objectDefinition.setClassName(
			_getClassName(
				objectDefinition.getObjectDefinitionId(), className, modifiable,
				system));
		objectDefinition.setEnableCategorization(
			!objectDefinition.isUnmodifiableSystemObject() &&
			StringUtil.equals(
				storageType, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT));
		objectDefinition.setEnableComments(enableComments);
		objectDefinition.setEnableLocalization(enableLocalization);
		objectDefinition.setEnableObjectEntryDraft(enableObjectEntryDraft);
		objectDefinition.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectDefinition.setModifiable(modifiable);
		objectDefinition.setName(name);
		objectDefinition.setPanelAppOrder(panelAppOrder);
		objectDefinition.setPanelCategoryKey(panelCategoryKey);
		objectDefinition.setPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName);
		objectDefinition.setPKObjectFieldName(pkObjectFieldName);
		objectDefinition.setPluralLabelMap(pluralLabelMap);
		objectDefinition.setPortlet(portlet);
		objectDefinition.setScope(scope);
		objectDefinition.setStorageType(
			Validator.isNotNull(storageType) ? storageType :
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT);
		objectDefinition.setSystem(system);
		objectDefinition.setVersion(version);
		objectDefinition.setStatus(status);

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		_resourceLocalService.addResources(
			objectDefinition.getCompanyId(), 0, objectDefinition.getUserId(),
			ObjectDefinition.class.getName(),
			objectDefinition.getObjectDefinitionId(), false, true, true);

		if (objectDefinition.isModifiable() ||
			!objectDefinition.isUnmodifiableSystemObject()) {

			dbTableName = "ObjectEntry";
		}

		_addSystemObjectFields(
			dbTableName, objectDefinition, pkObjectFieldName, userId);

		if (objectFields != null) {
			for (ObjectField objectField : objectFields) {
				if (objectDefinition.isUnmodifiableSystemObject() ||
					objectField.isSystem()) {

					_objectFieldLocalService.addOrUpdateSystemObjectField(
						objectField.getExternalReferenceCode(), userId,
						objectField.getListTypeDefinitionId(),
						objectDefinition.getObjectDefinitionId(),
						objectField.getBusinessType(),
						objectField.getDBColumnName(),
						objectDefinition.getDBTableName(),
						objectField.getDBType(), objectField.isIndexed(),
						objectField.isIndexedAsKeyword(),
						objectField.getIndexedLanguageId(),
						objectField.getLabelMap(), objectField.getName(),
						objectField.isRequired(), objectField.isState(),
						objectField.getObjectFieldSettings());
				}
				else {
					_objectFieldLocalService.addCustomObjectField(
						objectField.getExternalReferenceCode(), userId,
						objectField.getListTypeDefinitionId(),
						objectDefinition.getObjectDefinitionId(),
						objectField.getBusinessType(), objectField.getDBType(),
						objectField.isIndexed(),
						objectField.isIndexedAsKeyword(),
						objectField.getIndexedLanguageId(),
						objectField.getLabelMap(), objectField.isLocalized(),
						objectField.getName(), objectField.getReadOnly(),
						objectField.getReadOnlyConditionExpression(),
						objectField.isRequired(), objectField.isState(),
						objectField.getObjectFieldSettings());
				}
			}
		}

		_objectFolderItemLocalService.addObjectFolderItem(
			userId, objectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectFolderId(), 0, 0);

		objectDefinition = _updateTitleObjectFieldId(
			objectDefinition, titleObjectFieldName);

		if (objectDefinition.isUnmodifiableSystemObject()) {
			_createTable(
				objectDefinition.getExtensionDBTableName(), objectDefinition);
		}

		return objectDefinition;
	}

	private void _addOrUpdateObjectActions(
			long userId, long objectDefinitionId,
			List<ObjectAction> objectActions)
		throws PortalException {

		for (ObjectAction objectAction : objectActions) {
			_objectActionLocalService.addOrUpdateObjectAction(
				objectAction.getExternalReferenceCode(), 0, userId,
				objectDefinitionId, objectAction.getActive(),
				objectAction.getConditionExpression(),
				objectAction.getDescription(),
				objectAction.getErrorMessageMap(), objectAction.getLabelMap(),
				objectAction.getName(),
				objectAction.getObjectActionExecutorKey(),
				objectAction.getObjectActionTriggerKey(),
				objectAction.getParametersUnicodeProperties());
		}
	}

	private void _addSystemObjectFields(
			String dbTableName, ObjectDefinition objectDefinition,
			String pkObjectFieldName, long userId)
		throws PortalException {

		_objectFieldLocalService.addSystemObjectField(
			null, userId, 0, objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectEntryTable.INSTANCE.userName.getName(), dbTableName,
			ObjectFieldConstants.DB_TYPE_STRING, false, false, null,
			LocalizedMapUtil.getLocalizedMap(
				_language.get(LocaleUtil.getDefault(), "author")),
			"creator", false, false, null);

		_objectFieldLocalService.addSystemObjectField(
			null, userId, 0, objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectEntryTable.INSTANCE.createDate.getName(), dbTableName,
			ObjectFieldConstants.DB_TYPE_DATE, false, false, null,
			LocalizedMapUtil.getLocalizedMap(
				_language.get(LocaleUtil.getDefault(), "create-date")),
			"createDate", false, false, null);

		_objectFieldLocalService.addSystemObjectField(
			null, userId, 0, objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectEntryTable.INSTANCE.externalReferenceCode.getName(),
			dbTableName, ObjectFieldConstants.DB_TYPE_STRING, false, false,
			null,
			LocalizedMapUtil.getLocalizedMap(
				_language.get(
					LocaleUtil.getDefault(), "external-reference-code")),
			"externalReferenceCode", false, false, null);

		String dbColumnName = ObjectEntryTable.INSTANCE.objectEntryId.getName();

		if (objectDefinition.isUnmodifiableSystemObject()) {
			dbColumnName = pkObjectFieldName;
		}

		_objectFieldLocalService.addSystemObjectField(
			null, userId, 0, objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, dbColumnName,
			dbTableName, ObjectFieldConstants.DB_TYPE_LONG, true, true, null,
			LocalizedMapUtil.getLocalizedMap(
				_language.get(LocaleUtil.getDefault(), "id")),
			"id", false, false, null);

		_objectFieldLocalService.addSystemObjectField(
			null, userId, 0, objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectEntryTable.INSTANCE.modifiedDate.getName(), dbTableName,
			ObjectFieldConstants.DB_TYPE_DATE, false, false, null,
			LocalizedMapUtil.getLocalizedMap(
				_language.get(LocaleUtil.getDefault(), "modified-date")),
			"modifiedDate", false, false, null);

		_objectFieldLocalService.addSystemObjectField(
			null, userId, 0, objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectEntryTable.INSTANCE.status.getName(), dbTableName,
			ObjectFieldConstants.DB_TYPE_INTEGER, false, false, null,
			LocalizedMapUtil.getLocalizedMap(
				_language.get(LocaleUtil.getDefault(), "status")),
			"status", false, false, null);
	}

	private void _createLocalizationTable(ObjectDefinition objectDefinition) {
		DynamicObjectDefinitionLocalizationTable
			dynamicObjectDefinitionLocalizedTable =
				DynamicObjectDefinitionLocalizationTableFactory.create(
					objectDefinition, _objectFieldLocalService);

		if (dynamicObjectDefinitionLocalizedTable == null) {
			return;
		}

		runSQL(dynamicObjectDefinitionLocalizedTable.getCreateTableSQL());
	}

	private void _createTable(
			String dbTableName, ObjectDefinition objectDefinition)
		throws PortalException {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(), dbTableName);

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			new DynamicObjectDefinitionTable(
				objectDefinition, objectFields, dbTableName);

		runSQL(dynamicObjectDefinitionTable.getCreateTableSQL());

		for (ObjectField objectField : objectFields) {
			boolean indexable = false;
			boolean unique = false;

			if (StringUtil.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

				indexable = true;
			}
			else if (GetterUtil.getBoolean(
						ObjectFieldSettingUtil.getValue(
							ObjectFieldSettingConstants.NAME_UNIQUE_VALUES,
							objectField))) {

				indexable = true;
				unique = true;
			}

			if (!indexable) {
				continue;
			}

			ObjectDBManagerUtil.createIndexMetadata(
				objectField.getDBColumnName(),
				_currentConnection.getConnection(
					objectDefinitionPersistence.getDataSource()),
				dbTableName, unique);
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
		long objectDefinitionId, String className, boolean modifiable,
		boolean system) {

		if (_isUnmodifiableSystemObject(modifiable, system)) {
			return className;
		}

		return ObjectDefinition.class.getName() + "#" + objectDefinitionId;
	}

	private String _getDBTableName(
		String dbTableName, boolean modifiable, String name, boolean system,
		Long companyId, String shortName) {

		if (Validator.isNotNull(dbTableName)) {
			return dbTableName;
		}

		if (_isUnmodifiableSystemObject(modifiable, system)) {
			return name;
		}

		// See DBInspector.java#isObjectTable

		String prefix = "O_";

		if (modifiable && system) {
			prefix =
				ObjectDefinitionConstants.
					EXTERNAL_REFERENCE_CODE_PREFIX_SYSTEM_OBJECT_DEFINITION;
		}

		return StringBundler.concat(
			prefix, companyId, StringPool.UNDERLINE, shortName);
	}

	private String _getName(String name, boolean system) {
		name = StringUtil.trim(name);

		if (!system) {
			name = "C_" + name;
		}

		return name;
	}

	private long _getObjectFolderId(long companyId, long objectFolderId)
		throws PortalException {

		if (objectFolderId == 0) {
			ObjectFolder objectFolder =
				_objectFolderLocalService.addOrGetUncategorizedObjectFolder(
					companyId);

			return objectFolder.getObjectFolderId();
		}

		_objectFolderPersistence.findByPrimaryKey(objectFolderId);

		return objectFolderId;
	}

	private String _getPKObjectFieldDBColumnName(
		String pkObjectFieldDBColumnName, String pkObjectFieldName,
		boolean modifiable, boolean system) {

		if (Validator.isNotNull(pkObjectFieldDBColumnName)) {
			return pkObjectFieldDBColumnName;
		}

		if (_isUnmodifiableSystemObject(modifiable, system)) {
			return pkObjectFieldName;
		}

		return pkObjectFieldName + StringPool.UNDERLINE;
	}

	private String _getPKObjectFieldName(
		String pkObjectFieldName, boolean modifiable, boolean system,
		String shortName) {

		if (Validator.isNotNull(pkObjectFieldName)) {
			return pkObjectFieldName;
		}

		pkObjectFieldName = TextFormatter.format(
			shortName + "Id", TextFormatter.I);

		if (_isUnmodifiableSystemObject(modifiable, system)) {
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

	private void _invalidatePortalCache(ObjectDefinition objectDefinition) {
		PortalCache<String, String> portalCache =
			(PortalCache<String, String>)_multiVMPool.getPortalCache(
				FragmentEntryLink.class.getName());

		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.getLayoutClassedModelUsages(
				objectDefinition.getCompanyId(),
				_classNameLocalService.getClassNameId(
					objectDefinition.getClassName()),
				_portal.getClassNameId(FragmentEntryLink.class));

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			Set<Locale> availableLocales = _language.getAvailableLocales(
				layoutClassedModelUsage.getGroupId());

			for (Locale locale : availableLocales) {
				portalCache.remove(
					StringBundler.concat(
						layoutClassedModelUsage.getContainerKey(),
						StringPool.DASH, locale, StringPool.DASH, 0));
			}
		}
	}

	private boolean _isUnmodifiableSystemObject(
		boolean modifiable, boolean system) {

		if (FeatureFlagManagerUtil.isEnabled("LPS-167253")) {
			if (!modifiable && system) {
				return true;
			}

			return false;
		}

		return system;
	}

	private ObjectDefinition _publishObjectDefinition(
			long userId, ObjectDefinition objectDefinition)
		throws PortalException {

		if (!ListUtil.exists(
				_objectFieldPersistence.findByObjectDefinitionId(
					objectDefinition.getObjectDefinitionId()),
				objectField -> !objectField.isMetadata())) {

			throw new RequiredObjectFieldException();
		}

		objectDefinition.setActive(true);
		objectDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		_createLocalizationTable(objectDefinition);
		_createTable(objectDefinition.getDBTableName(), objectDefinition);
		_createTable(
			objectDefinition.getExtensionDBTableName(), objectDefinition);

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.getObjectRelationships(
					objectDefinition.getObjectDefinitionId(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			_objectRelationshipLocalService.
				createManyToManyObjectRelationshipTable(
					userId, objectRelationship);
		}

		deployObjectDefinition(objectDefinition);

		_registerTransactionCallbackForCluster(
			_deployObjectDefinitionMethodKey, objectDefinition);

		return objectDefinition;
	}

	private void _registerTransactionCallbackForCluster(
		MethodKey methodKey, ObjectDefinition objectDefinition) {

		if (ClusterExecutorUtil.isEnabled()) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					ClusterRequest clusterRequest =
						ClusterRequest.createMulticastRequest(
							new MethodHandler(methodKey, objectDefinition),
							true);

					clusterRequest.setFireAndForget(true);

					ClusterExecutorUtil.execute(clusterRequest);

					return null;
				});
		}
	}

	private ObjectDefinition _updateObjectDefinition(
			String externalReferenceCode, ObjectDefinition objectDefinition,
			long accountEntryRestrictedObjectFieldId,
			long descriptionObjectFieldId, long objectFolderId,
			long titleObjectFieldId, boolean accountEntryRestricted,
			boolean active, String dbTableName, boolean enableCategorization,
			boolean enableComments, boolean enableLocalization,
			boolean enableObjectEntryDraft, boolean enableObjectEntryHistory,
			Map<Locale, String> labelMap, String name, String panelAppOrder,
			String panelCategoryKey, boolean portlet,
			String pkObjectFieldDBColumnName, String pkObjectFieldName,
			Map<Locale, String> pluralLabelMap, String scope)
		throws PortalException {

		long oldObjectFolderId = objectDefinition.getObjectFolderId();
		boolean oldActive = objectDefinition.isActive();

		_validateExternalReferenceCode(
			false, externalReferenceCode, objectDefinition.isModifiable(), name,
			objectDefinition.isSystem());
		_validateAccountEntryRestrictedObjectFieldId(
			accountEntryRestrictedObjectFieldId, accountEntryRestricted,
			objectDefinition);
		_validateObjectFieldId(objectDefinition, descriptionObjectFieldId);
		_validateObjectFieldId(objectDefinition, titleObjectFieldId);
		_validateActive(objectDefinition, active);
		_validateEnableCategorization(
			enableCategorization, objectDefinition.isModifiable(),
			objectDefinition.getStorageType(), objectDefinition.isSystem());
		_validateEnableComments(
			enableComments, objectDefinition.isModifiable(),
			objectDefinition.getStorageType(), objectDefinition.isSystem());
		_validateEnableObjectEntryHistory(
			objectDefinition.isEnableObjectEntryHistory() !=
				enableObjectEntryHistory,
			objectDefinition.isModifiable(), objectDefinition.getStorageType(),
			objectDefinition.isSystem());
		_validateLabel(labelMap);
		_validatePluralLabel(pluralLabelMap);

		if (objectDefinition.getAccountEntryRestrictedObjectFieldId() != 0) {
			_objectFieldLocalService.updateRequired(
				objectDefinition.getAccountEntryRestrictedObjectFieldId(),
				false);
		}

		if (accountEntryRestricted &&
			(accountEntryRestrictedObjectFieldId > 0)) {

			_objectFieldLocalService.updateRequired(
				accountEntryRestrictedObjectFieldId, true);
		}

		objectDefinition.setExternalReferenceCode(externalReferenceCode);
		objectDefinition.setAccountEntryRestrictedObjectFieldId(
			accountEntryRestrictedObjectFieldId);
		objectDefinition.setDescriptionObjectFieldId(descriptionObjectFieldId);
		objectDefinition.setObjectFolderId(
			_getObjectFolderId(
				objectDefinition.getCompanyId(), objectFolderId));
		objectDefinition.setTitleObjectFieldId(titleObjectFieldId);
		objectDefinition.setAccountEntryRestricted(accountEntryRestricted);
		objectDefinition.setActive(active);
		objectDefinition.setClassName(
			_getClassName(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getClassName(),
				objectDefinition.isModifiable(), objectDefinition.isSystem()));
		objectDefinition.setEnableCategorization(enableCategorization);
		objectDefinition.setEnableComments(enableComments);
		objectDefinition.setEnableObjectEntryDraft(enableObjectEntryDraft);
		objectDefinition.setEnableObjectEntryHistory(enableObjectEntryHistory);
		objectDefinition.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectDefinition.setPanelAppOrder(panelAppOrder);
		objectDefinition.setPanelCategoryKey(panelCategoryKey);
		objectDefinition.setPortlet(portlet);
		objectDefinition.setPluralLabelMap(pluralLabelMap);

		if (objectDefinition.isApproved()) {
			if (!active && oldActive) {
				objectDefinitionLocalService.deployInactiveObjectDefinition(
					objectDefinition);
			}
			else if (active) {
				objectDefinitionLocalService.deployObjectDefinition(
					objectDefinition);
			}

			if (active != oldActive) {
				_updateWorkflowInstances(objectDefinition);
			}

			objectDefinition = objectDefinitionPersistence.update(
				objectDefinition);

			_objectFolderItemLocalService.updateObjectFolderObjectFolderItem(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getObjectFolderId(), oldObjectFolderId);

			return objectDefinition;
		}

		name = _getName(name, objectDefinition.isSystem());

		String shortName = ObjectDefinitionImpl.getShortName(name);

		dbTableName = _getDBTableName(
			dbTableName, objectDefinition.isModifiable(), name,
			objectDefinition.isSystem(), objectDefinition.getCompanyId(),
			shortName);

		pkObjectFieldName = _getPKObjectFieldName(
			pkObjectFieldName, objectDefinition.isModifiable(),
			objectDefinition.isSystem(), shortName);

		pkObjectFieldDBColumnName = _getPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName, pkObjectFieldName,
			objectDefinition.isModifiable(), objectDefinition.isSystem());

		_validateName(
			objectDefinition.getObjectDefinitionId(),
			objectDefinition.getCompanyId(), objectDefinition.isModifiable(),
			name, objectDefinition.isSystem());
		_validateScope(scope, objectDefinition.getStorageType());

		objectDefinition.setDBTableName(dbTableName);
		objectDefinition.setEnableLocalization(enableLocalization);
		objectDefinition.setName(name);
		objectDefinition.setPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName);
		objectDefinition.setPKObjectFieldName(pkObjectFieldName);
		objectDefinition.setScope(scope);

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId(),
					StringPool.BLANK)) {

			objectField.setDBTableName(objectDefinition.getDBTableName());

			_objectFieldLocalService.updateObjectField(objectField);
		}

		_objectFolderItemLocalService.updateObjectFolderObjectFolderItem(
			objectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectFolderId(), oldObjectFolderId);

		return objectDefinition;
	}

	private ObjectDefinition _updateTitleObjectFieldId(
			ObjectDefinition objectDefinition, String titleObjectFieldName)
		throws PortalException {

		if (Validator.isNull(titleObjectFieldName)) {
			titleObjectFieldName = "id";
		}

		ObjectField objectField = _objectFieldPersistence.findByODI_N(
			objectDefinition.getObjectDefinitionId(), titleObjectFieldName);

		_validateObjectFieldId(
			objectDefinition, objectField.getObjectFieldId());

		objectDefinition.setTitleObjectFieldId(objectField.getObjectFieldId());

		return objectDefinitionPersistence.update(objectDefinition);
	}

	private void _updateWorkflowInstances(ObjectDefinition objectDefinition)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_objectEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property objectDefinitionIdProperty =
					PropertyFactoryUtil.forName("objectDefinitionId");

				dynamicQuery.add(
					objectDefinitionIdProperty.eq(
						objectDefinition.getObjectDefinitionId()));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.ne(WorkflowConstants.STATUS_APPROVED));
			});
		actionableDynamicQuery.setParallel(true);
		actionableDynamicQuery.setPerformActionMethod(
			(ObjectEntry objectEntry) -> {
				WorkflowInstanceLink workflowInstanceLink =
					_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
						objectEntry.getCompanyId(),
						objectEntry.getNonzeroGroupId(),
						objectDefinition.getClassName(),
						objectEntry.getObjectEntryId());

				if (workflowInstanceLink != null) {
					_workflowInstanceManager.updateActive(
						objectDefinition.getUserId(),
						objectDefinition.getCompanyId(),
						workflowInstanceLink.getWorkflowInstanceId(),
						objectDefinition.isActive());
				}
			});

		actionableDynamicQuery.performActions();
	}

	private void _validateAccountEntryRestrictedObjectFieldId(
			long accountEntryRestrictedObjectFieldId,
			boolean accountEntryRestricted, ObjectDefinition objectDefinition)
		throws ObjectDefinitionAccountEntryRestrictedException,
			   ObjectDefinitionAccountEntryRestrictedObjectFieldIdException {

		if (accountEntryRestricted &&
			(accountEntryRestrictedObjectFieldId == 0)) {

			throw new ObjectDefinitionAccountEntryRestrictedObjectFieldIdException();
		}

		if (objectDefinition.isApproved() &&
			objectDefinition.isAccountEntryRestricted() &&
			!accountEntryRestricted) {

			throw new ObjectDefinitionAccountEntryRestrictedException(
				"Account entry restriction cannot be disabled when the " +
					"object definition is published");
		}
	}

	private void _validateActive(
			ObjectDefinition objectDefinition, boolean active)
		throws PortalException {

		if (active && !objectDefinition.isApproved()) {
			throw new ObjectDefinitionActiveException(
				"Object definitions must be published before being activated");
		}
	}

	private void _validateEnableCategorization(
			boolean enableCategorization, boolean modifiable,
			String storageType, boolean system)
		throws PortalException {

		if (enableCategorization &&
			_isUnmodifiableSystemObject(modifiable, system)) {

			throw new ObjectDefinitionEnableCategorizationException(
				"Enable categorization is not allowed for system object " +
					"definitions");
		}

		if (enableCategorization &&
			!StringUtil.equals(
				storageType, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT)) {

			throw new ObjectDefinitionEnableCategorizationException(
				"Enable categorization is only allowed for object " +
					"definitions with the default storage type");
		}
	}

	private void _validateEnableComments(
			boolean enableComments, boolean modifiable, String storageType,
			boolean system)
		throws PortalException {

		if (enableComments && _isUnmodifiableSystemObject(modifiable, system)) {
			throw new ObjectDefinitionEnableCommentsException(
				"Enable comments is not allowed for system object definitions");
		}

		if (enableComments &&
			!StringUtil.equals(
				storageType, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT)) {

			throw new ObjectDefinitionEnableCategorizationException(
				"Enable comments is only allowed for object definitions with " +
					"the default storage type");
		}
	}

	private void _validateEnableObjectEntryHistory(
			boolean enableObjectEntryHistoryChanged, boolean modifiable,
			String storageType, boolean system)
		throws PortalException {

		if (!enableObjectEntryHistoryChanged) {
			return;
		}

		if (_isUnmodifiableSystemObject(modifiable, system)) {
			throw new ObjectDefinitionEnableObjectEntryHistoryException(
				"Enable object entry history is not allowed for system " +
					"object definitions");
		}

		if (!StringUtil.equals(
				storageType, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT)) {

			throw new ObjectDefinitionEnableObjectEntryHistoryException(
				"Enable object entry history is only allowed for object " +
					"definitions with the default storage type");
		}
	}

	private void _validateExternalReferenceCode(
			boolean addObjectDefinition, String externalReferenceCode,
			boolean modifiable, String name, boolean system)
		throws PortalException {

		if (addObjectDefinition && !modifiable && system &&
			!ObjectDefinitionUtil.
				isAllowedUnmodifiableSystemObjectDefinitionExternalReferenceCode(
					externalReferenceCode, name)) {

			throw new ObjectDefinitionExternalReferenceCodeException.
				ForbiddenUnmodifiableSystemObjectDefinitionExternalReferenceCode(
					externalReferenceCode);
		}

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		char[] externalReferenceCodeCharArray =
			externalReferenceCode.toCharArray();

		if (externalReferenceCodeCharArray.length > 75) {
			throw new ObjectDefinitionExternalReferenceCodeException.
				MustBeLessThan75Characters();
		}

		if (!system &&
			externalReferenceCode.startsWith(
				ObjectDefinitionConstants.
					EXTERNAL_REFERENCE_CODE_PREFIX_SYSTEM_OBJECT_DEFINITION)) {

			throw new ObjectDefinitionExternalReferenceCodeException.
				MustNotStartWithPrefix();
		}
	}

	private void _validateLabel(Map<Locale, String> labelMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if ((labelMap == null) || Validator.isNull(labelMap.get(locale))) {
			throw new ObjectDefinitionLabelException(
				"Label is null for locale " + locale.getDisplayName());
		}
	}

	private void _validateName(
			long objectDefinitionId, long companyId, boolean modifiable,
			String name, boolean system)
		throws PortalException {

		if (modifiable && system &&
			!ObjectDefinitionUtil.isAllowedModifiableSystemObjectDefinitionName(
				name)) {

			throw new ObjectDefinitionNameException.
				ForbiddenModifiableSystemObjectDefinitionName(name);
		}

		if (Validator.isNull(name) || (!system && name.equals("C_"))) {
			throw new ObjectDefinitionNameException.MustNotBeNull();
		}

		if (_isUnmodifiableSystemObject(modifiable, system) &&
			(name.startsWith("C_") || name.startsWith("c_"))) {

			throw new ObjectDefinitionNameException.
				MustNotStartWithCAndUnderscoreForSystemObject();
		}
		else if (!system && !name.startsWith("C_")) {
			throw new ObjectDefinitionNameException.
				MustStartWithCAndUnderscoreForCustomObject();
		}

		char[] nameCharArray = name.toCharArray();

		for (int i = 0; i < nameCharArray.length; i++) {
			if (modifiable || !system) {

				// Skip C_

				if ((i == 0) || (i == 1)) {
					continue;
				}
			}

			char c = nameCharArray[i];

			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ObjectDefinitionNameException.
					MustOnlyContainLettersAndDigits();
			}
		}

		if ((system && !Character.isUpperCase(nameCharArray[0])) ||
			(!system && !Character.isUpperCase(nameCharArray[2]))) {

			throw new ObjectDefinitionNameException.
				MustBeginWithUpperCaseLetter();
		}

		if ((system && (nameCharArray.length > 41)) ||
			(!system && (nameCharArray.length > 43))) {

			throw new ObjectDefinitionNameException.
				MustBeLessThan41Characters();
		}

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.fetchByC_N(companyId, name);

		if ((objectDefinition != null) &&
			(objectDefinition.getObjectDefinitionId() != objectDefinitionId)) {

			throw new ObjectDefinitionNameException.MustNotBeDuplicate(name);
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

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			throw new ObjectFieldRelationshipTypeException(
				"Description and title object fields cannot have a " +
					"relationship type");
		}
	}

	private void _validatePluralLabel(Map<Locale, String> pluralLabelMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if ((pluralLabelMap == null) ||
			Validator.isNull(pluralLabelMap.get(locale))) {

			throw new ObjectDefinitionPluralLabelException(
				"Plural label is null for locale " + locale.getDisplayName());
		}
	}

	private void _validateScope(String scope, String storageType)
		throws PortalException {

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

		if (StringUtil.equals(scope, ObjectDefinitionConstants.SCOPE_SITE) &&
			StringUtil.equals(
				storageType,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE)) {

			throw new ObjectDefinitionScopeException(
				StringBundler.concat(
					"Scope \"", ObjectDefinitionConstants.SCOPE_SITE,
					"\" cannot be associated with storage type \"",
					ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE));
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

	private static final MethodKey _deployObjectDefinitionMethodKey =
		new MethodKey(
			ObjectDefinitionLocalServiceUtil.class, "deployObjectDefinition",
			ObjectDefinition.class);
	private static final MethodKey _undeployObjectDefinitionMethodKey =
		new MethodKey(
			ObjectDefinitionLocalServiceUtil.class, "undeployObjectDefinition",
			ObjectDefinition.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	private BundleContext _bundleContext;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CurrentConnection _currentConnection;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	private final Map
		<InactiveObjectDefinitionDeployer,
		 Map<Long, List<ServiceRegistration<?>>>>
			_inactiveObjectDefinitionsServiceRegistrationsMaps =
				Collections.synchronizedMap(new LinkedHashMap<>());

	@Reference
	private Language _language;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private ModelSearchRegistrarHelper _modelSearchRegistrarHelper;

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private ObjectActionLocalService _objectActionLocalService;

	private ServiceTracker<ObjectDefinitionDeployer, ObjectDefinitionDeployer>
		_objectDefinitionDeployerServiceTracker;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryPersistence _objectEntryPersistence;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectFolderItemLocalService _objectFolderItemLocalService;

	@Reference
	private ObjectFolderLocalService _objectFolderLocalService;

	@Reference
	private ObjectFolderPersistence _objectFolderPersistence;

	@Reference
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Reference
	private ObjectLayoutTabLocalService _objectLayoutTabLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectRelationshipPersistence _objectRelationshipPersistence;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	@Reference
	private ObjectViewLocalService _objectViewLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private final Map
		<ObjectDefinitionDeployer, Map<Long, List<ServiceRegistration<?>>>>
			_serviceRegistrationsMaps = Collections.synchronizedMap(
				new LinkedHashMap<>());

	@Reference
	private TreeFactory _treeFactory;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference(target = "(model.pre.filter.contributor.id=WorkflowStatus)")
	private ModelPreFilterContributor _workflowStatusModelPreFilterContributor;

}