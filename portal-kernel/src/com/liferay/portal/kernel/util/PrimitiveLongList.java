/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Michael C. Han
 */
public class PrimitiveLongList {

	public PrimitiveLongList() {
		_elements = new long[10];
	}

	public PrimitiveLongList(int capacity) {
		_elements = new long[capacity];
	}

	public void add(long value) {
		_checkCapacity(_elementsSize + 1);

		_elements[_elementsSize++] = value;
	}

	public void addAll(long[] values) {
		_checkCapacity(_elementsSize + values.length);

		System.arraycopy(values, 0, _elements, _elementsSize, values.length);

		_elementsSize += values.length;
	}

	public long[] getArray() {
		_trim();

		return _elements;
	}

	public int size() {
		return _elementsSize;
	}

	private void _checkCapacity(int minSize) {
		int oldSize = _elements.length;

		if (minSize > oldSize) {
			long[] previousElements = _elements;

			int newCapacity = ((oldSize * 3) / 2) + 1;

			if (newCapacity < minSize) {
				newCapacity = minSize;
			}

			_elements = new long[newCapacity];

			System.arraycopy(previousElements, 0, _elements, 0, _elementsSize);
		}
	}

	private void _trim() {
		int oldSize = _elements.length;

		if (_elementsSize < oldSize) {
			long[] previousElements = _elements;

			_elements = new long[_elementsSize];

			System.arraycopy(previousElements, 0, _elements, 0, _elementsSize);
		}
	}

	private long[] _elements;
	private int _elementsSize;

}