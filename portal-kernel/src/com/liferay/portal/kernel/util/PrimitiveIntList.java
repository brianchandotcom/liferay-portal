/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Michael C. Han
 */
public class PrimitiveIntList {

	public PrimitiveIntList() {
		_elements = new int[10];
	}

	public PrimitiveIntList(int capacity) {
		_elements = new int[capacity];
	}

	public void add(int value) {
		_checkCapacity(_elementsSize + 1);

		_elements[_elementsSize++] = value;
	}

	public void addAll(int[] values) {
		_checkCapacity(_elementsSize + values.length);

		System.arraycopy(values, 0, _elements, _elementsSize, values.length);

		_elementsSize += values.length;
	}

	public int[] getArray() {
		_trim();

		return _elements;
	}

	public int size() {
		return _elementsSize;
	}

	private void _checkCapacity(int minSize) {
		int oldSize = _elements.length;

		if (minSize > oldSize) {
			int[] previousElements = _elements;

			int newCapacity = ((oldSize * 3) / 2) + 1;

			if (newCapacity < minSize) {
				newCapacity = minSize;
			}

			_elements = new int[newCapacity];

			System.arraycopy(previousElements, 0, _elements, 0, _elementsSize);
		}
	}

	private void _trim() {
		int oldSize = _elements.length;

		if (_elementsSize < oldSize) {
			int[] previousElements = _elements;

			_elements = new int[_elementsSize];

			System.arraycopy(previousElements, 0, _elements, 0, _elementsSize);
		}
	}

	private int[] _elements;
	private int _elementsSize;

}