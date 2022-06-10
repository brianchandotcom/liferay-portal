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

package com.liferay.info.exception;

import com.liferay.petra.string.StringPool;

/**
 * @author Lourdes Fernández Besada
 */
public class InfoFormValidationException extends InfoFormException {

	public InfoFormValidationException() {
		_infoFieldUniqueId = StringPool.BLANK;
		_languageKey =
			"an-error-has-occurred-and-the-form-could-not-be-sent.-please-" +
				"try-again";
		_args = new Object[0];
	}

	public InfoFormValidationException(
		String infoFieldUniqueId, String languageKey) {

		_infoFieldUniqueId = infoFieldUniqueId;
		_languageKey = languageKey;

		_args = new Object[0];
	}

	public InfoFormValidationException(
		String infoFieldUniqueId, String languageKey, Object[] args) {

		_infoFieldUniqueId = infoFieldUniqueId;
		_languageKey = languageKey;
		_args = args;
	}

	public InfoFormValidationException(
		String infoFieldUniqueId, String languageKey, String message,
		Object[] args, Throwable throwable) {

		_infoFieldUniqueId = infoFieldUniqueId;
		_languageKey = languageKey;
		_args = args;
	}

	public Object[] getArgs() {
		return _args;
	}

	public String getInfoFieldUniqueId() {
		return _infoFieldUniqueId;
	}

	public String getLanguageKey() {
		return _languageKey;
	}

	public static class FileSize extends InfoFormValidationException {

		public FileSize(String infoFieldUniqueId, String maximumSizeAllowed) {
			super(
				infoFieldUniqueId, "the-attachment-has-to-be-x-or-less",
				new String[] {maximumSizeAllowed});
		}

	}

	public static class InvalidFileExtension
		extends InfoFormValidationException {

		public InvalidFileExtension(
			String infoFieldUniqueId, String validFileExtensions) {

			super(
				infoFieldUniqueId, "the-accepted-extensions-for-the-file-are-x",
				new String[] {validFileExtensions});
		}

	}

	public static class InvalidInfoFieldValue
		extends InfoFormValidationException {

		public InvalidInfoFieldValue(String infoFieldUniqueId) {
			super(infoFieldUniqueId, "this-field-is-invalid");
		}

	}

	public static class RequiredInfoField extends InfoFormValidationException {

		public RequiredInfoField(String infoFieldUniqueId) {
			super(infoFieldUniqueId, "this-field-is-required");
		}

	}

	private final Object[] _args;
	private final String _infoFieldUniqueId;
	private final String _languageKey;

}