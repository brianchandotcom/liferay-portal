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

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class IfPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new IfPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(readableSyntax)) {
			return new IfPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			if (readableBlock.startsWith(getName() + " (")) {
				add(
					PoshiNodeFactory.newPoshiNode(
						this, getParentheticalContent(readableBlock)));

				continue;
			}

			add(PoshiNodeFactory.newPoshiNode(this, readableBlock));
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		PoshiElement thenElement = (PoshiElement)element("then");

		String thenReadableSyntax = thenElement.toReadableSyntax();

		sb.append(createReadableBlock(thenReadableSyntax));

		for (PoshiElement elseIfElement : toPoshiElements(elements("elseif"))) {
			sb.append(elseIfElement.toReadableSyntax());
		}

		if (element("else") != null) {
			PoshiElement elseElement = (PoshiElement)element("else");

			sb.append(elseElement.toReadableSyntax());
		}

		return sb.toString();
	}

	protected IfPoshiElement() {
	}

	protected IfPoshiElement(Element element) {
		super("if", element);
	}

	protected IfPoshiElement(String readableSyntax) {
		super("if", readableSyntax);
	}

	protected IfPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected IfPoshiElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		StringBuilder sb = new StringBuilder();

		sb.append(getReadableName());

		for (String conditionName : _CONDITION_NAMES) {
			if (element(conditionName) != null) {
				PoshiElement poshiElement = (PoshiElement)element(
					conditionName);

				sb.append(" (");
				sb.append(poshiElement.toReadableSyntax());
				sb.append(")");

				break;
			}
		}

		return sb.toString();
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			String trimmedLine = line.trim();

			String readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (trimmedLine.startsWith(getReadableName() + " (") &&
				trimmedLine.endsWith("{") && (readableBlock.length() == 0)) {

				readableBlocks.add(line);

				sb.append("{\n");

				continue;
			}

			sb.append(line);
			sb.append("\n");

			readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (isValidReadableBlock(readableBlock)) {
				readableBlocks.add(readableBlock);

				sb.setLength(0);
			}
		}

		return readableBlocks;
	}

	protected String getReadableName() {
		return getName();
	}

	private boolean _isElementType(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.startsWith("if (")) {
			return false;
		}

		if (!readableSyntax.endsWith("}")) {
			return false;
		}

		return true;
	}

	private static final String[] _CONDITION_NAMES =
		{"and", "condition", "equals", "isset", "not", "or"};

	private static final String _ELEMENT_NAME = "if";

}