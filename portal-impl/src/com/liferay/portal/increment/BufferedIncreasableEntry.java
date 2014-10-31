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

package com.liferay.portal.increment;

import com.liferay.portal.kernel.concurrent.IncreasableEntry;
import com.liferay.portal.kernel.increment.Increment;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Zsolt Berentey
 */
public class BufferedIncreasableEntry<K, T>
	extends IncreasableEntry<K, Increment<T>> {

	public BufferedIncreasableEntry(
		MethodInvocation methodInvocation, K key, Increment<T> value) {

		super(key, value);

		_methodInvocation = methodInvocation;
	}

	@Override
	public BufferedIncreasableEntry<K, T> increase(Increment<T> deltaValue) {
		return new BufferedIncreasableEntry<K, T>(
			_methodInvocation, key,
			value.increaseForNew(deltaValue.getValue()));
	}

	public void proceed() throws Throwable {
		Object[] arguments = _methodInvocation.getArguments();

		arguments[arguments.length - 1] = getValue().getValue();

		_methodInvocation.proceed();
	}

	@Override
	public String toString() {
		return _methodInvocation.toString();
	}

	private final MethodInvocation _methodInvocation;

}