/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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