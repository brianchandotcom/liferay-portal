/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.query.QueryVisitor;

/**
 * @author Raymond Augé
 */
public class TermRangeQuery extends BaseQueryImpl {

	public TermRangeQuery(
		String field, String lowerTerm, String upperTerm, boolean includesLower,
		boolean includesUpper) {

		_field = field;
		_lowerTerm = lowerTerm;
		_upperTerm = upperTerm;
		_includesLower = includesLower;
		_includesUpper = includesUpper;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public String getField() {
		return _field;
	}

	public String getLowerTerm() {
		return _lowerTerm;
	}

	public String getUpperTerm() {
		return _upperTerm;
	}

	public boolean includesLower() {
		return _includesLower;
	}

	public boolean includesUpper() {
		return _includesUpper;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", field=");
		sb.append(_field);
		sb.append(", range=");

		if (_includesLower) {
			sb.append(CharPool.OPEN_BRACKET);
		}
		else {
			sb.append(CharPool.OPEN_CURLY_BRACE);
		}

		if (_lowerTerm != null) {
			sb.append(_lowerTerm);
		}
		else {
			sb.append(CharPool.STAR);
		}

		sb.append(" TO ");

		if (_upperTerm != null) {
			sb.append(_upperTerm);
		}
		else {
			sb.append(CharPool.STAR);
		}

		if (_includesUpper) {
			sb.append(CharPool.CLOSE_BRACKET);
		}
		else {
			sb.append(CharPool.CLOSE_CURLY_BRACE);
		}

		sb.append("}");

		return sb.toString();
	}

	private final String _field;
	private final boolean _includesLower;
	private final boolean _includesUpper;
	private final String _lowerTerm;
	private final String _upperTerm;

}