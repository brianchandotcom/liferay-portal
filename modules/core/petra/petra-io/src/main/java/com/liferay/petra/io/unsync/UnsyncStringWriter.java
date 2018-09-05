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

package com.liferay.petra.io.unsync;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.Writer;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncStringWriter extends Writer {

	public UnsyncStringWriter() {
		this(true);
	}

	public UnsyncStringWriter(boolean useStringBundler) {
		if (useStringBundler) {
			_stringBundler = new StringBundler();
		}
		else {
			_stringBuilder = new StringBuilder();
		}
	}

	public UnsyncStringWriter(boolean useStringBundler, int initialCapacity) {
		if (useStringBundler) {
			_stringBundler = new StringBundler(initialCapacity);
		}
		else {
			_stringBuilder = new StringBuilder(initialCapacity);
		}
	}

	public UnsyncStringWriter(int initialCapacity) {
		this(true, initialCapacity);
	}

	@Override
	public UnsyncStringWriter append(char c) {
		write(c);

		return this;
	}

	@Override
	public UnsyncStringWriter append(CharSequence charSequence) {
		if (charSequence == null) {
			write(StringPool.NULL);
		}
		else {
			write(charSequence.toString());
		}

		return this;
	}

	@Override
	public UnsyncStringWriter append(
		CharSequence charSequence, int start, int end) {

		if (charSequence == null) {
			charSequence = StringPool.NULL;
		}

		charSequence = charSequence.subSequence(start, end);

		write(charSequence.toString());

		return this;
	}

	@Override
	public void close() {
	}

	@Override
	public void flush() {
	}

	public StringBuilder getStringBuilder() {
		return _stringBuilder;
	}

	public StringBundler getStringBundler() {
		return _stringBundler;
	}

	public void reset() {
		if (_stringBundler != null) {
			_stringBundler.setIndex(0);
		}
		else {
			_stringBuilder.setLength(0);
		}
	}

	@Override
	public String toString() {
		if (_stringBundler != null) {
			return _stringBundler.toString();
		}

		return _stringBuilder.toString();
	}

	@Override
	public void write(char[] chars) {
		write(chars, 0, chars.length);
	}

	@Override
	public void write(char[] chars, int offset, int length) {
		BoundaryCheckerUtil.check(chars.length, offset, length);

		if (length == 0) {
			return;
		}

		if (_stringBundler != null) {
			_stringBundler.append(new String(chars, offset, length));
		}
		else {
			_stringBuilder.append(chars, offset, length);
		}
	}

	@Override
	public void write(int c) {
		if (_stringBundler != null) {
			char ch = (char)c;

			if (ch <= 127) {
				_stringBundler.append(StringPool.ASCII_TABLE[ch]);
			}
			else {
				_stringBundler.append(String.valueOf(ch));
			}
		}
		else {
			_stringBuilder.append((char)c);
		}
	}

	@Override
	public void write(String string) {
		if (_stringBundler != null) {
			_stringBundler.append(string);
		}
		else {
			_stringBuilder.append(string);
		}
	}

	@Override
	public void write(String string, int offset, int length) {
		if ((string == null) ||
			((offset == 0) && (length == string.length()))) {

			write(string);
		}
		else if (_stringBundler != null) {
			_stringBundler.append(string.substring(offset, offset + length));
		}
		else {
			_stringBuilder.append(string.substring(offset, offset + length));
		}
	}

	private StringBuilder _stringBuilder;
	private StringBundler _stringBundler;

}