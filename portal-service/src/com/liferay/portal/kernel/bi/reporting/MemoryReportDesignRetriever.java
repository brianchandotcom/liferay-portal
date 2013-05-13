/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

	private byte[] _bytes;
	private Date _modifiedDate;
	private String _reportName;

}