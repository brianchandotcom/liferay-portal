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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Iliyan Peychev
 * @author Miguel Pastor
 */
public class BBCodeTranslatorUtil {

	public static BBCodeTranslator getBBCodeTranslator() {
		return _bbCodeTranslatorUtil._getBBCodeTranslator();
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

	private BBCodeTranslatorUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(BBCodeTranslator.class);

		_serviceTracker.open();
	}

	private BBCodeTranslator _getBBCodeTranslator() {
		return _serviceTracker.getService();
	}

	private static final BBCodeTranslatorUtil _bbCodeTranslatorUtil =
		new BBCodeTranslatorUtil();

	private final ServiceTracker<BBCodeTranslator, BBCodeTranslator>
		_serviceTracker;

}