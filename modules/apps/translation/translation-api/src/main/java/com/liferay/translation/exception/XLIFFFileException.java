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

	public static class MustBeSupportedLanguage extends XLIFFFileException {

		public MustBeSupportedLanguage() {
		}

		public MustBeSupportedLanguage(String msg) {
			super(msg);
		}

		public MustBeSupportedLanguage(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MustBeSupportedLanguage(Throwable throwable) {
			super(throwable);
		}

	}

	public static class MustBeValid extends XLIFFFileException {

		public MustBeValid() {
		}

		public MustBeValid(String msg) {
			super(msg);
		}

		public MustBeValid(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MustBeValid(Throwable throwable) {
			super(throwable);
		}

	}

	public static class MustBeWellFormed extends XLIFFFileException {

		public MustBeWellFormed() {
		}

		public MustBeWellFormed(String msg) {
			super(msg);
		}

		public MustBeWellFormed(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MustBeWellFormed(Throwable throwable) {
			super(throwable);
		}

	}

	public static class MustHaveCorrectEncoding extends XLIFFFileException {

		public MustHaveCorrectEncoding() {
		}

		public MustHaveCorrectEncoding(String msg) {
			super(msg);
		}

		public MustHaveCorrectEncoding(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MustHaveCorrectEncoding(Throwable throwable) {
			super(throwable);
		}

	}

	public static class MustHaveValidId extends XLIFFFileException {

		public MustHaveValidId() {
		}

		public MustHaveValidId(String msg) {
			super(msg);
		}

		public MustHaveValidId(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MustHaveValidId(Throwable throwable) {
			super(throwable);
		}

	}

	public static class MustHaveValidModel extends XLIFFFileException {

		public MustHaveValidModel() {
		}

		public MustHaveValidModel(String msg) {
			super(msg);
		}

		public MustHaveValidModel(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MustHaveValidModel(Throwable throwable) {
			super(throwable);
		}

	}

	public static class MustHaveValidParameter extends XLIFFFileException {

		public MustHaveValidParameter() {
		}

		public MustHaveValidParameter(String msg) {
			super(msg);
		}

		public MustHaveValidParameter(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MustHaveValidParameter(Throwable throwable) {
			super(throwable);
		}

	}

	public static class MustNotHaveMoreThanOne extends XLIFFFileException {

		public MustNotHaveMoreThanOne() {
		}

		public MustNotHaveMoreThanOne(String msg) {
			super(msg);
		}

		public MustNotHaveMoreThanOne(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MustNotHaveMoreThanOne(Throwable throwable) {
			super(throwable);
		}

	}

	private XLIFFFileException() {
	}

	private XLIFFFileException(String msg) {
		super(msg);
	}

	private XLIFFFileException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	private XLIFFFileException(Throwable throwable) {
		super(throwable);
	}

}