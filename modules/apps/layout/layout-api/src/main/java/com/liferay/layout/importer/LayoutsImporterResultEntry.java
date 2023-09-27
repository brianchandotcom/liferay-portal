/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.importer;

/**
 * @author Rubén Pulido
 */
public class LayoutsImporterResultEntry {

	public LayoutsImporterResultEntry(String name, int type, Status status) {
		_name = name;
		_type = type;
		_status = status;
	}

	public LayoutsImporterResultEntry(
		String name, int type, Status status, String errorMessage) {

		_name = name;
		_type = type;
		_status = status;
		_errorMessage = errorMessage;
	}

	public LayoutsImporterResultEntry(
		String name, int type, Status status, String[] warningMessages) {

		_name = name;
		_type = type;
		_status = status;
		_warningMessages = warningMessages;
	}

	public LayoutsImporterResultEntry(
		String name, Status status, String errorMessage) {

		_name = name;
		_status = status;
		_errorMessage = errorMessage;
	}

	public LayoutsImporterResultEntry(
		String name, Status status, String[] warningMessages) {

		_name = name;
		_status = status;
		_warningMessages = warningMessages;
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

	public int getType() {
		return _type;
	}

	public String[] getWarningMessages() {
		return _warningMessages;
	}

	public enum Status {

		IGNORED("ignored"), IMPORTED("imported"), INVALID("invalid");

		public String getLabel() {
			return _label;
		}

		private Status(String label) {
			_label = label;
		}

		private final String _label;

	}

	private String _errorMessage;
	private final String _name;
	private final Status _status;
	private int _type = -1;
	private String[] _warningMessages;

}