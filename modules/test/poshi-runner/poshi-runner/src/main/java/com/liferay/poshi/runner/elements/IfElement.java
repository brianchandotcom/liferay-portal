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

	public IfElement(Element element) {
		super("if", element);
	}

	public IfElement(String readableSyntax) {
		super("if", readableSyntax);
	}

	@Override
	public String getBlockName() {
		StringBuilder sb = new StringBuilder();

		sb.append("if");

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
			if (readableBlock.startsWith("if (")) {
				String ifContent = getParentheticalContent(readableBlock);

				addElementFromReadableSyntax(ifContent);

				continue;
			}

			if (readableBlock.startsWith("else {")) {
				addElementFromReadableSyntax(readableBlock);

				continue;
			}

			PoshiElement thenElement = new ThenElement(readableBlock);

			add(thenElement);
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

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			if (line.startsWith("if (")) {
				readableBlocks.add(line);

				continue;
			}

			if (line.startsWith("else {")) {
				sb.setLength(0);
			}

			sb.append(line);

			String readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (isValidReadableBlock(readableBlock)) {
				readableBlocks.add(readableBlock);

				sb.setLength(0);
			}

			if (readableBlock.startsWith("else {")) {
				sb.append("\n");
			}
		}

		return readableBlocks;
	}

	private static final String[] _conditionNames =
		{"condition", "equals", "isset"};

}