/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.definition.tree.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.definition.tree.constants.TreeConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.TreeTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlags("LPS-187142")
@RunWith(Arquillian.class)
public class TreeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIterator() throws Exception {

		// Breadth first iterator

		Tree tree = TreeTestUtil.createTree(
			_objectDefinitionLocalService, _objectRelationshipLocalService,
			_treeFactory);

		_testIterator(
			Arrays.asList("A", "AA", "AB", "AAA", "AAB"),
			tree.iterator(TreeConstants.ITERATOR_TYPE_BREADTH_FIRST));

		// Default iterator

		_testIterator(
			Arrays.asList("A", "AA", "AB", "AAA", "AAB"), tree.iterator());

		// Default iterator, subtree

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "C_AA");

		_testIterator(
			Arrays.asList("AA", "AAA", "AAB"),
			tree.iterator(objectDefinition.getObjectDefinitionId()));

		// Post order iterator

		_testIterator(
			Arrays.asList("AAA", "AAB", "AA", "AB", "A"),
			tree.iterator(TreeConstants.ITERATOR_TYPE_POST_ORDER));

		TreeTestUtil.deleteObjectDefinitionHierarchy(
			_objectDefinitionLocalService);
	}

	private void _testIterator(
			List<String> expectedObjectDefinitionShortNames,
			Iterator<Node> iterator)
		throws Exception {

		int position = 0;

		while (iterator.hasNext()) {
			Node node = iterator.next();

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					node.getObjectDefinitionId());

			Assert.assertEquals(
				expectedObjectDefinitionShortNames.get(position),
				objectDefinition.getShortName());

			position++;
		}

		Assert.assertEquals(
			expectedObjectDefinitionShortNames.size(), position);
	}

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private static ObjectRelationshipLocalService
		_objectRelationshipLocalService;

	@Inject
	private static TreeFactory _treeFactory;

}