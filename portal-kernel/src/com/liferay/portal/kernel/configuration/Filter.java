/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public Filter(String selector1, String selector2) {
		this(new String[] {selector1, selector2}, null);
	}

	public Filter(String selector1, String selector2, String selector3) {
		this(new String[] {selector1, selector2, selector3}, null);
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