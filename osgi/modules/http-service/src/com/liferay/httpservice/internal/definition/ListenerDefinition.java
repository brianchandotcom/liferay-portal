/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.definition;

import java.util.Dictionary;

/**
 * @author Raymond Augé
 */
public class ListenerDefinition {

	public ListenerDefinition() {
	}

	public ListenerDefinition(
		Dictionary<String, String> contextParams, Object listener) {

		_contextParams = contextParams;
		_listener = listener;
	}

	public Dictionary<String, String> getContextParams() {
		return _contextParams;
	}

	public Object getListener() {
		return _listener;
	}

	public void setContextParams(Dictionary<String, String> contextParams) {
		_contextParams = contextParams;
	}

	public void setListener(Object listener) {
		_listener = listener;
	}

	private Dictionary<String, String> _contextParams;
	private Object _listener;

}