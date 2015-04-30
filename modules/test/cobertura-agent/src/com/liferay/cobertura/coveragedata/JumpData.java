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
public class JumpData implements BranchCoverageData<JumpData>, Serializable {

	private static final long serialVersionUID = 8;

	private final int _jumpNumber;

	private final AtomicLong _trueHitsCounter = new AtomicLong();

	private final AtomicLong _falseHitsCounter = new AtomicLong();

	public JumpData(
		String className, int lineNumber, int jumpNumber) {

		_className = className;
		_lineNumber = lineNumber;
		_jumpNumber = jumpNumber;
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

	public int getJumpNumber() {
		return _jumpNumber;
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
	public void merge(JumpData jumpData) {
		if (!_className.equals(jumpData._className) ||
			(_lineNumber != jumpData._lineNumber) ||
			(_jumpNumber != jumpData._jumpNumber)) {

			throw new IllegalArgumentException(
				"Jump data mismatch, left : " + toString() + ", right : " +
					jumpData);
		}

		_trueHitsCounter.addAndGet(jumpData._trueHitsCounter.get());
		_falseHitsCounter.addAndGet(jumpData._falseHitsCounter.get());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{className=");
		sb.append(_className);
		sb.append(", lineNumber=");
		sb.append(_lineNumber);
		sb.append(", jumpNumber=");
		sb.append(_jumpNumber);
		sb.append("}");

		return sb.toString();
	}

	private final String _className;
	private final int _lineNumber;

}