/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.security.permission.resource.util;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.persistence.ObjectActionPersistence;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.tree.Node;
import com.liferay.object.tree.ObjectDefinitionTreeFactory;
import com.liferay.object.tree.Tree;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Carolina Barbosa
 */
public class ObjectDefinitionResourcePermissionUtil {

	public static void populateResourceActions(
			ObjectActionLocalService objectActionLocalService,
			ObjectDefinition objectDefinition,
			Map<Long, List<ObjectRelationship>> objectRelationshipsMap,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionTreeFactory objectDefinitionTreeFactory,
			PortletLocalService portletLocalService,
			ResourceActions resourceActions,
			List<ObjectAction> standaloneObjectActions)
		throws Exception {

		if (objectDefinition.isRootDescendantNode()) {
			return;
		}

		List<String> rootDescendantNodeObjectDefinitionClassNames =
			new ArrayList<>();

		Document document = _readDocument(
			objectActionLocalService, objectDefinition, objectRelationshipsMap,
			objectDefinitionPersistence, objectDefinitionTreeFactory,
			rootDescendantNodeObjectDefinitionClassNames,
			standaloneObjectActions);

		try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
				objectDefinition.getCompanyId())) {

			resourceActions.populateModelResources(document);

			Portlet portlet = portletLocalService.getPortletById(
				objectDefinition.getCompanyId(),
				objectDefinition.getPortletId());

			if (portlet != null) {
				resourceActions.populatePortletResource(
					portlet,
					ObjectDefinitionResourcePermissionUtil.class.
						getClassLoader(),
					document);
			}

			for (String rootDescendantNodeObjectDefinitionClassName :
					rootDescendantNodeObjectDefinitionClassNames) {

				resourceActions.removeModelResource(
					rootDescendantNodeObjectDefinitionClassName,
					ActionKeys.PERMISSIONS);
			}

			_objectDefinitionResourceActionDocumentsMap.put(
				objectDefinition, document);
		}
	}

	public static void populateRootDescendantNodeModelResources(
			ObjectActionPersistence objectActionPersistence,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ResourceActions resourceActions,
			ObjectDefinition rootDescendantNodeObjectDefinition,
			long rootObjectDefinitionId)
		throws Exception {

		if (!rootDescendantNodeObjectDefinition.isApproved() ||
			!rootDescendantNodeObjectDefinition.isRootDescendantNode()) {

			return;
		}

		String objectActionPermissionKeys = _getObjectActionPermissionKeys(
			null, rootDescendantNodeObjectDefinition.getObjectDefinitionId(),
			objectActionPersistence.findByO_A_OATK(
				rootDescendantNodeObjectDefinition.getObjectDefinitionId(),
				true, ObjectActionTriggerConstants.KEY_STANDALONE));

		if (Validator.isNull(objectActionPermissionKeys)) {
			return;
		}

		ObjectDefinition rootObjectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(
				rootObjectDefinitionId);

		try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
				rootObjectDefinition.getCompanyId())) {

			resourceActions.populateModelResources(
				SAXReaderUtil.read(
					StringUtil.replace(
						StringUtil.read(
							ObjectDefinitionResourcePermissionUtil.class.
								getClassLoader(),
							"resource-actions/resource-actions-root-" +
								"descendant-node.xml.tpl"),
						new String[] {
							"[$MODEL_NAME$]",
							"[$PERMISSIONS_GUEST_UNSUPPORTED$]",
							"[$PERMISSIONS_SUPPORTS$]", "[$PORTLET_NAME$]"
						},
						new String[] {
							rootDescendantNodeObjectDefinition.getClassName(),
							objectActionPermissionKeys,
							objectActionPermissionKeys,
							rootObjectDefinition.getPortletId()
						})));

			resourceActions.removeModelResource(
				rootDescendantNodeObjectDefinition.getClassName(),
				ActionKeys.PERMISSIONS);
		}
	}

	public static void removeResourceActions(
			ObjectActionLocalService objectActionLocalService,
			ObjectDefinition objectDefinition,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionTreeFactory objectDefinitionTreeFactory,
			ResourceActions resourceActions)
		throws Exception {

		Document document = _objectDefinitionResourceActionDocumentsMap.remove(
			objectDefinition);

		if (document == null) {
			document = _readDocument(
				objectActionLocalService, objectDefinition, null,
				objectDefinitionPersistence, objectDefinitionTreeFactory,
				new ArrayList<>(), null);
		}

		resourceActions.removeModelResources(document);

		resourceActions.removePortletResources(document);
	}

	public static void removeRootDescendantNodeModelResources(
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ResourceActions resourceActions,
			ObjectDefinition rootDescendantNodeObjectDefinition,
			long rootObjectDefinitionId)
		throws Exception {

		if (!rootDescendantNodeObjectDefinition.isApproved()) {
			return;
		}

		ObjectDefinition rootObjectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(
				rootObjectDefinitionId);

		if (Objects.equals(
				rootDescendantNodeObjectDefinition.getObjectDefinitionId(),
				rootObjectDefinition.getObjectDefinitionId())) {

			return;
		}

		try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
				rootObjectDefinition.getCompanyId())) {

			resourceActions.removeModelResources(
				SAXReaderUtil.read(
					StringUtil.replace(
						StringUtil.read(
							ObjectDefinitionResourcePermissionUtil.class.
								getClassLoader(),
							"resource-actions/resource-actions-root-" +
								"descendant-node.xml.tpl"),
						new String[] {"[$MODEL_NAME$]", "[$PORTLET_NAME$]"},
						new String[] {
							rootDescendantNodeObjectDefinition.getClassName(),
							rootObjectDefinition.getPortletId()
						})));
		}
	}

	private static String _getObjectActionPermissionKeys(
		ObjectActionLocalService objectActionLocalService,
		long objectDefinitionId, List<ObjectAction> standaloneObjectActions) {

		String objectActionPermissionKeys = StringPool.BLANK;

		if (standaloneObjectActions == null) {
			standaloneObjectActions = objectActionLocalService.getObjectActions(
				objectDefinitionId,
				ObjectActionTriggerConstants.KEY_STANDALONE);
		}

		for (ObjectAction objectAction : standaloneObjectActions) {
			objectActionPermissionKeys = StringBundler.concat(
				objectActionPermissionKeys, "<action-key>",
				objectAction.getName(), "</action-key>");
		}

		return objectActionPermissionKeys;
	}

	private static String _getPermissionsGuestUnsupported(
		ObjectDefinition objectDefinition) {

		if (!objectDefinition.isEnableComments()) {
			return StringPool.BLANK;
		}

		return "<action-key>DELETE_DISCUSSION</action-key>" +
			"<action-key>UPDATE_DISCUSSION</action-key>";
	}

	private static String _getPermissionsSupports(
		ObjectDefinition objectDefinition) {

		String permissionsSupports = StringPool.BLANK;

		if (objectDefinition.isEnableComments()) {
			permissionsSupports = StringBundler.concat(
				"<action-key>ADD_DISCUSSION</action-key>",
				"<action-key>DELETE_DISCUSSION</action-key>",
				"<action-key>UPDATE_DISCUSSION</action-key>");
		}

		if (objectDefinition.isEnableObjectEntryHistory()) {
			permissionsSupports = StringBundler.concat(
				permissionsSupports, "<action-key>",
				ObjectActionKeys.OBJECT_ENTRY_HISTORY, "</action-key>");
		}

		if (objectDefinition.isEnableObjectEntrySubscription() &&
			FeatureFlagManagerUtil.isEnabled("LPD-42577")) {

			permissionsSupports = StringBundler.concat(
				permissionsSupports, "<action-key>", ActionKeys.SUBSCRIBE,
				"</action-key>");
		}

		return permissionsSupports;
	}

	private static String _getRootDescendantNodeObjectDefinitionsModelResources(
			ObjectActionLocalService objectActionLocalService,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionTreeFactory objectDefinitionTreeFactory,
			List<String> rootDescendantNodeObjectDefinitionClassNames,
			ObjectDefinition rootNodeObjectDefinition,
			Map<Long, List<ObjectRelationship>> objectRelationshipsMap,
			List<ObjectAction> standaloneObjectActions)
		throws Exception {

		int weight = _INITIAL_WEIGHT;

		Tree tree = null;

		if (objectRelationshipsMap == null) {
			tree = objectDefinitionTreeFactory.create(
				rootNodeObjectDefinition.getObjectDefinitionId());
		}
		else {
			tree = objectDefinitionTreeFactory.create(
				true, true, rootNodeObjectDefinition.getObjectDefinitionId(),
				pk -> ListUtil.filter(
					objectRelationshipsMap.getOrDefault(
						pk, Collections.emptyList()),
					ObjectRelationship::isEdge));
		}

		Iterator<Node> iterator = tree.iterator();

		String modelResources = StringPool.BLANK;

		while (iterator.hasNext()) {
			Node node = iterator.next();

			if (node.isRoot()) {
				continue;
			}

			ObjectDefinition rootDescendantNodeObjectDefinition =
				objectDefinitionPersistence.findByPrimaryKey(
					node.getPrimaryKey());

			rootDescendantNodeObjectDefinitionClassNames.add(
				rootDescendantNodeObjectDefinition.getClassName());

			String objectActionPermissionKeys = _getObjectActionPermissionKeys(
				objectActionLocalService, node.getPrimaryKey(),
				standaloneObjectActions);

			if (Validator.isNull(objectActionPermissionKeys)) {
				continue;
			}

			modelResources = StringBundler.concat(
				modelResources, "<model-resource><model-name>",
				rootDescendantNodeObjectDefinition.getClassName(),
				"</model-name><portlet-ref><portlet-name>",
				rootNodeObjectDefinition.getPortletId(),
				"</portlet-name></portlet-ref><weight>", weight++,
				"</weight><permissions><supports>", objectActionPermissionKeys,
				"</supports><site-member-defaults>",
				"</site-member-defaults><guest-defaults>",
				"</guest-defaults><guest-unsupported>",
				objectActionPermissionKeys,
				"</guest-unsupported></permissions></model-resource>");
		}

		return modelResources;
	}

	private static Document _readDocument(
			ObjectActionLocalService objectActionLocalService,
			ObjectDefinition objectDefinition,
			Map<Long, List<ObjectRelationship>> objectRelationshipsMap,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			ObjectDefinitionTreeFactory objectDefinitionTreeFactory,
			List<String> rootDescendantNodeObjectDefinitionClassNames,
			List<ObjectAction> standaloneObjectActions)
		throws Exception {

		String objectActionPermissionKeys = _getObjectActionPermissionKeys(
			objectActionLocalService, objectDefinition.getObjectDefinitionId(),
			standaloneObjectActions);

		String resourceActionsFileName =
			"resource-actions/resource-actions.xml.tpl";

		if (!StringUtil.equals(
				objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT)) {

			resourceActionsFileName =
				"resource-actions/resource-actions-nondefault-storage-type." +
					"xml.tpl";
		}

		return SAXReaderUtil.read(
			StringUtil.replace(
				StringUtil.read(
					ObjectDefinitionResourcePermissionUtil.class.
						getClassLoader(),
					resourceActionsFileName),
				new String[] {
					"[$MODEL_NAME$]", "[$PERMISSIONS_GUEST_UNSUPPORTED$]",
					"[$PERMISSIONS_SUPPORTS$]", "[$PORTLET_NAME$]",
					"[$RESOURCE_NAME$]",
					"[%ROOT_DESCENDANT_NODE_OBJECT_DEFINITIONS_MODEL_" +
						"RESOURCES%]"
				},
				new String[] {
					objectDefinition.getClassName(),
					_getPermissionsGuestUnsupported(objectDefinition) +
						objectActionPermissionKeys,
					_getPermissionsSupports(objectDefinition) +
						objectActionPermissionKeys,
					objectDefinition.getPortletId(),
					objectDefinition.getResourceName(),
					_getRootDescendantNodeObjectDefinitionsModelResources(
						objectActionLocalService, objectDefinitionPersistence,
						objectDefinitionTreeFactory,
						rootDescendantNodeObjectDefinitionClassNames,
						objectDefinition, objectRelationshipsMap,
						standaloneObjectActions)
				}));
	}

	private static final int _INITIAL_WEIGHT = 3;

	private static final Map<ObjectDefinition, Document>
		_objectDefinitionResourceActionDocumentsMap = new ConcurrentHashMap<>();

}