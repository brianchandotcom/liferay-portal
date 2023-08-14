/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.service.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.test.util.TreeTestUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class BindObjectDefinitionsMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testBindObjectDefinitions() throws Exception {

		// Bind object definitions creating a new hierarchical structure

		ObjectDefinition objectDefinitionA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"A", _objectDefinitionLocalService);

		ObjectDefinition objectDefinitionAA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"AA", _objectDefinitionLocalService);

		ObjectRelationship objectRelationshipA_AA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, objectDefinitionA,
				objectDefinitionAA);

		TreeTestUtil.bind(
			_mvcResourceCommand,
			Arrays.asList(
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, objectDefinitionAA,
					ObjectDefinitionTestUtil.addObjectDefinition(
						"AAA", _objectDefinitionLocalService)),
				objectRelationshipA_AA),
			_portletLocalService);

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[] {"AA"}
			).put(
				"AA", new String[] {"AAA"}
			).put(
				"AAA", new String[0]
			).build(),
			_treeFactory.create(objectDefinitionA.getObjectDefinitionId()),
			_objectDefinitionLocalService);

		// Bind one object definition to an existing hierarchical structure

		TreeTestUtil.bind(
			_mvcResourceCommand,
			Arrays.asList(
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, objectDefinitionAA,
					ObjectDefinitionTestUtil.addObjectDefinition(
						"AAB", _objectDefinitionLocalService)),
				objectRelationshipA_AA),
			_portletLocalService);

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[] {"AA"}
			).put(
				"AA", new String[] {"AAA", "AAB"}
			).put(
				"AAA", new String[0]
			).put(
				"AAB", new String[0]
			).build(),
			_treeFactory.create(objectDefinitionA.getObjectDefinitionId()),
			_objectDefinitionLocalService);
	}

	@Inject(
		filter = "mvc.command.name=/object_definitions/bind_object_definitions"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private TreeFactory _treeFactory;

}