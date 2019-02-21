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

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.RegexQuery;

/**
 * @author Michael C. Han
 */
public class RegexQueryImpl extends BaseQueryImpl implements RegexQuery {

	public RegexQueryImpl(String field, String regex) {
		_field = field;
		_regex = regex;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public Integer getMaxDeterminedStates() {
		return _maxDeterminedStates;
	}

	public String getRegex() {
		return _regex;
	}

	public Integer getRegexFlags() {
		return _regexFlags;
	}

	public String getRewrite() {
		return _rewrite;
	}

	public void setMaxDeterminedStates(Integer maxDeterminedStates) {
		_maxDeterminedStates = maxDeterminedStates;
	}

	@Override
	public void setRegexFlags(RegexFlag... regexFlags) {
		if (regexFlags == null) {
			return;
		}

		int value = 0;

		for (RegexFlag regexFlag : regexFlags) {
			value |= regexFlag.getValue();
		}

		_regexFlags = value;
	}

	public void setRewrite(String rewrite) {
		_rewrite = rewrite;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", field=");
		sb.append(_field);
		sb.append(", _regex=");
		sb.append(_regex);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final String _field;
	private Integer _maxDeterminedStates;
	private final String _regex;
	private Integer _regexFlags;
	private String _rewrite;

}