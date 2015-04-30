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

package com.liferay.cobertura.coveragedata;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * @author Shuyang Zhou
 */
public class SwitchData implements BranchCoverageData<SwitchData>, Serializable
{
	private static final long serialVersionUID = 9;


	private final int _switchNumber;

	private final AtomicLongArray _hitsArray;

	public SwitchData(
		String className, int lineNumber, int switchNumber, int caseNumber) {

		_className = className;
		_lineNumber = lineNumber;
		_switchNumber = switchNumber;

		_hitsArray = new AtomicLongArray(caseNumber + 1);
	}

	public void touchBranch(int branch,int new_hits) {
		if (branch >= _hitsArray.length()) {
			throw new IllegalStateException(
			"No instrument data for class " + _className + " line " +
				_lineNumber + " switch " + _switchNumber + " branch " +
					branch);
		}

		if (branch == -1) {
			branch = _hitsArray.length() - 1;
		}

		_hitsArray.addAndGet(branch, new_hits);
	}

	@Override
	public double getBranchCoverageRate() {
		return (double)getNumberOfCoveredBranches()/getNumberOfValidBranches();
	}

	@Override
	public int getNumberOfCoveredBranches() {
		int branches = 0;

		for (int i = 0; i < _hitsArray.length(); i++) {
			long hits = _hitsArray.get(i);

			if (hits > 0) {
				branches++;
			}
		}

		return branches;
	}

	@Override
	public int getNumberOfValidBranches() {
		return _hitsArray.length();
	}

	public int getSwitchNumber() {
		return _switchNumber;
	}

	@Override
	public void merge(SwitchData switchData) {
		if (_hitsArray.length() != switchData._hitsArray.length()) {
			throw new IllegalArgumentException("Switch case number mismatch");
		}

		for (int i = 0; i < _hitsArray.length(); i++) {
			_hitsArray.addAndGet(i, switchData._hitsArray.get(i));
		}

	}

	private final String _className;
	private final int _lineNumber;

}
