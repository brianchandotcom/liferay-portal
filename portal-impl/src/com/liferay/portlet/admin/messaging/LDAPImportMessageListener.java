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

package com.liferay.portlet.admin.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.PortalUserImporterUtil;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class LDAPImportMessageListener extends BaseMessageListener {

	protected void doImportOnStartup() throws Exception {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			if (LDAPSettingsUtil.isImportOnStartup(companyId)) {
				PortalUserImporterUtil.importUsers(companyId);
			}
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (_startup) {
			_startup = false;

			doImportOnStartup();
		}
		else {
			PortalUserImporterUtil.importUsers();
		}
	}

	private boolean _startup = true;

}