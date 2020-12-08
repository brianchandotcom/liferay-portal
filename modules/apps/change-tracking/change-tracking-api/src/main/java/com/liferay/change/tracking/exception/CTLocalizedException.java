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

package com.liferay.change.tracking.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.io.Serializable;

import java.util.ResourceBundle;

/**
 * @author Samuel Trong Tran
 */
public class CTLocalizedException extends PortalException {

	public CTLocalizedException(
		String msg, String languageKey, Serializable... args) {

		super(msg);

		_languageKey = languageKey;
		_args = args;
	}

	public CTLocalizedException(
		String msg, Throwable throwable, String languageKey,
		Serializable... args) {

		super(msg, throwable);

		_languageKey = languageKey;
		_args = args;
	}

	public String formatMessage(ResourceBundle resourceBundle) {
		return LanguageUtil.format(resourceBundle, _languageKey, _args, false);
	}

	private final Serializable[] _args;
	private final String _languageKey;

}