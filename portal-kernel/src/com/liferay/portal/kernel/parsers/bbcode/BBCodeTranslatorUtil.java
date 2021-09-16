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