/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.util.work;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.List;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkAction;

/**
 * @author Peter Shin
 */
public abstract class ExecuteJavaWorkAction
	implements WorkAction<ExecuteJavaWorkParameters> {

	@Override
	public void execute() {
		Class<?> clazz = null;

		ExecuteJavaWorkParameters executeJavaWorkParameters = getParameters();

		Property<String> mainProperty = executeJavaWorkParameters.getMain();

		try {
			clazz = Class.forName(mainProperty.get());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(classNotFoundException);
		}

		Method method = null;

		try {
			method = clazz.getMethod("main", String[].class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new RuntimeException(noSuchMethodException);
		}

		ListProperty<String> argsListProperty =
			executeJavaWorkParameters.getArgs();

		List<String> args = argsListProperty.get();

		try {
			method.invoke(null, (Object)args.toArray(new String[0]));
		}
		catch (IllegalAccessException illegalAccessException) {
			throw new RuntimeException(illegalAccessException);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			throw new RuntimeException(illegalArgumentException);
		}
		catch (InvocationTargetException invocationTargetException) {
			throw new RuntimeException(invocationTargetException);
		}
	}

}