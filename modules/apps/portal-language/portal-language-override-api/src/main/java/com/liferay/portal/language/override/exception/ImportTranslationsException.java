/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Thiago Buarque
 */
public class ImportTranslationsException extends PortalException {

	public static class InvalidPropertiesFile
		extends ImportTranslationsException {

		public InvalidPropertiesFile() {
			super("Invalid properties file");
		}

	}

	public static class InvalidTranslations
		extends ImportTranslationsException {

		public InvalidTranslations(Map<Class<?>, Exception> exceptions) {
			super("Unable to import translations");

			_exceptions = exceptions;
		}

		public Map<Class<?>, Exception> getExceptions() {
			return _exceptions;
		}

		private final Map<Class<?>, Exception> _exceptions;

	}

	private ImportTranslationsException(String msg) {
		super(msg);
	}

}