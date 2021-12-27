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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
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

		try {
			long formInstanceId = ParamUtil.getLong(
				resourceRequest, "formInstanceId");

			DDMFormInstanceReport ddmFormInstanceReport =
				_ddmFormInstanceReportLocalService.
					getFormInstanceReportByFormInstanceId(formInstanceId);

			String portletNamespace = _portal.getPortletNamespace(
				_portal.getPortletId(resourceRequest));

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"data", ddmFormInstanceReport.getData()
				).put(
					"fields",
					DDMFormReportDataUtil.getFieldsJSONArray(
						ddmFormInstanceReport)
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
							(ThemeDisplay)resourceRequest.getAttribute(
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"errorMessage",
					LanguageUtil.get(
						_portal.getHttpServletRequest(resourceRequest),
						"your-request-failed-to-complete")));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetFormReportDataMVCResourceCommand.class);

	@Reference
	private DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}