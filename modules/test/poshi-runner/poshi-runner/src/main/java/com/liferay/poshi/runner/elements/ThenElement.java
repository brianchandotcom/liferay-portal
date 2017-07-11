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
public class ThenElement extends PoshiElement {

	public ThenElement(Element element) {
		super("then", element);
	}

	public ThenElement(String readableSyntax) {
		super("then", readableSyntax);
	}

	public ThenElement(String name, Element element) {
		super(name, element);
	}

	public ThenElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	@Override
	public String getBlockName() {
		return "then";
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			addElementFromReadableSyntax(readableBlock);
		}
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			sb.append(line);

			String readableBlock = sb.toString();

			if (isValidReadableBlock(readableBlock)) {
				readableBlocks.add(readableBlock);

				sb.setLength(0);
			}
		}

		return readableBlocks;
	}

}