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

package com.liferay.portal.kernel.xml;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Leonardo Barros
 * @author Julius Lee
 */
public class XMLUtil {

	public static String stripInvalidChars(String xml) {
		if (Validator.isNull(xml)) {
			return xml;
		}

		// Strip characters that are not valid in the 1.0 XML spec
		// http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < xml.length(); i++) {
			char c = xml.charAt(i);

			if ((c == 0x9) || (c == 0xA) || (c == 0xD) ||
				((c >= 0x20) && (c <= 0xD7FF)) ||
				((c >= 0xE000) && (c <= 0xFFFD))) {

				sb.append(c);
			}

			if (Character.isHighSurrogate(c) && ((i + 1) < xml.length())) {
				char c2 = xml.charAt(i + 1);

				if (Character.isLowSurrogate(c2)) {
					int codePoint = Character.toCodePoint(c, c2);

					if ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)) {
						sb.appendCodePoint(codePoint);
					}
				}
			}
		}

		return sb.toString();
	}

}