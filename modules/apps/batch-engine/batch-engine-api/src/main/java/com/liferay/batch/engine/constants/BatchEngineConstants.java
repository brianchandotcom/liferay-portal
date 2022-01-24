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

package com.liferay.batch.engine.constants;

import com.liferay.petra.string.StringPool;

/**
 * @author Igor Beslic
 */
public class BatchEngineConstants {

	public static final String[] CSV_DELIMITERS = {
		StringPool.CARET, StringPool.CLOSE_BRACKET,
		StringPool.CLOSE_CURLY_BRACE, StringPool.CLOSE_PARENTHESIS,
		StringPool.DOLLAR, StringPool.EXCLAMATION, StringPool.OPEN_BRACKET,
		StringPool.OPEN_CURLY_BRACE, StringPool.OPEN_PARENTHESIS,
		StringPool.PERIOD, StringPool.PIPE, StringPool.PLUS,
		StringPool.QUESTION, StringPool.STAR
	};

	public static final String[] CSV_ENCLOSING_CHARACTERS = {
		StringPool.QUOTE, StringPool.APOSTROPHE
	};

}