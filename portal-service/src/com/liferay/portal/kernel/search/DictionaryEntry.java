/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Michael C. Han
 */
public class DictionaryEntry {

	public DictionaryEntry(String line) {
		String[] entryValues = StringUtil.split(line, StringPool.SPACE);

		_word = entryValues[0];

		if (entryValues.length == 2) {
			try {
				_weight = Float.parseFloat(entryValues[1]);
			}
			catch (NumberFormatException e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Invalid weight for word: " + _word);
				}
			}
		}
	}

	public float getWeight() {
		return _weight;
	}

	public String getWord() {
		return _word;
	}

	private static Log _log = LogFactoryUtil.getLog(DictionaryEntry.class);

	private float _weight;
	private String _word;

}