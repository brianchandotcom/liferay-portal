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
		super("There are validation errors");

		_infoFieldUniqueId = StringPool.BLANK;
		_languageKey =
			"an-error-has-occurred-and-the-form-could-not-be-sent.-please-" +
				"try-again";
		_args = new Object[0];
	}

	public InfoFormValidationException(
		String infoFieldUniqueId, String languageKey) {

		super("There are validation errors");

		_infoFieldUniqueId = infoFieldUniqueId;
		_languageKey = languageKey;

		_args = new Object[0];
	}

	public InfoFormValidationException(
		String infoFieldUniqueId, String languageKey, Object[] args) {

		super("There are validation errors");

		_infoFieldUniqueId = infoFieldUniqueId;
		_languageKey = languageKey;
		_args = args;
	}

	public InfoFormValidationException(
		String infoFieldUniqueId, String languageKey, String message,
		Object[] args, Throwable throwable) {

		super(message, throwable);

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

	private final Object[] _args;
	private final String _infoFieldUniqueId;
	private final String _languageKey;

}