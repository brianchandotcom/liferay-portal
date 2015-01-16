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

package com.liferay.markdown.converter.internal.pegdown.serializer;

import com.liferay.markdown.converter.internal.pegdown.ast.PicWithCaptionNode;

import java.util.List;

import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TextNode;

/**
 * Provides a visitor implementation for printing HTML for pictures with
 * captions, "side-bars", and in-line images.
 *
 * @author James Hinkey
 */
public class LiferayToHtmlSerializer extends ToHtmlSerializer {

	public LiferayToHtmlSerializer(LinkRenderer linkRenderer) {
		super(linkRenderer);
	}

	@Override
	public void visit(HeaderNode node) {
		boolean anchorInserted = false;

		if (node.getLevel() != 1) {
			List<Node> childNodes = node.getChildren();

			if (!childNodes.isEmpty()) {
				Node childNode = childNodes.get(0);

				if (childNode instanceof TextNode) {
					TextNode textNode = (TextNode)childNodes.get(0);

					String text = textNode.getText();

					text = text.toLowerCase();

					text = text.replaceAll("[^a-z0-9 ]", "");

					text = text.trim();

					text = text.replace(' ', '-');

					printer.print(
						"<a href=\"#" + text + "\" id=\"" + text + "\">");

					anchorInserted = true;
				}
			}
		}

		super.visit(node);

		if (anchorInserted) {
			printer.print("</a>");
		}
	}

	@Override
	public void visit(ParaNode node) {
		boolean printParagraphTag = true;

		List<Node> childNodes = node.getChildren();

		for (Node childNode : childNodes) {
			List<Node> grandchildNodes = childNode.getChildren();

			for (Node grandchildNode : grandchildNodes) {
				if (grandchildNode instanceof TextNode) {
					TextNode textNode = (TextNode)grandchildNode;

					String text = textNode.getText();

					if (text.equals("+$$$") || text.equals("$$$")) {
						visitChildren(node);

						printParagraphTag = false;
					}
				}
			}
		}

		if (printParagraphTag) {
			printTag(node, "p");
		}
	}

	public void visit(PicWithCaptionNode picWithCaptionNode) {
		print(picWithCaptionNode);
	}

	@Override
	public void visit(SuperNode superNode) {
		if (superNode instanceof PicWithCaptionNode) {
			visit((PicWithCaptionNode)superNode);
		}
		else {
			visitChildren(superNode);
		}
	}

	@Override
	public void visit(TextNode node) {
		String text = node.getText();

		if (text.equals("+$$$")) {
			printer.print("<div class=\"sidebar\">");
			printer.print("<div class=\"sidebar-image\"></div>");
			printer.print("<div class=\"sidebar-text\">");
		}
		else if (text.equals("$$$")) {
			printer.print("</div></div>");
		}
		else if (abbreviations.isEmpty()) {
			printer.print(text);
		}
		else {
			printWithAbbreviations(text);
		}
	}

	protected void print(PicWithCaptionNode picWithCaptionNode) {
		printer.print("<p><img src=\"");
		printer.print(picWithCaptionNode.getSrc());
		printer.print("\" alt=\"");
		printer.print(picWithCaptionNode.getAlt());
		printer.print("\" /><p class=\"caption\">");

		visitChildren(picWithCaptionNode);

		printer.print("</p>");
	}

}