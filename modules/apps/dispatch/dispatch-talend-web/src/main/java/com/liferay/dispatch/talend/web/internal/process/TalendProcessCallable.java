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

package com.liferay.dispatch.talend.web.internal.process;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

/**
 * @author Igor Beslic
 */
public class TalendProcessCallable implements ProcessCallable<Serializable> {

	public TalendProcessCallable(
		String[] mainMethodArgs, String mainMethodClassFQN) {

		_mainMethodArgs = ArrayUtil.clone(mainMethodArgs);
		_mainMethodClassFQN = mainMethodClassFQN;
	}

	@Override
	public Serializable call() throws ProcessException {
		ClassLoader classLoader = TalendProcessCallable.class.getClassLoader();

		try {
			Class<?> talendJobClass = classLoader.loadClass(
				_mainMethodClassFQN);

			Method talendJobClassMainMethod = talendJobClass.getMethod(
				"main", String[].class);

			talendJobClassMainMethod.setAccessible(true);

			talendJobClassMainMethod.invoke(
				null, new Object[] {_mainMethodArgs});
		}
		catch (Throwable throwable) {
			throw new ProcessException(throwable);
		}

		return null;
	}

	private static final long serialVersionUID = 1L;

	private final String[] _mainMethodArgs;
	private final String _mainMethodClassFQN;

}