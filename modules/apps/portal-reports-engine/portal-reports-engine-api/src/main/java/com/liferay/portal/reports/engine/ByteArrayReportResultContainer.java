/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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