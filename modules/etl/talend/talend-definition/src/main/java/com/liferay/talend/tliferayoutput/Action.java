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

package com.liferay.talend.tliferayoutput;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Zoltán Takács
 */
public enum Action {

	Delete("DELETE"), Unavailable("NOOP"), Update("PUT"), Upsert("POST");

	public static Stream<Action> getActionsStream() {
		return _actionsStreamSupplier.get();
	}

	public String getMethodName() {
		return _method;
	}

	private Action(String method) {
		_method = method;
	}

	private static final Supplier<Stream<Action>> _actionsStreamSupplier =
		() -> Arrays.stream(values());

	private final String _method;

}