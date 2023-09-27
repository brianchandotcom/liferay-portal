/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.search;

import com.liferay.petra.string.StringBundler;

/**
 * @author Mika Koivisto
 */
public class CMISNotExpression implements CMISCriterion {

	public CMISNotExpression(CMISCriterion cmisCriterion) {
		_cmisCriterion = cmisCriterion;
	}

	@Override
	public String toQueryFragment() {
		return StringBundler.concat(
			"NOT(", _cmisCriterion.toQueryFragment(), ")");
	}

	private final CMISCriterion _cmisCriterion;

}