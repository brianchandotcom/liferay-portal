/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.staging.StagingGroupHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Vendel Toreki
 */
public class CompanyExportPanelApp extends BasePanelApp {

	public CompanyExportPanelApp(
		Portal portal, Portlet portlet, StagingGroupHelper stagingGroupHelper) {

		_portal = portal;
		_portlet = portlet;
		_stagingGroupHelper = stagingGroupHelper;
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public String getPortletId() {
		return ExportImportPortletKeys.EXPORT;
	}

	@Override
	protected Group getGroup(HttpServletRequest httpServletRequest) {
		return _stagingGroupHelper.fetchCompanyGroup(
			_portal.getCompanyId(httpServletRequest));
	}

	private final Portal _portal;
	private final Portlet _portlet;
	private final StagingGroupHelper _stagingGroupHelper;

}