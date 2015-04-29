/*
 * Cobertura - http://cobertura.sourceforge.net/
 *
 * Copyright (C) 2006 Jiri Mares
 *
 * Cobertura is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * Cobertura is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cobertura; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package com.liferay.cobertura.coveragedata;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * <p>
 * This class implements HasBeenInstrumented so that when cobertura instruments
 * itself, it will omit this class. It does this to avoid an infinite recursion
 * problem because instrumented classes make use of this class.
 * </p>
 */
public class SwitchData implements BranchCoverageData, Serializable
{
	private static final long serialVersionUID = 9;


	private int switchNumber;

	private final AtomicLongArray _hitsArray;

	public SwitchData(int switchNumber, int caseNumber)
	{
		this.switchNumber = switchNumber;

		_hitsArray = new AtomicLongArray(caseNumber + 1);
	}

	public void touchBranch(
		String className, int lineNumber, int branch,int new_hits) {

		if (branch >= _hitsArray.length()) {
			throw new IllegalStateException(
			"No instrument data for class " + className + " line " +
				lineNumber + " switch " + switchNumber + " branch " +
					branch);
		}

		if (branch == -1) {
			branch = _hitsArray.length() - 1;
		}

		_hitsArray.addAndGet(branch, new_hits);
	}

	public double getBranchCoverageRate() {
		return (double)getNumberOfCoveredBranches()/getNumberOfValidBranches();
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || !(obj.getClass().equals(this.getClass())))
			return false;

		SwitchData switchData = (SwitchData) obj;

		if ((switchNumber == switchData.switchNumber) &&
			(_hitsArray.length() == switchData._hitsArray.length())) {

			for (int i = 0; i < _hitsArray.length(); i++) {
				if (_hitsArray.get(i) != _hitsArray.get(i)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public int hashCode()
	{
		return this.switchNumber;
	}

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

	public int getNumberOfValidBranches() {
		return _hitsArray.length();
	}

	public int getSwitchNumber() {
		return switchNumber;
	}

	public void merge(BranchCoverageData coverageData)
	{
		SwitchData switchData = (SwitchData) coverageData;

		if (_hitsArray.length() != switchData._hitsArray.length()) {
			throw new IllegalArgumentException("Switch case number mismatch");
		}

		for (int i = 0; i < _hitsArray.length(); i++) {
			_hitsArray.addAndGet(i, switchData._hitsArray.get(i));
		}

	}

}
