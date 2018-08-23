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

package com.liferay.dynamic.data.mapping.web.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class DDMStructureStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMDataProvider();
	}

	@Test
	public void testPublishStructureWithParentGroups() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

		DDMStructure parentStructure = DDMStructureTestUtil.addStructure(
			parentGroup.getGroupId(), _CLASS_NAME);

		DDMStructure childStructure = DDMStructureTestUtil.addStructure(
			childGroup.getGroupId(), _CLASS_NAME,
			parentStructure.getStructureId(),
			DDMStructureTestUtil.getSampleDDMForm("childname"),
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());

		exportStructure(parentGroup, parentStructure);

		Group newParentGroup = GroupTestUtil.addGroup();

		importStructure(parentGroup, newParentGroup, parentStructure);

		exportStructure(childGroup, childStructure);

		childGroup = GroupTestUtil.deleteGroup(childGroup);

		parentGroup = GroupTestUtil.deleteGroup(parentGroup);

		Group newChildGroup = GroupTestUtil.addGroup(
			newParentGroup.getGroupId());

		importStructure(childGroup, newChildGroup, childStructure);

		DDMStructure importedParentStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				parentStructure.getUuid(), newParentGroup.getGroupId());

		DDMStructure importedChildStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				childStructure.getUuid(), newChildGroup.getGroupId());

		Assert.assertNotNull(importedParentStructure);
		Assert.assertNotNull(importedChildStructure);
		Assert.assertEquals(
			importedParentStructure.getStructureId(),
			importedChildStructure.getParentStructureId());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		// Parent structure

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), _CLASS_NAME,
			DDMStructureTestUtil.getSampleDDMForm(
				RandomTestUtil.randomString()));

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		// Data provider instance

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), "Data provider");

		DDMFormValues ddmFormValues = getDDMDataProviderInstanceFormValues();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		DDMDataProviderInstance ddmDataProviderInstance =
			DDMDataProviderInstanceLocalServiceUtil.addDataProviderInstance(
				TestPropsValues.getUserId(), group.getGroupId(), nameMap,
				nameMap, ddmFormValues, "rest", serviceContext);

		addDependentStagedModel(
			dependentStagedModelsMap, DDMDataProviderInstance.class,
			ddmDataProviderInstance);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		// Parent structure

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		DDMStructure parentStructure = (DDMStructure)dependentStagedModels.get(
			0);

		// Data provider instance

		dependentStagedModels = dependentStagedModelsMap.get(
			DDMDataProviderInstance.class.getSimpleName());

		DDMDataProviderInstance ddmDataProviderInstance =
			(DDMDataProviderInstance)dependentStagedModels.get(0);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Name");

		DDMFormField selectDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Country", "Country", "select", "string", true, false, true);

		selectDDMFormField.setProperty("dataSourceType", "data-provider");

		selectDDMFormField.setProperty(
			"ddmDataProviderInstanceId",
			ddmDataProviderInstance.getDataProviderInstanceId());

		ddmForm.addDDMFormField(selectDDMFormField);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		return DDMStructureTestUtil.addStructure(
			group.getGroupId(), _CLASS_NAME, parentStructure.getStructureId(),
			ddmForm, LocaleUtil.getSiteDefault(), serviceContext);
	}

	protected void exportStructure(Group exportGroup, DDMStructure structure)
		throws Exception {

		initExport(exportGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, structure);
	}

	protected DDMFormValues getDDMDataProviderInstanceFormValues() {
		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProviderSettings);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"key", "countryId"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url",
				"http://localhost:8080/api/jsonws/country/get-countries"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"timeout", "1000"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"value", "nameCurrentValue"));

		return ddmFormValues;
	}

	protected DDMFormValues getDDMDataProviderInstanceFormValues(
		DDMDataProviderInstance ddmDataProviderInstance) {

		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProviderSettings);

		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			_ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
				"json");

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				ddmDataProviderInstance.getDefinition(), ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DDMStructure.class;
	}

	protected void importStructure(
			Group exportGroup, Group importGroup, DDMStructure structure)
		throws Exception {

		initImport(exportGroup, importGroup);

		if (Objects.nonNull(structure)) {
			DDMStructure exportedStructure =
				(DDMStructure)readExportedStagedModel(structure);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStructure);
		}
	}

	protected void setUpDDMDataProvider() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DDMDataProvider[] ddmDataProviders = registry.getServices(
			"com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider",
			"(ddm.data.provider.type=rest)");

		_ddmDataProvider = ddmDataProviders[0];
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		Assert.assertEquals(
			ddmStructureDependentStagedModels.toString(), 1,
			ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> ddmDataProviderInstanceDependentStagedModels =
			dependentStagedModelsMap.get(
				DDMDataProviderInstance.class.getSimpleName());

		Assert.assertEquals(
			ddmDataProviderInstanceDependentStagedModels.toString(), 1,
			ddmDataProviderInstanceDependentStagedModels.size());

		DDMDataProviderInstance dataProviderInstance =
			(DDMDataProviderInstance)
				ddmDataProviderInstanceDependentStagedModels.get(0);

		DDMDataProviderInstanceLocalServiceUtil.
			getDDMDataProviderInstanceByUuidAndGroupId(
				dataProviderInstance.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		// Structure

		DDMStructure structure = (DDMStructure)stagedModel;
		DDMStructure importedStructure = (DDMStructure)importedStagedModel;

		Assert.assertEquals(
			structure.getStructureKey(), importedStructure.getStructureKey());
		Assert.assertEquals(structure.getName(), importedStructure.getName());
		Assert.assertEquals(
			structure.getDescription(), importedStructure.getDescription());
		Assert.assertEquals(
			structure.getStructureKey(), importedStructure.getStructureKey());
		Assert.assertEquals(
			structure.getStorageType(), importedStructure.getStorageType());
		Assert.assertEquals(structure.getType(), importedStructure.getType());

		structure = DDMStructureLocalServiceUtil.fetchDDMStructure(
			structure.getStructureId());

		if (structure == null) {
			return;
		}

		// Data provider instance

		List<DDMDataProviderInstanceLink> dataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(structure.getStructureId());

		List<DDMDataProviderInstanceLink> importedDataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(
					importedStructure.getStructureId());

		Assert.assertEquals(
			dataProviderInstanceLinks.toString(), 1,
			dataProviderInstanceLinks.size());
		Assert.assertEquals(
			importedDataProviderInstanceLinks.toString(), 1,
			importedDataProviderInstanceLinks.size());

		DDMDataProviderInstanceLink dataProviderInstanceLink =
			dataProviderInstanceLinks.get(0);

		long dataProviderInstanceId =
			dataProviderInstanceLink.getDataProviderInstanceId();

		DDMDataProviderInstance dataProviderInstance =
			DDMDataProviderInstanceLocalServiceUtil.getDataProviderInstance(
				dataProviderInstanceId);

		DDMDataProviderInstanceLink importedDataProviderInstanceLink =
			importedDataProviderInstanceLinks.get(0);

		long importedDataProviderInstanceId =
			importedDataProviderInstanceLink.getDataProviderInstanceId();

		DDMDataProviderInstance importedDataProviderInstance =
			DDMDataProviderInstanceLocalServiceUtil.getDataProviderInstance(
				importedDataProviderInstanceId);

		Assert.assertEquals(
			getDDMDataProviderInstanceFormValues(dataProviderInstance),
			getDDMDataProviderInstanceFormValues(importedDataProviderInstance));
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.lists.model.DDLRecordSet";

	private DDMDataProvider _ddmDataProvider;

	@Inject
	private DDMFormValuesDeserializerTracker _ddmFormValuesDeserializerTracker;

}