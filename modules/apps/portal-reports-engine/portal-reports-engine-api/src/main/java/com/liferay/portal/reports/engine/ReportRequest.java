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

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 * @author Gavin Wan
 */
public class ReportRequest implements Serializable {

	public ReportRequest(
		ReportRequestContext reportRequestContext,
		ReportDesignRetriever reportDesignRetriever,
		Map<String, String> reportParameters, String reportFormat) {

		_reportRequestContext = reportRequestContext;
		_reportDesignRetriever = reportDesignRetriever;
		_reportParameters = reportParameters;

		_reportFormat = ReportFormat.parse(reportFormat);
	}

	public ReportDesignRetriever getReportDesignRetriever() {
		return _reportDesignRetriever;
	}

	public ReportFormat getReportFormat() {
		return _reportFormat;
	}

	public Map<String, String> getReportParameters() {
		return _reportParameters;
	}

	public ReportRequestContext getReportRequestContext() {
		return _reportRequestContext;
	}

	public void setReportDesignRetriever(
		ReportDesignRetriever reportDesignRetriever) {

		_reportDesignRetriever = reportDesignRetriever;
	}

	public void setReportFormat(ReportFormat reportFormat) {
		_reportFormat = reportFormat;
	}

	public void setReportParameters(Map<String, String> reportParameters) {
		_reportParameters.putAll(reportParameters);
	}

	public void setReportRequestContext(
		ReportRequestContext reportRequestContext) {

		_reportRequestContext = reportRequestContext;
	}

	private ReportDesignRetriever _reportDesignRetriever;
	private ReportFormat _reportFormat;
	private final Map<String, String> _reportParameters;
	private ReportRequestContext _reportRequestContext;

}