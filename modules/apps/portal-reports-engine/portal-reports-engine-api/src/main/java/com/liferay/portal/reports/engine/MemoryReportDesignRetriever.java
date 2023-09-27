/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;

import java.io.InputStream;

import java.util.Date;

/**
 * @author Michael C. Han
 */
public class MemoryReportDesignRetriever implements ReportDesignRetriever {

	public MemoryReportDesignRetriever(
		String reportName, Date modifiedDate, byte[] bytes) {

		_reportName = reportName;
		_modifiedDate = modifiedDate;
		_bytes = bytes;
	}

	@Override
	public InputStream getInputStream() {
		return new UnsyncByteArrayInputStream(_bytes);
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public String getReportName() {
		return _reportName;
	}

	private final byte[] _bytes;
	private final Date _modifiedDate;
	private final String _reportName;

}