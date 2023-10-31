/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource.util;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Iterator;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectDefinitionResourcePermissionUtil {

	public static final int INITIAL_WEIGHT = 3;

	public static void populateResourceActions(
			ObjectActionLocalService objectActionLocalService,
			ObjectDefinition objectDefinition,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			PortletLocalService portletLocalService,
			ResourceActions resourceActions, TreeFactory treeFactory)
		throws Exception {

		if (objectDefinition.isRootDescendantNode()) {
			return;
		}

		ClassLoader classLoader =
			ObjectDefinitionResourcePermissionUtil.class.getClassLoader();

		String objectActionPermissionKeys = StringPool.BLANK;

		for (ObjectAction objectAction :
				objectActionLocalService.getObjectActions(
					objectDefinition.getObjectDefinitionId(),
					ObjectActionTriggerConstants.KEY_STANDALONE)) {

			objectActionPermissionKeys = StringBundler.concat(
				objectActionPermissionKeys, "<action-key>",
				objectAction.getName(), "</action-key>");
		}

		String resourceActionsFileName =
			"resource-actions/resource-actions.xml.tpl";

		if (!StringUtil.equals(
				objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT)) {

			resourceActionsFileName =
				"resource-actions/resource-actions-nondefault-storage-type." +
					"xml.tpl";
		}

		Document document = SAXReaderUtil.read(
			StringUtil.replace(
				StringUtil.read(classLoader, resourceActionsFileName),
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
						objectActionLocalService, objectDefinition,
						objectDefinitionPersistence, treeFactory)
				}));

		resourceActions.populateModelResources(document);

		Portlet portlet = portletLocalService.getPortletById(
			objectDefinition.getCompanyId(), objectDefinition.getPortletId());

		if (portlet != null) {
			resourceActions.populatePortletResource(
				portlet, classLoader, document);
		}
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

		return permissionsSupports;
	}

	private static String _getRootDescendantNodeObjectDefinitionsModelResources(
			ObjectActionLocalService objectActionLocalService,
			ObjectDefinition objectDefinition,
			ObjectDefinitionPersistence objectDefinitionPersistence,
			TreeFactory treeFactory)
		throws Exception {

		if (!objectDefinition.isRootNode()) {
			return StringPool.BLANK;
		}

		int weight = INITIAL_WEIGHT;

		Tree tree = treeFactory.create(
			objectDefinition.getObjectDefinitionId());

		Iterator<Node> iterator = tree.iterator();

		String modelResources = StringPool.BLANK;

		while (iterator.hasNext()) {
			Node node = iterator.next();

			if (Objects.equals(
					node.getObjectDefinitionId(),
					objectDefinition.getObjectDefinitionId())) {

				continue;
			}

			String objectActionPermissionKeys = StringPool.BLANK;

			for (ObjectAction objectAction :
					objectActionLocalService.getObjectActions(
						node.getObjectDefinitionId(),
						ObjectActionTriggerConstants.KEY_STANDALONE)) {

				objectActionPermissionKeys = StringBundler.concat(
					objectActionPermissionKeys, "<action-key>",
					objectAction.getName(), "</action-key>");
			}

			if (StringUtil.equals(
					objectActionPermissionKeys, StringPool.BLANK)) {

				continue;
			}

			ObjectDefinition rootDescendantNodeObjectDefinition =
				objectDefinitionPersistence.findByPrimaryKey(
					node.getObjectDefinitionId());

			modelResources = StringBundler.concat(
				modelResources, "<model-resource><model-name>",
				rootDescendantNodeObjectDefinition.getClassName(),
				"</model-name><portlet-ref><portlet-name>",
				objectDefinition.getPortletId(), "</portlet-name>",
				"</portlet-ref><weight>", weight++, "</weight><permissions>",
				"<supports>", objectActionPermissionKeys,
				"</supports><site-member-defaults>",
				"</site-member-defaults><guest-defaults>",
				"</guest-defaults><guest-unsupported>",
				objectActionPermissionKeys,
				"</guest-unsupported></permissions></model-resource>");
		}

		return modelResources;
	}

}