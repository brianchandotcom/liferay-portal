/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.registry;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_2.KaleoProcessTemplateLinkUpgradeProcess;
import com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_1_0.KaleoProcessUpgradeProcess;
import com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v2_0_0.util.KaleoProcessTable;
import com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v3_0_0.UpgradeCompanyId;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = UpgradeStepRegistrator.class)
public class KaleoFormsServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0",
			new com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_0.
				SchemaUpgradeProcess());

		registry.register(
			"1.0.0", "1.0.1",
			new com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_1.
				KaleoProcessUpgradeProcess(),
			new com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_1.
				SchemaUpgradeProcess());

		registry.register(
			"1.0.1", "1.0.2",
			new com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_2.
				KaleoProcessUpgradeProcess(
					_assetEntryLocalService, _ddlRecordLocalService,
					_ddlRecordSetLocalService),
			new KaleoProcessTemplateLinkUpgradeProcess(
				_classNameLocalService, _ddmTemplateLinkLocalService));

		registry.register(
			"1.0.2", "1.1.0",
			new KaleoProcessUpgradeProcess(
				_ddlRecordSetLocalService, _ddmStructureLocalService,
				_ddmStructureVersionLocalService, _ddmTemplateLocalService,
				_ddmTemplateVersionLocalService, _resourceActionLocalService,
				_resourceActions, _resourcePermissionLocalService));

		registry.register(
			"1.1.0", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {KaleoProcessTable.class}));

		registry.register("2.0.0", "3.0.0", new UpgradeCompanyId());
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DDMTemplateVersionLocalService _ddmTemplateVersionLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}