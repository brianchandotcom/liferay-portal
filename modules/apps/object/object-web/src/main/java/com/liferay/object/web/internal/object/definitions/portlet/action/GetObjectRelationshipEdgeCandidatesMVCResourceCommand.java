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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;

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
		"mvc.command.name=/object_definitions/get_object_relationship_edge_candidates"
	},
	service = MVCResourceCommand.class
)
public class GetObjectRelationshipEdgeCandidatesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONArray edgeCandidatesJSONArray = _jsonFactory.createJSONArray();

		Locale locale = _portal.getLocale(resourceRequest);

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.
				getObjectRelationshipsByObjectDefinitionId2(
					_getObjectDefinitionId(resourceRequest));

		for (ObjectRelationship objectRelationship : objectRelationships) {
			if (!objectRelationship.isEdgeCandidate()) {
				continue;
			}

			ObjectDefinition objectDefinition1 =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			if (objectDefinition1.getRootObjectDefinitionId() == 0) {
				edgeCandidatesJSONArray.put(
					JSONUtil.put(
						"label",
						StringUtil.appendParentheticalSuffix(
							objectDefinition1.getLabel(locale),
							objectRelationship.getLabel(locale))
					).put(
						"objectRelationshipId",
						objectRelationship.getObjectRelationshipId()
					));

				continue;
			}

			Tree tree = _treeFactory.create(
				objectDefinition1.getRootObjectDefinitionId());

			int depth = ParamUtil.getInteger(resourceRequest, "depth");

			if (!_meetsTreeMaxHeight(depth, objectDefinition1, tree)) {
				continue;
			}

			JSONArray ancestorsJSONArray = _jsonFactory.createJSONArray();

			List<Edge> edges = tree.getAncestorEdges(
				objectDefinition1.getObjectDefinitionId());

			for (Edge edge : edges) {
				ancestorsJSONArray.put(
					JSONUtil.put(
						"label",
						() -> {
							ObjectRelationship edgeObjectRelationship =
								_objectRelationshipLocalService.
									getObjectRelationship(
										edge.getObjectRelationshipId());

							ObjectDefinition objectDefinition =
								_objectDefinitionLocalService.
									getObjectDefinition(
										edgeObjectRelationship.
											getObjectDefinitionId1());

							return StringUtil.appendParentheticalSuffix(
								objectDefinition.getLabel(locale),
								edgeObjectRelationship.getLabel(locale));
						}
					).put(
						"objectRelationshipId", edge.getObjectRelationshipId()
					));
			}

			edgeCandidatesJSONArray.put(
				JSONUtil.put(
					"ancestors", ancestorsJSONArray
				).put(
					"label",
					StringUtil.appendParentheticalSuffix(
						objectDefinition1.getLabel(locale),
						objectRelationship.getLabel(locale))
				).put(
					"objectRelationshipId",
					objectRelationship.getObjectRelationshipId()
				).put(
					"root",
					objectDefinition1.getObjectDefinitionId() ==
						objectDefinition1.getRootObjectDefinitionId()
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, edgeCandidatesJSONArray);
	}

	private long _getObjectDefinitionId(ResourceRequest resourceRequest)
		throws Exception {

		long objectRelationshipId = ParamUtil.getLong(
			resourceRequest, "objectRelationshipId");

		if (objectRelationshipId != 0) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					ParamUtil.getLong(resourceRequest, "objectRelationshipId"));

			return objectRelationship.getObjectDefinitionId1();
		}

		return ParamUtil.getLong(resourceRequest, "objectDefinitionId");
	}

	private boolean _meetsTreeMaxHeight(
		int depth, ObjectDefinition objectDefinition, Tree tree) {

		Node node = tree.getNode(objectDefinition.getObjectDefinitionId());

		if ((node.getDepth() + depth + 1) > Tree.MAX_HEIGHT) {
			return false;
		}

		return true;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TreeFactory _treeFactory;

}