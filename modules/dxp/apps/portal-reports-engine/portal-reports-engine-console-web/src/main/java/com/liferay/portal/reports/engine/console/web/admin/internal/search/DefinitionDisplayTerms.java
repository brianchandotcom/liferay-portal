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

package com.liferay.portal.reports.engine.console.web.admin.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Rafael Praxedes
 */
public class DefinitionDisplayTerms extends DisplayTerms {

	public static final String DEFINITION_NAME = "definitionName";

	public static final String DESCRIPTION = "description";

	public static final String REPORT_NAME = "reportName";

	public static final String SOURCE_ID = "sourceId";

	public DefinitionDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		definitionName = ParamUtil.getString(portletRequest, DEFINITION_NAME);
		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		reportName = ParamUtil.getString(portletRequest, REPORT_NAME);
		sourceId = ParamUtil.getString(portletRequest, SOURCE_ID);
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public String getDescription() {
		return description;
	}

	public String getReportName() {
		return reportName;
	}

	public String getSourceId() {
		return sourceId;
	}

	protected String definitionName;
	protected String description;
	protected String reportName;
	protected String sourceId;

}