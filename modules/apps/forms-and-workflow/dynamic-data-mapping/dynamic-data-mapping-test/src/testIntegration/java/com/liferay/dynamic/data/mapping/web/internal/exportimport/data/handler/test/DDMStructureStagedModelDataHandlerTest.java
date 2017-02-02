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
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
@Sync
public class DDMStructureStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMDataProvider();
		setUpDDMFormValuesJSONDeserializer();
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
				"value", "nameCurrentValue"));

		return ddmFormValues;
	}

	protected DDMFormValues getDDMDataProviderInstanceFormValues(
			DDMDataProviderInstance ddmDataProviderInstance)
		throws PortalException {

		Class<?> ddmDataProviderSettings = _ddmDataProvider.getSettings();

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProviderSettings);

		return _ddmFormValuesJSONDeserializer.deserialize(
			ddmForm, ddmDataProviderInstance.getDefinition());
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

	protected void setUpDDMDataProvider() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DDMDataProvider[] ddmDataProviders = registry.getServices(
			"com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider",
			"(ddm.data.provider.type=rest)");

		_ddmDataProvider = ddmDataProviders[0];
	}

	protected void setUpDDMFormValuesJSONDeserializer() {
		Registry registry = RegistryUtil.getRegistry();

		_ddmFormValuesJSONDeserializer = registry.getService(
			DDMFormValuesJSONDeserializer.class);
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
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

}