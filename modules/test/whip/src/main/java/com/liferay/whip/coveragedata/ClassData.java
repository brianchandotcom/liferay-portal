/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public LineData addLine(
		String methodName, String methodDescriptor, int lineNumber) {

		LineData lineData = new LineData(
			_name, methodName, methodDescriptor, lineNumber);

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