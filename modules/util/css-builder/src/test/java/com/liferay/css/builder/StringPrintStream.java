/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.css.builder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.nio.charset.Charset;

/**
 * @author Christopher Bryan Boyd
 */
public class StringPrintStream extends PrintStream {

	public StringPrintStream() {
		this(new ByteArrayOutputStream(), Charset.defaultCharset());
	}

	public StringPrintStream(
		ByteArrayOutputStream byteArrayOutputStream, Charset charset) {

		super(byteArrayOutputStream);

		_byteArrayOutputStream = byteArrayOutputStream;
	}

	@Override
	public String toString() {
		return new String(_byteArrayOutputStream.toByteArray());
	}

	private ByteArrayOutputStream _byteArrayOutputStream;

}