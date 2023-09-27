/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.markdown.converter.internal.pegdown.ast;

import java.util.List;

import org.pegdown.ast.Node;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.Visitor;

/**
 * Represents picture with an associated caption.
 *
 * @author James Hinkey
 */
public class PicWithCaptionNode extends SuperNode {

	public PicWithCaptionNode(String src, Node node) {
		super(node);

		_src = src;

		_alt = _getAlt(node);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public String getAlt() {
		return _alt;
	}

	public String getSrc() {
		return _src;
	}

	private String _getAlt(Node node) {
		if (node == null) {
			return "";
		}

		List<Node> nodes = node.getChildren();

		if ((nodes != null) && !nodes.isEmpty() &&
			(nodes.get(0) instanceof TextNode)) {

			TextNode textNode = (TextNode)nodes.get(0);

			return textNode.getText();
		}

		return "";
	}

	private final String _alt;
	private final String _src;

}