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

package com.liferay.social.networking.web.internal.events;

import com.liferay.expando.kernel.exception.DuplicateColumnNameException;
import com.liferay.expando.kernel.exception.DuplicateTableNameException;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true)
public class StartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Reference(unbind = "-")
	public void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	protected void doRun(long companyId) throws Exception {
		setupExpando(companyId);
	}

	/**
	 * See {@link com.liferay.portal.deploy.hot.HookHotDeployListener#initEvent}
	 */
	@Activate
	protected void run() throws ActionException {
		Long companyId = CompanyThreadLocal.getCompanyId();

		try {
			List<Company> companys = _companyLocalService.getCompanies();

			for (Company company : companys) {
				CompanyThreadLocal.setCompanyId(company.getCompanyId());

				run(new String[] {String.valueOf(company.getCompanyId())});
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
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

	protected void setupExpando(long companyId) throws Exception {
		ExpandoTable table = null;

		try {
			table = _expandoTableLocalService.addTable(
				companyId, User.class.getName(), "SN");
		}
		catch (DuplicateTableNameException dtne) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(dtne, dtne);
			}

			table = _expandoTableLocalService.getTable(
				companyId, User.class.getName(), "SN");
		}

		try {
			_expandoColumnLocalService.addColumn(
				table.getTableId(), "aboutMe", ExpandoColumnConstants.STRING);
		}
		catch (DuplicateColumnNameException dcne) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(dcne, dcne);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(StartupAction.class);

	private CompanyLocalService _companyLocalService;
	private ExpandoColumnLocalService _expandoColumnLocalService;
	private ExpandoTableLocalService _expandoTableLocalService;

}