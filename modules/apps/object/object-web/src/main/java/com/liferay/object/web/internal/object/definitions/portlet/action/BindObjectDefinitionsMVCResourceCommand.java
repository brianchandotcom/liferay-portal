/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"mvc.command.name=/object_definitions/bind_object_definitions"
	},
	service = MVCResourceCommand.class
)
public class BindObjectDefinitionsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long[] objectRelationshipIds = ParamUtil.getLongValues(
			resourceRequest, "objectRelationshipIds");

		ObjectRelationship rootObjectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipIds[objectRelationshipIds.length - 1]);

		long rootObjectDefinitionId =
			rootObjectRelationship.getObjectDefinitionId1();

		ObjectDefinition rootObjectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				rootObjectDefinitionId);

		if (rootObjectDefinition.getRootObjectDefinitionId() == 0) {
			_objectDefinitionLocalService.updateRootObjectDefinitionId(
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
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			if (objectDefinition1.getRootObjectDefinitionId() == 0) {
				_objectDefinitionLocalService.updateRootObjectDefinitionId(
					objectDefinition1.getObjectDefinitionId(),
					rootObjectDefinitionId);
			}

			ObjectDefinition objectDefinition2 =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId2());

			_objectDefinitionLocalService.updateRootObjectDefinitionId(
				objectDefinition2.getObjectDefinitionId(),
				rootObjectDefinitionId);
		}
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}