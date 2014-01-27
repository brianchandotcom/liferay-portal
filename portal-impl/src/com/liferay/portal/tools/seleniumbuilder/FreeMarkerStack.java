/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * A Stack wrapper class that can be used in FreeMarker.
 *
 * @author Michael Hashimoto
 */
public class FreeMarkerStack {

	/**
	 * Tests if this stack is empty.
	 *
	 * @return <code>true</code> if and only if this stack contains no items,
	 *         <code>false</code> otherwise
	 */
	public boolean empty() {
		return _stack.empty();
	}

	/**
	 * Looks at the object at the top of this stack without removing it from the
	 * stack.
	 *
	 * @return the object at the top of this stack
	 */
	public Object peek() {
		return _stack.peek();
	}

	/**
	 * Removes the object at the top of this stack and returns that object as
	 * the value of this function.
	 *
	 * @return the object to be removed at the top of this stack
	 */
	public Object pop() {
		return _stack.pop();
	}

	/**
	 * Pushes an item onto the top of this stack.
	 *
	 * @param  object the item to be pushed onto this stack.
	 * @return a blank String.
	 */
	public Object push(Object object) {
		_stack.push(object);

		return StringPool.BLANK;
	}

	private Stack<Object> _stack = new Stack<Object>();

}