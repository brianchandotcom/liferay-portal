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

package com.liferay.css.builder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.nio.charset.Charset;

public class StringPrintStream extends PrintStream {

	public StringPrintStream() {
		this(new ByteArrayOutputStream(), Charset.defaultCharset());
	}

	public StringPrintStream(
		ByteArrayOutputStream outputStream, Charset charset) {

		super(outputStream);

		_outputStream = outputStream;
	}

	@Override
	public String toString() {
		return new String(_outputStream.toByteArray());
	}

	private ByteArrayOutputStream _outputStream;

}