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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.increment.Increment;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.util.Arrays;

/**
 * @author Zsolt Berentey
 */
public class BufferedIncreasableEntry<K, T>
	extends IncreasableEntry<K, Increment<T>> {

	public BufferedIncreasableEntry(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation, K key,
		Increment<T> value) {

		super(key, value);

		_serviceBeanMethodInvocation = serviceBeanMethodInvocation;
	}

	@Override
	public BufferedIncreasableEntry<K, T> increase(Increment<T> deltaValue) {
		return new BufferedIncreasableEntry<>(
			_serviceBeanMethodInvocation, key,
			value.increaseForNew(deltaValue.getValue()));
	}

	public void proceed() throws Throwable {
		Object[] arguments = _serviceBeanMethodInvocation.getArguments();

		arguments[arguments.length - 1] = getValue().getValue();

		_serviceBeanMethodInvocation.proceed();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(4);

		sb.append(_serviceBeanMethodInvocation.toString());
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(Arrays.toString(_serviceBeanMethodInvocation.getArguments()));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private final ServiceBeanMethodInvocation _serviceBeanMethodInvocation;

}