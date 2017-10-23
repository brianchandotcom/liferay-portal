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

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class OnPoshiElement extends BasePoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new OnPoshiElement(_ELEMENT_NAME, element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (isElementType(readableSyntax)) {
			return new OnPoshiElement(_ELEMENT_NAME, readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			if (readableBlock.startsWith(getBlockName() + " {")) {
				continue;
			}

			add(PoshiElementFactory.newPoshiElement(this, readableBlock));
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		sb.append(getPad());
		sb.append(getBlockName());

		sb.append(" {");

		List<PoshiElement> poshiElements = toPoshiElements(elements());

		for (int i = 0; i < poshiElements.size(); i++) {
			PoshiElement poshiElement = poshiElements.get(i);

			String readableSyntax = poshiElement.toReadableSyntax();

			if (i == 0) {
				if (readableSyntax.startsWith("\n\n")) {
					readableSyntax = readableSyntax.replaceFirst("\n\n", "\n");
				}
			}

			readableSyntax = readableSyntax.replaceAll("\n", "\n" + getPad());

			sb.append(readableSyntax.replaceAll("\n\t\n", "\n\n"));
		}

		sb.append("\n");
		sb.append(getPad());
		sb.append("}");

		return sb.toString();
	}

	protected String getReadableName() {
		return getName();
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			String readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (line.startsWith(getReadableName() + " (") && line.endsWith("{") &&
					(readableBlock.length() == 0)) {

				readableBlocks.add(line);

				continue;
			}

			if (line.endsWith("{") && readableBlocks.isEmpty()) {
				continue;
			}

			if (isValidReadableBlock(readableBlock)) {
				readableBlocks.add(readableBlock);

				sb.setLength(0);
			}

			sb.append(line);
			sb.append("\n");
		}

		return readableBlocks;
	}

	@Override
	protected String getBlockName() {
		return "on";
	}

	@Override
	protected String getPad() {
		return super.getPad() + "\t";
	}

	protected OnPoshiElement() {
	}

	protected OnPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected OnPoshiElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	protected boolean isElementType(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.startsWith(getBlockName() + " {")) {
			return false;
		}

		if (!readableSyntax.endsWith("}")) {
			return false;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "on";

}