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

import java.util.Stack;

/**
 * @author Michael Hashimoto
 */
public class StackWrapper {

	public boolean empty() {
		return _stack.empty();
	}

	public String peek() {
		return _stack.peek();
	}

	public String pop() {
		return _stack.pop();
	}

	public String push(String string) {
		_stack.push(string);

		return "";
	}

	private Stack<String> _stack = new Stack<String>();

}