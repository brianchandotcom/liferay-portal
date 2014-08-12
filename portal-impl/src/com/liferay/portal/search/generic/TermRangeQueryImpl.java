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

package com.liferay.portal.search.generic;

import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Raymond Augé
 */
public class TermRangeQueryImpl extends BaseQueryImpl
	implements TermRangeQuery {

	public TermRangeQueryImpl(
		String field, String lowerTerm, String upperTerm, boolean includesLower,
		boolean includesUpper) {

		_field = field;
		_lowerTerm = lowerTerm;
		_upperTerm = upperTerm;
		_includesLower = includesLower;
		_includesUpper = includesUpper;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public String getLowerTerm() {
		return _lowerTerm;
	}

	@Override
	public String getUpperTerm() {
		return _upperTerm;
	}

	@Override
	public Object getWrappedQuery() {
		return this;
	}

	@Override
	public boolean includesLower() {
		return _includesLower;
	}

	@Override
	public boolean includesUpper() {
		return _includesUpper;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append(_field);
		sb.append(CharPool.COLON);

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

		return sb.toString();
	}

	private final String _field;
	private final boolean _includesLower;
	private final boolean _includesUpper;
	private final String _lowerTerm;
	private final String _upperTerm;

}