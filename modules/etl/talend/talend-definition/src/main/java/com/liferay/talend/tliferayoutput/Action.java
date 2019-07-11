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

import com.liferay.talend.common.oas.constants.OASConstants;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zoltán Takács
 */
public enum Action {

	Delete(OASConstants.OPERATION_DELETE), Insert(OASConstants.OPERATION_POST),
	Unavailable("noop"), Update(OASConstants.OPERATION_PATCH);

	public static Stream<Action> getActionsStream() {
		return _actionsStreamSupplier.get();
	}

	public static Set<String> getAvailableMethodNames() {
		Stream<Action> actionsStream = getActionsStream();

		return actionsStream.map(
			Action::getMethodName
		).collect(
			Collectors.toSet()
		);
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