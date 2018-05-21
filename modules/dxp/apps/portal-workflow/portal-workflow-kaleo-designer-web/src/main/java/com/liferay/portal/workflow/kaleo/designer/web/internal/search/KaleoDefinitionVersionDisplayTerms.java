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

package com.liferay.portal.workflow.kaleo.designer.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Inácio Nery
 */
public class KaleoDefinitionVersionDisplayTerms extends DisplayTerms {

	public static final String DESCRIPTION = "description";

	public static final String TITLE = "title";

	public KaleoDefinitionVersionDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		title = ParamUtil.getString(portletRequest, TITLE);
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	protected String description;
	protected String title;

}