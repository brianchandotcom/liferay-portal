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

package com.liferay.portal.internal.cluster;

import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.util.MethodHandler;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class MethodHandlerProcessCallable<T extends Serializable>
	implements ProcessCallable<T> {

	public MethodHandlerProcessCallable(MethodHandler methodHandler) {
		_methodHandler = methodHandler;
	}

	@Override
	public T call() throws ProcessException {
		try {
			return (T)_methodHandler.invoke();
		}
		catch (Exception e) {
			throw new ProcessException(e);
		}
	}

	private static final long serialVersionUID = 1L;

	private final MethodHandler _methodHandler;

}