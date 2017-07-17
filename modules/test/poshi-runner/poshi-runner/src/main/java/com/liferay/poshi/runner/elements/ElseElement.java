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

import java.util.List;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class ElseElement extends ThenElement {

	public ElseElement(Element element) {
		super("else", element);
	}

	public ElseElement(String readableSyntax) {
		super("else", readableSyntax);
	}

	@Override
	public String getBlockName() {
		return "else";
	}

	@Override
	public String toReadableSyntax() {
		String readableSyntax = super.toReadableSyntax();

		return createReadableBlock(readableSyntax);
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		String content = getBracedContent(readableSyntax);

		return super.getReadableBlocks(content);
	}

}