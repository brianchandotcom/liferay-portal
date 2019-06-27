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

package com.liferay.portal.kernel.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class Filter {

	public Filter(String selector1) {
		this(new String[] {selector1}, null);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public Filter(String selector1, Map<String, String> variables) {
		this(new String[] {selector1}, variables);
	}

	public Filter(String selector1, String selector2) {
		this(new String[] {selector1, selector2}, null);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public Filter(
		String selector1, String selector2, Map<String, String> variables) {

		this(new String[] {selector1, selector2}, variables);
	}

	public Filter(String selector1, String selector2, String selector3) {
		this(new String[] {selector1, selector2, selector3}, null);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public Filter(
		String selector1, String selector2, String selector3,
		Map<String, String> variables) {

		this(new String[] {selector1, selector2, selector3}, variables);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public Filter(String[] selectors, Map<String, String> variables) {
		_selectors = selectors;
		_variables = variables;
	}

	public Iterator<String> filterKeyIterator(String key) {
		return new FilterKeyIterator(key, _selectors);
	}

	public String[] getSelectors() {
		return _selectors;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public Map<String, String> getVariables() {
		return _variables;
	}

	private final String[] _selectors;
	private final Map<String, String> _variables;

	private static class FilterKeyIterator implements Iterator<String> {

		@Override
		public boolean hasNext() {
			if (_index >= 0) {
				return true;
			}

			return false;
		}

		@Override
		public String next() {
			int index = _index--;

			if (index == 0) {
				return _key;
			}

			StringBundler sb = new StringBundler();

			sb.append(_key);

			for (int i = 0; i < index; i++) {
				sb.append(StringPool.OPEN_BRACKET);
				sb.append(_selectors[i]);
				sb.append(StringPool.CLOSE_BRACKET);
			}

			return sb.toString();
		}

		private FilterKeyIterator(String key, String[] selectors) {
			_key = key;
			_selectors = selectors;

			if (selectors == null) {
				_index = 0;
			}
			else {
				_index = selectors.length;
			}
		}

		private int _index;
		private final String _key;
		private final String[] _selectors;

	}

}