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
		catch (InvocationTargetException ite) {
			ReflectionUtil.throwException(ite.getCause());
		}
		catch (ReflectiveOperationException roe) {
			ReflectionUtil.throwException(roe);
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
		catch (InvocationTargetException ite) {
			ReflectionUtil.throwException(ite.getCause());
		}
		catch (ReflectiveOperationException roe) {
			ReflectionUtil.throwException(roe);
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
		catch (InvocationTargetException ite) {
			ReflectionUtil.throwException(ite.getCause());
		}
		catch (ReflectiveOperationException roe) {
			ReflectionUtil.throwException(roe);
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
		catch (ReflectiveOperationException roe) {
			throw new ExceptionInInitializerError(roe);
		}
	}

}