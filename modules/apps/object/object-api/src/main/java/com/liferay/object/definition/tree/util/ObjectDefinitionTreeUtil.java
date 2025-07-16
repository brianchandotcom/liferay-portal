/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.tree.util;

import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.definition.security.permission.resource.util.ObjectDefinitionResourcePermissionUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectActionModel;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.persistence.ObjectActionPersistence;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
import com.liferay.object.tree.Node;
import com.liferay.object.tree.ObjectDefinitionTreeFactory;
import com.liferay.object.tree.Tree;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

/**
 * @author Feliphe Marinho
 */
public class ObjectDefinitionTreeUtil {

	public static void bindObjectDefinitions(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionSettingLocalService
				objectDefinitionSettingLocalService,
			ObjectEntryLocalService objectEntryLocalService,
			ObjectRelationship objectRelationship,
			ObjectRelationshipLocalService objectRelationshipLocalService)
		throws PortalException {

		objectRelationship.setDeletionType(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE);
		objectRelationship.setEdge(true);

		objectRelationship =
			objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship);

		ObjectDefinition objectDefinition1 =
			objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1());

		String objectDefinition1PreviousRESTContextPath =
			objectDefinition1.getRESTContextPath();

		if (objectDefinition1.getRootObjectDefinitionId() == 0) {
			_setRootObjectDefinitionId(
				objectDefinition1, objectDefinitionSettingLocalService,
				objectDefinition1.getObjectDefinitionId());
		}

		ObjectDefinition objectDefinition2 =
			objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId2());

		if (objectDefinition1.isApproved() == objectDefinition2.isApproved()) {
			if (objectDefinition1.isApproved()) {
				objectDefinition1.setPreviousRESTContextPath(
					objectDefinition1PreviousRESTContextPath);

				objectDefinitionLocalService.deployObjectDefinition(
					objectDefinition1);

				if (objectDefinition2.isApproved() &&
					!objectRelationship.isNew()) {

					objectEntryLocalService.updateRootObjectEntryIds(
						objectDefinition1, objectDefinition2,
						objectRelationship);
				}
			}

			ObjectDefinitionTreeFactory objectDefinitionTreeFactory =
				new ObjectDefinitionTreeFactory(
					objectDefinitionLocalService,
					objectRelationshipLocalService);

			Tree tree = objectDefinitionTreeFactory.create(
				objectDefinition2.getObjectDefinitionId());

			Iterator<Node> iterator = tree.iterator();

			while (iterator.hasNext()) {
				Node node = iterator.next();

				ObjectDefinition nodeObjectDefinition =
					objectDefinitionPersistence.findByPrimaryKey(
						node.getPrimaryKey());

				String nodeObjectDefinitionPreviousRESTContextPath =
					nodeObjectDefinition.getRESTContextPath();

				_setRootObjectDefinitionId(
					nodeObjectDefinition, objectDefinitionSettingLocalService,
					objectDefinition1.getRootObjectDefinitionId());

				if (nodeObjectDefinition.isApproved() &&
					objectDefinition1.isApproved()) {

					nodeObjectDefinition.setPreviousRESTContextPath(
						nodeObjectDefinitionPreviousRESTContextPath);

					objectDefinitionLocalService.deployObjectDefinition(
						nodeObjectDefinition);
				}
			}
		}
		else {
			if (objectDefinition2.isRootNode()) {
				return;
			}

			_setRootObjectDefinitionId(
				objectDefinition2, objectDefinitionSettingLocalService,
				objectDefinition2.getObjectDefinitionId());

			if (objectDefinition2.isApproved()) {
				objectDefinitionLocalService.deployObjectDefinition(
					objectDefinition2);
			}
		}

		ObjectDefinition rootObjectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(
				objectDefinition1.getRootObjectDefinitionId());

		if (rootObjectDefinition.isApproved()) {
			objectDefinitionLocalService.deployObjectDefinition(
				rootObjectDefinition);
		}
	}

	public static long getRootObjectDefinitionId(
		long objectDefinitionId,
		ObjectDefinitionSettingLocalService
			objectDefinitionSettingLocalService) {

		Long rootObjectDefinitionId = _rootObjectDefinitionIds.computeIfAbsent(
			objectDefinitionId,
			key -> {
				ObjectDefinitionSetting objectDefinitionSetting =
					objectDefinitionSettingLocalService.
						fetchObjectDefinitionSetting(
							key,
							ObjectDefinitionSettingConstants.
								NAME_ROOT_OBJECT_DEFINITION_IDS);

				if (objectDefinitionSetting == null) {
					return null;
				}

				return GetterUtil.getLong(objectDefinitionSetting.getValue());
			});

		if (rootObjectDefinitionId == null) {
			return 0L;
		}

		return rootObjectDefinitionId;
	}

	public static void populateRootObjectDefinitionIds(
		List<ObjectDefinition> objectDefinitions,
		Map<Long, ObjectDefinitionSetting> objectDefinitionSettingsMap) {

		for (Map.Entry<Long, ObjectDefinitionSetting> entry :
				objectDefinitionSettingsMap.entrySet()) {

			ObjectDefinitionSetting objectDefinitionSetting = entry.getValue();

			_rootObjectDefinitionIds.put(
				entry.getKey(),
				GetterUtil.getLong(objectDefinitionSetting.getValue()));
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_rootObjectDefinitionIds.putIfAbsent(
				objectDefinition.getObjectDefinitionId(), 0L);
		}
	}

	public static void unbindObjectDefinitions(
			ObjectActionPersistence objectActionPersistence,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionSettingLocalService
				objectDefinitionSettingLocalService,
			ObjectEntryLocalService objectEntryLocalService,
			ObjectFieldPersistence objectFieldPersistence,
			ObjectRelationship objectRelationship,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			ObjectRelationshipPersistence objectRelationshipPersistence,
			ResourceActionLocalService resourceActionLocalService,
			ResourceActions resourceActions,
			ResourcePermissionLocalService resourcePermissionLocalService,
			WorkflowDefinitionLinkLocalService
				workflowDefinitionLinkLocalService)
		throws PortalException {

		objectRelationship.setEdge(false);

		objectRelationship =
			objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship);

		ObjectDefinition objectDefinition1 =
			objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1());

		if (objectDefinition1.isRootDescendantNode()) {
			objectDefinition1 = objectDefinitionPersistence.findByPrimaryKey(
				objectDefinition1.getRootObjectDefinitionId());
		}

		long oldRootObjectDefinitionId1 =
			objectDefinition1.getRootObjectDefinitionId();
		long newRootObjectDefinitionId1 = _getRootObjectDefinitionId(
			objectDefinition1, objectRelationshipPersistence);

		_updateRootObjectDefinitionId(
			objectDefinition1, objectDefinitionLocalService,
			objectDefinitionSettingLocalService, oldRootObjectDefinitionId1,
			newRootObjectDefinitionId1);

		_updateObjectEntries(
			objectDefinition1, objectEntryLocalService,
			oldRootObjectDefinitionId1, newRootObjectDefinitionId1);

		if (newRootObjectDefinitionId1 == 0) {
			for (ObjectAction objectAction :
					objectActionPersistence.findByO_A_OATK(
						objectDefinition1.getObjectDefinitionId(), true,
						ObjectActionTriggerConstants.
							KEY_ON_AFTER_ROOT_UPDATE)) {

				objectAction.setActive(false);
				objectAction.setObjectActionTriggerKey(
					ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE);

				objectActionPersistence.update(objectAction);
			}
		}

		ObjectDefinition objectDefinition2 =
			objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId2());

		objectDefinition2.setScope(objectDefinition1.getScope());

		long oldRootObjectDefinitionId2 =
			objectDefinition2.getRootObjectDefinitionId();
		long newRootObjectDefinitionId2 = _getRootObjectDefinitionId(
			objectDefinition2, objectRelationshipPersistence);

		_updateRootObjectDefinitionId(
			objectDefinition2, objectDefinitionLocalService,
			objectDefinitionSettingLocalService, oldRootObjectDefinitionId2,
			newRootObjectDefinitionId2);

		_copyResourcePermissions(
			objectActionPersistence, objectDefinition1, objectDefinition2,
			objectEntryLocalService, resourceActionLocalService,
			resourcePermissionLocalService);

		_updateObjectEntries(
			objectDefinition2, objectEntryLocalService,
			oldRootObjectDefinitionId2, newRootObjectDefinitionId2);

		_updateObjectDefinitionTree(
			objectActionPersistence, objectDefinition2,
			objectDefinitionLocalService, objectDefinitionPersistence,
			objectDefinitionSettingLocalService, objectEntryLocalService,
			objectFieldPersistence, objectRelationshipLocalService,
			objectRelationshipPersistence, oldRootObjectDefinitionId2,
			newRootObjectDefinitionId2, resourceActions);

		_copyWorkflowDefinitionLinks(
			objectDefinition1, objectDefinition2,
			workflowDefinitionLinkLocalService);

		if (objectDefinition2.isRootNode()) {
			_deployObjectDefinition(
				objectDefinition2, objectDefinitionLocalService);
		}
	}

	public static void updateNodeObjectDefinition(
			ObjectDefinition objectDefinition,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionSettingLocalService
				objectDefinitionSettingLocalService,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			ObjectRelationshipPersistence objectRelationshipPersistence)
		throws PortalException {

		_updateNodeObjectDefinition(
			objectDefinition, objectDefinitionPersistence,
			objectDefinitionSettingLocalService, objectRelationshipPersistence);
		_updateDescendantNodeObjectDefinitions(
			objectDefinition, objectDefinitionLocalService,
			objectDefinitionPersistence, objectDefinitionSettingLocalService,
			objectRelationshipLocalService, objectRelationshipPersistence);
	}

	private static void _copyResourcePermissions(
			long companyId,
			ResourceActionLocalService resourceActionLocalService,
			ResourcePermissionLocalService resourcePermissionLocalService,
			String sourceName, String targetName,
			List<String> targetObjectActionNames)
		throws PortalException {

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(sourceName);

		_copyResourcePermissions(
			resourcePermissionLocalService, resourceActions,
			resourcePermissionLocalService.getResourcePermissions(
				companyId, sourceName, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(companyId)),
			targetName, targetObjectActionNames, String.valueOf(companyId));
		_copyResourcePermissions(
			resourcePermissionLocalService, resourceActions,
			resourcePermissionLocalService.getResourcePermissions(
				companyId, sourceName, ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID)),
			targetName, targetObjectActionNames,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID));
	}

	private static void _copyResourcePermissions(
			ObjectActionPersistence objectActionPersistence,
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2,
			ObjectEntryLocalService objectEntryLocalService,
			ResourceActionLocalService resourceActionLocalService,
			ResourcePermissionLocalService resourcePermissionLocalService)
		throws PortalException {

		if (!objectDefinition1.isApproved() ||
			!objectDefinition2.isApproved()) {

			return;
		}

		List<String> objectActionNames = TransformUtil.transform(
			objectActionPersistence.findByO_A_OATK(
				objectDefinition2.getObjectDefinitionId(), true,
				ObjectActionTriggerConstants.KEY_STANDALONE),
			ObjectActionModel::getName);
		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(
				objectDefinition1.getClassName());

		_performActions(
			objectDefinition2.getObjectDefinitionId(), objectEntryLocalService,
			true,
			(ObjectEntry objectEntry) -> _copyResourcePermissions(
				resourcePermissionLocalService, resourceActions,
				resourcePermissionLocalService.getResourcePermissions(
					objectDefinition1.getCompanyId(),
					objectDefinition1.getClassName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(objectEntry.getRootObjectEntryId())),
				objectDefinition2.getClassName(), objectActionNames,
				String.valueOf(objectEntry.getObjectEntryId())));

		_copyResourcePermissions(
			objectDefinition1.getCompanyId(), resourceActionLocalService,
			resourcePermissionLocalService, objectDefinition1.getClassName(),
			objectDefinition2.getClassName(), objectActionNames);

		_copyResourcePermissions(
			objectDefinition1.getCompanyId(), resourceActionLocalService,
			resourcePermissionLocalService, objectDefinition1.getPortletId(),
			objectDefinition2.getPortletId(), null);
		_copyResourcePermissions(
			objectDefinition1.getCompanyId(), resourceActionLocalService,
			resourcePermissionLocalService, objectDefinition1.getResourceName(),
			objectDefinition2.getResourceName(), null);
	}

	private static void _copyResourcePermissions(
			ResourcePermissionLocalService resourcePermissionLocalService,
			List<ResourceAction> sourceResourceActions,
			List<ResourcePermission> sourceResourcePermissions,
			String targetName, List<String> targetObjectActionNames,
			String targetPrimKey)
		throws PortalException {

		for (ResourcePermission sourceResourcePermission :
				sourceResourcePermissions) {

			List<String> targetResourceActionIds = new ArrayList<>();

			for (ResourceAction sourceResourceAction : sourceResourceActions) {
				long bitwiseValue = sourceResourceAction.getBitwiseValue();

				if ((sourceResourcePermission.getActionIds() & bitwiseValue) !=
						bitwiseValue) {

					continue;
				}

				targetResourceActionIds.add(sourceResourceAction.getActionId());
			}

			if (ListUtil.isNotEmpty(targetObjectActionNames)) {
				targetResourceActionIds.addAll(
					resourcePermissionLocalService.
						getAvailableResourcePermissionActionIds(
							sourceResourcePermission.getCompanyId(), targetName,
							sourceResourcePermission.getScope(), targetPrimKey,
							sourceResourcePermission.getRoleId(),
							targetObjectActionNames));
			}

			resourcePermissionLocalService.setResourcePermissions(
				sourceResourcePermission.getCompanyId(), targetName,
				sourceResourcePermission.getScope(), targetPrimKey,
				sourceResourcePermission.getRoleId(),
				targetResourceActionIds.toArray(new String[0]));
		}
	}

	private static void _copyWorkflowDefinitionLinks(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2,
			WorkflowDefinitionLinkLocalService
				workflowDefinitionLinkLocalService)
		throws PortalException {

		if (!objectDefinition1.isApproved() ||
			!objectDefinition2.isApproved()) {

			return;
		}

		for (WorkflowDefinitionLink workflowDefinitionLink :
				workflowDefinitionLinkLocalService.getWorkflowDefinitionLinks(
					objectDefinition1.getCompanyId(),
					objectDefinition1.getClassName())) {

			workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
				workflowDefinitionLink.getUserId(),
				workflowDefinitionLink.getCompanyId(),
				workflowDefinitionLink.getGroupId(),
				objectDefinition2.getClassName(), 0, 0,
				workflowDefinitionLink.getWorkflowDefinitionName(),
				workflowDefinitionLink.getWorkflowDefinitionVersion());
		}
	}

	private static void _deployObjectDefinition(
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		if (!objectDefinition.isApproved()) {
			return;
		}

		objectDefinitionLocalService.deployObjectDefinition(objectDefinition);
	}

	private static long _getRootObjectDefinitionId(
		ObjectDefinition objectDefinition,
		ObjectRelationshipPersistence objectRelationshipPersistence) {

		long count = objectRelationshipPersistence.countByODI1_E(
			objectDefinition.getObjectDefinitionId(), true);

		if (count == 0) {
			return 0;
		}

		return objectDefinition.getObjectDefinitionId();
	}

	private static void _performActions(
			long objectDefinitionId,
			ObjectEntryLocalService objectEntryLocalService, boolean parallel,
			ActionableDynamicQuery.PerformActionMethod<?> performActionMethod)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			objectEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"objectDefinitionId", objectDefinitionId)));
		actionableDynamicQuery.setParallel(parallel);
		actionableDynamicQuery.setPerformActionMethod(performActionMethod);

		actionableDynamicQuery.performActions();
	}

	private static void _runSQL(
		ObjectRelationshipPersistence objectRelationshipPersistence,
		String sql) {

		DataSource dataSource = objectRelationshipPersistence.getDataSource();

		Connection currentConnection = CurrentConnectionUtil.getConnection(
			dataSource);

		try {
			DB db = DBManagerUtil.getDB();

			if (currentConnection != null) {
				db.runSQL(currentConnection, new String[] {sql});

				return;
			}

			try (Connection connection = dataSource.getConnection()) {
				db.runSQL(connection, new String[] {sql});
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private static void _setRootObjectDefinitionId(
			ObjectDefinition objectDefinition,
			ObjectDefinitionSettingLocalService
				objectDefinitionSettingLocalService,
			long rootObjectDefinitionId)
		throws PortalException {

		ObjectDefinitionSetting objectDefinitionSetting =
			objectDefinitionSettingLocalService.fetchObjectDefinitionSetting(
				objectDefinition.getObjectDefinitionId(),
				ObjectDefinitionSettingConstants.
					NAME_ROOT_OBJECT_DEFINITION_IDS);

		if (objectDefinitionSetting == null) {
			objectDefinitionSettingLocalService.addObjectDefinitionSetting(
				objectDefinition.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				ObjectDefinitionSettingConstants.
					NAME_ROOT_OBJECT_DEFINITION_IDS,
				String.valueOf(rootObjectDefinitionId));
		}
		else {
			objectDefinitionSetting.setValue(
				String.valueOf(rootObjectDefinitionId));

			objectDefinitionSettingLocalService.updateObjectDefinitionSetting(
				objectDefinitionSetting);
		}

		_rootObjectDefinitionIds.put(
			objectDefinition.getObjectDefinitionId(), rootObjectDefinitionId);
	}

	private static void _updateDescendantNodeObjectDefinitions(
			ObjectDefinition objectDefinition1,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionSettingLocalService
				objectDefinitionSettingLocalService,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			ObjectRelationshipPersistence objectRelationshipPersistence)
		throws PortalException {

		List<ObjectRelationship> objectRelationships =
			objectRelationshipPersistence.findByODI1_E(
				objectDefinition1.getObjectDefinitionId(), true);

		if (objectRelationships.isEmpty()) {
			return;
		}

		objectDefinitionLocalService.deployObjectDefinition(objectDefinition1);

		objectDefinition1.setPreviousRESTContextPath(null);

		boolean containsDraftDescendantNodeObjectDefinitions = false;
		ObjectDefinitionTreeFactory objectDefinitionTreeFactory =
			new ObjectDefinitionTreeFactory(
				objectDefinitionPersistence, objectRelationshipLocalService);

		for (ObjectRelationship objectRelationship : objectRelationships) {
			ObjectDefinition objectDefinition2 =
				objectDefinitionPersistence.findByPrimaryKey(
					objectRelationship.getObjectDefinitionId2());

			if (!objectDefinition2.isApproved()) {
				containsDraftDescendantNodeObjectDefinitions = true;

				continue;
			}

			Tree tree = objectDefinitionTreeFactory.create(
				objectRelationship.getObjectDefinitionId2());

			Iterator<Node> iterator = tree.iterator();

			while (iterator.hasNext()) {
				Node node = iterator.next();

				ObjectDefinition nodeObjectDefinition =
					objectDefinitionPersistence.findByPrimaryKey(
						node.getPrimaryKey());

				String previousRESTContextPath =
					nodeObjectDefinition.getRESTContextPath();

				_setRootObjectDefinitionId(
					nodeObjectDefinition, objectDefinitionSettingLocalService,
					objectDefinition1.getRootObjectDefinitionId());

				nodeObjectDefinition.setPreviousRESTContextPath(
					previousRESTContextPath);

				objectDefinitionLocalService.deployObjectDefinition(
					nodeObjectDefinition);
			}
		}

		if (containsDraftDescendantNodeObjectDefinitions) {
			Tree tree = objectDefinitionTreeFactory.create(
				false, objectDefinition1.getObjectDefinitionId());

			Node rootNode = tree.getRootNode();

			for (Node childNode : rootNode.getChildNodes()) {
				Iterator<Node> iterator = tree.iterator(
					childNode.getPrimaryKey());

				while (iterator.hasNext()) {
					Node node = iterator.next();

					ObjectDefinition nodeObjectDefinition =
						objectDefinitionPersistence.findByPrimaryKey(
							node.getPrimaryKey());

					_setRootObjectDefinitionId(
						nodeObjectDefinition,
						objectDefinitionSettingLocalService,
						childNode.getPrimaryKey());

					objectDefinitionPersistence.update(nodeObjectDefinition);
				}
			}
		}
	}

	private static void _updateNodeObjectDefinition(
			ObjectDefinition objectDefinition2,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionSettingLocalService
				objectDefinitionSettingLocalService,
			ObjectRelationshipPersistence objectRelationshipPersistence)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.fetchByODI2_E(
				objectDefinition2.getObjectDefinitionId(), true);

		if (objectRelationship == null) {
			return;
		}

		ObjectDefinition objectDefinition1 =
			objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1());

		String previousRESTContextPath = objectDefinition2.getRESTContextPath();

		if (objectDefinition1.isApproved()) {
			_setRootObjectDefinitionId(
				objectDefinition2, objectDefinitionSettingLocalService,
				objectDefinition1.getRootObjectDefinitionId());
		}
		else {
			_setRootObjectDefinitionId(
				objectDefinition2, objectDefinitionSettingLocalService,
				objectDefinition2.getObjectDefinitionId());
		}

		objectDefinition2.setPreviousRESTContextPath(previousRESTContextPath);
	}

	private static void _updateObjectDefinitionTree(
			ObjectActionPersistence objectActionPersistence,
			ObjectDefinition objectDefinition1,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionSettingLocalService
				objectDefinitionSettingLocalService,
			ObjectEntryLocalService objectEntryLocalService,
			ObjectFieldPersistence objectFieldPersistence,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			ObjectRelationshipPersistence objectRelationshipPersistence,
			long oldRootObjectDefinitionId, long newRootObjectDefinitionId,
			ResourceActions resourceActions)
		throws PortalException {

		try {
			ObjectDefinitionResourcePermissionUtil.
				populateRootDescendantNodeModelResources(
					objectActionPersistence, objectDefinitionPersistence,
					resourceActions, objectDefinition1,
					newRootObjectDefinitionId);

			ObjectDefinitionResourcePermissionUtil.
				removeRootDescendantNodeModelResources(
					objectDefinitionPersistence, resourceActions,
					objectDefinition1, oldRootObjectDefinitionId);
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}

		if (newRootObjectDefinitionId == 0) {
			return;
		}

		for (ObjectRelationship objectRelationship :
				objectRelationshipLocalService.getObjectRelationships(
					objectDefinition1.getObjectDefinitionId(), true)) {

			ObjectDefinition objectDefinition2 =
				objectDefinitionPersistence.findByPrimaryKey(
					objectRelationship.getObjectDefinitionId2());

			if (oldRootObjectDefinitionId !=
					objectDefinition2.getRootObjectDefinitionId()) {

				continue;
			}

			_updateRootObjectDefinitionId(
				objectDefinition2, objectDefinitionLocalService,
				objectDefinitionSettingLocalService, oldRootObjectDefinitionId,
				newRootObjectDefinitionId);

			if (objectDefinition2.isApproved()) {
				ObjectField objectField =
					objectFieldPersistence.findByPrimaryKey(
						objectRelationship.getObjectFieldId2());

				_performActions(
					objectDefinition1.getObjectDefinitionId(),
					objectEntryLocalService, true,
					(ObjectEntry objectEntry) -> _runSQL(
						objectRelationshipPersistence,
						StringBundler.concat(
							"update ObjectEntry set rootObjectEntryId = ",
							objectEntry.getRootObjectEntryId(),
							" where objectEntryId in (select ",
							objectDefinition2.getPKObjectFieldDBColumnName(),
							" from ", objectField.getDBTableName(), " where ",
							objectField.getDBColumnName(), " = ",
							objectEntry.getObjectEntryId(), ")")));

				if (objectDefinition2.isEnableIndexSearch()) {
					Indexer<ObjectEntry> indexer =
						IndexerRegistryUtil.getIndexer(
							objectDefinition2.getClassName());

					_performActions(
						objectDefinition2.getObjectDefinitionId(),
						objectEntryLocalService, true,
						(ObjectEntry objectEntry) -> indexer.reindex(
							objectEntry));
				}
			}

			_updateObjectDefinitionTree(
				objectActionPersistence, objectDefinition2,
				objectDefinitionLocalService, objectDefinitionPersistence,
				objectDefinitionSettingLocalService, objectEntryLocalService,
				objectFieldPersistence, objectRelationshipLocalService,
				objectRelationshipPersistence, oldRootObjectDefinitionId,
				newRootObjectDefinitionId, resourceActions);
		}
	}

	private static void _updateObjectEntries(
			ObjectDefinition objectDefinition,
			ObjectEntryLocalService objectEntryLocalService,
			long oldRootObjectDefinitionId, long newRootObjectDefinitionId)
		throws PortalException {

		if (!objectDefinition.isApproved() ||
			(oldRootObjectDefinitionId == newRootObjectDefinitionId)) {

			return;
		}

		_performActions(
			objectDefinition.getObjectDefinitionId(), objectEntryLocalService,
			false,
			(ObjectEntry objectEntry) -> {
				if (newRootObjectDefinitionId == 0) {
					objectEntry.setRootObjectEntryId(0);
				}
				else {
					objectEntry.setRootObjectEntryId(
						objectEntry.getObjectEntryId());
				}

				objectEntryLocalService.updateObjectEntry(objectEntry);
			});
	}

	private static void _updateRootObjectDefinitionId(
			ObjectDefinition objectDefinition,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectDefinitionSettingLocalService
				objectDefinitionSettingLocalService,
			long oldRootObjectDefinitionId, long newRootObjectDefinitionId)
		throws PortalException {

		if (oldRootObjectDefinitionId == newRootObjectDefinitionId) {
			_deployObjectDefinition(
				objectDefinition, objectDefinitionLocalService);

			return;
		}

		String previousRESTContextPath = objectDefinition.getRESTContextPath();

		_setRootObjectDefinitionId(
			objectDefinition, objectDefinitionSettingLocalService,
			newRootObjectDefinitionId);

		objectDefinition.setPreviousRESTContextPath(previousRESTContextPath);

		_deployObjectDefinition(objectDefinition, objectDefinitionLocalService);
	}

	private static final Map<Long, Long> _rootObjectDefinitionIds =
		new ConcurrentHashMap<>();

}