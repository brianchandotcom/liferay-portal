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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * @author Dante Wang
 */
public class BytecodeJavaFileObject extends SimpleJavaFileObject {

	public BytecodeJavaFileObject(URI uri, String className) {
		super(uri, Kind.CLASS);

		_className = className;
	}

	public byte[] getBytecode() {
		return _bytecode;
	}

	public String getClassName() {
		return _className;
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

	private byte[] _bytecode;
	private final String _className;

}