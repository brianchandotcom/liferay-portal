/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Thiago Buarque
 */
public class PLOEntryImportException extends PortalException {

	public static class InvalidPropertiesFile extends PLOEntryImportException {

		public InvalidPropertiesFile() {
			super("Invalid properties file");
		}

	}

	public static class InvalidTranslations extends PLOEntryImportException {

		public InvalidTranslations(List<Exception> exceptions) {
			super("Unable to import translations");

			_exceptions = exceptions;
		}

		public List<Exception> getExceptions() {
			return _exceptions;
		}

		private final List<Exception> _exceptions;

	}

	private PLOEntryImportException(String msg) {
		super(msg);
	}

}