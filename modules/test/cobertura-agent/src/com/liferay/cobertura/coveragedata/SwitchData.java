package com.liferay.cobertura.coveragedata;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicLongArray;

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
