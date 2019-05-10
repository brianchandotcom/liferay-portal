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

package com.liferay.portal.search.query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface RegexQuery extends Query {

	public static final int ALL_SYNTAX_FLAG = 0xffff;

	public static final int ANYSTRING_SYNTAX_FLAG = 0x0008;

	public static final int AUTOMATON_SYNTAX_FLAG = 0x0010;

	public static final int COMPLEMENT_SYNTAX_FLAG = 0x0002;

	public static final int EMPTY_SYNTAX_FLAG = 0x0004;

	public static final int INTERSECTION_SYNTAX_FLAG = 0x0001;

	public static final int INTERVAL_SYNTAX_FLAG = 0x0020;

	public static final int NONE_SYNTAX_FLAG = 0;

	public String getField();

	public Integer getMaxDeterminedStates();

	public String getRegex();

	public Integer getRegexFlags();

	public String getRewrite();

	public void setMaxDeterminedStates(Integer maxDeterminedStates);

	public void setRegexFlags(RegexFlag... regexFlags);

	public void setRewrite(String rewrite);

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

}