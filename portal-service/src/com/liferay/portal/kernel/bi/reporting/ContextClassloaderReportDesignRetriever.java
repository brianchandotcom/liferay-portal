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

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;

/**
 * @author Michael C. Han
 */
public class ContextClassloaderReportDesignRetriever
	implements ReportDesignRetriever, Serializable {

	public ContextClassloaderReportDesignRetriever(String reportName) {
		_reportName = reportName;
	}

	@Override
	public InputStream getInputStream() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		return contextClassLoader.getResourceAsStream(_reportName);
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public String getReportName() {
		return _reportName;
	}

	private final String _reportName;

}