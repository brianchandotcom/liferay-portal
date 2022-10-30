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

package com.liferay.portal.reports.engine;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.OutputStream;

/**
 * @author Michael C. Han
 */
public class ByteArrayReportResultContainer implements ReportResultContainer {

	public ByteArrayReportResultContainer(String reportName) {
		_reportName = reportName;
	}

	@Override
	public OutputStream getOutputStream() {
		if (_unsyncByteArrayOutputStream == null) {
			_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
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

		return false;
	}

	@Override
	public void setReportGenerationException(
		ReportGenerationException reportGenerationException) {

		_reportGenerationException = reportGenerationException;
	}

	private ReportGenerationException _reportGenerationException;
	private final String _reportName;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;

}