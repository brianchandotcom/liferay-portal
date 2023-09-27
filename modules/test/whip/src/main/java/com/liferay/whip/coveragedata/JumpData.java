/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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