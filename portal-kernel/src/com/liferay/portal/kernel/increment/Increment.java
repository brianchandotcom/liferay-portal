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

package com.liferay.portal.kernel.increment;

/**
 * @author Zsolt Berentey
 */
public interface Increment<T> {

	public void decrease(T delta);

	public Increment<T> decreaseForNew(T delta);

	public T getValue();

	public void increase(T delta);

	public Increment<T> increaseForNew(T delta);

	public void setValue(T value);

}