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

package com.liferay.commerce.account.internal.upgrade;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.account.internal.upgrade.v1_1_0.CommerceAccountUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupCommerceAccountRelUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupRelUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_2_0.CommerceAccountGroupUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_3_0.CommerceAccountNameUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v1_4_0.CommerceAccountDefaultAddressesUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v2_0_0.CommerceAccountGroupSystemUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v4_0_0.CommerceAccountOrganizationRelUpgradeProcess;
import com.liferay.commerce.account.internal.upgrade.v5_0_0.CommerceAccountUserRelUpgradeProcess;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceAccountUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce account upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0", new CommerceAccountUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0",
			new CommerceAccountGroupCommerceAccountRelUpgradeProcess(),
			new CommerceAccountGroupRelUpgradeProcess(),
			new CommerceAccountGroupUpgradeProcess());

		registry.register(
			"1.2.0", "1.3.0", new CommerceAccountNameUpgradeProcess());

		registry.register(
			"1.3.0", "1.4.0",
			new CommerceAccountDefaultAddressesUpgradeProcess());

		registry.register("1.4.0", "1.5.0", new DummyUpgradeProcess());

		registry.register(
			"1.5.0", "2.0.0", new CommerceAccountGroupSystemUpgradeProcess());

		registry.register(
			"2.0.0", "3.0.0",
			new com.liferay.commerce.account.internal.upgrade.v3_0_0.
				CommerceAccountUpgradeProcess(
					_accountEntryLocalService, _classNameLocalService,
					_expandoTableLocalService, _expandoValueLocalService,
					_groupLocalService, _resourceLocalService,
					_workflowDefinitionLinkLocalService,
					_workflowInstanceLinkLocalService));

		registry.register(
			"3.0.0", "4.0.0",
			new CommerceAccountOrganizationRelUpgradeProcess(
				_accountEntryOrganizationRelLocalService));

		registry.register(
			"4.0.0", "5.0.0",
			new CommerceAccountUserRelUpgradeProcess(
				_accountEntryUserRelLocalService));

		registry.register(
			"5.0.0", "6.0.0",
			new com.liferay.commerce.account.internal.upgrade.v6_0_0.
				CommerceAccountGroupUpgradeProcess(_accountGroupLocalService));

		registry.register(
			"6.0.0", "7.0.0",
			new com.liferay.commerce.account.internal.upgrade.v7_0_0.
				CommerceAccountGroupRelUpgradeProcess(
					_accountGroupRelLocalService));

		registry.register(
			"7.0.0", "8.0.0",
			new com.liferay.commerce.account.internal.upgrade.v8_0_0.
				CommerceAccountUpgradeProcess());

		registry.register(
			"8.0.0", "9.0.0",
			new com.liferay.commerce.account.internal.upgrade.v9_0_0.
				CommerceAccountGroupCommerceAccountRelUpgradeProcess(
					_accountGroupRelLocalService));

		registry.register(
			"9.0.0", "9.1.0",
			new com.liferay.commerce.account.internal.upgrade.v9_1_0.
				CommerceAccountRoleUpgradeProcess(
					_accountRoleLocalService, _classNameLocalService,
					_groupLocalService, _resourcePermissionLocalService,
					_roleLocalService));

		registry.register(
			"9.1.0", "9.1.1",
			new com.liferay.commerce.account.internal.upgrade.v9_0_1.
				CommerceAccountPortletUpgradeProcess());

		registry.register(
			"9.1.1", "9.2.0",
			new com.liferay.commerce.account.internal.upgrade.v9_2_0.
				CommerceAccountRoleUpgradeProcess(
					_companyLocalService, _resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));

		if (_log.isInfoEnabled()) {
			_log.info("Commerce account upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountUpgradeStepRegistrator.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.account.service)(release.schema.version>=2.1.0))"
	)
	private Release _release;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}