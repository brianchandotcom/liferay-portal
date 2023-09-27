/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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