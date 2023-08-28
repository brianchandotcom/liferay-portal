/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test.util;

import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.definition.tree.Edge;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;

/**
 * @author Feliphe Marinho
 */
public class TreeTestUtil {

	public static void assertTree(
			Map<String, String[]> expectedMap, Tree actualTree,
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws PortalException {

		Map<String, String[]> actualMap = new LinkedHashMap<>();

		Iterator<Node> iterator = actualTree.iterator();

		while (iterator.hasNext()) {
			Node node = iterator.next();

			actualMap.put(
				_getShortName(node, objectDefinitionLocalService),
				TransformUtil.transformToArray(
					node.getChildNodes(),
					childNode -> _getShortName(
						childNode, objectDefinitionLocalService),
					String.class));
		}

		AssertUtils.assertEquals(expectedMap, actualMap);
	}

	public static void bind(
			MVCResourceCommand mvcResourceCommand,
			List<ObjectRelationship> objectRelationships,
			PortletLocalService portletLocalService)
		throws PortletException {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.addParameter(
			"objectRelationshipIds",
			TransformUtil.transformToArray(
				objectRelationships,
				objectRelationship -> String.valueOf(
					objectRelationship.getObjectRelationshipId()),
				String.class));

		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG,
			PortletConfigFactoryUtil.create(
				portletLocalService.getPortletById(
					ObjectPortletKeys.OBJECT_DEFINITIONS),
				null));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getSiteDefault());
		themeDisplay.setTimeZone(TimeZoneUtil.getDefault());

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mvcResourceCommand.serveResource(mockLiferayResourceRequest, null);
	}

	public static Tree createTree(
			MVCResourceCommand bindObjectDefinitionsMVCResourceCommand,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			PortletLocalService portletLocalService, TreeFactory treeFactory)
		throws PortalException, PortletException {

		ObjectDefinition objectDefinitionA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"A", objectDefinitionLocalService);

		ObjectDefinition objectDefinitionAA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"AA", objectDefinitionLocalService);

		bind(
			bindObjectDefinitionsMVCResourceCommand,
			Arrays.asList(
				ObjectRelationshipTestUtil.addObjectRelationship(
					objectRelationshipLocalService, objectDefinitionA,
					objectDefinitionAA),
				ObjectRelationshipTestUtil.addObjectRelationship(
					objectRelationshipLocalService, objectDefinitionAA,
					ObjectDefinitionTestUtil.addObjectDefinition(
						"AAA", objectDefinitionLocalService)),
				ObjectRelationshipTestUtil.addObjectRelationship(
					objectRelationshipLocalService, objectDefinitionAA,
					ObjectDefinitionTestUtil.addObjectDefinition(
						"AAB", objectDefinitionLocalService)),
				ObjectRelationshipTestUtil.addObjectRelationship(
					objectRelationshipLocalService, objectDefinitionA,
					ObjectDefinitionTestUtil.addObjectDefinition(
						"AB", objectDefinitionLocalService))),
			portletLocalService);

		return treeFactory.create(objectDefinitionA.getObjectDefinitionId());
	}

	public static ObjectRelationship getEdgeObjectRelationship(
			ObjectDefinition objectDefinition,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			Tree tree)
		throws PortalException {

		Node node = tree.getNode(objectDefinition.getObjectDefinitionId());

		Edge edge = node.getEdge();

		return objectRelationshipLocalService.getObjectRelationship(
			edge.getObjectRelationshipId());
	}

	private static String _getShortName(
			Node node,
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				node.getObjectDefinitionId());

		return objectDefinition.getShortName();
	}

}