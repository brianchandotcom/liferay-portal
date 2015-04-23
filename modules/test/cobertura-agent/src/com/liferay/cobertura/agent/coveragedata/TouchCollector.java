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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.ProjectData;
import net.sourceforge.cobertura.coveragedata.countermaps.AtomicCounterMap;
import net.sourceforge.cobertura.coveragedata.countermaps.CounterMap;

/**
 * @author Cristina González
 */
public class TouchCollector {

	public static synchronized void applyTouchesOnProjectData(
		ProjectData projectData) {

		System.out.println("Flushing results...");

		Map<LineTouchData, Integer> touches =
			_touchedLines.getFinalStateAndCleanIt();

		for (Entry<LineTouchData, Integer> touch :touches.entrySet()) {
			if (touch.getValue() > 0) {
				_getClassFor(
					touch.getKey(),
					projectData).touch(touch.getKey().lineNumber,
					touch.getValue());
			}
		}

		Map<SwitchTouchData, Integer> switchTouches =
			_switchTouchData.getFinalStateAndCleanIt();

		for (Entry<SwitchTouchData, Integer> touch :switchTouches.entrySet()) {
			if (touch.getValue() > 0) {
				_getClassFor(touch.getKey(), projectData).touchSwitch(
					touch.getKey().lineNumber, touch.getKey().switchNumber,
					touch.getKey().branch, touch.getValue());
			}
		}

		Map<JumpTouchData, Integer> jumpTouches =
			_jumpTouchData.getFinalStateAndCleanIt();

		for (Entry<JumpTouchData, Integer> touch :jumpTouches.entrySet()) {
			if (touch.getValue() > 0) {
				_getClassFor(touch.getKey(), projectData).touchJump(
					touch.getKey().lineNumber, touch.getKey().branchNumber,
					touch.getKey().branch, touch.getValue());
			}
		}

		System.out.println("Flushing results done");
	}

	/**
	 * This method is only called by code that has been instrumented.  It
	 * is not called by any of the Cobertura code or ant tasks.
	 */
	public static final void touch(String classId, int lineNumber) {
		_touchedLines.incrementValue(
			new LineTouchData(_registerClassData(classId), lineNumber));
	}

	/**
	 * This method is only called by code that has been instrumented.  It
	 * is not called by any of the Cobertura code or ant tasks.
	 */
	public static final void touchJump(
		String classId, int lineNumber, int branchNumber, boolean branch) {

		_jumpTouchData.incrementValue(
			new JumpTouchData(
				_registerClassData(classId), lineNumber, branchNumber, branch));
	}

	/**
	 * This method is only called by code that has been instrumented.  It
	 * is not called by any of the Cobertura code or ant tasks.
	 */
	public static final void touchSwitch(
		String classId, int lineNumber, int switchNumber, int branch) {

		_switchTouchData.incrementValue(
			new SwitchTouchData(
				_registerClassData(classId), lineNumber, switchNumber, branch));
	}

	private static ClassData _getClassFor(
		LineTouchData key, ProjectData projectData) {

		return projectData.getOrCreateClassData(
			_classId2class.get(key.classId));
	}

	private static final int _registerClassData(String name) {
		Integer res =_class2classId.get(name);

		if (res == null) {
			int new_id = _lastClassId.incrementAndGet();

			_class2classId.put(name, new_id);

			_classId2class.put(new_id, name);

			return new_id;
		}

		return res;
	}

	private static final Map<String, Integer> _class2classId =
		new ConcurrentHashMap<>();
	private static final Map<Integer, String> _classId2class =
		new ConcurrentHashMap<>();
	private static final CounterMap<JumpTouchData> _jumpTouchData =
		new AtomicCounterMap<>();
	private static final AtomicInteger _lastClassId = new AtomicInteger(1);
	private static final CounterMap<SwitchTouchData> _switchTouchData =
		new AtomicCounterMap<>();
	private static final CounterMap<LineTouchData> _touchedLines =
		new AtomicCounterMap<>();

	static {
		InstrumentationAgent.initialize();
	}

	private static class JumpTouchData extends LineTouchData {

		public JumpTouchData(
			int classId, int lineNumber, int branchNumber, boolean branch) {

			super(classId, lineNumber);

			this.branchNumber = branchNumber;

			this.branch = branch;
		}

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

			if (branch != other.branch)return false;

			if (branchNumber != other.branchNumber)return false;
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;

			int result = super.hashCode();

			result = prime * result + (branch ? 1231 : 1237);

			result = prime * result + branchNumber;

			return result;
		}

		protected boolean branch;
		protected int branchNumber;

	}

	private static class LineTouchData {

		public LineTouchData(int classId, int lineNumber) {
			this.classId = classId;
			this.lineNumber = lineNumber;
		}

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

			if (classId != other.classId) {
				return false;
			}

			if (lineNumber != other.lineNumber) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;

			int result = 1;

			result = prime * result + classId;

			result = prime * result + lineNumber;

			return result;
		}

		protected int classId, lineNumber;

	}

	private static class SwitchTouchData extends LineTouchData {

		public SwitchTouchData(
			int classId, int lineNumber, int switchNumber, int branch) {

			super(classId, lineNumber);

			this.switchNumber = switchNumber;

			this.branch = branch;
		}

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

			if (branch != other.branch) {
				return false;
			}

			if (switchNumber != other.switchNumber) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;

			int result = super.hashCode();

			result = prime * result + branch;

			result = prime * result + switchNumber;

			return result;
		}

		protected int switchNumber, branch;

	}

}