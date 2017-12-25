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

package com.liferay.petra.nio;

import com.liferay.petra.reflect.ReflectionUtil;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author Shuyang Zhou
 */
public class TestCharset extends Charset {

	public TestCharset() {
		super("fake-charset", null);
	}

	@Override
	public boolean contains(Charset charset) {
		return false;
	}

	public CharacterCodingException getCharacterCodingException() {
		return _cce;
	}

	@Override
	public CharsetDecoder newDecoder() {
		return ReflectionUtil.throwException(_cce);
	}

	@Override
	public CharsetEncoder newEncoder() {
		return ReflectionUtil.throwException(_cce);
	}

	private final CharacterCodingException _cce =
		new CharacterCodingException();

}