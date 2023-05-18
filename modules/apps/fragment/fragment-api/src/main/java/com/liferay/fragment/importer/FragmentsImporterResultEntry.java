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

package com.liferay.fragment.importer;

/**
 * @author Jürgen Kappler
 */
public class FragmentsImporterResultEntry {

	public FragmentsImporterResultEntry(String name, Status status) {
		_name = name;
		_status = status;
	}

	public FragmentsImporterResultEntry(
		String name, Status status, String errorMessage) {

		_name = name;
		_status = status;
		_errorMessage = errorMessage;
	}

	public FragmentsImporterResultEntry(
		String name, Status status, Type type, String errorMessage) {

		_name = name;
		_status = status;
		_type = type;
		_errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getName() {
		return _name;
	}

	public Status getStatus() {
		return _status;
	}

	public Type getType() {
		return _type;
	}

	public enum Status {

		IMPORTED("imported"), IMPORTED_DRAFT("imported-draft"),
		INVALID("invalid");

		public String getLabel() {
			return _label;
		}

		private Status(String label) {
			_label = label;
		}

		private final String _label;

	}

	public enum Type {

		COMPOSITION("composition"), FRAGMENT("fragment");

		public String getLabel() {
			return _label;
		}

		private Type(String label) {
			_label = label;
		}

		private final String _label;

	}

	private String _errorMessage;
	private final String _name;
	private final Status _status;
	private Type _type;

}