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