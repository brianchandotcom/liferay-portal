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

package com.liferay.dynamic.data.mapping.internal.upgrade;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_1.UpgradeResourcePermission;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_2.UpgradeDDMTemplateSmallImageURL;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.UpgradeCheckboxFieldToCheckboxMultipleField;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1.UpgradeDDMFormFieldSettings;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.UpgradeDDMFormInstanceDefinition;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.UpgradeDDMFormInstanceEntries;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
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
		DDMFormSerializer ddmFormSerializer = getDDMFormSerializer();

		DDMFormLayoutSerializer ddmFormLayoutSerializer =
			getDDMFormLayoutSerializer();

		DDMFormDeserializer ddmFormJSONDeserializer =
			getDDMFormJSONDeserializer();

		DDMFormDeserializer ddmFormXSDDeserializer =
			getDDMFormXSDDeserializer();

		DDMFormValuesSerializer ddmFormValuesSerializer =
			getDDMFormValuesSerializer();

		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			getDDMFormValuesDeserializer();

		registry.register("0.0.1", "0.0.2", new UpgradeSchema());

		registry.register("0.0.2", "0.0.3", new UpgradeKernelPackage());

		registry.register(
			"0.0.3", "1.0.0", new UpgradeCompanyId(),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.
				UpgradeDynamicDataMapping(
					_assetEntryLocalService, _ddm, ddmFormJSONDeserializer,
					ddmFormXSDDeserializer, ddmFormLayoutSerializer,
					ddmFormSerializer, ddmFormValuesDeserializer,
					ddmFormValuesSerializer, _dlFileEntryLocalService,
					_dlFileVersionLocalService, _dlFolderLocalService,
					_expandoRowLocalService, _expandoTableLocalService,
					_expandoValueLocalService, _resourceActions,
					_resourceLocalService, _resourcePermissionLocalService,
					_store),
			new UpgradeLastPublishDate());

		registry.register(
			"1.0.0", "1.0.1", new UpgradeResourcePermission(_resourceActions));

		registry.register(
			"1.0.1", "1.0.2", new UpgradeDDMTemplateSmallImageURL());

		registry.register(
			"1.0.2", "1.1.0",
			new UpgradeCheckboxFieldToCheckboxMultipleField(
				ddmFormJSONDeserializer, ddmFormValuesDeserializer,
				ddmFormValuesSerializer, _jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.
				UpgradeDDMStructure(
					_ddmExpressionFactory, ddmFormJSONDeserializer,
					ddmFormSerializer),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.
				UpgradeDataProviderInstance(_jsonFactory));

		registry.register(
			"1.1.0", "1.1.1",
			new UpgradeDDMFormFieldSettings(
				ddmFormJSONDeserializer, ddmFormSerializer),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1.
				UpgradeDataProviderInstance(
					_ddmDataProviderTracker, ddmFormValuesDeserializer,
					ddmFormValuesSerializer));

		registry.register(
			"1.1.1", "1.1.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_2.
				UpgradeDynamicDataMapping(
					ddmFormJSONDeserializer, ddmFormSerializer,
					ddmFormValuesDeserializer, ddmFormValuesSerializer,
					_jsonFactory));

		registry.register(
			"1.1.2", "1.1.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_3.
				UpgradeDDMStorageLink());

		registry.register(
			"1.1.3", "1.2.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_2_0.
				UpgradeSchema());

		registry.register(
			"1.2.0", "1.2.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_2_0.
				UpgradeDDMFormAdminPortletId(),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_2_0.
				UpgradeDDMFormPortletId());

		registry.register(
			"1.2.1", "2.0.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0.
				UpgradeDDMFormInstance(
					_classNameLocalService, _counterLocalService,
					_resourceActions, _resourceActionLocalService,
					_resourcePermissionLocalService),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0.
				UpgradeDDMFormInstanceRecord(_assetEntryLocalService),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0.
				UpgradeDDMFormInstanceRecordVersion(),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0.
				UpgradeResourceAction(_resourceActionLocalService));

		registry.register(
			"2.0.0", "2.0.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_1.
				UpgradeAutocompleteDDMTextFieldSetting(
					ddmFormJSONDeserializer, ddmFormSerializer));

		registry.register(
			"2.0.1", "2.0.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_2.
				UpgradeDDMFormInstanceStructureResourceAction(
					_resourceActions));

		registry.register(
			"2.0.2", "2.0.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.
				UpgradeDataProviderInstance(_jsonFactory),
			new UpgradeDDMFormInstanceDefinition(_jsonFactory),
			new UpgradeDDMFormInstanceEntries(_jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.
				UpgradeDDMFormInstanceSettings(_jsonFactory));
	}

	protected DDMFormDeserializer getDDMFormJSONDeserializer() {
		return _ddmFormDeserializerTracker.getDDMFormDeserializer("json");
	}

	protected DDMFormLayoutSerializer getDDMFormLayoutSerializer() {
		return _ddmFormLayoutSerializerTracker.getDDMFormLayoutSerializer(
			"json");
	}

	protected DDMFormSerializer getDDMFormSerializer() {
		return _ddmFormSerializerTracker.getDDMFormSerializer("json");
	}

	protected DDMFormValuesDeserializer getDDMFormValuesDeserializer() {
		return _ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
			"json");
	}

	protected DDMFormValuesSerializer getDDMFormValuesSerializer() {
		return _ddmFormValuesSerializerTracker.getDDMFormValuesSerializer(
			"json");
	}

	protected DDMFormDeserializer getDDMFormXSDDeserializer() {
		return _ddmFormDeserializerTracker.getDDMFormDeserializer("xsd");
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

	@Reference
	private DDMFormLayoutSerializerTracker _ddmFormLayoutSerializerTracker;

	@Reference
	private DDMFormSerializerTracker _ddmFormSerializerTracker;

	@Reference
	private DDMFormValuesDeserializerTracker _ddmFormValuesDeserializerTracker;

	@Reference
	private DDMFormValuesSerializerTracker _ddmFormValuesSerializerTracker;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference(target = "(dl.store.upgrade=true)")
	private Store _store;

}