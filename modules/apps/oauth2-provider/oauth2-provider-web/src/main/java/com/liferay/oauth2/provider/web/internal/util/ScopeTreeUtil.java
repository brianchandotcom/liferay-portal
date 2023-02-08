/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.oauth2.provider.web.internal.util;

import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcher;
import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcherFactory;
import com.liferay.oauth2.provider.web.internal.tree.Tree;
import com.liferay.oauth2.provider.web.internal.tree.util.TreeUtil;
import com.liferay.oauth2.provider.web.internal.tree.visitor.TreeVisitor;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Carlos Sierra
 * @author Marta Medio
 */
public class ScopeTreeUtil {

	public static Tree.Node<String> getScopeAliasTreeNode(
		List<String> scopeAliasesList,
		ScopeMatcherFactory scopeMatcherFactory) {

		return getScopeAliasTreeNode(
			new HashSet<>(scopeAliasesList), scopeMatcherFactory);
	}

	public static Tree.Node<String> getScopeAliasTreeNode(
		Set<String> scopeAliases, ScopeMatcherFactory scopeMatcherFactory) {

		HashMap<String, ScopeMatcher> scopeMatchers = new HashMap<>();

		Tree.Node<String> node = TreeUtil.getTreeNode(
			scopeAliases, StringPool.BLANK,
			(scopeAlias1, scopeAlias2) -> {
				ScopeMatcher scopeMatcher = scopeMatchers.computeIfAbsent(
					scopeAlias1, scopeMatcherFactory::create);

				return scopeMatcher.match(scopeAlias2);
			});

		return (Tree.Node<String>)node.accept(_sortTreeVisitor);
	}

	private static final TreeVisitor<String, Tree<String>> _sortTreeVisitor =
		new TreeVisitor<String, Tree<String>>() {

			@Override
			public Tree.Leaf<String> visitLeaf(Tree.Leaf<String> leaf) {
				return leaf;
			}

			@Override
			public Tree.Node<String> visitNode(Tree.Node<String> node) {
				return new Tree.Node<>(
					node.getValue(),
					TransformUtil.transform(
						ListUtil.sort(
							node.getTrees(),
							Comparator.comparing(
								Tree::getValue, String.CASE_INSENSITIVE_ORDER)),
						tree -> tree.accept(this)));
			}

		};

}