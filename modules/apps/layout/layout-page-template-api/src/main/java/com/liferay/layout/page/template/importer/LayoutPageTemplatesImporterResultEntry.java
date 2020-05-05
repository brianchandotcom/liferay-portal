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

package com.liferay.layout.page.template.importer;

/**
 * @author Rubén Pulido
 */
public class LayoutPageTemplatesImporterResultEntry {

	public LayoutPageTemplatesImporterResultEntry(
		String name, int type, Status status) {

		_name = name;
		_type = type;
		_status = status;
	}

	public LayoutPageTemplatesImporterResultEntry(
		String name, int type, Status status, String errorMessage) {

		_name = name;
		_type = type;
		_status = status;
		_errorMessage = errorMessage;
	}

	public LayoutPageTemplatesImporterResultEntry(
		String name, int type, Status status, String[] warningMessages) {

		_name = name;
		_type = type;
		_status = status;
		_warningMessages = warningMessages;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #LayoutPageTemplatesImporterResultEntry(String, int, Status)}
	 */
	@Deprecated
	public LayoutPageTemplatesImporterResultEntry(String name, Status status) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #LayoutPageTemplatesImporterResultEntry(String, int, Status, String)}
	 */
	@Deprecated
	public LayoutPageTemplatesImporterResultEntry(
		String name, Status status, String errorMessage) {

		throw new UnsupportedOperationException();
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
	private int _type;
	private String[] _warningMessages;

}