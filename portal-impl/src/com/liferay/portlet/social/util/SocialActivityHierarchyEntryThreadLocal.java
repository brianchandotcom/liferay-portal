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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.util.PortalUtil;

import java.util.Stack;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityHierarchyEntryThreadLocal {

	public static void clear() {
		Stack<SocialActivityHierarchyEntry> activityHierarchyEntries =
			_activityHierarchyEntries.get();

		activityHierarchyEntries.clear();
	}

	public static SocialActivityHierarchyEntry peek() {
		Stack<SocialActivityHierarchyEntry> activityHierarchyEntries =
			_activityHierarchyEntries.get();

		if (activityHierarchyEntries.isEmpty()) {
			return null;
		}

		return activityHierarchyEntries.peek();
	}

	public static SocialActivityHierarchyEntry pop() {
		Stack<SocialActivityHierarchyEntry> activityHierarchyEntries =
			_activityHierarchyEntries.get();

		if (activityHierarchyEntries.isEmpty()) {
			return null;
		}

		return activityHierarchyEntries.pop();
	}

	public static void push(Class<?> clazz, long classPK) {
		long classNameId = PortalUtil.getClassNameId(clazz);

		push(classNameId, classPK);
	}

	public static void push(long classNameId, long classPK) {
		Stack<SocialActivityHierarchyEntry> activityHierarchyEntries =
			_activityHierarchyEntries.get();

		activityHierarchyEntries.push(
			new SocialActivityHierarchyEntry(classNameId, classPK));
	}

	public static void push(String className, long classPK) {
		long classNameId = PortalUtil.getClassNameId(className);

		push(classNameId, classPK);
	}

	private static final ThreadLocal<Stack<SocialActivityHierarchyEntry>>
		_activityHierarchyEntries =
			new AutoResetThreadLocal<Stack<SocialActivityHierarchyEntry>>(
				SocialActivityHierarchyEntryThreadLocal.class +
					"._activityHierarchyEntries",
				new Stack<SocialActivityHierarchyEntry>());

}