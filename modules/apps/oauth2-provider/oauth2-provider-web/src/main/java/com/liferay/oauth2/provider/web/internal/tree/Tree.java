/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.tree;

import com.liferay.oauth2.provider.web.internal.tree.visitor.TreeVisitor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marta Medio
 */
public abstract class Tree<T> {

	public abstract <R> R accept(TreeVisitor<T, R> treeVisitor);

	public abstract T getValue();

	public static final class Leaf<T> extends Tree<T> {

		public Leaf(T value) {
			_value = value;
		}

		@Override
		public <R> R accept(TreeVisitor<T, R> treeVisitor) {
			return treeVisitor.visitLeaf(this);
		}

		@Override
		public T getValue() {
			return _value;
		}

		private final T _value;

	}

	public static final class Node<T> extends Tree<T> {

		public Node(T value, List<Tree<T>> trees) {
			_value = value;
			_trees = trees;
		}

		public Node(T value, Tree<T>... trees) {
			this(value, Arrays.asList(trees));
		}

		@Override
		public <R> R accept(TreeVisitor<T, R> treeVisitor) {
			return treeVisitor.visitNode(this);
		}

		public List<Tree<T>> getTrees() {
			return _trees;
		}

		@Override
		public T getValue() {
			return _value;
		}

		private final List<Tree<T>> _trees;
		private final T _value;

	}

	private Tree() {
	}

}