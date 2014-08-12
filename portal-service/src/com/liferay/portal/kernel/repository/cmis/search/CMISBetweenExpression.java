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