/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

/**
 * @author Shuyang Zhou
 */
public class CharsetEncoderUtil {

	public static ByteBuffer encode(String charsetName, CharBuffer charBuffer) {
		try {
			return encode(charsetName, CodingErrorAction.REPLACE, charBuffer);
		}
		catch (CharacterCodingException characterCodingException) {
			throw new Error(characterCodingException);
		}
	}

	public static ByteBuffer encode(
			String charsetName, CodingErrorAction codingErrorAction,
			CharBuffer charBuffer)
		throws CharacterCodingException {

		CharsetEncoder charsetEncoder = getCharsetEncoder(
			charsetName, codingErrorAction);

		return charsetEncoder.encode(charBuffer);
	}

	public static CharsetEncoder getCharsetEncoder(String charsetName) {
		return getCharsetEncoder(charsetName, CodingErrorAction.REPLACE);
	}

	public static CharsetEncoder getCharsetEncoder(
		String charsetName, CodingErrorAction codingErrorAction) {

		Charset charset = Charset.forName(charsetName);

		CharsetEncoder charsetEncoder = charset.newEncoder();

		charsetEncoder.onMalformedInput(codingErrorAction);
		charsetEncoder.onUnmappableCharacter(codingErrorAction);

		return charsetEncoder;
	}

}