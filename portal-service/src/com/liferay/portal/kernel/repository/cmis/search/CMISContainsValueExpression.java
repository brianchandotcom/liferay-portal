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

package com.liferay.portal.kernel.repository.cmis.search;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Ivan Zaera
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