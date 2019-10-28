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

/**
 * @author Mika Koivisto
 */
public class DQLBetweenExpression implements DQLCriterion {

	public DQLBetweenExpression(
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