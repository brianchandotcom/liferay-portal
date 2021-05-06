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

package com.liferay.gradle.plugins.defaults.internal.util.copy;

import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;

import java.io.IOException;
import java.io.Reader;

import org.apache.tools.ant.filters.BaseParamFilterReader;
import org.apache.tools.ant.filters.ChainableReader;
import org.apache.tools.ant.types.Parameter;

/**
 * @author Andrea Di Giorgi
 */
public class ReplaceContentFilterReader
	extends BaseParamFilterReader implements ChainableReader {

	public ReplaceContentFilterReader() {
	}

	public ReplaceContentFilterReader(Reader reader) {
		super(reader);
	}

	@Override
	public Reader chain(Reader reader) {
		ReplaceContentFilterReader replaceContentFilterReader =
			new ReplaceContentFilterReader(reader);

		replaceContentFilterReader.setFrom(getFrom());
		replaceContentFilterReader.setResources(isResources());
		replaceContentFilterReader.setTo(getTo());

		replaceContentFilterReader.setInitialized(true);

		return replaceContentFilterReader;
	}

	public String getFrom() {
		return _from;
	}

	public String getTo() {
		return _to;
	}

	public boolean isResources() {
		return _resources;
	}

	@Override
	public int read() throws IOException {
		if (!getInitialized()) {
			_initialize();

			setInitialized(true);
		}

		if (_index > _EOF) {
			if (_buffer == null) {
				String content = _normalize(readFully());

				content = content.replace(getFrom(), getTo());

				_buffer = content.toCharArray();
			}

			if (_index < _buffer.length) {
				return _buffer[_index++];
			}

			_index = _EOF;
		}

		return _EOF;
	}

	public void setFrom(String from) {
		_from = from;
	}

	public void setResources(boolean resources) {
		_resources = resources;
	}

	public void setTo(String to) {
		_to = to;
	}

	private void _initialize() throws IOException {
		Parameter[] parameters = getParameters();

		if (parameters != null) {
			for (Parameter parameter : parameters) {
				String name = parameter.getName();
				String value = parameter.getValue();

				if (name.equals("from")) {
					setFrom(value);
				}
				else if (name.equals("resources")) {
					setResources(Boolean.parseBoolean(value));
				}
				else if (name.equals("to")) {
					setTo(value);
				}
			}
		}

		String from = getFrom();
		String to = getTo();

		if (isResources()) {
			from = FileUtil.read(from);
			to = FileUtil.read(to);
		}

		setFrom(_normalize(from));
		setTo(_normalize(to));
	}

	private String _normalize(String s) {
		if (s == null) {
			return "";
		}

		return s.replace("\r\n", "\n");
	}

	private static final int _EOF = -1;

	private char[] _buffer;
	private String _from;
	private int _index;
	private boolean _resources;
	private String _to;

}