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

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
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

	@Override
	public double getBranchCoverageRate()
	{
			return ((double) getNumberOfCoveredBranches()) / getNumberOfValidBranches();
	}

	public int getConditionNumber() {
		return conditionNumber;
	}

	@Override
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

	@Override
	public int hashCode()
	{
		return this.conditionNumber;
	}

	@Override
	public int getNumberOfCoveredBranches()
	{
			return ((_trueHitsCounter.get() > 0) ? 1 : 0) + ((_falseHitsCounter.get() > 0) ? 1: 0);
	}

	@Override
	public int getNumberOfValidBranches()
	{
		return 2;
	}

	@Override
	public void merge(BranchCoverageData coverageData)
	{
		JumpData jumpData = (JumpData) coverageData;
			_trueHitsCounter.addAndGet(jumpData._trueHitsCounter.get());
			_falseHitsCounter.addAndGet(jumpData._falseHitsCounter.get());
	}

}
