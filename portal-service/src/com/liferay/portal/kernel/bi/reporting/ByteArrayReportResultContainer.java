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

package com.liferay.portal.kernel.bi.reporting;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class ByteArrayReportResultContainer
	implements ReportResultContainer, Serializable {

	public static final int DEFAULT_INITIAL_CAPCITY = 15360;

	public ByteArrayReportResultContainer() {
		this(null, DEFAULT_INITIAL_CAPCITY);
	}

	public ByteArrayReportResultContainer(String reportName) {
		this(reportName, DEFAULT_INITIAL_CAPCITY);
	}

	public ByteArrayReportResultContainer(
		String reportName, int initialCapacity) {

		_reportName = reportName;
		_initialCapacity = initialCapacity;
	}

	@Override
	public ReportResultContainer clone(String reportName) {
		return new ByteArrayReportResultContainer(reportName, _initialCapacity);
	}

	@Override
	public OutputStream getOutputStream() {
		if (_unsyncByteArrayOutputStream == null) {
			_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream(
				_initialCapacity);
		}

		return _unsyncByteArrayOutputStream;
	}

	@Override
	public ReportGenerationException getReportGenerationException() {
		return _reportGenerationException;
	}

	@Override
	public String getReportName() {
		return _reportName;
	}

	@Override
	public byte[] getResults() {
		return _unsyncByteArrayOutputStream.toByteArray();
	}

	@Override
	public boolean hasError() {
		if (_reportGenerationException != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void setReportGenerationException(
		ReportGenerationException reportGenerationException) {

		_reportGenerationException = reportGenerationException;
	}

	private final int _initialCapacity;
	private ReportGenerationException _reportGenerationException;
	private final String _reportName;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;

}