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

package com.liferay.portal.kernel.parsers.bbcode.bundle.bbcodetranslatorutil;

import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestBBCodeTranslator implements BBCodeTranslator {

	public static final String END_TAG = "</TestBBCcodeTranslator>";

	public static final String START_TAG = "<TestBBCcodeTranslator>";

	@Override
	public String[] getEmoticonDescriptions() {
		return new String[] {"1", "2", "3"};
	}

	@Override
	public String[] getEmoticonFiles() {
		return new String[] {"1", "2"};
	}

	@Override
	public String[][] getEmoticons() {
		return null;
	}

	@Override
	public String[] getEmoticonSymbols() {
		return new String[] {"1", "2", "3", "4"};
	}

	@Override
	public String getHTML(String bbcode) {
		return START_TAG + bbcode + END_TAG;
	}

	@Override
	public String parse(String message) {
		if (message.equals(START_TAG)) {
			return END_TAG;
		}

		return START_TAG;
	}

}