/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.definition.tree.Edge;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Iterator;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/unbind_object_definition"
	},
	service = MVCResourceCommand.class
)
public class UnbindObjectDefinitionMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				ParamUtil.getLong(resourceRequest, "objectDefinitionId"));

		Tree tree = _treeFactory.create(
			objectDefinition.getRootObjectDefinitionId());

		Iterator<Node> iterator = tree.iterator(
			objectDefinition.getObjectDefinitionId());

		while (iterator.hasNext()) {
			Node node = iterator.next();

			_objectDefinitionLocalService.updateRootObjectDefinitionId(
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

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private TreeFactory _treeFactory;

}