/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.upgrade.registry;

import com.liferay.headless.builder.internal.upgrade.v0_1_0.DeleteAPIPropertiesToAPIPropertiesUpgradeProcess;
import com.liferay.headless.builder.internal.upgrade.v0_2_0.ModifyAPIBuilderPicklistsUpgradeProcess;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = UpgradeStepRegistrator.class)
public class HeadlessBuilderUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "0.1.0",
			new DeleteAPIPropertiesToAPIPropertiesUpgradeProcess(
				_companyLocalService, _objectDefinitionLocalService,
				_objectRelationshipLocalService));

		registry.register(
			"0.1.0", "0.2.0",
			new ModifyAPIBuilderPicklistsUpgradeProcess(
				_companyLocalService, _listTypeDefinitionLocalService,
				_listTypeEntryLocalService, _objectDefinitionLocalService,
				_objectFieldLocalService, _objectStateFlowLocalService));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

}