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
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.data.provider.settings.DDMDataProviderSettingsProvider;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.SchemaUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_1.ResourcePermissionUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_2.DDMTemplateSmallImageURLUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.CheckboxFieldToCheckboxMultipleFieldUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1.DDMFormFieldSettingsUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1.DDMStructureIndexTypeUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.DDMFormInstanceDefinitionUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.DDMFormInstanceEntriesUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_4.DDMFormParagraphFieldsUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_5.DDMFormFieldValidationUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_6.DDMDataProviderInstanceUpgradeProcess;
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
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_1_0.DDMStructureLayoutUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_2_4.DDMContentUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_5_0.util.DDMFormInstanceReportTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_1.DDMStructureEmptyValidationUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_1_0.DDMFieldUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_0.DLFileEntryTypeDataDefinitionIdUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_4.DDMStructureLinkDLFileEntryTypeUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_4.DLFileEntryTypeDDMFieldAttributeUpgradeProcess;
import com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_5.DDMTemplateVersionUpgradeProcess;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMDataDefinitionConverter;
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
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
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
		DDMFormSerializer ddmFormSerializer = _jsonDDMFormSerializer;

		DDMFormLayoutSerializer ddmFormLayoutSerializer =
			_jsonDDMFormLayoutSerializer;

		DDMFormDeserializer ddmFormJSONDeserializer = _jsonDDMFormDeserializer;

		DDMFormDeserializer ddmFormXSDDeserializer = _xsdDDMFormDeserializer;

		DDMFormValuesSerializer ddmFormValuesSerializer =
			_jsonDDMFormValuesSerializer;

		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			_jsonDDMFormValuesDeserializer;

		registry.register("0.0.1", "0.0.2", new SchemaUpgradeProcess());

		registry.register("0.0.2", "0.0.3", new UpgradeKernelPackage());

		registry.register(
			"0.0.3", "1.0.0", new UpgradeCompanyId(),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.
				DynamicDataMappingUpgradeProcess(
					_assetEntryLocalService, _classNameLocalService, _ddm,
					ddmFormJSONDeserializer, ddmFormXSDDeserializer,
					ddmFormLayoutSerializer, ddmFormSerializer,
					ddmFormValuesDeserializer, ddmFormValuesSerializer,
					_dlFileEntryLocalService, _dlFileVersionLocalService,
					_dlFolderLocalService, _expandoRowLocalService,
					_expandoTableLocalService, _expandoValueLocalService,
					_resourceActions, _resourceLocalService,
					_resourcePermissionLocalService, _storeFactory.getStore(),
					_viewCountEntryLocalService),
			new UpgradeLastPublishDate());

		registry.register(
			"1.0.0", "1.0.1",
			new ResourcePermissionUpgradeProcess(_resourceActions));

		registry.register(
			"1.0.1", "1.0.2", new DDMTemplateSmallImageURLUpgradeProcess());

		registry.register(
			"1.0.2", "1.0.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_3.
				DDMFormParagraphFieldsUpgradeProcess(_jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_3.
				DDMFormFieldValidationUpgradeProcess(_jsonFactory));

		registry.register(
			"1.0.3", "1.1.0",
			new CheckboxFieldToCheckboxMultipleFieldUpgradeProcess(
				ddmFormJSONDeserializer, ddmFormValuesDeserializer,
				ddmFormValuesSerializer, _jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.
				DDMStructureUpgradeProcess(
					ddmFormJSONDeserializer, ddmFormSerializer),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0.
				DataProviderInstanceUpgradeProcess(_jsonFactory));

		registry.register(
			"1.1.0", "1.1.1",
			new DDMFormFieldSettingsUpgradeProcess(
				ddmFormJSONDeserializer, ddmFormSerializer),
			new DDMStructureIndexTypeUpgradeProcess(_jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_1.
				DataProviderInstanceUpgradeProcess(
					_ddmDataProviderSettingsProviderServiceTracker,
					ddmFormValuesDeserializer, ddmFormValuesSerializer));

		registry.register(
			"1.1.1", "1.1.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_2.
				DynamicDataMappingUpgradeProcess(
					ddmFormJSONDeserializer, ddmFormSerializer,
					ddmFormValuesDeserializer, ddmFormValuesSerializer,
					_jsonFactory));

		registry.register(
			"1.1.2", "1.1.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_3.
				DDMStorageLinkUpgradeProcess());

		registry.register(
			"1.1.3", "1.2.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v1_2_0.
				SchemaUpgradeProcess());

		registry.register("1.2.0", "1.2.1", new DummyUpgradeStep());

		registry.register(
			"1.2.1", "2.0.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0.
				DDMFormInstanceUpgradeProcess(
					_classNameLocalService, _counterLocalService,
					_resourceActions, _resourceActionLocalService,
					_resourcePermissionLocalService),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0.
				DDMFormInstanceRecordUpgradeProcess(_assetEntryLocalService),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0.
				DDMFormInstanceRecordVersionUpgradeProcess(),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0.
				ResourceActionUpgradeProcess(_resourceActionLocalService));

		registry.register(
			"2.0.0", "2.0.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_1.
				AutocompleteDDMTextFieldSettingUpgradeProcess(
					ddmFormJSONDeserializer, ddmFormSerializer),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_1.
				DDMFormFieldValidationUpgradeProcess(_jsonFactory));

		registry.register(
			"2.0.1", "2.0.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_2.
				DDMFormInstanceStructureResourceActionUpgradeProcess(
					_resourceActions));

		registry.register(
			"2.0.2", "2.0.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.
				DataProviderInstanceUpgradeProcess(_jsonFactory),
			new DDMFormInstanceDefinitionUpgradeProcess(_jsonFactory),
			new DDMFormInstanceEntriesUpgradeProcess(_jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_3.
				DDMFormInstanceSettingsUpgradeProcess(_jsonFactory));

		registry.register(
			"2.0.3", "2.0.4",
			new DDMFormParagraphFieldsUpgradeProcess(_jsonFactory));

		registry.register(
			"2.0.4", "2.0.5",
			new DDMFormFieldValidationUpgradeProcess(_jsonFactory));

		registry.register(
			"2.0.5", "2.0.6",
			new DDMDataProviderInstanceUpgradeProcess(_jsonFactory));

		registry.register("2.0.6", "2.0.7", new DummyUpgradeStep());

		registry.register("2.0.7", "2.0.8", new DummyUpgradeStep());

		registry.register("2.0.8", "2.0.9", new DummyUpgradeStep());

		registry.register("2.0.9", "2.0.10", new DummyUpgradeStep());

		registry.register("2.0.10", "2.0.11", new DummyUpgradeStep());

		registry.register(
			"2.0.11", "3.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
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

		registry.register(
			"3.0.0", "3.1.0", new DDMStructureLayoutUpgradeProcess());

		registry.register(
			"3.1.0", "3.1.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_1_1.
				DDMStructureLayoutUpgradeProcess());

		registry.register("3.1.1", "3.1.2", new DummyUpgradeStep());

		registry.register(
			"3.1.2", "3.2.0",
			new MVCCVersionUpgradeProcess() {

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
				DDMFormFieldValidationUpgradeProcess(_jsonFactory));

		registry.register(
			"3.2.2", "3.2.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_2_3.
				DDMFormFieldValidationUpgradeProcess(_jsonFactory));

		registry.register(
			"3.2.3", "3.2.4", new DDMContentUpgradeProcess(_jsonFactory));

		registry.register("3.2.4", "3.2.5", new DummyUpgradeStep());

		registry.register("3.2.5", "3.2.6", new DummyUpgradeStep());

		registry.register("3.2.6", "3.2.7", new DummyUpgradeStep());

		registry.register("3.2.7", "3.2.8", new DummyUpgradeStep());

		registry.register("3.2.8", "3.2.9", new DummyUpgradeStep());

		registry.register(
			"3.2.9", "3.3.0",
			new CTModelUpgradeProcess(
				"DDMStructure", "DDMStructureVersion", "DDMTemplate",
				"DDMTemplateVersion"));

		registry.register(
			"3.3.0", "3.4.0",
			new CTModelUpgradeProcess("DDMStructureLink", "DDMTemplateLink"));

		registry.register(
			"3.4.0", "3.5.0", DDMFormInstanceReportTable.create());

		registry.register(
			"3.5.0", "3.6.0",
			new CTModelUpgradeProcess(
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
			new DDMStructureEmptyValidationUpgradeProcess(
				ddmFormJSONDeserializer, ddmFormSerializer));

		registry.register(
			"3.7.1", "3.7.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_2.
				SchemaUpgradeProcess());

		registry.register(
			"3.7.2", "3.7.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_3.
				DDMFormInstanceReportUpgradeProcess(
					ddmFormJSONDeserializer, _jsonFactory));

		registry.register(
			"3.7.3", "3.7.4",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_4.
				DDMTemplateUpgradeProcess());

		registry.register(
			"3.7.4", "3.8.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_8_0.
				DDMStructureUpgradeProcess(
					ddmFormJSONDeserializer, _ddmFormLayoutDeserializer,
					ddmFormLayoutSerializer, ddmFormSerializer, _jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_8_0.
				DDMContentUpgradeProcess(
					ddmFormJSONDeserializer, _jsonFactory));

		registry.register(
			"3.8.0", "3.8.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_8_1.
				DDMFormFieldUpgradeProcess(_jsonFactory));

		registry.register(
			"3.8.1", "3.9.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_9_0.
				DDMDataProviderInstanceUpgradeProcess(
					_ddmDataProviderSettingsProviderServiceTracker,
					ddmFormValuesDeserializer, ddmFormValuesSerializer));

		registry.register(
			"3.9.0", "3.9.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_9_1.
				DDMStructureUpgradeProcess(
					ddmFormJSONDeserializer, ddmFormSerializer));

		registry.register("3.9.1", "3.10.0", new DummyUpgradeStep());

		registry.register("3.10.0", "3.10.1", new DummyUpgradeStep());

		registry.register(
			"3.10.1", "3.10.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2.
				DDMContentUpgradeProcess(
					ddmFormJSONDeserializer, ddmFormValuesDeserializer,
					ddmFormValuesSerializer, _jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2.
				DDMFormInstanceReportUpgradeProcess(_jsonFactory),
			new com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2.
				DDMStructureUpgradeProcess(
					ddmFormJSONDeserializer, _ddmFormLayoutDeserializer,
					ddmFormLayoutSerializer, ddmFormSerializer, _jsonFactory));

		registry.register(
			"3.10.2", "4.0.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0.
				DDMStructureUpgradeProcess(_ddmDataDefinitionConverter));

		registry.register(
			"4.0.0", "4.1.0",
			new DDMFieldUpgradeProcess(
				_jsonFactory, _jsonDDMFormDeserializer,
				_jsonDDMFormValuesDeserializer));

		registry.register(
			"4.1.0", "4.2.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v4_2_0.
				DDMFormInstanceRecordUpgradeProcess());

		registry.register(
			"4.2.0", "4.3.0",
			new DLFileEntryTypeDataDefinitionIdUpgradeProcess(
				_dlFileEntryTypeLocalService));

		registry.register(
			"4.3.0", "4.3.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_1.
				DDMFormInstanceUpgradeProcess(_jsonFactory));

		registry.register(
			"4.3.1", "4.3.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_2.
				DDMTemplateUpgradeProcess());

		registry.register(
			"4.3.2", "4.3.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_3.
				DDMStructureLayoutUpgradeProcess(
					_ddmFormLayoutDeserializer, ddmFormLayoutSerializer,
					_jsonFactory));

		registry.register(
			"4.3.3", "4.3.4",
			new DDMStructureLinkDLFileEntryTypeUpgradeProcess(
				_dlFileEntryTypeLocalService),
			new DLFileEntryTypeDDMFieldAttributeUpgradeProcess());

		registry.register(
			"4.3.4", "4.3.5", new DDMTemplateVersionUpgradeProcess());

		registry.register(
			"4.3.5", "5.0.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v5_0_0.
				PollsToDDMUpgradeProcess(
					ddmFormLayoutSerializer, ddmFormSerializer,
					ddmFormValuesSerializer, _resourceActionLocalService,
					_resourcePermissionLocalService));

		registry.register(
			"5.0.0", "5.1.0",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_0.
				DDMFieldUpgradeProcess());

		registry.register(
			"5.1.0", "5.1.1",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_1.
				DDMValidationUpgradeProcess(_jsonFactory));

		registry.register(
			"5.1.1", "5.1.2",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_2.
				DDMFormInstanceUpgradeProcess(_jsonFactory));

		registry.register(
			"5.1.2", "5.1.3",
			new com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_3.
				FieldNameUpgradeProcess());
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

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMDataDefinitionConverter _ddmDataDefinitionConverter;

	private ServiceTrackerMap<String, DDMDataProviderSettingsProvider>
		_ddmDataProviderSettingsProviderServiceTracker;

	@Reference
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Reference
	private DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

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

	@Reference(target = "(dl.store.impl.enabled=true)")
	private StoreFactory _storeFactory;

	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

	@Reference(target = "(ddm.form.deserializer.type=xsd)")
	private DDMFormDeserializer _xsdDDMFormDeserializer;

}