/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.tree.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationRegistryUtil;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.definition.tree.util.ObjectDefinitionTreeUtil;
import com.liferay.object.exception.ObjectRelationshipEdgeException;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectActionTestUtil;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.test.util.TreeTestUtil;
import com.liferay.object.tree.Node;
import com.liferay.object.tree.ObjectDefinitionTreeFactory;
import com.liferay.object.tree.Tree;
import com.liferay.object.tree.constants.TreeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlag("LPD-34594")
@RunWith(Arquillian.class)
public class ObjectDefinitionTreeUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinitionTreeFactory = new ObjectDefinitionTreeFactory(
			_objectDefinitionLocalService, _objectRelationshipLocalService);

		_draftObjectDefinitionA =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();
		_draftObjectDefinitionAA =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipA_AA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionA,
				_draftObjectDefinitionAA);

		_draftObjectDefinitionAAA =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipAA_AAA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionAA,
				_draftObjectDefinitionAAA);

		_draftObjectDefinitionAAAA =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipAAA_AAAA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionAAA,
				_draftObjectDefinitionAAAA);

		_draftObjectDefinitionAAAAA =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipAAAA_AAAAA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionAAAA,
				_draftObjectDefinitionAAAAA);

		_draftObjectDefinitionAAAAAA =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipAAAAA_AAAAAA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionAAAAA,
				_draftObjectDefinitionAAAAAA);

		_draftObjectDefinitionB =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();
		_draftObjectDefinitionBB =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipB_BB =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionB,
				_draftObjectDefinitionBB);

		_draftObjectDefinitionBBB =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipBB_BBB =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionBB,
				_draftObjectDefinitionBBB);

		_draftObjectDefinitionC =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();
		_draftObjectDefinitionCC =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipC_CC =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionC,
				_draftObjectDefinitionCC);

		_draftObjectDefinitionCCC =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_draftObjectRelationshipCC_CCC =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _draftObjectDefinitionCC,
				_draftObjectDefinitionCCC);

		_publishedObjectDefinitionA =
			ObjectDefinitionTestUtil.publishObjectDefinition();
		_publishedObjectDefinitionAA =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		_publishedObjectRelationshipA_AA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _publishedObjectDefinitionA,
				_publishedObjectDefinitionAA);

		_publishedObjectRelationshipA_AAObjectField2 =
			_objectFieldLocalService.fetchObjectField(
				_publishedObjectRelationshipA_AA.getObjectFieldId2());

		_publishedObjectDefinitionAB =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		_publishedObjectRelationshipA_AB =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _publishedObjectDefinitionA,
				_publishedObjectDefinitionAB);

		_publishedObjectDefinitionAAA =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		_publishedObjectRelationshipAA_AAA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _publishedObjectDefinitionAA,
				_publishedObjectDefinitionAAA);

		_publishedObjectRelationshipAA_AAAObjectField2 =
			_objectFieldLocalService.fetchObjectField(
				_publishedObjectRelationshipAA_AAA.getObjectFieldId2());

		_publishedObjectDefinitionAAAA =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		_publishedObjectRelationshipAAA_AAAA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _publishedObjectDefinitionAAA,
				_publishedObjectDefinitionAAAA);

		_publishedObjectDefinitionAAAAA =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		_publishedObjectRelationshipAAAA_AAAAA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _publishedObjectDefinitionAAAA,
				_publishedObjectDefinitionAAAAA);

		_publishedObjectDefinitionB =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		_publishedObjectRelationshipB_AA =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _publishedObjectDefinitionB,
				_publishedObjectDefinitionAA);

		_publishedObjectDefinitionBB =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		_publishedObjectRelationshipB_BB =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _publishedObjectDefinitionB,
				_publishedObjectDefinitionBB);

		_publishedObjectRelationshipB_AAObjectField2 =
			_objectFieldLocalService.fetchObjectField(
				_publishedObjectRelationshipB_AA.getObjectFieldId2());
	}

	@After
	public void tearDown() {
		ObjectDefinitionTreeUtil.invalidate();
	}

	@Test
	public void testBindDescendantObjectDefinitionAndObjectDefinition()
		throws Exception {

		_testBindDescendantObjectDefinitionAndObjectDefinition(
			_draftObjectDefinitionA, _draftObjectDefinitionAA,
			_draftObjectDefinitionAAA, _draftObjectRelationshipA_AA,
			_draftObjectRelationshipAA_AAA);
		_testBindDescendantObjectDefinitionAndObjectDefinition(
			_publishedObjectDefinitionA, _publishedObjectDefinitionAA,
			_publishedObjectDefinitionAAA, _publishedObjectRelationshipA_AA,
			_publishedObjectRelationshipAA_AAA);

		_assertScreenNavigationCategories(2, _publishedObjectDefinitionA);
		_assertScreenNavigationCategories(2, _publishedObjectDefinitionAA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAAA);
	}

	@Test
	public void testBindDescendantObjectDefinitionAndObjectDefinitionWithGreaterThanTreeMaxHeight()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_draftObjectRelationshipA_AA, _draftObjectRelationshipAA_AAA,
				_draftObjectRelationshipAAA_AAAA,
				_draftObjectRelationshipAAAA_AAAAA));

		Tree tree = _objectDefinitionTreeFactory.create(
			_draftObjectDefinitionA.getObjectDefinitionId());

		Assert.assertEquals(
			TreeConstants.MAX_HEIGHT, tree.getHeight(tree.getRootNode()));

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			"The object relationship cannot be an edge in the root context " +
				"because it would exceed the tree's maximum height",
			() -> TreeTestUtil.bind(
				_objectRelationshipLocalService,
				List.of(_draftObjectRelationshipAAAAA_AAAAAA)));
	}

	@Test
	public void testBindDescendantObjectDefinitionAndObjectDefinitionWithObjectEntries()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		ObjectEntry objectEntryA = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionA.getObjectDefinitionId(), Map.of());

		ObjectEntry objectEntryAA = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionAA.getObjectDefinitionId(),
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA.getObjectEntryId()));

		ObjectEntry objectEntryAAA = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionAAA.getObjectDefinitionId(),
			Map.of(
				_publishedObjectRelationshipAA_AAAObjectField2.getName(),
				objectEntryAA.getObjectEntryId()));

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAAA, 0L
			).build());

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipAA_AAA));

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAAA, objectEntryA.getObjectEntryId()
			).build());
	}

	@Test
	public void testBindDescendantObjectDefinitionAndRootObjectDefinition()
		throws Exception {

		_testBindDescendantObjectDefinitionAndRootObjectDefinition(
			_draftObjectDefinitionA, _draftObjectDefinitionAA,
			_draftObjectDefinitionAAA, _draftObjectDefinitionAAAA,
			_draftObjectRelationshipA_AA, _draftObjectRelationshipAA_AAA,
			_draftObjectRelationshipAAA_AAAA);
		_testBindDescendantObjectDefinitionAndRootObjectDefinition(
			_publishedObjectDefinitionA, _publishedObjectDefinitionAA,
			_publishedObjectDefinitionAAA, _publishedObjectDefinitionAAAA,
			_publishedObjectRelationshipA_AA,
			_publishedObjectRelationshipAA_AAA,
			_publishedObjectRelationshipAAA_AAAA);

		_assertScreenNavigationCategories(2, _publishedObjectDefinitionA);
		_assertScreenNavigationCategories(2, _publishedObjectDefinitionAA);
		_assertScreenNavigationCategories(2, _publishedObjectDefinitionAAA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAAAA);
	}

	@Test
	public void testBindDescendantObjectDefinitionAndRootObjectDefinitionWithGreaterThanTreeMaxHeight()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_draftObjectRelationshipA_AA, _draftObjectRelationshipAA_AAA,
				_draftObjectRelationshipAAAA_AAAAA,
				_draftObjectRelationshipAAAAA_AAAAAA));

		Tree treeA = _objectDefinitionTreeFactory.create(
			_draftObjectDefinitionA.getObjectDefinitionId());

		Node nodeAAA = treeA.getNode(_draftObjectDefinitionAAA.getPrimaryKey());

		Assert.assertEquals(2, nodeAAA.getDepth());

		Tree treeAAA = _objectDefinitionTreeFactory.create(
			_draftObjectDefinitionA.getObjectDefinitionId());

		Assert.assertEquals(2, treeAAA.getHeight(treeAAA.getRootNode()));

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			"The object relationship cannot be an edge in the root context " +
				"because it would exceed the tree's maximum height",
			() -> TreeTestUtil.bind(
				_objectRelationshipLocalService,
				List.of(_draftObjectRelationshipAAA_AAAA)));
	}

	@Test
	public void testBindDescendantObjectDefinitionsWithGreaterThanTreeMaxHeight()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_draftObjectRelationshipA_AA, _draftObjectRelationshipAA_AAA,
				_draftObjectRelationshipAAA_AAAA,
				_draftObjectRelationshipAAAA_AAAAA,
				_draftObjectRelationshipB_BB, _draftObjectRelationshipBB_BBB));

		Tree treeA = _objectDefinitionTreeFactory.create(
			_draftObjectDefinitionA.getObjectDefinitionId());

		Node nodeAAA = treeA.getNode(_draftObjectDefinitionAAA.getPrimaryKey());

		Assert.assertEquals(2, nodeAAA.getDepth());
		Assert.assertEquals(2, treeA.getHeight(nodeAAA));

		Tree treeB = _objectDefinitionTreeFactory.create(
			_draftObjectDefinitionB.getObjectDefinitionId());

		Node nodeBBB = treeA.getNode(_draftObjectDefinitionBBB.getPrimaryKey());

		Assert.assertEquals(4, nodeBBB.getDepth());
		Assert.assertEquals(0, treeB.getHeight(nodeBBB));

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			"The object relationship cannot be an edge in the root context " +
				"because it would exceed the tree's maximum height",
			() -> TreeTestUtil.bind(
				_draftObjectDefinitionBBB.getObjectDefinitionId(),
				_draftObjectDefinitionAAA.getObjectDefinitionId(),
				_objectRelationshipLocalService));
	}

	@Test
	public void testBindDraftDescendantObjectDefinitionAndDraftDescendantObjectDefinition()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_draftObjectRelationshipA_AA, _draftObjectRelationshipB_BB,
				_draftObjectRelationshipBB_BBB));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionB,
				new ObjectDefinition[] {_draftObjectDefinitionB}
			).put(
				_draftObjectDefinitionBB,
				new ObjectDefinition[] {_draftObjectDefinitionB}
			).put(
				_draftObjectDefinitionBBB,
				new ObjectDefinition[] {_draftObjectDefinitionB}
			).build());

		TreeTestUtil.bind(
			_draftObjectDefinitionAA.getObjectDefinitionId(),
			_draftObjectDefinitionBB.getObjectDefinitionId(),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionB,
				new ObjectDefinition[] {_draftObjectDefinitionB}
			).put(
				_draftObjectDefinitionBB,
				new ObjectDefinition[] {
					_draftObjectDefinitionA, _draftObjectDefinitionB
				}
			).put(
				_draftObjectDefinitionBBB,
				new ObjectDefinition[] {
					_draftObjectDefinitionA, _draftObjectDefinitionB
				}
			).build());
	}

	@Test
	public void testBindDraftDescendantObjectDefinitionAndPublishedObjectDefinition()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_draftObjectRelationshipA_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAA, new ObjectDefinition[0]
			).build());

		TreeTestUtil.bind(
			_draftObjectDefinitionAA.getObjectDefinitionId(),
			_publishedObjectDefinitionAAA.getObjectDefinitionId(),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAA}
			).build());

		_assertScreenNavigationCategories(0, _draftObjectDefinitionA);
		_assertScreenNavigationCategories(0, _draftObjectDefinitionAA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAAA);
	}

	@Test
	public void testBindDraftDescendantObjectDefinitionAndPublishedRootObjectDefinition()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_draftObjectRelationshipA_AA,
				_publishedObjectRelationshipAAA_AAAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAA}
			).put(
				_publishedObjectDefinitionAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAA}
			).build());

		TreeTestUtil.bind(
			_draftObjectDefinitionAA.getObjectDefinitionId(),
			_publishedObjectDefinitionAAA.getObjectDefinitionId(),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAA}
			).put(
				_publishedObjectDefinitionAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAA}
			).build());

		_assertScreenNavigationCategories(0, _draftObjectDefinitionA);
		_assertScreenNavigationCategories(0, _draftObjectDefinitionAA);
		_assertScreenNavigationCategories(2, _publishedObjectDefinitionAAA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAAAA);
	}

	@Test
	public void testBindDraftObjectDefinitionAndPublishedObjectDefinition()
		throws Exception {

		TreeTestUtil.bind(
			_draftObjectDefinitionA.getObjectDefinitionId(),
			_publishedObjectDefinitionAA.getObjectDefinitionId(),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAA}
			).build());

		_assertScreenNavigationCategories(0, _draftObjectDefinitionA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAA);
	}

	@Test
	public void testBindDraftObjectDefinitionAndPublishedRootObjectDefinition()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipAA_AAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA, new ObjectDefinition[0]
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAA}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAA}
			).build());

		TreeTestUtil.bind(
			_draftObjectDefinitionA.getObjectDefinitionId(),
			_publishedObjectDefinitionAA.getObjectDefinitionId(),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAA}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAA}
			).build());

		_assertScreenNavigationCategories(0, _draftObjectDefinitionA);
		_assertScreenNavigationCategories(2, _publishedObjectDefinitionAA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAAA);
	}

	@Test
	public void testBindObjectDefinitionAndDescendantObjectDefinitionWithObjectEntries()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		ObjectEntry objectEntryA = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionA.getObjectDefinitionId(), Map.of());
		ObjectEntry objectEntryB = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionB.getObjectDefinitionId(), Map.of());

		ObjectEntry objectEntryAA = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionAA.getObjectDefinitionId(),
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA.getObjectEntryId(),
				_publishedObjectRelationshipB_AAObjectField2.getName(),
				objectEntryB.getObjectEntryId()));

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryB, 0L
			).build());

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			"You cannot enable inheritance because there are already child " +
				"entries in the regular relationship",
			() -> TreeTestUtil.bind(
				_objectRelationshipLocalService,
				List.of(_publishedObjectRelationshipB_AA)));
	}

	@Test
	public void testBindObjectDefinitionAndRootObjectDefinition()
		throws Exception {

		_testBindObjectDefinitionAndRootObjectDefinition(
			_draftObjectDefinitionA, _draftObjectDefinitionAA,
			_draftObjectDefinitionAAA, _draftObjectRelationshipA_AA,
			_draftObjectRelationshipAA_AAA);
		_testBindObjectDefinitionAndRootObjectDefinition(
			_publishedObjectDefinitionA, _publishedObjectDefinitionAA,
			_publishedObjectDefinitionAAA, _publishedObjectRelationshipA_AA,
			_publishedObjectRelationshipAA_AAA);

		_assertScreenNavigationCategories(2, _publishedObjectDefinitionA);
		_assertScreenNavigationCategories(2, _publishedObjectDefinitionAA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAAA);
	}

	@Test
	public void testBindObjectDefinitionAndRootObjectDefinitionWithObjectEntries()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipAA_AAA));

		ObjectEntry objectEntryA1 = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionA.getObjectDefinitionId(), Map.of());

		ObjectEntry objectEntryAA1 = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionAA.getObjectDefinitionId(),
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA1.getObjectEntryId()));

		ObjectEntry objectEntryAAA1 = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionAAA.getObjectDefinitionId(),
			Map.of(
				_publishedObjectRelationshipAA_AAAObjectField2.getName(),
				objectEntryAA1.getObjectEntryId()));

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, 0L
			).put(
				objectEntryAA1, objectEntryAA1.getObjectEntryId()
			).put(
				objectEntryAAA1, objectEntryAA1.getObjectEntryId()
			).build());

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAAA1, objectEntryA1.getObjectEntryId()
			).build());
	}

	@Test
	public void testBindObjectDefinitions() throws Exception {
		_testBindObjectDefinitions(
			_draftObjectDefinitionA, _draftObjectDefinitionAA,
			_draftObjectRelationshipA_AA);
		_testBindObjectDefinitions(
			_publishedObjectDefinitionA, _publishedObjectDefinitionAA,
			_publishedObjectRelationshipA_AA);

		_assertScreenNavigationCategories(2, _publishedObjectDefinitionA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAA);
	}

	@Test
	public void testBindObjectDefinitionsWithObjectEntries() throws Exception {
		ObjectEntry objectEntryA1 = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionA.getObjectDefinitionId(), Map.of());

		ObjectEntry objectEntryAA1 = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionAA.getObjectDefinitionId(),
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA1.getObjectEntryId()));

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, 0L
			).put(
				objectEntryAA1, 0L
			).build());

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA1.getObjectEntryId()
			).build());
	}

	@Test
	public void testBindObjectDefinitionsWithOngoingWorkflowInstances()
		throws Exception {

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), 0,
			_publishedObjectDefinitionA.getClassName(), 0, 0, "Single Approver",
			1);

		ObjectEntry objectEntryA1 = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionA.getObjectDefinitionId(), Map.of());
		ObjectEntry objectEntryA2 = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionA.getObjectDefinitionId(), Map.of());

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), 0,
			_publishedObjectDefinitionAA.getClassName(), 0, 0,
			"Single Approver", 1);

		ObjectEntry objectEntryAA = ObjectEntryTestUtil.addObjectEntry(
			0, _publishedObjectDefinitionAA.getObjectDefinitionId(),
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA1.getObjectEntryId()));

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			String.format(
				"These ongoing workflow instances must be completed to " +
					"enable inheritance: \"%s\" (\"%s\" object entries) and " +
						"\"%s\" (\"%s\" object entries)",
				_publishedObjectDefinitionA.getLabel(LocaleUtil.US), 2,
				_publishedObjectDefinitionAA.getLabel(LocaleUtil.US), 1),
			() -> TreeTestUtil.bind(
				_objectRelationshipLocalService,
				List.of(_publishedObjectRelationshipA_AA)));

		_completeWorkflowTask(
			_publishedObjectDefinitionA.getClassName(),
			objectEntryA1.getObjectEntryId());

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			String.format(
				"These ongoing workflow instances must be completed to " +
					"enable inheritance: \"%s\" (\"%s\" object entries) and " +
						"\"%s\" (\"%s\" object entries)",
				_publishedObjectDefinitionA.getLabel(LocaleUtil.US), 1,
				_publishedObjectDefinitionAA.getLabel(LocaleUtil.US), 1),
			() -> TreeTestUtil.bind(
				_objectRelationshipLocalService,
				List.of(_publishedObjectRelationshipA_AA)));

		_completeWorkflowTask(
			_publishedObjectDefinitionA.getClassName(),
			objectEntryA2.getObjectEntryId());

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			String.format(
				"These ongoing workflow instances must be completed to " +
					"enable inheritance: \"%s\" (\"%s\" object entries)",
				_publishedObjectDefinitionAA.getLabel(LocaleUtil.US), 1),
			() -> TreeTestUtil.bind(
				_objectRelationshipLocalService,
				List.of(_publishedObjectRelationshipA_AA)));

		_completeWorkflowTask(
			_publishedObjectDefinitionAA.getClassName(),
			objectEntryAA.getObjectEntryId());

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		objectEntryAA = _objectEntryLocalService.getObjectEntry(
			objectEntryAA.getObjectEntryId());

		Assert.assertEquals(
			objectEntryA1.getObjectEntryId(),
			objectEntryAA.getRootObjectEntryId());
	}

	@Test
	public void testBindObjectDefinitionsWithParentObjectDefinitionsWithGreaterThanTreeMaxHeight()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_draftObjectRelationshipA_AA, _draftObjectRelationshipAA_AAA,
				_draftObjectRelationshipAAA_AAAA,
				_draftObjectRelationshipAAAA_AAAAA,
				_draftObjectRelationshipB_BB,
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, _draftObjectDefinitionBB,
					_draftObjectDefinitionAAA),
				_draftObjectRelationshipBB_BBB, _draftObjectRelationshipC_CC,
				_draftObjectRelationshipCC_CCC));

		Tree treeA = _objectDefinitionTreeFactory.create(
			_draftObjectDefinitionA.getObjectDefinitionId());

		Node nodeAAA = treeA.getNode(_draftObjectDefinitionAAA.getPrimaryKey());

		Assert.assertEquals(2, nodeAAA.getDepth());
		Assert.assertEquals(2, treeA.getHeight(nodeAAA));

		Tree treeB = _objectDefinitionTreeFactory.create(
			_draftObjectDefinitionB.getObjectDefinitionId());

		Node nodeBBB = treeA.getNode(_draftObjectDefinitionBBB.getPrimaryKey());

		Assert.assertEquals(4, nodeBBB.getDepth());
		Assert.assertEquals(0, treeB.getHeight(nodeBBB));

		Tree treeC = _objectDefinitionTreeFactory.create(
			_draftObjectDefinitionB.getObjectDefinitionId());

		Node nodeC = treeA.getRootNode();

		Assert.assertEquals(0, nodeC.getDepth());
		Assert.assertEquals(4, treeC.getHeight(nodeC));

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			"The object relationship cannot be an edge in the root context " +
				"because it would exceed the tree's maximum height",
			() -> TreeTestUtil.bind(
				_draftObjectDefinitionAAA.getObjectDefinitionId(),
				_draftObjectDefinitionC.getObjectDefinitionId(),
				_objectRelationshipLocalService));

		TreeTestUtil.bind(
			_draftObjectDefinitionAAA.getObjectDefinitionId(),
			_draftObjectDefinitionCC.getObjectDefinitionId(),
			_objectRelationshipLocalService);
	}

	@Test
	public void testBindPublishedDescendantObjectDefinitionAndDraftObjectDefinition()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAA, new ObjectDefinition[0]
			).build());

		TreeTestUtil.bind(
			_publishedObjectDefinitionAA.getObjectDefinitionId(),
			_draftObjectDefinitionAAA.getObjectDefinitionId(),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAA}
			).build());

		_assertScreenNavigationCategories(2, _publishedObjectDefinitionA);
		_assertScreenNavigationCategories(1, _publishedObjectDefinitionAA);
		_assertScreenNavigationCategories(0, _draftObjectDefinitionAAA);
	}

	@Test
	public void testBindPublishedObjectDefinitionsWithCircularReference()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			"The object relationship must not create a circular reference in " +
				"a root context",
			() -> TreeTestUtil.bind(
				_publishedObjectDefinitionAA.getObjectDefinitionId(),
				_publishedObjectDefinitionA.getObjectDefinitionId(),
				_objectRelationshipLocalService));
	}

	@Test
	public void testBindPublishedObjectDefinitionsWithDifferentScope()
		throws Exception {

		ObjectDefinition siteObjectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				List.of(
					new TextObjectFieldBuilder(
					).labelMap(
						RandomTestUtil.randomLocaleStringMap()
					).name(
						StringUtil.randomId()
					).build()),
				ObjectDefinitionConstants.SCOPE_SITE);

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			StringBundler.concat(
				"The scope of \"", _publishedObjectDefinitionA.getShortName(),
				"\" is not the same as \"", siteObjectDefinition.getShortName(),
				"\". To enable inheritance, the object definitions must have ",
				"the same scope"),
			() -> TreeTestUtil.bind(
				_publishedObjectDefinitionA.getObjectDefinitionId(),
				siteObjectDefinition.getObjectDefinitionId(),
				_objectRelationshipLocalService));
	}

	@Test
	public void testUnbindObjectDefinitions() throws Exception {
		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipA_AA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA, new ObjectDefinition[0]
			).put(
				_publishedObjectDefinitionAA, new ObjectDefinition[0]
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithDescendantNode()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipAA_AAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipA_AA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA, new ObjectDefinition[0]
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAA}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAA}
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithDescendantNodeAndGrandParentNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipAA_AAA,
				_publishedObjectRelationshipAAA_AAAA,
				_publishedObjectRelationshipB_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionAAAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipAA_AAA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAA}
			).put(
				_publishedObjectDefinitionAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAA}
			).put(
				_publishedObjectDefinitionB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithDescendantNodeAndParentNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipAA_AAA,
				_publishedObjectRelationshipB_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipB_AA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionB, new ObjectDefinition[0]
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithGrandParentNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipAA_AAA,
				_publishedObjectRelationshipB_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionAAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipAA_AAA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionAAA, new ObjectDefinition[0]
			).put(
				_publishedObjectDefinitionB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithObjectAction() throws Exception {
		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		ObjectAction objectAction = ObjectActionTestUtil.addObjectAction(
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ROOT_UPDATE,
			_publishedObjectDefinitionA,
			UnicodePropertiesBuilder.put(
				"secret", "onafterrootupdate"
			).put(
				"url", "https://onafterrootupdate.com"
			).build());

		TreeTestUtil.unbind(
			_publishedObjectRelationshipA_AA, _objectRelationshipLocalService);

		objectAction = _objectActionLocalService.fetchObjectAction(
			objectAction.getObjectActionId());

		Assert.assertEquals(
			objectAction.getObjectActionTriggerKey(),
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE);
		Assert.assertFalse(objectAction.isActive());
	}

	@Test
	public void testUnbindObjectDefinitionsWithObjectEntries()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		ObjectEntry objectEntryA1 = _addObjectEntry(
			_publishedObjectDefinitionA, Map.of());
		ObjectEntry objectEntryA2 = _addObjectEntry(
			_publishedObjectDefinitionA, Map.of());

		ObjectEntry objectEntryAA1 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA1.getObjectEntryId()));
		ObjectEntry objectEntryAA2 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA2.getObjectEntryId()));
		ObjectEntry objectEntryAA3 = _addObjectEntry(
			_publishedObjectDefinitionAA, Map.of());

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAA3, 0L
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipA_AA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, 0L
			).put(
				objectEntryA2, 0L
			).put(
				objectEntryAA1, 0L
			).put(
				objectEntryAA2, 0L
			).put(
				objectEntryAA3, 0L
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithObjectEntriesWithDescendantAndParentNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipAA_AAA,
				_publishedObjectRelationshipB_AA));

		ObjectEntry objectEntryA = _addObjectEntry(
			_publishedObjectDefinitionA, Map.of());

		ObjectEntry objectEntryAA1 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA.getObjectEntryId()));

		ObjectEntry objectEntryB = _addObjectEntry(
			_publishedObjectDefinitionB, Map.of());

		ObjectEntry objectEntryAA2 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipB_AAObjectField2.getName(),
				objectEntryB.getObjectEntryId()));

		ObjectEntry objectEntryAA3 = _addObjectEntry(
			_publishedObjectDefinitionAA, Map.of());

		ObjectEntry objectEntryAAA1 = _addObjectEntry(
			_publishedObjectDefinitionAAA,
			Map.of(
				_publishedObjectRelationshipAA_AAAObjectField2.getName(),
				objectEntryAA1.getObjectEntryId()));
		ObjectEntry objectEntryAAA2 = _addObjectEntry(
			_publishedObjectDefinitionAAA,
			Map.of(
				_publishedObjectRelationshipAA_AAAObjectField2.getName(),
				objectEntryAA2.getObjectEntryId()));
		ObjectEntry objectEntryAAA3 = _addObjectEntry(
			_publishedObjectDefinitionAAA, Map.of());

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA2, objectEntryB.getObjectEntryId()
			).put(
				objectEntryAA3, 0L
			).put(
				objectEntryAAA1, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAAA2, objectEntryB.getObjectEntryId()
			).put(
				objectEntryAAA3, 0L
			).put(
				objectEntryB, objectEntryB.getObjectEntryId()
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipB_AA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA2, 0L
			).put(
				objectEntryAA3, 0L
			).put(
				objectEntryAAA1, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAAA2, 0L
			).put(
				objectEntryAAA3, 0L
			).put(
				objectEntryB, 0L
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithObjectEntriesWithDescendantNode()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipAA_AAA));

		ObjectEntry objectEntryA1 = _addObjectEntry(
			_publishedObjectDefinitionA, Map.of());
		ObjectEntry objectEntryA2 = _addObjectEntry(
			_publishedObjectDefinitionA, Map.of());

		ObjectEntry objectEntryAA1 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA1.getObjectEntryId()));
		ObjectEntry objectEntryAA2 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA2.getObjectEntryId()));
		ObjectEntry objectEntryAA3 = _addObjectEntry(
			_publishedObjectDefinitionAA, Map.of());

		ObjectEntry objectEntryAAA1 = _addObjectEntry(
			_publishedObjectDefinitionAAA,
			Map.of(
				_publishedObjectRelationshipAA_AAAObjectField2.getName(),
				objectEntryAA1.getObjectEntryId()));
		ObjectEntry objectEntryAAA2 = _addObjectEntry(
			_publishedObjectDefinitionAAA,
			Map.of(
				_publishedObjectRelationshipAA_AAAObjectField2.getName(),
				objectEntryAA2.getObjectEntryId()));
		ObjectEntry objectEntryAAA3 = _addObjectEntry(
			_publishedObjectDefinitionAAA, Map.of());

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAA3, 0L
			).put(
				objectEntryAAA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAAA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAAA3, 0L
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipA_AA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, 0L
			).put(
				objectEntryA2, 0L
			).put(
				objectEntryAA1, objectEntryAA1.getObjectEntryId()
			).put(
				objectEntryAA2, objectEntryAA2.getObjectEntryId()
			).put(
				objectEntryAA3, 0L
			).put(
				objectEntryAAA1, objectEntryAA1.getObjectEntryId()
			).put(
				objectEntryAAA2, objectEntryAA2.getObjectEntryId()
			).put(
				objectEntryAAA3, 0L
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithObjectEntriesWithParentNode()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipAA_AAA));

		ObjectEntry objectEntryA1 = _addObjectEntry(
			_publishedObjectDefinitionA, Map.of());
		ObjectEntry objectEntryA2 = _addObjectEntry(
			_publishedObjectDefinitionA, Map.of());

		ObjectEntry objectEntryAA1 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA1.getObjectEntryId()));
		ObjectEntry objectEntryAA2 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA2.getObjectEntryId()));
		ObjectEntry objectEntryAA3 = _addObjectEntry(
			_publishedObjectDefinitionAA, Map.of());

		ObjectEntry objectEntryAAA1 = _addObjectEntry(
			_publishedObjectDefinitionAAA,
			Map.of(
				_publishedObjectRelationshipAA_AAAObjectField2.getName(),
				objectEntryAA1.getObjectEntryId()));
		ObjectEntry objectEntryAAA2 = _addObjectEntry(
			_publishedObjectDefinitionAAA,
			Map.of(
				_publishedObjectRelationshipAA_AAAObjectField2.getName(),
				objectEntryAA2.getObjectEntryId()));
		ObjectEntry objectEntryAAA3 = _addObjectEntry(
			_publishedObjectDefinitionAAA, Map.of());

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAA3, 0L
			).put(
				objectEntryAAA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAAA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAAA3, 0L
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipAA_AAA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA1.getObjectEntryId()
			).put(
				objectEntryAA2, objectEntryA2.getObjectEntryId()
			).put(
				objectEntryAA3, 0L
			).put(
				objectEntryAAA1, 0L
			).put(
				objectEntryAAA2, 0L
			).put(
				objectEntryAAA3, 0L
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithObjectEntriesWithParentNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipB_AA));

		ObjectEntry objectEntryA = _addObjectEntry(
			_publishedObjectDefinitionA, Map.of());

		ObjectEntry objectEntryAA1 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipA_AAObjectField2.getName(),
				objectEntryA.getObjectEntryId()));

		ObjectEntry objectEntryB = _addObjectEntry(
			_publishedObjectDefinitionB, Map.of());

		ObjectEntry objectEntryAA2 = _addObjectEntry(
			_publishedObjectDefinitionAA,
			Map.of(
				_publishedObjectRelationshipB_AAObjectField2.getName(),
				objectEntryB.getObjectEntryId()));

		ObjectEntry objectEntryAA3 = _addObjectEntry(
			_publishedObjectDefinitionAA, Map.of());

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA2, objectEntryB.getObjectEntryId()
			).put(
				objectEntryAA3, 0L
			).put(
				objectEntryB, objectEntryB.getObjectEntryId()
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipB_AA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		_assertRootObjectEntryIds(
			LinkedHashMapBuilder.put(
				objectEntryA, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA1, objectEntryA.getObjectEntryId()
			).put(
				objectEntryAA2, 0L
			).put(
				objectEntryAA3, 0L
			).put(
				objectEntryB, 0L
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithOngoingWorkflowInstances()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), 0,
			_publishedObjectDefinitionA.getClassName(), 0, 0, "Single Approver",
			1);

		Tree objectEntryTree1 = TreeTestUtil.createObjectEntryTree(
			"1", _objectDefinitionLocalService, _objectEntryLocalService,
			_objectFieldLocalService, _objectRelationshipLocalService,
			_publishedObjectDefinitionA.getObjectDefinitionId());
		Tree objectEntryTree2 = TreeTestUtil.createObjectEntryTree(
			"2", _objectDefinitionLocalService, _objectEntryLocalService,
			_objectFieldLocalService, _objectRelationshipLocalService,
			_publishedObjectDefinitionA.getObjectDefinitionId());

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			String.format(
				"These ongoing workflow instances must be completed to " +
					"disable inheritance: \"%s\" (\"%s\" object entries)",
				_publishedObjectDefinitionA.getLabel(LocaleUtil.US), 2),
			() -> TreeTestUtil.unbind(
				_publishedObjectRelationshipA_AA,
				_objectRelationshipLocalService));

		Node rootNode1 = objectEntryTree1.getRootNode();

		_completeWorkflowTask(
			_publishedObjectDefinitionA.getClassName(),
			rootNode1.getPrimaryKey());

		AssertUtils.assertFailure(
			ObjectRelationshipEdgeException.class,
			String.format(
				"These ongoing workflow instances must be completed to " +
					"disable inheritance: \"%s\" (\"%s\" object entries)",
				_publishedObjectDefinitionA.getLabel(LocaleUtil.US), 1),
			() -> TreeTestUtil.unbind(
				_publishedObjectRelationshipA_AA,
				_objectRelationshipLocalService));

		Node rootNode2 = objectEntryTree2.getRootNode();

		_completeWorkflowTask(
			_publishedObjectDefinitionA.getClassName(),
			rootNode2.getPrimaryKey());

		TreeTestUtil.unbind(
			_publishedObjectRelationshipA_AA, _objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA, new ObjectDefinition[0]
			).put(
				_publishedObjectDefinitionAA, new ObjectDefinition[0]
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithParentNodes() throws Exception {
		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipB_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).put(
				_publishedObjectDefinitionB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipB_AA.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionB, new ObjectDefinition[0]
			).build());
	}

	@Test
	public void testUnbindObjectDefinitionsWithResourcePermissions()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(_publishedObjectRelationshipA_AA));

		Role organizationRole = RoleTestUtil.addRole(
			RoleConstants.TYPE_ORGANIZATION);

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			_publishedObjectDefinitionA.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			organizationRole.getRoleId(), new String[] {ActionKeys.UPDATE});

		Role regularRole = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			_publishedObjectDefinitionA.getPortletId(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			regularRole.getRoleId(),
			new String[] {ActionKeys.ACCESS_IN_CONTROL_PANEL});

		Role siteRole = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			_publishedObjectDefinitionA.getResourceName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			siteRole.getRoleId(),
			new String[] {ObjectActionKeys.ADD_OBJECT_ENTRY});

		ObjectAction objectAction = ObjectActionTestUtil.addObjectAction(
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			_publishedObjectDefinitionAA,
			UnicodePropertiesBuilder.put(
				"secret", "standalone"
			).put(
				"url", "https://standalone.com"
			).build());

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			_publishedObjectDefinitionAA.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			organizationRole.getRoleId(),
			new String[] {objectAction.getName()});

		TreeTestUtil.unbind(
			_publishedObjectRelationshipA_AA, _objectRelationshipLocalService);

		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				TestPropsValues.getCompanyId(),
				_publishedObjectDefinitionAA.getClassName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				organizationRole.getRoleId(), ActionKeys.UPDATE));
		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				TestPropsValues.getCompanyId(),
				_publishedObjectDefinitionAA.getPortletId(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()),
				regularRole.getRoleId(), ActionKeys.ACCESS_IN_CONTROL_PANEL));
		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				TestPropsValues.getCompanyId(),
				_publishedObjectDefinitionAA.getResourceName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				siteRole.getRoleId(), ObjectActionKeys.ADD_OBJECT_ENTRY));

		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				TestPropsValues.getCompanyId(),
				_publishedObjectDefinitionAA.getClassName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				organizationRole.getRoleId(), objectAction.getName()));
	}

	@Test
	public void testUnbindObjectDefinitionsWithSiblingNode() throws Exception {
		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				_publishedObjectRelationshipA_AB));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAB,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).build());

		TreeTestUtil.unbind(
			_objectRelationshipLocalService.getObjectRelationship(
				_publishedObjectRelationshipA_AB.getObjectRelationshipId()),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAB, new ObjectDefinition[0]
			).build());
	}

	@Test
	public void testUpdateNodeObjectDefinition() throws Exception {
		_testUpdateNodeObjectDefinition(
			_draftObjectDefinitionAA, _publishedObjectDefinitionA);
		_testUpdateNodeObjectDefinition(
			_publishedObjectDefinitionB, _draftObjectDefinitionBB);
	}

	@Test
	public void testUpdateNodeObjectDefinitionWithDraftDescendantNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService,
					_publishedObjectDefinitionAA, _draftObjectDefinitionAAA),
				_draftObjectRelationshipAAA_AAAA,
				_draftObjectRelationshipAAAA_AAAAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAA}
			).put(
				_draftObjectDefinitionAAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAA}
			).put(
				_draftObjectDefinitionAAAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAA}
			).build());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			_draftObjectDefinitionAAA.getObjectDefinitionId());

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAAA}
			).put(
				_draftObjectDefinitionAAAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAAA}
			).build());
	}

	@Test
	public void testUpdateNodeObjectDefinitionWithGrandParentNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService,
					_publishedObjectDefinitionAA, _draftObjectDefinitionC),
				_publishedObjectRelationshipB_BB,
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService,
					_publishedObjectDefinitionBB, _draftObjectDefinitionC)));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).put(
				_publishedObjectDefinitionBB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).put(
				_draftObjectDefinitionC,
				new ObjectDefinition[] {_draftObjectDefinitionC}
			).build());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			_draftObjectDefinitionC.getObjectDefinitionId());

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).put(
				_publishedObjectDefinitionBB,
				new ObjectDefinition[] {_publishedObjectDefinitionB}
			).put(
				_draftObjectDefinitionC,
				new ObjectDefinition[] {
					_publishedObjectDefinitionA, _publishedObjectDefinitionB
				}
			).build());
	}

	@Test
	public void testUpdateNodeObjectDefinitionWithParentAndDescendantNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_draftObjectRelationshipA_AA, _draftObjectRelationshipAA_AAA,
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, _draftObjectDefinitionAAA,
					_publishedObjectDefinitionAAAA),
				_publishedObjectRelationshipAAAA_AAAAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAAA}
			).put(
				_publishedObjectDefinitionAAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAAA}
			).build());

		_draftObjectDefinitionAAA =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_draftObjectDefinitionAAA.getObjectDefinitionId());

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_draftObjectDefinitionA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAA,
				new ObjectDefinition[] {_draftObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAA}
			).put(
				_publishedObjectDefinitionAAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAA}
			).put(
				_publishedObjectDefinitionAAAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAA}
			).build());
	}

	@Test
	public void testUpdateNodeObjectDefinitionWithPublishedDescendantNodes()
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(
				_publishedObjectRelationshipA_AA,
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService,
					_publishedObjectDefinitionAA, _draftObjectDefinitionAAA),
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, _draftObjectDefinitionAAA,
					_publishedObjectDefinitionAAAA),
				_publishedObjectRelationshipAAAA_AAAAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAA,
				new ObjectDefinition[] {_draftObjectDefinitionAAA}
			).put(
				_publishedObjectDefinitionAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAAA}
			).put(
				_publishedObjectDefinitionAAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionAAAA}
			).build());

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			_draftObjectDefinitionAAA.getObjectDefinitionId());

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				_publishedObjectDefinitionA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_draftObjectDefinitionAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).put(
				_publishedObjectDefinitionAAAAA,
				new ObjectDefinition[] {_publishedObjectDefinitionA}
			).build());
	}

	private ObjectEntry _addObjectEntry(
			ObjectDefinition objectDefinition, Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null, values, ServiceContextTestUtil.getServiceContext());
	}

	private void _assertRootObjectEntryIds(Map<ObjectEntry, Long> expectedMap)
		throws Exception {

		_entityCache.clearCache();

		for (Map.Entry<ObjectEntry, Long> entry : expectedMap.entrySet()) {
			ObjectEntry objectEntry = entry.getKey();

			objectEntry = _objectEntryLocalService.getObjectEntry(
				objectEntry.getObjectEntryId());

			Assert.assertEquals(
				GetterUtil.getLong(entry.getValue()),
				objectEntry.getRootObjectEntryId());
		}
	}

	private void _assertScreenNavigationCategories(
			int expectedSize, ObjectDefinition objectDefinition)
		throws Exception {

		List<ScreenNavigationCategory> screenNavigationCategories =
			ScreenNavigationRegistryUtil.getScreenNavigationCategories(
				objectDefinition.getClassName(), TestPropsValues.getUser(),
				null);

		Assert.assertEquals(
			screenNavigationCategories.toString(), expectedSize,
			screenNavigationCategories.size());
	}

	private void _completeWorkflowTask(String className, long classPK)
		throws Exception {

		List<WorkflowInstance> workflowInstances =
			_workflowInstanceManager.getWorkflowInstances(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				className, classPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		WorkflowInstance workflowInstance = workflowInstances.get(0);

		for (WorkflowTask workflowTask :
				_workflowTaskManager.getWorkflowTasksBySubmittingUser(
					TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
					false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			if (workflowInstance.getWorkflowInstanceId() !=
					workflowTask.getWorkflowInstanceId()) {

				continue;
			}

			workflowTask = _workflowTaskManager.assignWorkflowTaskToUser(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				workflowTask.getWorkflowTaskId(), TestPropsValues.getUserId(),
				StringPool.BLANK, null, null);

			_workflowTaskManager.completeWorkflowTask(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				workflowTask.getWorkflowTaskId(), Constants.APPROVE,
				StringPool.BLANK, null);
		}
	}

	private void _testBindDescendantObjectDefinitionAndObjectDefinition(
			ObjectDefinition objectDefinitionA,
			ObjectDefinition objectDefinitionAA,
			ObjectDefinition objectDefinitionAAA,
			ObjectRelationship objectRelationshipA_AA,
			ObjectRelationship objectRelationshipAA_AAA)
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService, List.of(objectRelationshipA_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAAA, new ObjectDefinition[0]
			).build());

		TreeTestUtil.bind(
			_objectRelationshipLocalService, List.of(objectRelationshipAA_AAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAAA, new ObjectDefinition[] {objectDefinitionA}
			).build());
	}

	private void _testBindDescendantObjectDefinitionAndRootObjectDefinition(
			ObjectDefinition objectDefinitionA,
			ObjectDefinition objectDefinitionAA,
			ObjectDefinition objectDefinitionAAA,
			ObjectDefinition objectDefinitionAAAA,
			ObjectRelationship objectRelationshipA_AA,
			ObjectRelationship objectRelationshipAA_AAA,
			ObjectRelationship objectRelationshipAAA_AAAA)
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			List.of(objectRelationshipA_AA, objectRelationshipAAA_AAAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAAA,
				new ObjectDefinition[] {objectDefinitionAAA}
			).put(
				objectDefinitionAAAA,
				new ObjectDefinition[] {objectDefinitionAAA}
			).build());

		TreeTestUtil.bind(
			_objectRelationshipLocalService, List.of(objectRelationshipAA_AAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAAA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAAAA, new ObjectDefinition[] {objectDefinitionA}
			).build());
	}

	private void _testBindObjectDefinitionAndRootObjectDefinition(
			ObjectDefinition objectDefinitionA,
			ObjectDefinition objectDefinitionAA,
			ObjectDefinition objectDefinitionAAA,
			ObjectRelationship objectRelationshipA_AA,
			ObjectRelationship objectRelationshipAA_AAA)
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService, List.of(objectRelationshipAA_AAA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionAA}
			).put(
				objectDefinitionAAA, new ObjectDefinition[] {objectDefinitionAA}
			).build());

		TreeTestUtil.bind(
			_objectRelationshipLocalService, List.of(objectRelationshipA_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAAA, new ObjectDefinition[] {objectDefinitionA}
			).build());
	}

	private void _testBindObjectDefinitions(
			ObjectDefinition objectDefinitionA,
			ObjectDefinition objectDefinitionAA,
			ObjectRelationship objectRelationshipA_AA)
		throws Exception {

		TreeTestUtil.bind(
			_objectRelationshipLocalService, List.of(objectRelationshipA_AA));

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionA}
			).build());
	}

	private void _testUpdateNodeObjectDefinition(
			ObjectDefinition objectDefinitionA,
			ObjectDefinition objectDefinitionAA)
		throws Exception {

		TreeTestUtil.bind(
			objectDefinitionA.getObjectDefinitionId(),
			objectDefinitionAA.getObjectDefinitionId(),
			_objectRelationshipLocalService);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionAA}
			).build());

		long objectDefinitionId = objectDefinitionA.getObjectDefinitionId();

		if (objectDefinitionA.isApproved()) {
			objectDefinitionId = objectDefinitionAA.getObjectDefinitionId();
		}

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(), objectDefinitionId);

		TreeTestUtil.assertRootObjectDefinitionIds(
			LinkedHashMapBuilder.put(
				objectDefinitionA, new ObjectDefinition[] {objectDefinitionA}
			).put(
				objectDefinitionAA, new ObjectDefinition[] {objectDefinitionA}
			).build());
	}

	private ObjectDefinition _draftObjectDefinitionA;
	private ObjectDefinition _draftObjectDefinitionAA;
	private ObjectDefinition _draftObjectDefinitionAAA;
	private ObjectDefinition _draftObjectDefinitionAAAA;
	private ObjectDefinition _draftObjectDefinitionAAAAA;
	private ObjectDefinition _draftObjectDefinitionAAAAAA;
	private ObjectDefinition _draftObjectDefinitionB;
	private ObjectDefinition _draftObjectDefinitionBB;
	private ObjectDefinition _draftObjectDefinitionBBB;
	private ObjectDefinition _draftObjectDefinitionC;
	private ObjectDefinition _draftObjectDefinitionCC;
	private ObjectDefinition _draftObjectDefinitionCCC;
	private ObjectRelationship _draftObjectRelationshipA_AA;
	private ObjectRelationship _draftObjectRelationshipAA_AAA;
	private ObjectRelationship _draftObjectRelationshipAAA_AAAA;
	private ObjectRelationship _draftObjectRelationshipAAAA_AAAAA;
	private ObjectRelationship _draftObjectRelationshipAAAAA_AAAAAA;
	private ObjectRelationship _draftObjectRelationshipB_BB;
	private ObjectRelationship _draftObjectRelationshipBB_BBB;
	private ObjectRelationship _draftObjectRelationshipC_CC;
	private ObjectRelationship _draftObjectRelationshipCC_CCC;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectDefinitionTreeFactory _objectDefinitionTreeFactory;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private ObjectDefinition _publishedObjectDefinitionA;
	private ObjectDefinition _publishedObjectDefinitionAA;
	private ObjectDefinition _publishedObjectDefinitionAAA;
	private ObjectDefinition _publishedObjectDefinitionAAAA;
	private ObjectDefinition _publishedObjectDefinitionAAAAA;
	private ObjectDefinition _publishedObjectDefinitionAB;
	private ObjectDefinition _publishedObjectDefinitionB;
	private ObjectDefinition _publishedObjectDefinitionBB;
	private ObjectRelationship _publishedObjectRelationshipA_AA;
	private ObjectField _publishedObjectRelationshipA_AAObjectField2;
	private ObjectRelationship _publishedObjectRelationshipA_AB;
	private ObjectRelationship _publishedObjectRelationshipAA_AAA;
	private ObjectField _publishedObjectRelationshipAA_AAAObjectField2;
	private ObjectRelationship _publishedObjectRelationshipAAA_AAAA;
	private ObjectRelationship _publishedObjectRelationshipAAAA_AAAAA;
	private ObjectRelationship _publishedObjectRelationshipB_AA;
	private ObjectField _publishedObjectRelationshipB_AAObjectField2;
	private ObjectRelationship _publishedObjectRelationshipB_BB;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowInstanceManager _workflowInstanceManager;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}