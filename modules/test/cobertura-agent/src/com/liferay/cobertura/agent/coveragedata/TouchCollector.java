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

package com.liferay.cobertura.agent.coveragedata;

import com.liferay.cobertura.agent.InstrumentationAgent;
import com.liferay.cobertura.agent.util.HashUtil;

import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.ProjectData;
import net.sourceforge.cobertura.coveragedata.countermaps.AtomicCounterMap;
import net.sourceforge.cobertura.coveragedata.countermaps.CounterMap;

/**
 * @author Cristina González
 */
public class TouchCollector {

	public static void applyTouchesOnProjectData(ProjectData projectData) {
		Map<LineTouchData, Integer> lineTouchDataMap =
			_touchedLines.getFinalStateAndCleanIt();

		for (Entry<LineTouchData, Integer> lineTouchDataEntry :
				lineTouchDataMap.entrySet()) {

			int hits = lineTouchDataEntry.getValue();

			if (hits > 0) {
				LineTouchData lineTouchData = lineTouchDataEntry.getKey();

				ClassData classData = projectData.getOrCreateClassData(
					lineTouchData.getClassName());

				classData.touch(lineTouchData.getLineNumber(), hits);
			}
		}

		Map<SwitchTouchData, Integer> switchTouchDataMap =
			_switchTouchData.getFinalStateAndCleanIt();

		for (Entry<SwitchTouchData, Integer> switchTouchDataEntry :
				switchTouchDataMap.entrySet()) {

			int hits = switchTouchDataEntry.getValue();

			if (hits > 0) {
				SwitchTouchData switchTouchData = switchTouchDataEntry.getKey();

				ClassData classData = projectData.getOrCreateClassData(
					switchTouchData.getClassName());

				classData.touchSwitch(
					switchTouchData.getLineNumber(),
					switchTouchData.getSwitchNumber(),
					switchTouchData.getBranch(), hits);
			}
		}

		Map<JumpTouchData, Integer> jumpTouchDataMap =
			_jumpTouchData.getFinalStateAndCleanIt();

		for (Entry<JumpTouchData, Integer> jumpTouchDataEntry :
				jumpTouchDataMap.entrySet()) {

			int hits = jumpTouchDataEntry.getValue();

			if (hits > 0) {
				JumpTouchData jumpTouchData = jumpTouchDataEntry.getKey();

				ClassData classData = projectData.getOrCreateClassData(
					jumpTouchData.getClassName());

				classData.touchJump(
					jumpTouchData.getLineNumber(),
					jumpTouchData.getBranchNumber(), jumpTouchData.isBranch(),
					hits);
			}
		}
	}

	public static void touch(String className, int lineNumber) {
		_touchedLines.incrementValue(new LineTouchData(className, lineNumber));
	}

	public static void touchJump(
		String className, int lineNumber, int branchNumber, boolean branch) {

		_jumpTouchData.incrementValue(
			new JumpTouchData(className, lineNumber, branchNumber, branch));
	}

	public static void touchSwitch(
		String className, int lineNumber, int switchNumber, int branch) {

		_switchTouchData.incrementValue(
			new SwitchTouchData(className, lineNumber, switchNumber, branch));
	}

	private static final CounterMap<JumpTouchData> _jumpTouchData =
		new AtomicCounterMap<>();
	private static final CounterMap<SwitchTouchData> _switchTouchData =
		new AtomicCounterMap<>();
	private static final CounterMap<LineTouchData> _touchedLines =
		new AtomicCounterMap<>();

	static {
		InstrumentationAgent.initialize();
	}

	private static class JumpTouchData extends LineTouchData {

		@Override
		public boolean equals(Object obj) {
			JumpTouchData jumpTouchData = (JumpTouchData)obj;

			if (super.equals(jumpTouchData) &&
				(_branch == jumpTouchData._branch) &&
				(_branchNumber == jumpTouchData._branchNumber)) {

				return true;
			}

			return false;
		}

		public int getBranchNumber() {
			return _branchNumber;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(super.hashCode(), _branch);

			return HashUtil.hash(hashCode, _branchNumber);
		}

		public boolean isBranch() {
			return _branch;
		}

		private JumpTouchData(
			String className, int lineNumber, int branchNumber,
			boolean branch) {

			super(className, lineNumber);

			_branchNumber = branchNumber;
			_branch = branch;
		}

		private final boolean _branch;
		private final int _branchNumber;

	}

	private static class LineTouchData {

		@Override
		public boolean equals(Object obj) {
			LineTouchData lineTouchData = (LineTouchData)obj;

			if (_className.equals(lineTouchData._className) &&
				(_lineNumber == lineTouchData._lineNumber)) {

				return true;
			}

			return false;
		}

		public String getClassName() {
			return _className;
		}

		public int getLineNumber() {
			return _lineNumber;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _className);

			return HashUtil.hash(hashCode, _lineNumber);
		}

		private LineTouchData(String className, int lineNumber) {
			_className = className;
			_lineNumber = lineNumber;
		}

		private final String _className;
		private final int _lineNumber;

	}

	private static class SwitchTouchData extends LineTouchData {

		@Override
		public boolean equals(Object obj) {
			SwitchTouchData switchTouchData = (SwitchTouchData)obj;

			if (super.equals(switchTouchData) &&
				(_branch == switchTouchData._branch) &&
				(_switchNumber == switchTouchData._switchNumber)) {

				return true;
			}

			return false;
		}

		public int getBranch() {
			return _branch;
		}

		public int getSwitchNumber() {
			return _switchNumber;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(super.hashCode(), _branch);

			return HashUtil.hash(hashCode, _switchNumber);
		}

		private SwitchTouchData(
			String className, int lineNumber, int switchNumber, int branch) {

			super(className, lineNumber);

			_switchNumber = switchNumber;
			_branch = branch;
		}

		private final int _branch;
		private final int _switchNumber;

	}

}