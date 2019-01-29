/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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