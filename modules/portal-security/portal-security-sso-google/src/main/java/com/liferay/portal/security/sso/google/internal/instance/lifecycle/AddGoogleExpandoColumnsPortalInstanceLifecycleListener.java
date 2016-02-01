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

package com.liferay.portal.security.sso.google.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddGoogleExpandoColumnsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Long companyId = CompanyThreadLocal.getCompanyId();

		try {
			CompanyThreadLocal.setCompanyId(company.getCompanyId());

			long classNameId = _classNameLocalService.getClassNameId(
				User.class.getName());

			ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
				company.getCompanyId(), classNameId,
				ExpandoTableConstants.DEFAULT_TABLE_NAME);

			if (expandoTable == null) {
				expandoTable = _expandoTableLocalService.addTable(
					company.getCompanyId(), classNameId,
					ExpandoTableConstants.DEFAULT_TABLE_NAME);
			}

			UnicodeProperties properties = new UnicodeProperties();

			properties.setProperty("hidden", "true");
			properties.setProperty("visible-with-update-permission", "false");

			addExpandoColumn(expandoTable, "googleAccessToken", properties);
			addExpandoColumn(expandoTable, "googleRefreshToken", properties);
			addExpandoColumn(expandoTable, "googleUserId", properties);
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
	}

	protected void addExpandoColumn(
			ExpandoTable expandoTable, String name,
			UnicodeProperties properties)
		throws Exception {

		ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
			expandoTable.getTableId(), name);

		if (expandoColumn != null) {
			return;
		}

		expandoColumn = _expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), name, ExpandoColumnConstants.STRING);

		_expandoColumnLocalService.updateTypeSettings(
			expandoColumn.getColumnId(), properties.toString());
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {

		_expandoColumnLocalService = expandoColumnLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoTableLocalService(
		ExpandoTableLocalService expandoTableLocalService) {

		_expandoTableLocalService = expandoTableLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private ClassNameLocalService _classNameLocalService;
	private ExpandoColumnLocalService _expandoColumnLocalService;
	private ExpandoTableLocalService _expandoTableLocalService;

}