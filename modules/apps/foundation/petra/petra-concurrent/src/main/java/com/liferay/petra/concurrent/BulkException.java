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

package com.liferay.petra.concurrent;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Collection;

/**
 * @author Michael C. Han
 */
public class BulkException extends Exception {

	public BulkException(Collection<Throwable> causes) {
		_causes = causes;
	}

	public BulkException(String message, Collection<Throwable> causes) {
		super(message);

		_causes = causes;
	}

	public Collection<Throwable> getCauses() {
		return _causes;
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder(7 * _causes.size() + 4);

		sb.append("{message = ");
		sb.append(super.getMessage());
		sb.append("\n");

		for (Throwable cause : _causes) {
			sb.append("{");
			sb.append(_getClassName(cause));
			sb.append(":");
			sb.append(cause.getMessage());
			sb.append(", ");
			sb.append(_getStackTrace(cause));
			sb.append("}\n");
		}

		sb.append("}");

		return sb.toString();
	}

	private String _getClassName(Object object) {
		if (object == null) {
			return null;
		}

		Class<?> clazz = object.getClass();

		return clazz.getName();
	}

	private String _getStackTrace(Throwable t) {
		String stackTrace = null;

		PrintWriter printWriter = null;

		try {
			StringWriter stringWriter = new StringWriter();

			printWriter = new PrintWriter(stringWriter);

			t.printStackTrace(printWriter);

			printWriter.flush();

			stackTrace = stringWriter.toString();
		}
		finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}

		return stackTrace;
	}

	private final Collection<Throwable> _causes;

}