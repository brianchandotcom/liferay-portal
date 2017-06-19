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

package com.liferay.portal.search.web.internal.display.context;

import com.liferay.portal.kernel.util.CharPool;

/**
 * @author André de Oliveira
 */
public class Keywords {

	public Keywords(String keywords) {
		boolean luceneSyntax = false;

		if ((keywords.length() > 1) && (keywords.charAt(0) == CharPool.STAR)) {
			keywords = keywords.substring(1);

			luceneSyntax = true;
		}

		_keywords = keywords;
		_luceneSyntax = luceneSyntax;
	}

	public String getKeywords() {
		return _keywords;
	}

	public boolean isLuceneSyntax() {
		return _luceneSyntax;
	}

	private final String _keywords;
	private final boolean _luceneSyntax;

}