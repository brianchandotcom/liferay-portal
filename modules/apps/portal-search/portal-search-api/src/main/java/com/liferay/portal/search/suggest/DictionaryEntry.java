/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.suggest;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael C. Han
 */
public class DictionaryEntry {

	public DictionaryEntry(String line) {
		String[] values = StringUtils.split(line);

		if (values.length == 0) {
			_weight = 0;
			_word = StringPool.BLANK;

			return;
		}

		_word = values[0];

		if (values.length == 2) {
			_weight = GetterUtil.getFloat(values[1]);
		}
		else {
			_weight = 0;
		}
	}

	public float getWeight() {
		return _weight;
	}

	public String getWord() {
		return _word;
	}

	private final float _weight;
	private final String _word;

}