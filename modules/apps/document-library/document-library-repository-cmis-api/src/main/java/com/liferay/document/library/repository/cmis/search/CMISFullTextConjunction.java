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

package com.liferay.document.library.repository.cmis.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class CMISFullTextConjunction extends CMISJunction {

	@Override
	public String toQueryFragment() {
		if (isEmpty()) {
			return StringPool.BLANK;
		}

		List<CMISCriterion> cmisCriterions = list();

		StringBundler sb = new StringBundler(cmisCriterions.size() * 2 + 1);

		if (cmisCriterions.size() > 1) {
			sb.append(StringPool.OPEN_PARENTHESIS);
		}

		for (int i = 0; i < cmisCriterions.size(); i++) {
			CMISCriterion cmisCriterion = cmisCriterions.get(i);

			if (i != 0) {
				sb.append(" ");
			}

			sb.append(cmisCriterion.toQueryFragment());
		}

		if (cmisCriterions.size() > 1) {
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
	}

}