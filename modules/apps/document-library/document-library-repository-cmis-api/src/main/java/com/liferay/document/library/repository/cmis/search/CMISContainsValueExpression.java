/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Iván Zaera
 */
public class CMISContainsValueExpression implements CMISCriterion {

	public CMISContainsValueExpression(String value) {
		_value = value;
	}

	@Override
	public String toQueryFragment() {
		boolean multipleTerms = _value.contains(StringPool.SPACE);

		StringBundler sb = new StringBundler(1 + (multipleTerms ? 4 : 0));

		if (_value.contains(StringPool.SPACE)) {
			sb.append(StringPool.BACK_SLASH);
			sb.append(StringPool.APOSTROPHE);
		}

		sb.append(_value);

		if (_value.contains(StringPool.SPACE)) {
			sb.append(StringPool.BACK_SLASH);
			sb.append(StringPool.APOSTROPHE);
		}

		return sb.toString();
	}

	private final String _value;

}