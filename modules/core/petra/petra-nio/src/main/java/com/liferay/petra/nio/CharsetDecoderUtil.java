/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

/**
 * @author Shuyang Zhou
 */
public class CharsetDecoderUtil {

	public static CharBuffer decode(String charsetName, ByteBuffer byteBuffer) {
		try {
			return decode(charsetName, CodingErrorAction.REPLACE, byteBuffer);
		}
		catch (CharacterCodingException characterCodingException) {
			throw new Error(characterCodingException);
		}
	}

	public static CharBuffer decode(
			String charsetName, CodingErrorAction codingErrorAction,
			ByteBuffer byteBuffer)
		throws CharacterCodingException {

		CharsetDecoder charsetDecoder = getCharsetDecoder(
			charsetName, codingErrorAction);

		return charsetDecoder.decode(byteBuffer);
	}

	public static CharsetDecoder getCharsetDecoder(String charsetName) {
		return getCharsetDecoder(charsetName, CodingErrorAction.REPLACE);
	}

	public static CharsetDecoder getCharsetDecoder(
		String charsetName, CodingErrorAction codingErrorAction) {

		Charset charset = Charset.forName(charsetName);

		CharsetDecoder charsetDecoder = charset.newDecoder();

		charsetDecoder.onMalformedInput(codingErrorAction);
		charsetDecoder.onUnmappableCharacter(codingErrorAction);

		return charsetDecoder;
	}

}