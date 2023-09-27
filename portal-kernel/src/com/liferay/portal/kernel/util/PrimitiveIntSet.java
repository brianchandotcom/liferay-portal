/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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