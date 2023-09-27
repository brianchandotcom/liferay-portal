/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.parsers.bbcode;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Iliyan Peychev
 * @author Miguel Pastor
 */
public class BBCodeTranslatorUtil {

	public static BBCodeTranslator getBBCodeTranslator() {
		return _bbCodeTranslator;
	}

	public static String[] getEmoticonDescriptions() {
		return _bbCodeTranslator.getEmoticonDescriptions();
	}

	public static String[] getEmoticonFiles() {
		return _bbCodeTranslator.getEmoticonFiles();
	}

	public static String[][] getEmoticons() {
		return _bbCodeTranslator.getEmoticons();
	}

	public static String[] getEmoticonSymbols() {
		return _bbCodeTranslator.getEmoticonSymbols();
	}

	public static String getHTML(String bbcode) {
		return _bbCodeTranslator.getHTML(bbcode);
	}

	public static String parse(String message) {
		return _bbCodeTranslator.parse(message);
	}

	private BBCodeTranslatorUtil() {
	}

	private static volatile BBCodeTranslator _bbCodeTranslator =
		ServiceProxyFactory.newServiceTrackedInstance(
			BBCodeTranslator.class, BBCodeTranslatorUtil.class,
			"_bbCodeTranslator", false, true);

}