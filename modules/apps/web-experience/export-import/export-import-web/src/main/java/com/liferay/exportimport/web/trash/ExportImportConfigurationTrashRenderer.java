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

package com.liferay.exportimport.web.trash;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.web.constants.ExportImportWebKeys;
import com.liferay.portal.kernel.trash.BaseJSPTrashRenderer;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Levente Hudák
 */
@ProviderType
public class ExportImportConfigurationTrashRenderer
	extends BaseJSPTrashRenderer {

	public static final String TYPE = "export_import_configuration";

	public ExportImportConfigurationTrashRenderer(
		ExportImportConfiguration exportImportConfiguration) {

		_exportImportConfiguration = exportImportConfiguration;
	}

	@Override
	public String getClassName() {
		return ExportImportConfiguration.class.getName();
	}

	@Override
	public long getClassPK() {
		return _exportImportConfiguration.getPrimaryKey();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		return "/view_configuration.jsp";
	}

	@Override
	public String getPortletId() {
		return PortletKeys.EXPORT_IMPORT;
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _exportImportConfiguration.getDescription();
	}

	@Override
	public String getTitle(Locale locale) {
		return _exportImportConfiguration.getName();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(
			ExportImportWebKeys.EXPORT_IMPORT_CONFIGURATION_ID,
			_exportImportConfiguration.getExportImportConfigurationId());

		return super.include(request, response, template);
	}

	private final ExportImportConfiguration _exportImportConfiguration;

}