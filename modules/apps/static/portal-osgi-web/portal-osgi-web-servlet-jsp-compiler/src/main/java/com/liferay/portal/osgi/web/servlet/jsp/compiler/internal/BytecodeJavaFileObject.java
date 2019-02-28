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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;

/**
 * @author Dante Wang
 */
public class BytecodeJavaFileObject extends BaseJavaFileObject {

	public BytecodeJavaFileObject(String className) {
		super(Kind.CLASS, className);
	}

	public byte[] getBytecode() {
		return _bytecode;
	}

	@Override
	public InputStream openInputStream() {
		return new ByteArrayInputStream(_bytecode);
	}

	@Override
	public OutputStream openOutputStream() {
		return new ByteArrayOutputStream() {

			public void close() {
				_bytecode = toByteArray();
			}

		};
	}

	@Override
	public URI toUri() {
		return URI.create(
			"file:///".concat(
				StringUtil.replace(
					className, CharPool.PERIOD, CharPool.FORWARD_SLASH)
			).concat(
				String.valueOf(kind)
			));
	}

	private byte[] _bytecode;

}