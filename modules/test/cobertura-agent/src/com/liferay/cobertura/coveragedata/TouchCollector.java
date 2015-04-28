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

import com.liferay.cobertura.util.HashUtil;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.sourceforge.cobertura.coveragedata.ClassData;

/**
 * @author Cristina González
 */
public class TouchCollector {

	public static void applyTouchesOnProjectData(ProjectData projectData) {
		for (Entry<LineTouchData, AtomicInteger> entry :
				_lineTouchHitsMap.entrySet()) {

			LineTouchData lineTouchData = entry.getKey();

			ClassData classData = projectData.getOrCreateClassData(
				lineTouchData.getClassName());

			AtomicInteger atomicInteger = entry.getValue();

			classData.touch(lineTouchData.getLineNumber(), atomicInteger.get());
		}

		_lineTouchHitsMap.clear();

		for (Entry<SwitchTouchData, AtomicInteger> entry :
				_switchTouchHitsMap.entrySet()) {

			SwitchTouchData switchTouchData = entry.getKey();

			ClassData classData = projectData.getOrCreateClassData(
				switchTouchData.getClassName());

			AtomicInteger atomicInteger = entry.getValue();

			classData.touchSwitch(
				switchTouchData.getLineNumber(),
				switchTouchData.getSwitchNumber(), switchTouchData.getBranch(),
				atomicInteger.get());
		}

		_switchTouchHitsMap.clear();

		for (Entry<JumpTouchData, AtomicInteger> entry :
				_jumpTouchHitsMap.entrySet()) {

			JumpTouchData jumpTouchData = entry.getKey();

			ClassData classData = projectData.getOrCreateClassData(
				jumpTouchData.getClassName());

			AtomicInteger atomicInteger = entry.getValue();

			classData.touchJump(
				jumpTouchData.getLineNumber(), jumpTouchData.getBranchNumber(),
				jumpTouchData.isBranch(), atomicInteger.get());
		}

		_jumpTouchHitsMap.clear();
	}

	public static void touch(String className, int lineNumber) {
		_incrementHits(
			_lineTouchHitsMap, new LineTouchData(className, lineNumber));
	}

	public static void touchJump(
		String className, int lineNumber, int branchNumber, boolean branch) {

		_incrementHits(
			_jumpTouchHitsMap,
			new JumpTouchData(className, lineNumber, branchNumber, branch));
	}

	public static void touchSwitch(
		String className, int lineNumber, int switchNumber, int branch) {

		_incrementHits(
			_switchTouchHitsMap,
			new SwitchTouchData(className, lineNumber, switchNumber, branch));
	}

	private static <T extends LineTouchData> void _incrementHits(
		ConcurrentMap<T, AtomicInteger> hitsMap, T t) {

		AtomicInteger atomicInteger = hitsMap.get(t);

		if (atomicInteger == null) {
			atomicInteger = new AtomicInteger();

			AtomicInteger previousAtomicInteger = hitsMap.putIfAbsent(
				t, atomicInteger);

			if (previousAtomicInteger != null) {
				atomicInteger = previousAtomicInteger;
			}
		}

		atomicInteger.incrementAndGet();
	}

	private static final ConcurrentMap<JumpTouchData, AtomicInteger>
		_jumpTouchHitsMap = new ConcurrentHashMap<>();
	private static final ConcurrentMap<LineTouchData, AtomicInteger>
		_lineTouchHitsMap = new ConcurrentHashMap<>();
	private static final ConcurrentMap<SwitchTouchData, AtomicInteger>
		_switchTouchHitsMap = new ConcurrentHashMap<>();

	static {
		ProjectDataUtil.addMergeHook();
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