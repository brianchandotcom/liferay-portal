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

package com.liferay.account.admin.web.internal.verify;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(property = "initial.deployment=true", service = VerifyProcess.class)
public class AccountAdminWebVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_companyLocalService.forEachCompany(_modelListener::onAfterCreate);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(
		target = "(component.name=com.liferay.account.admin.web.internal.model.listener.AddDefaultAccountRolesPortletPermissionsCompanyModelListener)"
	)
	private ModelListener<Company> _modelListener;

}