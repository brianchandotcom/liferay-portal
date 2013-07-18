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
 * @author Michael Hashimoto
 */
public class FreeMarkerStack {

	public boolean empty() {
		return _stack.empty();
	}

	public Object peek() {
		return _stack.peek();
	}

	public Object pop() {
		return _stack.pop();
	}

	public Object push(Object o) {
		_stack.push(o);

		return StringPool.BLANK;
	}

	private Stack<Object> _stack = new Stack<Object>();

}