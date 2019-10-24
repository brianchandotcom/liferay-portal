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

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade;

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
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_2.UpgradeKaleoProcessTemplateLink;
import com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_1_0.UpgradeKaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v2_0_0.util.KaleoProcessTable;
import com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v3_0_0.UpgradeCompanyId;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	service = {KaleoFormsServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class KaleoFormsServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.portal.workflow.kaleo.forms.service", "0.0.1", "1.0.0",
			new com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_0.
				UpgradeSchema());

		registry.register(
			"com.liferay.portal.workflow.kaleo.forms.service", "1.0.0", "1.0.1",
			new com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_1.
				UpgradeKaleoProcess(),
			new com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_1.
				UpgradeSchema());

		registry.register(
			"com.liferay.portal.workflow.kaleo.forms.service", "1.0.1", "1.0.2",
			new com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_2.
				UpgradeKaleoProcess(
					_assetEntryLocalService, _ddlRecordLocalService,
					_ddlRecordSetLocalService),
			new UpgradeKaleoProcessTemplateLink(
				_classNameLocalService, _ddmTemplateLinkLocalService));

		registry.register(
			"com.liferay.portal.workflow.kaleo.forms.service", "1.0.2", "1.1.0",
			new UpgradeKaleoProcess(
				_ddlRecordSetLocalService, _ddmStructureLocalService,
				_ddmStructureVersionLocalService, _ddmTemplateLocalService,
				_ddmTemplateVersionLocalService, _resourceActionLocalService,
				_resourceActions, _resourcePermissionLocalService));

		registry.register(
			"1.1.0", "2.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {KaleoProcessTable.class}));

		registry.register("2.0.0", "3.0.0", new UpgradeCompanyId());
	}

	@Reference(unbind = "-")
	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	public void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	public void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	@Reference(unbind = "-")
	public void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	public void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {

		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	@Reference(unbind = "-")
	public void setDDMTemplateLinkLocalService(
		DDMTemplateLinkLocalService ddmTemplateLinkLocalService) {

		_ddmTemplateLinkLocalService = ddmTemplateLinkLocalService;
	}

	@Reference(unbind = "-")
	public void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(unbind = "-")
	public void setDDMTemplateVersionLocalService(
		DDMTemplateVersionLocalService ddmTemplateVersionLocalService) {

		_ddmTemplateVersionLocalService = ddmTemplateVersionLocalService;
	}

	@Reference(unbind = "-")
	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Reference(unbind = "-")
	public void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Reference(unbind = "-")
	public void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private ClassNameLocalService _classNameLocalService;
	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordSetLocalService _ddlRecordSetLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;
	private DDMTemplateLocalService _ddmTemplateLocalService;
	private DDMTemplateVersionLocalService _ddmTemplateVersionLocalService;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourceActions _resourceActions;
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}