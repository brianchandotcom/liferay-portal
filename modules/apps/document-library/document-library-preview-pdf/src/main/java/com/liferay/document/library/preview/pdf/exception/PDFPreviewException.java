/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.pdf.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alicia García
 */
public class PDFPreviewException extends PortalException {

	public PDFPreviewException() {
		this(0);
	}

	public PDFPreviewException(long maxNumberOfPages) {
		_maxNumberOfPages = maxNumberOfPages;
	}

	public PDFPreviewException(long maxNumberOfPages, Throwable throwable) {
		super(throwable);

		_maxNumberOfPages = maxNumberOfPages;
	}

	public PDFPreviewException(String msg) {
		this(msg, 0L);
	}

	public PDFPreviewException(String msg, long maxNumberOfPages) {
		super(msg);

		_maxNumberOfPages = maxNumberOfPages;
	}

	public PDFPreviewException(
		String msg, long maxNumberOfPages, Throwable throwable) {

		super(msg, throwable);

		_maxNumberOfPages = maxNumberOfPages;
	}

	public PDFPreviewException(String msg, Throwable throwable) {
		this(msg, 0, throwable);
	}

	public PDFPreviewException(Throwable throwable) {
		this(0, throwable);
	}

	public long getMaxNumberOfPages() {
		return _maxNumberOfPages;
	}

	private final long _maxNumberOfPages;

}