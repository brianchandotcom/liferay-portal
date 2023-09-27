/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.util;

import com.liferay.oauth2.provider.scope.internal.spi.scope.matcher.ChunkScopeMatcherFactory;
import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcherFactory;
import com.liferay.oauth2.provider.web.internal.tree.Tree;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marta Medio
 */
public class ScopeTreeUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMultipleLevelsScopeTree() {
		List<String> scopeAliasesList = Arrays.asList(
			"everything.read", "everything.write", "everything",
			"everything.read.user", "everything.read.user.documents");

		Tree.Node<String> node = ScopeTreeUtil.getScopeAliasTreeNode(
			scopeAliasesList, _scopeMatcherFactory);

		Assert.assertEquals(StringPool.BLANK, node.getValue());

		Tree<String> tree = _getTree(node, 0);

		Assert.assertEquals("everything", tree.getValue());
		Assert.assertFalse(tree instanceof Tree.Leaf);

		Tree<String> firstChildTree = _getTree((Tree.Node<String>)tree, 0);

		Assert.assertEquals("everything.read", firstChildTree.getValue());
		Assert.assertFalse(firstChildTree instanceof Tree.Leaf);

		Tree<String> firstGrandChildTree = _getTree(
			(Tree.Node<String>)firstChildTree, 0);

		Assert.assertEquals(
			"everything.read.user", firstGrandChildTree.getValue());
		Assert.assertFalse(firstGrandChildTree instanceof Tree.Leaf);

		Tree<String> greatGrandChildTree = _getTree(
			(Tree.Node<String>)firstGrandChildTree, 0);

		Assert.assertEquals(
			"everything.read.user.documents", greatGrandChildTree.getValue());

		Tree<String> lastChildTree = _getLastTree((Tree.Node<String>)tree);

		Assert.assertEquals("everything.write", lastChildTree.getValue());
		Assert.assertTrue(lastChildTree instanceof Tree.Leaf);
	}

	@Test
	public void testMultipleParentsScopeTree() {
		List<String> scopeAliasesList = Arrays.asList(
			"everything.read", "everything.write", "everything",
			"everything.read.user", "analytics.read", "analytics");

		Tree.Node<String> node = ScopeTreeUtil.getScopeAliasTreeNode(
			scopeAliasesList, _scopeMatcherFactory);

		Assert.assertEquals(StringPool.BLANK, node.getValue());

		Tree<String> tree = _getTree(node, 0);

		Assert.assertEquals("analytics", tree.getValue());
		Assert.assertFalse(tree instanceof Tree.Leaf);

		Tree<String> firstChildTree = _getTree((Tree.Node<String>)tree, 0);

		Assert.assertEquals("analytics.read", firstChildTree.getValue());
		Assert.assertTrue(firstChildTree instanceof Tree.Leaf);

		Tree<String> lastTree = _getLastTree(node);

		Assert.assertEquals("everything", lastTree.getValue());
		Assert.assertFalse(lastTree instanceof Tree.Leaf);

		Tree<String> lastChildTree = _getTree((Tree.Node<String>)lastTree, 0);

		Assert.assertEquals("everything.read", lastChildTree.getValue());
		Assert.assertFalse(lastChildTree instanceof Tree.Leaf);
	}

	@Test
	public void testOneLevelScopeTree() {
		List<String> scopeAliasesList = Arrays.asList(
			"everything.read", "everything.write", "everything");

		Tree.Node<String> node = ScopeTreeUtil.getScopeAliasTreeNode(
			scopeAliasesList, _scopeMatcherFactory);

		Assert.assertEquals(StringPool.BLANK, node.getValue());

		Tree<String> tree = _getTree(node, 0);

		Assert.assertEquals("everything", tree.getValue());
		Assert.assertFalse(tree instanceof Tree.Leaf);

		Tree<String> firstChildTree = _getTree((Tree.Node<String>)tree, 0);

		Assert.assertEquals("everything.read", firstChildTree.getValue());
		Assert.assertTrue(firstChildTree instanceof Tree.Leaf);

		Tree<String> lastChildTree = _getLastTree((Tree.Node<String>)tree);

		Assert.assertEquals("everything.write", lastChildTree.getValue());
		Assert.assertTrue(lastChildTree instanceof Tree.Leaf);
	}

	private Tree<String> _getLastTree(Tree.Node<String> node) {
		Collection<Tree<String>> trees = node.getTrees();

		return _getTree(node, trees.size() - 1);
	}

	private List<Tree<String>> _getSortedTrees(Tree.Node<String> node) {
		return ListUtil.sort(
			node.getTrees(),
			Comparator.comparing(
				Tree::getValue, String.CASE_INSENSITIVE_ORDER));
	}

	private Tree<String> _getTree(Tree.Node<String> node, int index) {
		List<Tree<String>> trees = _getSortedTrees(node);

		return trees.get(index);
	}

	private final ScopeMatcherFactory _scopeMatcherFactory =
		new ChunkScopeMatcherFactory();

}