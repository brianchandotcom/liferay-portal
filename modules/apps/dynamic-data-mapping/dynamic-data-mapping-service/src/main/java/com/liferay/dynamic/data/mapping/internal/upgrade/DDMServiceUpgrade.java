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
import com.liferay.dynamic.data.mapping.data.provider.settings.DDMDataProviderSettingsProvider;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_1.UpgradeResourcePermission;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_2.UpgradeDDMTemplateSmallImageURL;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.UpgradeCheckboxFieldToCheckboxMultipleField;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1.UpgradeDDMFormFieldSettings;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1.UpgradeDDMStructureIndexType;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.UpgradeDDMFormInstanceDefinition;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.UpgradeDDMFormInstanceEntries;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_4.UpgradeDDMFormParagraphFields;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_5.UpgradeDDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_6.UpgradeDDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMContentTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMDataProviderInstanceTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMFormInstanceRecordTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMFormInstanceRecordVersionTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMFormInstanceVersionTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMStructureLayoutTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMStructureTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMStructureVersionTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMTemplateTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util.DDMTemplateVersionTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_1_0.UpgradeDDMStructureLayout;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_2_4.UpgradeDDMContent;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_5_0.UpgradeDDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_1.UpgradeDDMStructureEmptyValidation;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeCTModel;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.view.count.service.ViewCountEntryLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
					_assetEntryLocalService, _classNameLocalService, _ddm,
					ddmFormJSONDeserializer, ddmFormXSDDeserializer,
					ddmFormLayoutSerializer, ddmFormSerializer,
					ddmFormValuesDeserializer, ddmFormValuesSerializer,
					_dlFileEntryLocalService, _dlFileVersionLocalService,
					_dlFolderLocalService, _expandoRowLocalService,
					_expandoTableLocalService, _expandoValueLocalService,
					_resourceActions, _resourceLocalService,
					_resourcePermissionLocalService, _store,
					_viewCountEntryLocalService),
			new UpgradeLastPublishDate());

		registry.register(
			"1.0.0", "1.0.1", new UpgradeResourcePermission(_resourceActions));

		registry.register(
			"1.0.1", "1.0.2", new UpgradeDDMTemplateSmallImageURL());

		registry.register(
			"1.0.2", "1.0.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_3.
				UpgradeDDMFormParagraphFields(_jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_3.
				UpgradeDDMFormFieldValidation(_jsonFactory));

		registry.register(
			"1.0.3", "1.1.0",
			new UpgradeCheckboxFieldToCheckboxMultipleField(
				ddmFormJSONDeserializer, ddmFormValuesDeserializer,
				ddmFormValuesSerializer, _jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.
				UpgradeDDMStructure(ddmFormJSONDeserializer, ddmFormSerializer),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.
				UpgradeDataProviderInstance(_jsonFactory));

		registry.register(
			"1.1.0", "1.1.1",
			new UpgradeDDMFormFieldSettings(
				ddmFormJSONDeserializer, ddmFormSerializer),
			new UpgradeDDMStructureIndexType(_jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1.
				UpgradeDataProviderInstance(
					_ddmDataProviderSettingsProviderServiceTracker,
					ddmFormValuesDeserializer, ddmFormValuesSerializer));

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

		registry.register("1.2.0", "1.2.1", new DummyUpgradeStep());

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
					ddmFormJSONDeserializer, ddmFormSerializer),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_1.
				UpgradeDDMFormFieldValidation(_jsonFactory));

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

		registry.register(
			"2.0.3", "2.0.4", new UpgradeDDMFormParagraphFields(_jsonFactory));

		registry.register(
			"2.0.4", "2.0.5", new UpgradeDDMFormFieldValidation(_jsonFactory));

		registry.register(
			"2.0.5", "2.0.6", new UpgradeDDMDataProviderInstance(_jsonFactory));

		registry.register("2.0.6", "2.0.7", new DummyUpgradeStep());

		registry.register("2.0.7", "2.0.8", new DummyUpgradeStep());

		registry.register("2.0.8", "2.0.9", new DummyUpgradeStep());

		registry.register(
			"2.0.9", "3.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {
					DDMContentTable.class, DDMDataProviderInstanceTable.class,
					DDMFormInstanceRecordTable.class,
					DDMFormInstanceRecordVersionTable.class,
					DDMFormInstanceTable.class,
					DDMFormInstanceVersionTable.class,
					DDMStructureLayoutTable.class, DDMStructureTable.class,
					DDMStructureVersionTable.class, DDMTemplateTable.class,
					DDMTemplateVersionTable.class
				}));

		registry.register("3.0.0", "3.1.0", new UpgradeDDMStructureLayout());

		registry.register(
			"3.1.0", "3.1.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_1_1.
				UpgradeDDMStructureLayout());

		registry.register("3.1.1", "3.1.2", new DummyUpgradeStep());

		registry.register(
			"3.1.2", "3.2.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"DDMContent", "DDMDataProviderInstance",
						"DDMDataProviderInstanceLink", "DDMFormInstance",
						"DDMFormInstanceRecord", "DDMFormInstanceRecordVersion",
						"DDMFormInstanceVersion", "DDMStorageLink",
						"DDMStructure", "DDMStructureLayout",
						"DDMStructureLink", "DDMStructureVersion",
						"DDMTemplate", "DDMTemplateLink", "DDMTemplateVersion"
					};
				}

			});

		registry.register("3.2.0", "3.2.1", new DummyUpgradeStep());

		registry.register(
			"3.2.1", "3.2.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_2_2.
				UpgradeDDMFormFieldValidation(_jsonFactory));

		registry.register(
			"3.2.2", "3.2.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_2_3.
				UpgradeDDMFormFieldValidation(_jsonFactory));

		registry.register(
			"3.2.3", "3.2.4", new UpgradeDDMContent(_jsonFactory));

		registry.register("3.2.4", "3.2.5", new DummyUpgradeStep());

		registry.register("3.2.5", "3.2.6", new DummyUpgradeStep());

		registry.register("3.2.6", "3.2.7", new DummyUpgradeStep());

		registry.register(
			"3.2.7", "3.3.0",
			new UpgradeCTModel(
				"DDMStructure", "DDMStructureVersion", "DDMTemplate",
				"DDMTemplateVersion"));

		registry.register(
			"3.3.0", "3.4.0",
			new UpgradeCTModel("DDMStructureLink", "DDMTemplateLink"));

		registry.register("3.4.0", "3.5.0", new UpgradeDDMFormInstanceReport());

		registry.register(
			"3.5.0", "3.6.0",
			new UpgradeCTModel(
				"DDMContent", "DDMDataProviderInstance",
				"DDMDataProviderInstanceLink", "DDMFormInstance",
				"DDMFormInstanceRecord", "DDMFormInstanceRecordVersion",
				"DDMFormInstanceReport", "DDMFormInstanceVersion",
				"DDMStorageLink", "DDMStructureLayout"));

		registry.register(
			"3.6.0", "3.7.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_0.
				UpgradeDDMDataProviderInstance());

		registry.register(
			"3.7.0", "3.7.1",
			new UpgradeDDMStructureEmptyValidation(
				ddmFormJSONDeserializer, ddmFormSerializer));

		registry.register(
			"3.7.1", "3.7.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_2.
				UpgradeSchema());

		registry.register(
			"3.7.2", "3.7.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_3.
				UpgradeDDMFormInstanceReport(
					ddmFormJSONDeserializer, _jsonFactory));

		registry.register(
			"3.7.3", "3.7.4",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_4.
				UpgradeDDMTemplate());

		registry.register(
			"3.7.4", "3.8.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_8_0.
				UpgradeDDMStructure(
					ddmFormJSONDeserializer, _ddmFormLayoutDeserializer,
					ddmFormLayoutSerializer, ddmFormSerializer, _jsonFactory));

		registry.register(
			"3.8.0", "3.8.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_8_1.
				UpgradeDDMFormFieldDataSourceType(_jsonFactory));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_ddmDataProviderSettingsProviderServiceTracker =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMDataProviderSettingsProvider.class,
				"ddm.data.provider.type");
	}

	@Deactivate
	protected void deactivate() {
		_ddmDataProviderSettingsProviderServiceTracker.close();
	}

	protected DDMFormDeserializer getDDMFormJSONDeserializer() {
		return _jsonDDMFormDeserializer;
	}

	protected DDMFormLayoutSerializer getDDMFormLayoutSerializer() {
		return _jsonDDMFormLayoutSerializer;
	}

	protected DDMFormSerializer getDDMFormSerializer() {
		return _jsonDDMFormSerializer;
	}

	protected DDMFormValuesDeserializer getDDMFormValuesDeserializer() {
		return _jsonDDMFormValuesDeserializer;
	}

	protected DDMFormValuesSerializer getDDMFormValuesSerializer() {
		return _jsonDDMFormValuesSerializer;
	}

	protected DDMFormDeserializer getDDMFormXSDDeserializer() {
		return _xsdDDMFormDeserializer;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DDM _ddm;

	private ServiceTrackerMap<String, DDMDataProviderSettingsProvider>
		_ddmDataProviderSettingsProviderServiceTracker;

	@Reference
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Reference
	private DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;

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

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _jsonDDMFormLayoutSerializer;

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _jsonDDMFormSerializer;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

	@Reference(target = "(ddm.form.values.serializer.type=json)")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

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

	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

	@Reference(target = "(ddm.form.deserializer.type=xsd)")
	private DDMFormDeserializer _xsdDDMFormDeserializer;

}