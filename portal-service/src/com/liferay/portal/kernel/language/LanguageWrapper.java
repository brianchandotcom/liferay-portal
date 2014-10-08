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

package com.liferay.portal.kernel.language;

/**
 * @author Brian Wing Shun Chan
 */
public class LanguageWrapper {

	public LanguageWrapper(String before, String text, String after) {
		_before = before;
		_text = text;
		_after = after;
	}

	public String getAfter() {
		return _after;
	}

	public String getBefore() {
		return _before;
	}

	public String getText() {
		return _text;
	}

	private final String _after;
	private final String _before;
	private final String _text;

}