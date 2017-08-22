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
public class IfElement extends PoshiElement {

	public static final String ELEMENT_NAME = "if";

	static {
		PoshiElementFactory poshiElementFactory = new PoshiElementFactory() {

			@Override
			public PoshiElement newPoshiElement(Element element) {
				if (isElementType(ELEMENT_NAME, element)) {
					return new IfElement(element);
				}

				return null;
			}

			@Override
			public PoshiElement newPoshiElement(
				PoshiElement parentPoshiElement, String readableSyntax) {

				if (isElementType(parentPoshiElement, readableSyntax)) {
					return new IfElement(readableSyntax);
				}

				return null;
			}

		};

		PoshiElement.addPoshiElementFactory(poshiElementFactory);
	}

	public static boolean isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

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

	@Override
	public String getBlockName() {
		StringBuilder sb = new StringBuilder();

		sb.append(getName());

		for (String conditionName : _conditionNames) {
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

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			if (readableBlock.startsWith(getName() + " (")) {
				add(
					PoshiElement.newPoshiElement(
						this, getParentheticalContent(readableBlock)));

				continue;
			}

			add(newPoshiElement(this, readableBlock));
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		PoshiElement thenElement = (PoshiElement)element("then");

		String thenReadableSyntax = thenElement.toReadableSyntax();

		sb.append(createReadableBlock(thenReadableSyntax));

		if (element("else") != null) {
			PoshiElement elseElement = (PoshiElement)element("else");

			sb.append(elseElement.toReadableSyntax());
		}

		return sb.toString();
	}

	protected IfElement(Element element) {
		super("if", element);
	}

	protected IfElement(String readableSyntax) {
		super("if", readableSyntax);
	}

	protected IfElement(String name, Element element) {
		super(name, element);
	}

	protected IfElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			String readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (line.startsWith(getName() + " (") && line.endsWith("{") &&
				(readableBlock.length() == 0)) {

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

	private static final String[] _conditionNames =
		{"condition", "equals", "isset"};

}