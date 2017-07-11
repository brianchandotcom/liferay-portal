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

/**
 * @author Kenji Heigel
 */
public class ConditionElement extends ExecuteElement {

	public ConditionElement(Element element) {
		super("condition", element);
	}

	public ConditionElement(String readableSyntax) {
		super("condition", readableSyntax);
	}

	@Override
	public String getBlockName() {
		return attributeValue("function");
	}

	@Override
	protected String createReadableBlock(String content) {
		String readableBlock = super.createReadableBlock(content);

		readableBlock = readableBlock.trim();

		if (readableBlock.endsWith(";")) {
			readableBlock = readableBlock.substring(
				0, readableBlock.length() - 1);
		}

		return readableBlock;
	}

}