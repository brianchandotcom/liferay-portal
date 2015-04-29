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

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * This class implements HasBeenInstrumented so that when cobertura instruments
 * itself, it will omit this class. It does this to avoid an infinite recursion
 * problem because instrumented classes make use of this class.
 * </p>
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
