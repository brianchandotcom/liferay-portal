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
		if (_nextElement == null) {
			if (_iterator.hasNext()) {
				_nextElement = _iterator.next();
			}
			else if (_enumeration != null) {
				while ((_nextElement == null) &&
					   _enumeration.hasMoreElements()) {

					_nextElement = _enumeration.nextElement();

					if (_set.contains(_nextElement)) {
						_nextElement = null;
					}
				}
			}
		}

		return _nextElement != null;
	}

	@Override
	public String nextElement() {
		if (hasMoreElements()) {
			String nextElement = _nextElement;

			_nextElement = null;

			return nextElement;
		}

		throw new NoSuchElementException();
	}

	private final Enumeration<String> _enumeration;
	private final Iterator<String> _iterator;
	private String _nextElement;
	private final Set<String> _set;

}