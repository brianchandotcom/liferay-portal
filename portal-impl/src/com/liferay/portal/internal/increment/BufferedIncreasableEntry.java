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

package com.liferay.portal.internal.increment;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.increment.Increment;

import java.util.Arrays;

/**
 * @author Zsolt Berentey
 */
public class BufferedIncreasableEntry<K, T>
	extends IncreasableEntry<K, Increment<T>> {

	public BufferedIncreasableEntry(
		AopMethodInvocation aopMethodInvocation, Object[] arguments, K key,
		Increment<T> value) {

		super(key, value);

		_aopMethodInvocation = aopMethodInvocation;
		_arguments = arguments;
	}

	@Override
	public BufferedIncreasableEntry<K, T> increase(Increment<T> deltaValue) {
		return new BufferedIncreasableEntry<>(
			_aopMethodInvocation, _arguments, key,
			value.increaseForNew(deltaValue.getValue()));
	}

	public void proceed() throws Throwable {
		_arguments[_arguments.length - 1] = getValue().getValue();

		_aopMethodInvocation.proceed(_arguments);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			_aopMethodInvocation, StringPool.OPEN_PARENTHESIS,
			Arrays.toString(_arguments), StringPool.CLOSE_PARENTHESIS);
	}

	private final AopMethodInvocation _aopMethodInvocation;
	private final Object[] _arguments;

}