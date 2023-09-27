/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.tree.visitor;

import com.liferay.oauth2.provider.web.internal.tree.Tree;

/**
 * @author Carlos Sierra Andrés
 */
public interface TreeVisitor<T, R> {

	public R visitLeaf(Tree.Leaf<T> leaf);

	public R visitNode(Tree.Node<T> node);

}