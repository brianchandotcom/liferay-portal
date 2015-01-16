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

package com.liferay.portal.kernel.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class PrimitiveIntSet {

	public PrimitiveIntSet() {
		_elements = new HashSet<>();
	}

	public PrimitiveIntSet(int capacity) {
		_elements = new HashSet<>(capacity);
	}

	public void add(int value) {
		_elements.add(value);
	}

	public void addAll(int[] values) {
		for (int value : values) {
			_elements.add(value);
		}
	}

	public int[] getArray() {
		int[] values = new int[_elements.size()];

		int counter = 0;

		for (Integer element : _elements) {
			values[counter] = element;

			counter++;
		}

		return values;
	}

	public int size() {
		return _elements.size();
	}

	private final Set<Integer> _elements;

}