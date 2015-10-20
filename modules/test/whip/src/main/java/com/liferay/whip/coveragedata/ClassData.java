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

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Shuyang Zhou
 */
public class ClassData
	extends CoverageDataContainer<Integer, LineData, ClassData> {

	public ClassData(String name) {
		_name = name;
	}

	public LineData addLine(int lineNumber) {
		LineData lineData = new LineData(_name, lineNumber);

		LineData previousLineData = children.putIfAbsent(
			lineData.getLineNumber(), lineData);

		if (previousLineData == null) {
			return lineData;
		}

		return previousLineData;
	}

	public void addLineJump(int lineNumber, int branchNumber) {
		LineData lineData = _getLineData(lineNumber);

		lineData.addJump(new JumpData(_name, lineNumber, branchNumber));
	}

	public void addLineSwitch(
		int lineNumber, int switchNumber, int min, int max) {

		LineData lineData = _getLineData(lineNumber);

		lineData.addSwitch(
			new SwitchData(_name, lineNumber, switchNumber, max - min + 1));
	}

	public void addLineSwitch(int lineNumber, int switchNumber, int[] keys) {
		LineData lineData = _getLineData(lineNumber);

		lineData.addSwitch(
			new SwitchData(_name, lineNumber, switchNumber, keys.length));
	}

	public Set<LineData> getLines() {
		Set<LineData> set = new TreeSet<>(
			new Comparator<LineData>() {

				@Override
				public int compare(LineData lineData1, LineData lineData2) {
					return lineData1.getLineNumber() -
						lineData2.getLineNumber();
				}

			});

		set.addAll(children.values());

		return set;
	}

	public String getName() {
		return _name;
	}

	@Override
	public int getNumberOfCoveredBranches() {
		int numberOfCoveredBranches = 0;

		for (LineData lineData : children.values()) {
			numberOfCoveredBranches += lineData.getNumberOfCoveredBranches();
		}

		return numberOfCoveredBranches;
	}

	@Override
	public int getNumberOfValidBranches() {
		int numberOfValidBranches = 0;

		for (LineData lineData : children.values()) {
			numberOfValidBranches += lineData.getNumberOfValidBranches();
		}

		return numberOfValidBranches;
	}

	@Override
	public void merge(ClassData classData) {
		if (!_name.equals(classData._name)) {
			throw new IllegalArgumentException(
				"Class data mismatch, left : " + _name + ", right : " +
					classData._name);
		}

		super.merge(classData);
	}

	public void touch(int lineNumber) {
		LineData lineData = _getLineData(lineNumber);

		lineData.touch();
	}

	public void touchJump(int lineNumber, int branchNumber, boolean branch) {
		LineData lineData = _getLineData(lineNumber);

		lineData.touchJump(branchNumber, branch);
	}

	public void touchSwitch(int lineNumber, int switchNumber, int branch) {
		LineData lineData = _getLineData(lineNumber);

		lineData.touchSwitch(switchNumber, branch);
	}

	private LineData _getLineData(int lineNumber) {
		LineData lineData = children.get(lineNumber);

		if (lineData == null) {
			throw new IllegalStateException(
				"No instrument data for class " + _name + " line " +
					lineNumber);
		}

		return lineData;
	}

	private static final long serialVersionUID = 1;

	private final String _name;

}