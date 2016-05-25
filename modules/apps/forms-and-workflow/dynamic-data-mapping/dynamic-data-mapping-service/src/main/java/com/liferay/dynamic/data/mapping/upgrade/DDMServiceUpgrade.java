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

package com.liferay.dynamic.data.mapping.upgrade;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializer;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeDynamicDataMapping;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	service = {DDMServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class DDMServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.dynamic.data.mapping.service", "0.0.1", "0.0.2",
			new UpgradeSchema());

		registry.register(
			"com.liferay.dynamic.data.mapping.service", "0.0.2", "0.0.3",
			new UpgradeKernelPackage());

		registry.register(
			"com.liferay.dynamic.data.mapping.service", "0.0.3", "1.0.0",
			new UpgradeCompanyId(),
			new UpgradeDynamicDataMapping(
				_assetEntryLocalService, _ddm, _ddmFormJSONDeserializer,
				_ddmFormJSONSerializer, _ddmFormLayoutJSONSerializer,
				_ddmFormValuesJSONDeserializer, _ddmFormValuesJSONSerializer,
				_ddmFormXSDDeserializer, _dlFileEntryLocalService,
				_dlFileVersionLocalService, _dlFolderLocalService,
				_expandoRowLocalService, _expandoTableLocalService,
				_expandoValueLocalService, _resourceActions,
				_resourceLocalService, _resourcePermissionLocalService),
			new UpgradeLastPublishDate());
	}

	@Reference(unbind = "-")
	public void setExpandoRowLocalService(
		ExpandoRowLocalService expandoRowLocalService) {

		_expandoRowLocalService = expandoRowLocalService;
	}

	@Reference(unbind = "-")
	public void setExpandoTableLocalService(
		ExpandoTableLocalService expandoTableLocalService) {

		_expandoTableLocalService = expandoTableLocalService;
	}

	@Reference(unbind = "-")
	public void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {

		_expandoValueLocalService = expandoValueLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(unbind = "-")
	protected void setDDMFormJSONDeserializer(
		DDMFormJSONDeserializer ddmFormJSONDeserializer) {

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormJSONSerializer(
		DDMFormJSONSerializer ddmFormJSONSerializer) {

		_ddmFormJSONSerializer = ddmFormJSONSerializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormLayoutJSONSerializer(
		DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer) {

		_ddmFormLayoutJSONSerializer = ddmFormLayoutJSONSerializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONDeserializer(
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer) {

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONSerializer(
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer) {

		_ddmFormValuesJSONSerializer = ddmFormValuesJSONSerializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormXSDDeserializer(
		DDMFormXSDDeserializer ddmFormXSDDeserializer) {

		_ddmFormXSDDeserializer = ddmFormXSDDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDlFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDlFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Reference(unbind = "-")
	protected void setResourceLocalService(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private DDM _ddm;
	private DDMFormJSONDeserializer _ddmFormJSONDeserializer;
	private DDMFormJSONSerializer _ddmFormJSONSerializer;
	private DDMFormLayoutJSONSerializer _ddmFormLayoutJSONSerializer;
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;
	private DDMFormXSDDeserializer _ddmFormXSDDeserializer;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFileVersionLocalService _dlFileVersionLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private ExpandoRowLocalService _expandoRowLocalService;
	private ExpandoTableLocalService _expandoTableLocalService;
	private ExpandoValueLocalService _expandoValueLocalService;
	private ResourceActions _resourceActions;
	private ResourceLocalService _resourceLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}