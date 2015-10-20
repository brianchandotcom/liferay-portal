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

package com.liferay.whip.coveragedata;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class JumpData implements BranchCoverageData<JumpData>, Serializable {

	public JumpData(String className, int lineNumber, int jumpNumber) {
		_className = className;
		_lineNumber = lineNumber;
		_jumpNumber = jumpNumber;
	}

	@Override
	public double getBranchCoverageRate() {
		return (double)getNumberOfCoveredBranches() /
			getNumberOfValidBranches();
	}

	public int getJumpNumber() {
		return _jumpNumber;
	}

	@Override
	public int getNumberOfCoveredBranches() {
		int numberOfCoveredBranches = 0;

		if (_trueHitCounter.get() > 0) {
			numberOfCoveredBranches++;
		}

		if (_falseHitCounter.get() > 0) {
			numberOfCoveredBranches++;
		}

		return numberOfCoveredBranches;
	}

	@Override
	public int getNumberOfValidBranches() {
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

		AtomicLong trueHitCounter = jumpData._trueHitCounter;

		_trueHitCounter.addAndGet(trueHitCounter.get());

		AtomicLong falseHitCounter = jumpData._falseHitCounter;

		_falseHitCounter.addAndGet(falseHitCounter.get());
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

	public void touchBranch(boolean branch) {
		if (branch) {
			_trueHitCounter.incrementAndGet();
		}
		else {
			_falseHitCounter.incrementAndGet();
		}
	}

	private static final long serialVersionUID = 1;

	private final String _className;
	private final AtomicLong _falseHitCounter = new AtomicLong();
	private final int _jumpNumber;
	private final int _lineNumber;
	private final AtomicLong _trueHitCounter = new AtomicLong();

}