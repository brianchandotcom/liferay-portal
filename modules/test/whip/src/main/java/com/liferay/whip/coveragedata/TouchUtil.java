/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.whip.coveragedata;

import com.liferay.whip.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class TouchUtil {

	public static void touch(String className, int lineNumber) {
		if (TouchUtil.class.getClassLoader() ==
				ClassLoader.getSystemClassLoader()) {

			ProjectData projectData = ProjectDataUtil.getProjectData();

			ClassData classData = projectData.getClassData(className);

			classData.touch(lineNumber);

			return;
		}

		try {
			_touchMethod.invoke(null, className, lineNumber);
		}
		catch (InvocationTargetException invocationTargetException) {
			ReflectionUtil.throwException(invocationTargetException.getCause());
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			ReflectionUtil.throwException(reflectiveOperationException);
		}
	}

	public static void touchJump(
		String className, int lineNumber, int branchNumber, boolean branch) {

		if (TouchUtil.class.getClassLoader() ==
				ClassLoader.getSystemClassLoader()) {

			ProjectData projectData = ProjectDataUtil.getProjectData();

			ClassData classData = projectData.getClassData(className);

			classData.touchJump(lineNumber, branchNumber, branch);

			return;
		}

		try {
			_touchJumpMethod.invoke(
				null, className, lineNumber, branchNumber, branch);
		}
		catch (InvocationTargetException invocationTargetException) {
			ReflectionUtil.throwException(invocationTargetException.getCause());
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			ReflectionUtil.throwException(reflectiveOperationException);
		}
	}

	public static void touchSwitch(
		String className, int lineNumber, int switchNumber, int branch) {

		if (TouchUtil.class.getClassLoader() ==
				ClassLoader.getSystemClassLoader()) {

			ProjectData projectData = ProjectDataUtil.getProjectData();

			ClassData classData = projectData.getClassData(className);

			classData.touchSwitch(lineNumber, switchNumber, branch);

			return;
		}

		try {
			_touchSwitchMethod.invoke(
				null, className, lineNumber, switchNumber, branch);
		}
		catch (InvocationTargetException invocationTargetException) {
			ReflectionUtil.throwException(invocationTargetException.getCause());
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			ReflectionUtil.throwException(reflectiveOperationException);
		}
	}

	private static final Method _touchJumpMethod;
	private static final Method _touchMethod;
	private static final Method _touchSwitchMethod;

	static {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		try {
			Class<?> touchUtilClass = classLoader.loadClass(
				TouchUtil.class.getName());

			_touchMethod = touchUtilClass.getDeclaredMethod(
				"touch", String.class, int.class);
			_touchJumpMethod = touchUtilClass.getDeclaredMethod(
				"touchJump", String.class, int.class, int.class, boolean.class);
			_touchSwitchMethod = touchUtilClass.getDeclaredMethod(
				"touchSwitch", String.class, int.class, int.class, int.class);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new ExceptionInInitializerError(reflectiveOperationException);
		}
	}

}