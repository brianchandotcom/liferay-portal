package com.liferay.cobertura.coveragedata;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicLong;

public class JumpData implements BranchCoverageData, Serializable
{
	private static final long serialVersionUID = 8;

	private int conditionNumber;

	private final AtomicLong _trueHitsCounter = new AtomicLong();

	private final AtomicLong _falseHitsCounter = new AtomicLong();

	JumpData(int conditionNumber)
	{
		this.conditionNumber = conditionNumber;
	}

	void touchBranch(boolean branch,int new_hits)
	{
			if (branch)
			{
				_trueHitsCounter.addAndGet(new_hits);
			}
			else
			{
				_falseHitsCounter.addAndGet(new_hits);
			}
	}

	public double getBranchCoverageRate()
	{
			return ((double) getNumberOfCoveredBranches()) / getNumberOfValidBranches();
	}

	public int getConditionNumber() {
		return conditionNumber;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || !(obj.getClass().equals(this.getClass())))
			return false;

		JumpData branchData = (JumpData) obj;
			return (_trueHitsCounter.get() == branchData._trueHitsCounter.get())
					&& (_falseHitsCounter.get() == branchData._falseHitsCounter.get())
					&& (this.conditionNumber == branchData.conditionNumber);
	}

	public int hashCode()
	{
		return this.conditionNumber;
	}

	public int getNumberOfCoveredBranches()
	{
			return ((_trueHitsCounter.get() > 0) ? 1 : 0) + ((_falseHitsCounter.get() > 0) ? 1: 0);
	}

	public int getNumberOfValidBranches()
	{
		return 2;
	}

	public void merge(BranchCoverageData coverageData)
	{
		JumpData jumpData = (JumpData) coverageData;
			_trueHitsCounter.addAndGet(jumpData._trueHitsCounter.get());
			_falseHitsCounter.addAndGet(jumpData._falseHitsCounter.get());
	}

}
