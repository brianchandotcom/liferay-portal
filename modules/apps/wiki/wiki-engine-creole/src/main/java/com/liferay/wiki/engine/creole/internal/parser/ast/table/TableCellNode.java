/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.parser.ast.table;

import com.liferay.wiki.engine.creole.internal.parser.ast.BaseParentableNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.CollectionNode;

/**
 * @author Miguel Pastor
 */
public abstract class TableCellNode extends BaseParentableNode {

	public TableCellNode() {
	}

	public TableCellNode(CollectionNode collectionNode) {
		super(collectionNode);
	}

	public TableCellNode(int tokenType) {
		super(tokenType);
	}

}