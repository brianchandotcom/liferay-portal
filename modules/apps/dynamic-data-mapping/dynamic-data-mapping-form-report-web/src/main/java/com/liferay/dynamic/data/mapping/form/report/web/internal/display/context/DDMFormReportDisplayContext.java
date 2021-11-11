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

package com.liferay.dynamic.data.mapping.form.report.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.util.DDMFormReportDataUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

/**
 * @author Bruno Farache
 */
public class DDMFormReportDisplayContext {

	public DDMFormReportDisplayContext(
		DDMFormInstanceReport ddmFormInstanceReport,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_ddmFormInstanceReport = ddmFormInstanceReport;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public DDMFormInstanceReport getDDMFormInstanceReport() {
		return _ddmFormInstanceReport;
	}

	public JSONArray getFieldsJSONArray() throws PortalException {
		return DDMFormReportDataUtil.getFieldsJSONArray(_ddmFormInstanceReport);
	}

	public String getFormReportRecordsFieldValuesURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setResourceID(
			"/dynamic_data_mapping_form_report/get_form_records_field_values");

		return resourceURL.toString();
	}

	public String getLastModifiedDate() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return DDMFormReportDataUtil.getLastModifiedDate(
			_ddmFormInstanceReport, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
	}

	public int getTotalItems() throws PortalException {
		return DDMFormReportDataUtil.getTotalItems(_ddmFormInstanceReport);
	}

	private final DDMFormInstanceReport _ddmFormInstanceReport;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}