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

package com.liferay.portal.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import java.net.URI;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;

import javax.tools.JavaFileObject;

/**
 * @author Shuyang Zhou
 */
public class StringJavaFileObject implements JavaFileObject {

	public StringJavaFileObject(String simpleName, String content) {
		_simpleName = simpleName;
		_content = content;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public Modifier getAccessLevel() {
		return null;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return _content;
	}

	@Override
	public Kind getKind() {
		return Kind.SOURCE;
	}

	@Override
	public long getLastModified() {
		return 0;
	}

	@Override
	public String getName() {
		return _simpleName.concat(Kind.SOURCE.extension);
	}

	@Override
	public NestingKind getNestingKind() {
		return null;
	}

	@Override
	public boolean isNameCompatible(String simpleName, Kind kind) {
		if ((kind == Kind.SOURCE) && _simpleName.equals(simpleName)) {
			return true;
		}

		return false;
	}

	@Override
	public InputStream openInputStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public OutputStream openOutputStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader openReader(boolean ignoreEncodingErrors) {
		return new UnsyncStringReader(_content);
	}

	@Override
	public Writer openWriter() {
		throw new UnsupportedOperationException();
	}

	@Override
	public URI toUri() {
		return URI.create("string:///".concat(getName()));
	}

	private final String _content;
	private final String _simpleName;

}