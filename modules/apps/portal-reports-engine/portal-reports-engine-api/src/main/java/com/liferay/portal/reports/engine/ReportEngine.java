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

import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Michael C. Han
 */
public interface ReportEngine {

	public void compile(ReportRequest reportRequest)
		throws ReportGenerationException;

	public void destroy();

	public void execute(
			ReportRequest reportRequest,
			ReportResultContainer reportResultContainer)
		throws ReportGenerationException;

	public Map<String, String> getEngineParameters();

	public void init(ServletContext servletContext);

	public void setEngineParameters(Map<String, String> engineParameters);

}