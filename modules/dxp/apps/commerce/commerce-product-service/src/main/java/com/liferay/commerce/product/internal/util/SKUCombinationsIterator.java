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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Marco Leo
 */
public class SKUCombinationsIterator
	implements Iterator<CPDefinitionOptionValueRel[]> {

	public SKUCombinationsIterator(
		Map<CPDefinitionOptionRel, CPDefinitionOptionValueRel[]>
			cpDefinitionOptionRelMap) {

		_combinationLength = cpDefinitionOptionRelMap.size();

		_cpDefinitionOptionValueRels =
			new CPDefinitionOptionValueRel[_combinationLength][];
		_currentIndexes = new int[_combinationLength];
		_maxIndexes = new int[_combinationLength];

		if (_combinationLength == 0) {
			_hasNext = false;

			return;
		}

		_hasNext = true;

		int valuesIndex = 0;

		List<CPDefinitionOptionRel> cpDefinitionOptionRels = new ArrayList<>(
			cpDefinitionOptionRelMap.keySet());

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			_cpDefinitionOptionValueRels[valuesIndex++] =
				cpDefinitionOptionRelMap.get(cpDefinitionOptionRel);
		}

		for (int i = 0; i < _combinationLength; i++) {
			if (_cpDefinitionOptionValueRels[i].length == 0) {
				_hasNext = false;

				return;
			}

			_currentIndexes[i] = 0;
			_maxIndexes[i] = _cpDefinitionOptionValueRels[i].length - 1;
		}
	}

	@Override
	public boolean hasNext() {
		return _hasNext;
	}

	@Override
	public CPDefinitionOptionValueRel[] next() {
		if (!_hasNext) {
			throw new NoSuchElementException(
				"No more combinations are available");
		}

		CPDefinitionOptionValueRel[] combination =
			_getCombinationByCurrentIndexes();

		_nextIndexesCombination();

		return combination;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(
			"Remove operation is not supported");
	}

	private CPDefinitionOptionValueRel[] _getCombinationByCurrentIndexes() {
		CPDefinitionOptionValueRel[] combination =
			new CPDefinitionOptionValueRel[_combinationLength];

		for (int i = 0; i < _combinationLength; i++) {
			combination[i] =
				_cpDefinitionOptionValueRels[i][_currentIndexes[i]];
		}

		return combination;
	}

	private void _nextIndexesCombination() {
		for (int i = _combinationLength - 1; i >= 0; i--) {
			if (_currentIndexes[i] < _maxIndexes[i]) {
				++_currentIndexes[i];

				return;
			}

			_currentIndexes[i] = 0;
		}

		_hasNext = false;
	}

	private final int _combinationLength;
	private final CPDefinitionOptionValueRel[][] _cpDefinitionOptionValueRels;
	private final int[] _currentIndexes;
	private boolean _hasNext;
	private final int[] _maxIndexes;

}