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