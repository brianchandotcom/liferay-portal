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

package com.liferay.portal.tools.rest.builder.internal.yaml.exception;

import com.liferay.petra.string.StringBundler;

import java.util.Optional;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

/**
 * @author Javier de Arcos
 */
public class InvalidYAMLException extends IllegalArgumentException {

	public InvalidYAMLException(Exception exception) {
		super(_getProblem(exception), exception);
	}

	private static String _getProblem(Throwable throwable) {
		if (throwable.getCause() instanceof MarkedYAMLException) {
			return _getProblem(throwable.getCause());
		}

		MarkedYAMLException markedYAMLException =
			(MarkedYAMLException)throwable;

		Optional<Mark> markOptional = Optional.ofNullable(
			markedYAMLException.getProblemMark());

		return StringBundler.concat(
			"Invalid YAML",
			markOptional.map(
				Mark::toString
			).orElse(
				""
			),
			": ", markedYAMLException.getProblem());
	}

}