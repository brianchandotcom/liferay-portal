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

package com.liferay.commerce.account.internal.upgrade.registry;

import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.account.util.CommerceAccountRoleHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Chaparro
 */
@Component(
	enabled = true, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceAccountServiceInitialUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialUpgradeSteps(
			new UpgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setCompanyId(_portal.getDefaultCompanyId());
					serviceContext.setUserId(
						_getAdminUserId(_portal.getDefaultCompanyId()));
					serviceContext.setUuid(_portalUUID.generate());

					_commerceAccountRoleHelper.checkCommerceAccountRoles(
						serviceContext);

					_commerceAccountGroupLocalService.
						checkGuestCommerceAccountGroup(
							_portal.getDefaultCompanyId());
				}

				private long _getAdminUserId(long companyId) throws Exception {
					Role role = _roleLocalService.getRole(
						companyId, RoleConstants.ADMINISTRATOR);

					long[] userIds = _userLocalService.getRoleUserIds(
						role.getRoleId());

					if (userIds.length == 0) {
						throw new NoSuchUserException(
							StringBundler.concat(
								"No user exists in company ", companyId,
								" with role ", role.getName()));
					}

					return userIds[0];
				}

			});
	}

	@Reference
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Reference
	private CommerceAccountRoleHelper _commerceAccountRoleHelper;

	@Reference
	private Portal _portal;

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}