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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormReportDataUtil;
import com.liferay.petra.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"mvc.command.name=/dynamic_data_mapping_form/get_form_report_data"
	},
	service = MVCResourceCommand.class
)
public class GetFormReportDataMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		long formInstanceId = ParamUtil.getLong(
			httpServletRequest, "formInstanceId");

		DDMFormInstanceReport ddmFormInstanceReport =
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(formInstanceId);

		String portletNamespace = _portal.getPortletNamespace(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"data", ddmFormInstanceReport.getData()
			).put(
				"fields",
				DDMFormReportDataUtil.getFieldsJSONArray(ddmFormInstanceReport)
			).put(
				"formReportRecordsFieldValuesURL",
				_http.addParameter(
					ResourceURLBuilder.createResourceURL(
						resourceResponse
					).setResourceID(
						"/dynamic_data_mapping_form" +
							"/get_form_records_field_values"
					).buildString(),
					portletNamespace + "formInstanceId", formInstanceId)
			).put(
				"lastModifiedDate",
				() -> {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return DDMFormReportDataUtil.getLastModifiedDate(
						ddmFormInstanceReport, themeDisplay.getLocale(),
						themeDisplay.getTimeZone());
				}
			).put(
				"portletNamespace", portletNamespace
			).put(
				"totalItems",
				DDMFormReportDataUtil.getTotalItems(ddmFormInstanceReport)
			));
	}

	@Reference
	private DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}