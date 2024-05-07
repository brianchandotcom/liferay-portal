/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class FileExtensionException extends PortalException {

	public static class InvalidExtension extends FileExtensionException {

		public InvalidExtension() {
		}

		public InvalidExtension(String msg) {
			super(msg);
		}

		public InvalidExtension(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public InvalidExtension(Throwable throwable) {
			super(throwable);
		}

	}

	public static class MismatchExtension extends FileExtensionException {

		public MismatchExtension() {
		}

		public MismatchExtension(String msg) {
			super(msg);
		}

		public MismatchExtension(String msg, Throwable throwable) {
			super(msg, throwable);
		}

		public MismatchExtension(Throwable throwable) {
			super(throwable);
		}

	}

	private FileExtensionException() {
	}

	private FileExtensionException(String msg) {
		super(msg);
	}

	private FileExtensionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	private FileExtensionException(Throwable throwable) {
		super(throwable);
	}

}