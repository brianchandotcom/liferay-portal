/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public class CMISDisjunction extends CMISJunction {

	@Override
	public String toQueryFragment() {
		if (isEmpty()) {
			return StringPool.BLANK;
		}

		List<CMISCriterion> cmisCriterions = list();

		StringBundler sb = new StringBundler((cmisCriterions.size() * 2) + 1);

		if (cmisCriterions.size() > 1) {
			sb.append(StringPool.OPEN_PARENTHESIS);
		}

		for (int i = 0; i < cmisCriterions.size(); i++) {
			CMISCriterion cmisCriterion = cmisCriterions.get(i);

			if (i != 0) {
				sb.append(" OR ");
			}

			sb.append(cmisCriterion.toQueryFragment());
		}

		if (cmisCriterions.size() > 1) {
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
	}

}