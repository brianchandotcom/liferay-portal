/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.Iterator;

/**
 * @author Michael C. Han
 */
public class DictionaryReader {

	public DictionaryReader(InputStream inputStream)
		throws UnsupportedEncodingException {

		this(inputStream, StringPool.UTF8);
	}

	public DictionaryReader(InputStream inputStream, String encoding)
		throws UnsupportedEncodingException {

		_bufferedReader = new BufferedReader(
			new InputStreamReader(inputStream, encoding));

		_encoding = encoding;
	}

	public Iterator<DictionaryEntry> getDictionaryEntriesIterator() {
		return new DictionaryIterator();
	}

	class DictionaryIterator implements Iterator<DictionaryEntry> {
		public DictionaryEntry next() {

			if (!_hasNextCalled) {
				hasNext();
			}

			_hasNextCalled = false;

			if (StringPool.UTF8.equals(_encoding) &&
				(_line.charAt(0) == StringPool.UNICODE_BYTE_ORDER_MARK)) {

				_line = _line.substring(1);
			}

			return new DictionaryEntry(_line);
		}

		public boolean hasNext() {
			if (!_hasNextCalled) {
				try {
					_line = _bufferedReader.readLine();

					_hasNextCalled = true;
				}
				catch (IOException ex) {
					throw new IllegalStateException(ex);
				}
			}

			if (Validator.isNotNull(_line)) {
				return true;
			}

			return false;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		private String _line;
		private boolean _hasNextCalled;

	}

	private BufferedReader _bufferedReader;
	private final String _encoding;

}