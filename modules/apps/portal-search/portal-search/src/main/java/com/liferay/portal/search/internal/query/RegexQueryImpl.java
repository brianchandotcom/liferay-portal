/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Integer getMaxDeterminedStates() {
		return _maxDeterminedStates;
	}

	@Override
	public String getRegex() {
		return _regex;
	}

	@Override
	public Integer getRegexFlags() {
		return _regexFlags;
	}

	@Override
	public String getRewrite() {
		return _rewrite;
	}

	@Override
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

	@Override
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