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

package com.liferay.portal.language;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class ResourceBundleEnumeration implements Enumeration<String> {

	public ResourceBundleEnumeration(
		Set<String> set, Enumeration<String> enumeration) {

		_set = set;
		_enumeration = enumeration;

		_iterator = set.iterator();
	}

	@Override
	public boolean hasMoreElements() {
		if (_next == null) {
			if (_iterator.hasNext()) {
				_next = _iterator.next();
			}
			else if (_enumeration != null) {
				while ((_next == null) && _enumeration.hasMoreElements()) {
					_next = _enumeration.nextElement();

					if (_set.contains(_next)) {
						_next = null;
					}
				}
			}
		}

		return _next != null;
	}

	@Override
	public String nextElement() {
		if (hasMoreElements()) {
			String result = _next;

			_next = null;

			return result;
		}

		throw new NoSuchElementException();
	}

	private final Enumeration<String> _enumeration;
	private final Iterator<String> _iterator;
	private String _next;
	private final Set<String> _set;

}