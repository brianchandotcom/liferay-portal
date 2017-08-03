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

package com.liferay.documentum.repository.search;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public class DQLDisjunction extends DQLJunction {

	@Override
	public String toQueryFragment() {
		if (isEmpty()) {
			return StringPool.BLANK;
		}

		List<DQLCriterion> dqlCriterions = list();

		StringBundler sb = new StringBundler(dqlCriterions.size() * 2 + 1);

		sb.append("(");

		for (int i = 0; i < dqlCriterions.size(); i++) {
			DQLCriterion dqlCriterion = dqlCriterions.get(i);

			if (i != 0) {
				sb.append(" OR ");
			}

			sb.append(dqlCriterion.toQueryFragment());
		}

		sb.append(")");

		return sb.toString();
	}

}