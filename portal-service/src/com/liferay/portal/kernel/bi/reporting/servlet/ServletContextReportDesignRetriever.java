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

package com.liferay.portal.kernel.bi.reporting.servlet;

import com.liferay.portal.kernel.bi.reporting.ReportDesignRetriever;

import java.io.InputStream;

import java.util.Date;

import javax.servlet.ServletContext;

/**
 * @author Michael C. Han
 */
public class ServletContextReportDesignRetriever
	implements ReportDesignRetriever {

	public ServletContextReportDesignRetriever(
		ServletContext servletContext, String reportName, String prefix,
		String postfix) {

		_servletContext = servletContext;
		_reportName = reportName;
		_prefix = prefix;
		_postfix = postfix;
	}

	@Override
	public InputStream getInputStream() {
		return _servletContext.getResourceAsStream(
			_prefix + _reportName + _postfix);
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public String getReportName() {
		return _reportName;
	}

	private final String _postfix;
	private final String _prefix;
	private final String _reportName;
	private final ServletContext _servletContext;

}