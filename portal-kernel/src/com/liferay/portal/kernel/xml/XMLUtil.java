/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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