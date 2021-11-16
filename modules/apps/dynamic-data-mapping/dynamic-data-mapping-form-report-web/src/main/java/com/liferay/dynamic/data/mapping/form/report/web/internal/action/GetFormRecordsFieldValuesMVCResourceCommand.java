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

package com.liferay.dynamic.data.mapping.form.report.web.internal.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.util.DDMFormReportDataUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos André
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_REPORT,
		"mvc.command.name=/dynamic_data_mapping_form_report/get_form_records_field_values"
	},
	service = MVCResourceCommand.class
)
public class GetFormRecordsFieldValuesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			throw new PrincipalException.MustBeAuthenticated(
				themeDisplay.getUserId());
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_getFieldValuesJSONArray(httpServletRequest));
	}

	private JSONArray _getFieldValuesJSONArray(
			HttpServletRequest httpServletRequest)
		throws Exception {

		String fieldName = ParamUtil.getString(httpServletRequest, "fieldName");

		long formInstanceId = ParamUtil.getLong(
			httpServletRequest, "formInstanceId");

		int start = ParamUtil.getInteger(
			httpServletRequest, "start", QueryUtil.ALL_POS);
		int end = ParamUtil.getInteger(
			httpServletRequest, "end", QueryUtil.ALL_POS);

		BaseModelSearchResult<DDMFormInstanceRecord> baseModelSearchResult =
			_ddmFormInstanceRecordService.searchFormInstanceRecords(
				formInstanceId, new String[] {fieldName},
				WorkflowConstants.STATUS_APPROVED, start, end,
				new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, true));

		return DDMFormReportDataUtil.getFieldValuesJSONArray(
			baseModelSearchResult.getBaseModels(), fieldName);
	}

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private Portal _portal;

}