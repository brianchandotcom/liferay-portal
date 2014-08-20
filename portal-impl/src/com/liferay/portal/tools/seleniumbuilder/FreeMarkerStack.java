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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Stack;

/**
 * Provides methods for stack interaction in FreeMarker.
 *
 * @author Michael Hashimoto
 */
public class FreeMarkerStack {

	/**
	 * Returns <code>true</code> if the stack is empty.
	 *
	 * @return <code>true</code> if the stack is empty; <code>false</code>
	 *         otherwise
	 */
	public boolean empty() {
		return _stack.empty();
	}

	/**
	 * Returns the object at the top of the stack, without removing it from the
	 * stack.
	 *
	 * @return the object at the top of the stack, without removing it from the
	 *         stack
	 */
	public Object peek() {
		return _stack.peek();
	}

	/**
	 * Returns the object at the top of the stack, removing it from the stack.
	 *
	 * @return the object at the top of the stack
	 */
	public Object pop() {
		return _stack.pop();
	}

	/**
	 * Pushes the object onto the stack.
	 *
	 * @param  object the item to be pushed onto the stack
	 * @return a blank string
	 */
	public Object push(Object object) {
		_stack.push(object);

		return StringPool.BLANK;
	}

	private final Stack<Object> _stack = new Stack<Object>();

}