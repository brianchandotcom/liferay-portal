/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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