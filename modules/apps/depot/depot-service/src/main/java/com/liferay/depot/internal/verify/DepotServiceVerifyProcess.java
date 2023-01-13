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

package com.liferay.depot.internal.verify;

import com.liferay.depot.internal.util.DepotRoleUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "verify.process.name=com.liferay.depot.service",
	service = VerifyProcess.class
)
public class DepotServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_checkDepotRoleDescriptions();
	}

	private void _checkDepotRoleDescriptions() {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				for (String name : DepotRoleUtil.DEPOT_ROLE_NAMES) {
					Role role = _roleLocalService.fetchRole(companyId, name);

					if (role != null) {
						Map<Locale, String> descriptionMap =
							DepotRoleUtil.getDescriptionMap(_language, name);

						if (!Objects.equals(
								descriptionMap, role.getDescriptionMap())) {

							role.setDescriptionMap(descriptionMap);

							_roleLocalService.updateRole(role);
						}
					}
				}
			});
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Language _language;

	@Reference
	private RoleLocalService _roleLocalService;

}