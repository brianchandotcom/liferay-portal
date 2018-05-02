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

package com.liferay.wiki.engine.creole.internal.antlrwiki.translator.internal;

import com.liferay.wiki.engine.creole.internal.parser.ast.CollectionNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.LinkNode;

/**
 * @author Miguel Pastor
 */
public class UnformattedLinksTextVisitor extends UnformattedTextVisitor {

	public String getUnformattedText(LinkNode linkNode) {
		CollectionNode altCollectionNode = linkNode.getAltCollectionNode();

		traverse(altCollectionNode.getASTNodes());

		return getText();
	}

}