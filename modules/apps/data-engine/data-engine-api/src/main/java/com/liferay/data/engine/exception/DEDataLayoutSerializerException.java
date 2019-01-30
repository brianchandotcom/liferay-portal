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

package com.liferay.data.engine.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Exception class that is thrown when validation errors occur
 * when handling DEDataLayout and other DataLayout entities
 *
 * @see com.liferay.data.engine.model.DEDataLayout
 * @see com.liferay.data.engine.model.DEDataLayoutPage
 * @see com.liferay.data.engine.model.DEDataLayoutRow
 * @see com.liferay.data.engine.model.DEDataLayoutColumn
 *
 * @author Jeyvison Nascimento
 *
 * @review
 */
public class DEDataLayoutSerializerException extends PortalException {

	public DEDataLayoutSerializerException() {
	}

	public DEDataLayoutSerializerException(String message) {
		super(message);
	}

	public DEDataLayoutSerializerException(String message, Throwable cause) {
		super(message, cause);
	}

	public DEDataLayoutSerializerException(Throwable cause) {
		super(cause);
	}

	public static class InvalidDefaultLanguageId
		extends DEDataLayoutSerializerException {

		public InvalidDefaultLanguageId(String message) {
			super(message);
		}

		public InvalidDefaultLanguageId(String message, Throwable cause) {
			super(message, cause);
		}

	}

	public static class InvalidPageTitle
		extends DEDataLayoutSerializerException {

		public InvalidPageTitle(String message) {
			super(message);
		}

		public InvalidPageTitle(String message, Throwable cause) {
			super(message, cause);
		}

	}

	public static class InvalidPaginationMode
		extends DEDataLayoutSerializerException {

		public InvalidPaginationMode(String message) {
			super(message);
		}

		public InvalidPaginationMode(String message, Throwable cause) {
			super(message, cause);
		}

	}

}