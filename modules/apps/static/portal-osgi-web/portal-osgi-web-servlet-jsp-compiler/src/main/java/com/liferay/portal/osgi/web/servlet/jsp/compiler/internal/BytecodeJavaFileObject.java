/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public InputStream openInputStream() {
		return new ByteArrayInputStream(_bytecode);
	}

	@Override
	public OutputStream openOutputStream() {
		return new ByteArrayOutputStream() {

			@Override
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