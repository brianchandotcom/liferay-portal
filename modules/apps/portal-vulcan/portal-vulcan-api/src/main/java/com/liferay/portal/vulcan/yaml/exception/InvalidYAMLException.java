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

package com.liferay.portal.vulcan.yaml.exception;

import com.liferay.petra.string.StringBundler;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

/**
 * @author     Javier de Arcos
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
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

		Mark mark = markedYAMLException.getProblemMark();

		StringBundler sb = new StringBundler(4);

		sb.append("Invalid YAML");

		if (mark != null) {
			sb.append(mark.toString());
		}

		sb.append(": ");
		sb.append(markedYAMLException.getProblem());

		return sb.toString();
	}

}