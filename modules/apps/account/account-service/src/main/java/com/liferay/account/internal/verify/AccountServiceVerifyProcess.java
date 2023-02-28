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

package com.liferay.account.internal.verify;

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
public class AccountServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				_addAccountEntryAddressListTypesCompanyModelListener.
					onAfterCreate(company);
				_addDefaultAccountGroupCompanyModelListener.onAfterCreate(
					company);
				_addDefaultAccountRolesCompanyModelListener.onAfterCreate(
					company);
			});
	}

	@Reference(
		target = "(component.name=com.liferay.account.internal.model.listener.AddAccountEntryAddressListTypesCompanyModelListener)"
	)
	private ModelListener<Company>
		_addAccountEntryAddressListTypesCompanyModelListener;

	@Reference(
		target = "(component.name=com.liferay.account.internal.model.listener.AddDefaultAccountGroupCompanyModelListener)"
	)
	private ModelListener<Company> _addDefaultAccountGroupCompanyModelListener;

	@Reference(
		target = "(component.name=com.liferay.account.internal.model.listener.AddDefaultAccountRolesCompanyModelListener)"
	)
	private ModelListener<Company> _addDefaultAccountRolesCompanyModelListener;

	@Reference
	private CompanyLocalService _companyLocalService;

}