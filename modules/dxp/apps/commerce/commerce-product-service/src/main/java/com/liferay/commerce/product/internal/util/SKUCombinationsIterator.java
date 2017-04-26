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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * @author Marco Leo
 */
public class SKUCombinationsIterator
	implements Iterator<CommerceProductDefinitionOptionValueRel[]> {

	public SKUCombinationsIterator(
		Map<CommerceProductDefinitionOptionRel, CommerceProductDefinitionOptionValueRel[]>
			map) {

		_combinationLength = map.size();

		_values =
			new CommerceProductDefinitionOptionValueRel[_combinationLength][];
		_maxIndexes = new int[_combinationLength];
		_currentIndexes = new int[_combinationLength];

		if (_combinationLength == 0) {
			_hasNext = false;
			return;
		}

		_hasNext = true;

		int valuesIndex = 0;

		for (CommerceProductDefinitionOptionRel
				commerceProductDefinitionOptionRel :
					new TreeSet<>(map.keySet())) {

			_values[valuesIndex++] = map.get(
				commerceProductDefinitionOptionRel);
		}

		for (int i = 0; i < _combinationLength; ++i) {
			if (_values[i].length == 0) {
				_hasNext = false;
				return;
			}

			_maxIndexes[i] = _values[i].length - 1;
			_currentIndexes[i] = 0;
		}
	}

	@Override
	public boolean hasNext() {
		return _hasNext;
	}

	@Override
	public CommerceProductDefinitionOptionValueRel[] next() {
		if (!_hasNext) {
			throw new NoSuchElementException(
				"No more combinations are available");
		}

		final CommerceProductDefinitionOptionValueRel[] combination =
			_getCombinationByCurrentIndexes();
		_nextIndexesCombination();
		return combination;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(
			"Remove operation is not supported");
	}

	private CommerceProductDefinitionOptionValueRel[]
		_getCombinationByCurrentIndexes() {

		final CommerceProductDefinitionOptionValueRel[] combination =
			new CommerceProductDefinitionOptionValueRel[_combinationLength];

		for (int i = 0; i < _combinationLength; ++i) {
			combination[i] = _values[i][_currentIndexes[i]];
		}

		return combination;
	}

	private void _nextIndexesCombination() {
		for (int i = _combinationLength - 1; i >= 0; --i) {
			if (_currentIndexes[i] < _maxIndexes[i]) {

				++_currentIndexes[i];
				return;
			}
			else {
				_currentIndexes[i] = 0;
			}
		}

		_hasNext = false;
	}

	private final int _combinationLength;
	private final int[] _currentIndexes;
	private boolean _hasNext;
	private final int[] _maxIndexes;
	private final CommerceProductDefinitionOptionValueRel[][] _values;

}