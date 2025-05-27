/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.jaxrs.container.request.filter;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.tree.Edge;
import com.liferay.object.tree.Node;
import com.liferay.object.tree.ObjectDefinitionTreeFactory;
import com.liferay.object.tree.Tree;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.util.NestedFieldsContextUtil;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Carlos Correa
 */
@Provider
public class NestedFieldsContainerRequestFilter
	implements ContainerRequestFilter {

	public NestedFieldsContainerRequestFilter(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
		throws IOException {

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		if (nestedFieldsContext == null) {
			return;
		}

		List<String> nestedFields = new ArrayList<>(
			nestedFieldsContext.getNestedFields());

		if (!nestedFields.remove("rootModelHierarchy")) {
			return;
		}

		int treeHeight = 1;

		try {
			ObjectDefinitionTreeFactory objectDefinitionTreeFactory =
				new ObjectDefinitionTreeFactory(
					_objectDefinitionLocalService,
					_objectRelationshipLocalService);

			Tree tree = objectDefinitionTreeFactory.create(
				_objectDefinition.getObjectDefinitionId());

			treeHeight += tree.getHeight(tree.getRootNode());

			Iterator<Node> iterator = tree.iterator();

			while (iterator.hasNext()) {
				Node node = iterator.next();

				List<Node> childNodes = node.getChildNodes();

				if (ListUtil.isEmpty(childNodes)) {
					continue;
				}

				for (int i = childNodes.size() - 1; i >= 0; i--) {
					Node childNode = childNodes.get(i);

					Edge edge = childNode.getEdge();

					if (edge == null) {
						continue;
					}

					ObjectRelationship objectRelationship =
						_objectRelationshipLocalService.getObjectRelationship(
							edge.getObjectRelationshipId());

					nestedFields.add(objectRelationship.getName());
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			return;
		}

		ListUtil.distinct(nestedFields);

		NestedFieldsContextThreadLocal.setNestedFieldsContext(
			new NestedFieldsContext(
				NestedFieldsContextUtil.limitDepth(treeHeight),
				nestedFieldsContext.getMessage(), nestedFields,
				nestedFieldsContext.getPathParameters(),
				nestedFieldsContext.getQueryParameters(),
				nestedFieldsContext.getResourceVersion()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NestedFieldsContainerRequestFilter.class);

	@Context
	private ObjectDefinition _objectDefinition;

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}