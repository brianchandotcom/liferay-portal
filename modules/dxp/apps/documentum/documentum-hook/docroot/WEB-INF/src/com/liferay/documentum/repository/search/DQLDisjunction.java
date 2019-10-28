/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.documentum.repository.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

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