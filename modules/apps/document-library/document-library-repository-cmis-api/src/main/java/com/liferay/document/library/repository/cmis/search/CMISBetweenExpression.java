/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.search;

import com.liferay.petra.string.StringBundler;

/**
 * @author Mika Koivisto
 */
public class CMISBetweenExpression implements CMISCriterion {

	public CMISBetweenExpression(
		String field, String lowerTerm, String upperTerm, boolean includesLower,
		boolean includesUpper) {

		_field = field;
		_lowerTerm = lowerTerm;
		_upperTerm = upperTerm;
		_includesLower = includesLower;
		_includesUpper = includesUpper;
	}

	@Override
	public String toQueryFragment() {
		StringBundler sb = new StringBundler(7);

		sb.append(_field);

		if (_includesLower) {
			sb.append(" >= ");
		}
		else {
			sb.append(" > ");
		}

		sb.append(_lowerTerm);
		sb.append(" AND ");
		sb.append(_field);

		if (_includesUpper) {
			sb.append(" <= ");
		}
		else {
			sb.append(" < ");
		}

		sb.append(_upperTerm);

		return sb.toString();
	}

	private final String _field;
	private final boolean _includesLower;
	private final boolean _includesUpper;
	private final String _lowerTerm;
	private final String _upperTerm;

}