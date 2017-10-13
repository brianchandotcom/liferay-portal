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

package com.liferay.petra.io;

import java.io.Writer;

/**
 * @author Shuyang Zhou
 */
public class DummyWriter extends Writer {

	@Override
	public Writer append(char c) {
		return this;
	}

	@Override
	public Writer append(CharSequence charSequence) {
		return this;
	}

	@Override
	public Writer append(CharSequence charSequence, int start, int end) {
		return this;
	}

	@Override
	public void close() {
	}

	@Override
	public void flush() {
	}

	@Override
	public void write(char[] chars) {
	}

	@Override
	public void write(char[] chars, int offset, int length) {
	}

	@Override
	public void write(int c) {
	}

	@Override
	public void write(String string) {
	}

	@Override
	public void write(String string, int offset, int length) {
	}

}