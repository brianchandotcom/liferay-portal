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

import com.liferay.portal.kernel.dao.search.DAOParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Rafael Praxedes
 */
public class EntrySearchTerms extends EntryDisplayTerms {

	public EntrySearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		definitionName = DAOParamUtil.getString(
			portletRequest, DEFINITION_NAME);
		endDateDay = DAOParamUtil.getInteger(portletRequest, END_DATE_DAY);
		endDateMonth = DAOParamUtil.getInteger(portletRequest, END_DATE_MONTH);
		endDateYear = DAOParamUtil.getInteger(portletRequest, END_DATE_YEAR);
		startDateDay = DAOParamUtil.getInteger(portletRequest, START_DATE_DAY);
		startDateMonth = DAOParamUtil.getInteger(
			portletRequest, START_DATE_MONTH);
		startDateYear = DAOParamUtil.getInteger(
			portletRequest, START_DATE_YEAR);
		userName = DAOParamUtil.getString(portletRequest, USERNAME);
	}

}