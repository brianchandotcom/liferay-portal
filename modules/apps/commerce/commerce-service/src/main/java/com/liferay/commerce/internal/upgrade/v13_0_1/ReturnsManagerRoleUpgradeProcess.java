/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v13_0_1;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.currency.constants.CommerceCurrencyActionKeys;
import com.liferay.commerce.payment.constants.CommercePaymentEntryActionKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Crescenzo Rega
 */
public class ReturnsManagerRoleUpgradeProcess extends UpgradeProcess {

	public ReturnsManagerRoleUpgradeProcess(
		CompanyLocalService companyLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_companyLocalService = companyLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				try {
					_updateReturnsManagerPermissions(company.getCompanyId());
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});
	}

	private void _updateReturnsManagerPermissions(long companyId)
		throws PortalException {

		Role returnsManagerRole = _roleLocalService.fetchRole(
			companyId, "Returns Manager");

		if (returnsManagerRole != null) {
			if (!_resourcePermissionLocalService.hasResourcePermission(
					companyId, CommerceChannel.class.getName(),
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(), ActionKeys.UPDATE)) {

				_resourcePermissionLocalService.addResourcePermission(
					companyId, CommerceChannel.class.getName(),
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(), ActionKeys.UPDATE);
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					companyId, CommerceChannel.class.getName(),
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(), ActionKeys.VIEW)) {

				_resourcePermissionLocalService.addResourcePermission(
					companyId, CommerceChannel.class.getName(),
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(), ActionKeys.VIEW);
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					companyId, CommercePortletKeys.COMMERCE_PAYMENT,
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

				_resourcePermissionLocalService.addResourcePermission(
					companyId, CommercePortletKeys.COMMERCE_PAYMENT,
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL);
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					companyId, "com.liferay.commerce.currency",
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(),
					CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES)) {

				_resourcePermissionLocalService.addResourcePermission(
					companyId, "com.liferay.commerce.currency",
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(),
					CommerceCurrencyActionKeys.MANAGE_COMMERCE_CURRENCIES);
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					companyId, "com.liferay.commerce.payment",
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(),
					CommercePaymentEntryActionKeys.ADD_REFUND)) {

				_resourcePermissionLocalService.addResourcePermission(
					companyId, "com.liferay.commerce.payment",
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					returnsManagerRole.getRoleId(),
					CommercePaymentEntryActionKeys.ADD_REFUND);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReturnsManagerRoleUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}