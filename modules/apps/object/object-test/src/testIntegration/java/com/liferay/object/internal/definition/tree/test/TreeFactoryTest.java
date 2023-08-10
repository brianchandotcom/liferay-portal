/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.definition.tree.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class TreeFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_rootObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				false, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						StringUtil.randomId()
					).build()));

		_rootObjectDefinition =
			_objectDefinitionLocalService.updateRootObjectDefinitionId(
				_rootObjectDefinition.getObjectDefinitionId(),
				_rootObjectDefinition.getObjectDefinitionId());

		ObjectDefinition objectDefinitionAA = _createObjectDefinition("AA");

		_relateObjectDefinitions(
			_rootObjectDefinition, objectDefinitionAA,
			_createObjectDefinition("AB"));

		_relateObjectDefinitions(
			objectDefinitionAA, _createObjectDefinition("AAA"),
			_createObjectDefinition("AAB"));
	}

	@Test
	public void testCreate() throws PortalException {
		Tree tree = _treeFactory.create(
			_rootObjectDefinition.getObjectDefinitionId());

		Assert.assertEquals("A, AA, AB, AAA, AAB", _toString(tree));
	}

	private static ObjectDefinition _createObjectDefinition(
			String objectDefinitionName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				objectDefinitionName, null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				false, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						StringUtil.randomId()
					).build()));

		return _objectDefinitionLocalService.updateRootObjectDefinitionId(
			objectDefinition.getObjectDefinitionId(),
			_rootObjectDefinition.getObjectDefinitionId());
	}

	private static void _relateObjectDefinitions(
			ObjectDefinition objectDefinition1,
			ObjectDefinition... objectDefinitions2)
		throws Exception {

		for (ObjectDefinition objectDefinition2 : objectDefinitions2) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.addObjectRelationship(
					TestPropsValues.getUserId(),
					objectDefinition1.getObjectDefinitionId(),
					objectDefinition2.getObjectDefinitionId(), 0,
					ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					StringUtil.randomId(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			_objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship.getObjectRelationshipId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE, true,
				objectRelationship.getLabelMap());
		}
	}

	private String _toString(Tree tree) {
		Iterator<Node> iterator = tree.iterator();

		StringBundler sb = new StringBundler(2);

		iterator.forEachRemaining(
			node -> {
				if (!node.isRoot()) {
					sb.append(", ");
				}

				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.fetchObjectDefinition(
						node.getObjectDefinitionId());

				sb.append(objectDefinition.getShortName());
			});

		return sb.toString();
	}

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private static ObjectRelationshipLocalService
		_objectRelationshipLocalService;

	private static ObjectDefinition _rootObjectDefinition;

	@Inject
	private TreeFactory _treeFactory;

}