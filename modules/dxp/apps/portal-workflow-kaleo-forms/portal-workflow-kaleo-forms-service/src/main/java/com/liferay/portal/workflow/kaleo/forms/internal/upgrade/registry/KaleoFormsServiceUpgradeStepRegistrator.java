/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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