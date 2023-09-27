/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.propertiesdoc;

import com.liferay.petra.string.StringPool;

/**
 * @author Jesse Rao
 * @author James Hinkey
 * @author Hugo Huijser
 */
public class PropertyComment {

	public PropertyComment(String comment) {
		_comment = comment;

		String[] lines = comment.split(StringPool.NEW_LINE);

		boolean preformatted = false;

		for (String line : lines) {
			if (line.startsWith(PropertiesDocBuilder.INDENT)) {
				preformatted = true;

				break;
			}
		}

		_preformatted = preformatted;
	}

	public String getComment() {
		return _comment;
	}

	public boolean isPreformatted() {
		return _preformatted;
	}

	private final String _comment;
	private final boolean _preformatted;

}