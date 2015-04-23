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
		Map<LineTouchData, Integer> touches =
			_touchedLines.getFinalStateAndCleanIt();

		for (Entry<LineTouchData, Integer> touch :touches.entrySet()) {
			if (touch.getValue() > 0) {
				_getClassFor(
					touch.getKey(),
					projectData).touch(touch.getKey()._lineNumber,
					touch.getValue());
			}
		}

		Map<SwitchTouchData, Integer> switchTouches =
			_switchTouchData.getFinalStateAndCleanIt();

		for (Entry<SwitchTouchData, Integer> touch :switchTouches.entrySet()) {
			if (touch.getValue() > 0) {
				_getClassFor(touch.getKey(), projectData).touchSwitch(
					touch.getKey().getLineNumber(),
					touch.getKey()._switchNumber, touch.getKey()._branch,
					touch.getValue());
			}
		}

		Map<JumpTouchData, Integer> jumpTouches =
			_jumpTouchData.getFinalStateAndCleanIt();

		for (Entry<JumpTouchData, Integer> touch :jumpTouches.entrySet()) {
			if (touch.getValue() > 0) {
				_getClassFor(touch.getKey(), projectData).touchJump(
					touch.getKey().getLineNumber(),
					touch.getKey()._branchNumber, touch.getKey()._branch,
					touch.getValue());
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

	private static ClassData _getClassFor(
		LineTouchData key, ProjectData projectData) {

		return projectData.getOrCreateClassData(key.getClassName());
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
			if (this == obj)return true;

			if (!super.equals(obj)) {
				return false;
			}

			if (getClass() != obj.getClass()) {
				return false;
			}

			JumpTouchData other = (JumpTouchData)obj;

			if (_branch != other._branch)return false;

			if (_branchNumber != other._branchNumber)return false;
			return true;
		}

		public int getBranchNumber() {
			return _branchNumber;
		}

		@Override
		public int hashCode() {
			final int prime = 31;

			int result = super.hashCode();

			result = prime * result + (_branch ? 1231 : 1237);

			result = prime * result + _branchNumber;

			return result;
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
			if (this == obj) {
				return true;
			}

			if (obj == null) {
				return false;
			}

			if (getClass() != obj.getClass()) {
				return false;
			}

			LineTouchData other = (LineTouchData)obj;

			if (!_className.equals(other._className)) {
				return false;
			}

			if (_lineNumber != other._lineNumber) {
				return false;
			}

			return true;
		}

		public String getClassName() {
			return _className;
		}

		public int getLineNumber() {
			return _lineNumber;
		}

		@Override
		public int hashCode() {
			final int prime = 31;

			int result = 1;

			result = prime * result + _className.hashCode();

			result = prime * result + _lineNumber;

			return result;
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
			if (this == obj) {
				return true;
			}

			if (!super.equals(obj)) {
				return false;
			}

			if (getClass() != obj.getClass()) {
				return false;
			}

			SwitchTouchData other = (SwitchTouchData)obj;

			if (_branch != other._branch) {
				return false;
			}

			if (_switchNumber != other._switchNumber) {
				return false;
			}

			return true;
		}

		public int getBranch() {
			return _branch;
		}

		public int getSwitchNumber() {
			return _switchNumber;
		}

		@Override
		public int hashCode() {
			final int prime = 31;

			int result = super.hashCode();

			result = prime * result + _branch;

			result = prime * result + _switchNumber;

			return result;
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