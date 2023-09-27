/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.importer;

/**
 * @author Jürgen Kappler
 */
public class FragmentsImporterResultEntry {

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

	private final String _errorMessage;
	private final String _name;
	private final Status _status;
	private final Type _type;

}