/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Iliyan Peychev
 * @author Miguel Pastor
 */
public class BBCodeTranslatorUtil {

	public static BBCodeTranslator getBBCodeTranslator() {
		PortalRuntimePermission.checkGetBeanProperty(
			BBCodeTranslatorUtil.class);

		return _bbCodeTranslator;
	}

	public static String[] getEmoticonDescriptions() {
		return getBBCodeTranslator().getEmoticonDescriptions();
	}

	public static String[] getEmoticonFiles() {
		return getBBCodeTranslator().getEmoticonFiles();
	}

	public static String[][] getEmoticons() {
		return getBBCodeTranslator().getEmoticons();
	}

	public static String[] getEmoticonSymbols() {
		return getBBCodeTranslator().getEmoticonSymbols();
	}

	public static String getHTML(String bbcode) {
		return getBBCodeTranslator().getHTML(bbcode);
	}

	public static String parse(String message) {
		return getBBCodeTranslator().parse(message);
	}

	public void setBBCodeTranslator(BBCodeTranslator bbCodeTranslator) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_bbCodeTranslator = bbCodeTranslator;
	}

	private static BBCodeTranslator _bbCodeTranslator;

}