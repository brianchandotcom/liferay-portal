/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.petra.string.StringBundler;

/**
 * @author Michael C. Han
 */
public class RegexQuery extends Query {

	public static final int ALL_SYNTAX_FLAG = 0xffff;

	public static final int ANYSTRING_SYNTAX_FLAG = 0x0008;

	public static final int AUTOMATON_SYNTAX_FLAG = 0x0010;

	public static final int COMPLEMENT_SYNTAX_FLAG = 0x0002;

	public static final int EMPTY_SYNTAX_FLAG = 0x0004;

	public static final int INTERSECTION_SYNTAX_FLAG = 0x0001;

	public static final int INTERVAL_SYNTAX_FLAG = 0x0020;

	public static final int NONE_SYNTAX_FLAG = 0;

	public RegexQuery(String field, String regex) {
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

	public enum RegexFlag {

		ALL(ALL_SYNTAX_FLAG), ANYSTRING(ANYSTRING_SYNTAX_FLAG),
		AUTOMATON(AUTOMATON_SYNTAX_FLAG), COMPLEMENT(COMPLEMENT_SYNTAX_FLAG),
		EMPTY(EMPTY_SYNTAX_FLAG), INTERSECTION(INTERSECTION_SYNTAX_FLAG),
		INTERVAL(INTERVAL_SYNTAX_FLAG), NONE(NONE_SYNTAX_FLAG);

		public int getValue() {
			return _value;
		}

		private RegexFlag(int value) {
			_value = value;
		}

		private final int _value;

	}

	private static final long serialVersionUID = 1L;

	private final String _field;
	private Integer _maxDeterminedStates;
	private final String _regex;
	private Integer _regexFlags;
	private String _rewrite;

}