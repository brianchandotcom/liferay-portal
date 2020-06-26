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

package com.liferay.translation.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class XLIFFFileException extends PortalException {

	public static class MustNotBeIncomplete extends XLIFFFileException {

		public MustNotBeIncomplete() {
		}

		public MustNotBeIncomplete(String msg) {
			super(msg);
		}

		public MustNotBeIncomplete(String msg, Throwable cause) {
			super(msg, cause);
		}

		public MustNotBeIncomplete(Throwable cause) {
			super(cause);
		}

	}

	public static class MustNotBeInvalidFile extends XLIFFFileException {

		public MustNotBeInvalidFile() {
		}

		public MustNotBeInvalidFile(String msg) {
			super(msg);
		}

		public MustNotBeInvalidFile(String msg, Throwable cause) {
			super(msg, cause);
		}

		public MustNotBeInvalidFile(Throwable cause) {
			super(cause);
		}

	}

	public static class MustNotBeUnsupportedLanguage
		extends XLIFFFileException {

		public MustNotBeUnsupportedLanguage() {
		}

		public MustNotBeUnsupportedLanguage(String msg) {
			super(msg);
		}

		public MustNotBeUnsupportedLanguage(String msg, Throwable cause) {
			super(msg, cause);
		}

		public MustNotBeUnsupportedLanguage(Throwable cause) {
			super(cause);
		}

	}

	public static class MustNotHaveInvalidId extends XLIFFFileException {

		public MustNotHaveInvalidId() {
		}

		public MustNotHaveInvalidId(String msg) {
			super(msg);
		}

		public MustNotHaveInvalidId(String msg, Throwable cause) {
			super(msg, cause);
		}

		public MustNotHaveInvalidId(Throwable cause) {
			super(cause);
		}

	}

	public static class MustNotHaveInvalidParameter extends XLIFFFileException {

		public MustNotHaveInvalidParameter() {
		}

		public MustNotHaveInvalidParameter(String msg) {
			super(msg);
		}

		public MustNotHaveInvalidParameter(String msg, Throwable cause) {
			super(msg, cause);
		}

		public MustNotHaveInvalidParameter(Throwable cause) {
			super(cause);
		}

	}

	public static class MustNotHaveMoreThanOne extends XLIFFFileException {

		public MustNotHaveMoreThanOne() {
		}

		public MustNotHaveMoreThanOne(String msg) {
			super(msg);
		}

		public MustNotHaveMoreThanOne(String msg, Throwable cause) {
			super(msg, cause);
		}

		public MustNotHaveMoreThanOne(Throwable cause) {
			super(cause);
		}

	}

	private XLIFFFileException() {
	}

	private XLIFFFileException(String msg) {
		super(msg);
	}

	private XLIFFFileException(String msg, Throwable cause) {
		super(msg, cause);
	}

	private XLIFFFileException(Throwable cause) {
		super(cause);
	}

}