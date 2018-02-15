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

package com.liferay.poshi.runner.elements;

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class CommandPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new CommandPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(readableSyntax)) {
			return new CommandPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			if (isReadableSyntaxComment(readableBlock)) {
				add(PoshiNodeFactory.newPoshiNode(null, readableBlock));

				continue;
			}

			if (readableBlock.endsWith("}") || readableBlock.endsWith(";") ||
				readableBlock.startsWith("@description")) {

				add(PoshiNodeFactory.newPoshiNode(this, readableBlock));

				continue;
			}

			if (readableBlock.endsWith("{")) {
				String name = RegexUtil.getGroup(
					readableBlock, "test([\\w]*)", 1);

				addAttribute("name", name);

				continue;
			}

			if (readableBlock.startsWith("@")) {
				String name = getNameFromAssignment(readableBlock);
				String value = getQuotedContent(readableBlock);

				addAttribute(name, value);
			}
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement :
				toPoshiElements(elements("description"))) {

			sb.append("\n\t@description = \"");
			sb.append(poshiElement.attributeValue("message"));
			sb.append("\"");
		}

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributeList())) {

			String name = poshiElementAttribute.getName();

			if (name.equals("name")) {
				continue;
			}

			sb.append("\n\t@");
			sb.append(poshiElementAttribute.toReadableSyntax());
		}

		List<String> readableBlocks = new ArrayList<>();

		for (Node node : Dom4JUtil.toNodeList(content())) {
			if (node instanceof PoshiComment) {
				PoshiComment poshiComment = (PoshiComment)node;

				readableBlocks.add(poshiComment.toReadableSyntax());
			}
			else if (node instanceof PoshiElement) {
				PoshiElement poshiElement = (PoshiElement)node;

				readableBlocks.add(poshiElement.toReadableSyntax());
			}
		}

		sb.append(createReadableBlock(readableBlocks));

		return sb.toString();
	}

	protected CommandPoshiElement() {
	}

	protected CommandPoshiElement(Element element) {
		this(_ELEMENT_NAME, element);
	}

	protected CommandPoshiElement(String readableSyntax) {
		this(_ELEMENT_NAME, readableSyntax);
	}

	protected CommandPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected CommandPoshiElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	protected String createReadableBlock(List<String> items) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		String pad = getPad();

		sb.append(pad);

		sb.append(getBlockName());
		sb.append(" {");

		for (int i = 0; i < items.size(); i++) {
			String item = items.get(i);

			if (i == 0) {
				if (item.startsWith("\n\n")) {
					item = item.replaceFirst("\n\n", "\n");
				}
			}

			if (isCDATAVar(item)) {
				item = item.replaceFirst("\t", pad + "\t");

				String trimmedItem = item.trim();

				if (!trimmedItem.startsWith("var")) {
					Matcher matcher = nestedVarAssignmentPattern.matcher(item);

					item = matcher.replaceAll("\t$1$2");

					if (item.endsWith(");")) {
						item = item.substring(0, item.length() - 2);

						item = item + "\t);";
					}
				}

				sb.append(item);

				continue;
			}

			item = item.replaceAll("\n", "\n" + pad);

			sb.append(item.replaceAll("\n\t\n", "\n\n"));
		}

		sb.append("\n");
		sb.append(pad);
		sb.append("}");

		return sb.toString();
	}

	@Override
	protected String getBlockName() {
		return getReadableCommandTitle();
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			String trimmedLine = line.trim();

			if (trimmedLine.length() == 0) {
				sb.append("\n");

				continue;
			}

			if (trimmedLine.startsWith("setUp") ||
				trimmedLine.startsWith("tearDown")) {

				continue;
			}

			if ((trimmedLine.endsWith(" {") &&
				 trimmedLine.startsWith("test")) ||
				trimmedLine.startsWith("@")) {

				readableBlocks.add(trimmedLine);

				continue;
			}

			if (!trimmedLine.startsWith("else {") &&
				!trimmedLine.startsWith("else if")) {

				String readableBlock = sb.toString();

				readableBlock = readableBlock.trim();

				if (isValidReadableBlock(readableBlock)) {
					readableBlocks.add(readableBlock);

					sb.setLength(0);
				}
			}

			sb.append(line);
			sb.append("\n");
		}

		return readableBlocks;
	}

	protected String getReadableCommandTitle() {
		return "test" + attributeValue("name");
	}

	protected boolean isCDATAVar(String readableSyntax) {
		if (readableSyntax.contains("escapeText(")) {
			return true;
		}

		return false;
	}

	private boolean _isElementType(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.endsWith("}")) {
			return false;
		}

		for (String line : readableSyntax.split("\n")) {
			line = line.trim();

			if (line.startsWith("@")) {
				continue;
			}

			if (!(line.endsWith("{") && line.startsWith("test"))) {
				return false;
			}

			break;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "command";

}