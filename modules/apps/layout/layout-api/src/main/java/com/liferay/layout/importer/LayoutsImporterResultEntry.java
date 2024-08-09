/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.importer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Rubén Pulido
 */
public class LayoutsImporterResultEntry {

	public static final int TYPE_COLLECTION = 0;

	public static final int TYPE_ENTRY = 1;

	public LayoutsImporterResultEntry(String name, int type, Status status) {
		_name = name;
		_type = type;
		_status = status;
	}

	public LayoutsImporterResultEntry(
		String name, int type, Status status, ErrorMessage errorMessage) {

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
		String name, Status status, ErrorMessage errorMessage) {

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

	public String getErrorMessage(Locale locale) {
		if (_errorMessage == null) {
			return StringPool.BLANK;
		}

		return _errorMessage.getErrorMessage(locale);
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

	public static class ErrorMessage {

		public ErrorMessage(String[] arguments, String message) {
			_arguments = arguments;
			_message = message;
		}

		public String getErrorMessage(Locale locale) {
			return LanguageUtil.format(locale, _message, _arguments);
		}

		private final String[] _arguments;
		private final String _message;

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

	private ErrorMessage _errorMessage;
	private final String _name;
	private final Status _status;
	private int _type = TYPE_ENTRY;
	private String[] _warningMessages;

}