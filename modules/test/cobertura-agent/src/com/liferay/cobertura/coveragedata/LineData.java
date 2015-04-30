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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class LineData implements CoverageData<LineData>, Serializable {
	private static final long serialVersionUID = 4;

	private final AtomicLong _hitCounter = new AtomicLong();
	private final ConcurrentMap<Integer, JumpData> _jumpDatas =
		new ConcurrentHashMap<>();
	private final ConcurrentMap<Integer, SwitchData> _switchDatas =
		new ConcurrentHashMap<>();
	private final int _lineNumber;

	public LineData(String className, int lineNumber) {
		_className = className;
		_lineNumber = lineNumber;
	}

	@Override
	public double getBranchCoverageRate()
	{
		if (getNumberOfValidBranches() == 0)
			return 1d;

			return ((double) getNumberOfCoveredBranches()) / getNumberOfValidBranches();
	}

	public boolean isCovered()
	{
		return (_hitCounter.get() > 0) && ((getNumberOfValidBranches() == 0) || ((1.0 - getBranchCoverageRate()) < 0.0001));
	}

	public double getLineCoverageRate()
	{
		return (_hitCounter.get() > 0) ? 1 : 0;
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	@Override
	public int getNumberOfCoveredLines()
	{
		return (_hitCounter.get() > 0) ? 1 : 0;
	}

	@Override
	public int getNumberOfValidBranches()
	{
		int ret = 0;
			for (JumpData jumpData : _jumpDatas.values()) {
				ret += jumpData.getNumberOfValidBranches();
			}

			for (SwitchData switchData : _switchDatas.values()) {
				ret += switchData.getNumberOfValidBranches();
			}

			return ret;
	}

	@Override
	public int getNumberOfCoveredBranches()
	{
		int ret = 0;
			for (JumpData jumpData : _jumpDatas.values()) {
				ret += jumpData.getNumberOfCoveredBranches();
			}

			for (SwitchData switchData : _switchDatas.values()) {
				ret += switchData.getNumberOfCoveredBranches();
			}

			return ret;
	}

	@Override
	public int getNumberOfValidLines()
	{
		return 1;
	}

	@Override
	public void merge(LineData lineData) {
		if (!_className.equals(lineData._className) ||
			(_lineNumber != lineData._lineNumber)) {

			throw new IllegalArgumentException(
				"Line data mismatch, left : " + toString() + ", right : " +
					lineData);
		}

		_hitCounter.addAndGet(lineData._hitCounter.get());

		ConcurrentMap<Integer, JumpData> otherJumpDatas =
			lineData._jumpDatas;

		for (JumpData jumpData : otherJumpDatas.values()) {
			JumpData previousJumpData = _jumpDatas.putIfAbsent(
				jumpData.getJumpNumber(), jumpData);

			if (previousJumpData != null) {
				previousJumpData.merge(jumpData);
			}
		}

		ConcurrentMap<Integer, SwitchData> otherSwitchDatas =
			lineData._switchDatas;

		for (SwitchData switchData : otherSwitchDatas.values()) {
			SwitchData previousSwitchData = _switchDatas.putIfAbsent(
				switchData.getSwitchNumber(), switchData);

			if (previousSwitchData != null) {
				previousSwitchData.merge(switchData);
			}
		}
	}

	public JumpData addJump(JumpData jumpData) {
		JumpData previousJumpData = _jumpDatas.putIfAbsent(
			jumpData.getJumpNumber(), jumpData);

		if (previousJumpData != null) {
			return previousJumpData;
		}

		return jumpData;
	}

	public SwitchData addSwitch(SwitchData switchData) {
		SwitchData previousSwitchData = _switchDatas.putIfAbsent(
			switchData.getSwitchNumber(), switchData);

		if (previousSwitchData != null) {
			return previousSwitchData;
		}

		return switchData;
	}

	public void touch(int new_hits) {
		_hitCounter.addAndGet(new_hits);
	}

	public void touchJump(int jumpNumber, boolean branch,int hits) {
		JumpData jumpData = _jumpDatas.get(jumpNumber);

		if (jumpData == null) {
			throw new IllegalStateException(
				"No instrument data for class " + _className + " line " +
					_lineNumber + " jump " + jumpNumber);
		}

		jumpData.touchBranch(branch, hits);
	}

	public void touchSwitch(int switchNumber, int branch,int hits) {
		SwitchData switchData = _switchDatas.get(switchNumber);

		if (switchData == null) {
			throw new IllegalStateException(
				"No instrument data for class " + _className + " line " +
					_lineNumber + " switch " + switchNumber);
		}

		switchData.touchBranch(branch, hits);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{className=");
		sb.append(_className);
		sb.append(", lineNumber=");
		sb.append(_lineNumber);
		sb.append("}");

		return sb.toString();
	}

	private final String _className;

}
