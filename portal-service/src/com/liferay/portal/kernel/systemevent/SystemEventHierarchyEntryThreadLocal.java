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

package com.liferay.portal.kernel.systemevent;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.util.PortalUtil;

import java.util.Stack;

/**
 * @author Zsolt Berentey
 */
public class SystemEventHierarchyEntryThreadLocal {

	public static void clear() {
		Stack<SystemEventHierarchyEntry> systemEventHierarchyEntries =
			_systemEventHierarchyEntries.get();

		systemEventHierarchyEntries.clear();
	}

	public static SystemEventHierarchyEntry peek() {
		Stack<SystemEventHierarchyEntry> systemEventHierarchyEntries =
			_systemEventHierarchyEntries.get();

		if (systemEventHierarchyEntries.isEmpty()) {
			return null;
		}

		return systemEventHierarchyEntries.peek();
	}

	public static SystemEventHierarchyEntry pop() {
		return pop(-1, -1);
	}

	public static SystemEventHierarchyEntry pop(Class<?> clazz) {
		return pop(PortalUtil.getClassNameId(clazz), 0);
	}

	public static SystemEventHierarchyEntry pop(Class<?> clazz, long classPK) {
		return pop(PortalUtil.getClassNameId(clazz), classPK);
	}

	public static SystemEventHierarchyEntry pop(
		long classNameId, long classPK) {

		Stack<SystemEventHierarchyEntry> systemEventHierarchyEntries =
			_systemEventHierarchyEntries.get();

		if (systemEventHierarchyEntries.isEmpty()) {
			return null;
		}

		SystemEventHierarchyEntry systemEventHierarchyEntry =
			systemEventHierarchyEntries.peek();

		if (((classNameId < 0) && (classPK < 0)) ||
			systemEventHierarchyEntry.hasTypedModel(classNameId, classPK)) {

			return systemEventHierarchyEntries.pop();
		}

		return null;
	}

	public static SystemEventHierarchyEntry pop(String className) {
		return pop(PortalUtil.getClassNameId(className), 0);
	}

	public static SystemEventHierarchyEntry pop(
		String className, long classPK) {

		return pop(PortalUtil.getClassNameId(className), classPK);
	}

	public static SystemEventHierarchyEntry push() {
		return push(SystemEventConstants.ACTION_SKIP);
	}

	public static SystemEventHierarchyEntry push(Class<?> clazz) {
		return push(
			PortalUtil.getClassNameId(clazz), 0,
			SystemEventConstants.ACTION_SKIP);
	}

	public static SystemEventHierarchyEntry push(Class<?> clazz, long classPK) {
		return push(
			PortalUtil.getClassNameId(clazz), classPK,
			SystemEventConstants.ACTION_SKIP);
	}

	public static SystemEventHierarchyEntry push(
		Class<?> clazz, long classPK, int action) {

		return push(PortalUtil.getClassNameId(clazz), classPK, action);
	}

	public static SystemEventHierarchyEntry push(int action) {
		return push(0, 0, action);
	}

	public static SystemEventHierarchyEntry push(
		long classNameId, long classPK, int action) {

		long parentSystemEventId = 0;
		long systemEventSetKey = 0;

		Stack<SystemEventHierarchyEntry> systemEventHierarchyEntries =
			_systemEventHierarchyEntries.get();

		SystemEventHierarchyEntry parentSystemEventHierarchyEntry = null;

		if (!systemEventHierarchyEntries.isEmpty()) {
			parentSystemEventHierarchyEntry =
				systemEventHierarchyEntries.peek();
		}

		if (parentSystemEventHierarchyEntry == null) {
			systemEventSetKey = CounterLocalServiceUtil.increment();
		}
		else if (parentSystemEventHierarchyEntry.getAction() ==
					SystemEventConstants.ACTION_SKIP) {

			return null;
		}
		else {
			parentSystemEventId =
				parentSystemEventHierarchyEntry.getSystemEventId();
			systemEventSetKey =
				parentSystemEventHierarchyEntry.getSystemEventSetKey();
		}

		SystemEventHierarchyEntry systemEventHierarchyEntry =
			new SystemEventHierarchyEntry(
				CounterLocalServiceUtil.increment(), classNameId, classPK,
				parentSystemEventId, systemEventSetKey, action);

		return systemEventHierarchyEntries.push(systemEventHierarchyEntry);
	}

	public static SystemEventHierarchyEntry push(String className) {
		return push(className, 0, SystemEventConstants.ACTION_SKIP);
	}

	public static SystemEventHierarchyEntry push(
		String className, long classPK, int action) {

		return push(PortalUtil.getClassNameId(className), classPK, action);
	}

	private static ThreadLocal<Stack<SystemEventHierarchyEntry>>
		_systemEventHierarchyEntries =
			new AutoResetThreadLocal<Stack<SystemEventHierarchyEntry>>(
				SystemEventHierarchyEntryThreadLocal.class +
					"._systemEventHierarchyEntries",
				new Stack<SystemEventHierarchyEntry>());

}