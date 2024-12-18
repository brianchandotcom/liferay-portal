/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;

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
		push(PortalUtil.getClassNameId(clazz), classPK);
	}

	public static void push(long classNameId, long classPK) {
		Stack<SocialActivityHierarchyEntry> activityHierarchyEntries =
			_activityHierarchyEntries.get();

		activityHierarchyEntries.push(
			new SocialActivityHierarchyEntry(classNameId, classPK));
	}

	public static void push(String className, long classPK) {
		push(PortalUtil.getClassNameId(className), classPK);
	}

	private static final ThreadLocal<Stack<SocialActivityHierarchyEntry>>
		_activityHierarchyEntries = new CentralizedThreadLocal<>(
			SocialActivityHierarchyEntryThreadLocal.class +
				"._activityHierarchyEntries",
			Stack::new);

}