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

package com.liferay.source.formatter.parser;

/**
 * @author Hugo Huijser
 */
public class JavaMethod extends BaseJavaTerm {

	public JavaMethod(
		String name, String content, String accessModifier, boolean isAbstract,
		boolean isStatic) {

		super(name, content, accessModifier, isAbstract, isStatic);
	}

	@Override
	public JavaSignature getSignature() {
		if (_signature == null) {
			_signature = JavaSignatureParser.parseJavaSignature(
				getContent(), getAccessModifier(), true);
		}

		return _signature;
	}

	private JavaSignature _signature;

}