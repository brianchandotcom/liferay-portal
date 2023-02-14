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