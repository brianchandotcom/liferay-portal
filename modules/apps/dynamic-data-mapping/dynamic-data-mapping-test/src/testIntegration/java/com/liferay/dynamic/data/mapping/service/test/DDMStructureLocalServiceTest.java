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

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.exception.InvalidParentStructureException;
import com.liferay.dynamic.data.mapping.exception.RequiredStructureException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateStructureKeyException;
import com.liferay.dynamic.data.mapping.exception.StructureNameException;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.dynamic.data.mapping.util.comparator.StructureIdComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class DDMStructureLocalServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_classNameId = PortalUtil.getClassNameId(DDL_RECORD_SET_CLASS_NAME);
	}

	@Test(expected = StructureDefinitionException.class)
	public void testAddStructureMissingRequiredElementAttribute()
		throws Exception {

		addStructure(
			_classNameId, null, "Test Structure",
			read("ddm-structure-required-element-attribute.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	@Test(expected = StructureDuplicateElementException.class)
	public void testAddStructureWithDuplicateElementName() throws Exception {
		addStructure(
			_classNameId, null, "Test Structure",
			read("ddm-structure-duplicate-element-name.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	@Test(expected = StructureDuplicateElementException.class)
	public void testAddStructureWithDuplicateElementNameInParent()
		throws Exception {

		DDMStructure parentStructure = addStructure(
			_classNameId, null, "Test Parent Structure",
			read("ddm-structure-duplicate-element-name.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		addStructure(
			parentStructure.getStructureId(), _classNameId, null,
			"Test Structure", read("ddm-structure-duplicate-element-name.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	@Test(expected = StructureDuplicateStructureKeyException.class)
	public void testAddStructureWithDuplicateKey() throws Exception {
		String structureKey = RandomTestUtil.randomString();

		addStructure(
			_classNameId, structureKey, "Test Structure 1",
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);

		addStructure(
			_classNameId, structureKey, "Test Structure 2",
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	@Test(expected = StructureDefinitionException.class)
	public void testAddStructureWithInvalidElementAttribute() throws Exception {
		addStructure(
			_classNameId, null, "Test Structure",
			read("ddm-structure-invalid-element-attribute.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	@Test(expected = StructureDefinitionException.class)
	public void testAddStructureWithoutDefinition() throws Exception {
		addStructure(
			_classNameId, null, "Test Structure", StringPool.BLANK,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	@Test(expected = StructureNameException.class)
	public void testAddStructureWithoutName() throws Exception {
		addStructure(
			_classNameId, null, StringPool.BLANK, read("test-structure.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	@Test
	public void testAddStructureWithReferencedDataProviderInstance1()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("Field", "select");

		ddmFormField.setDataType("string");

		long ddmDataProviderInstanceId = RandomTestUtil.randomLong();

		ddmFormField.setProperty("dataSourceType", "data-provider");

		ddmFormField.setProperty(
			"ddmDataProviderInstanceId", ddmDataProviderInstanceId);

		ddmForm.addDDMFormField(ddmFormField);

		DDMStructure structure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		DDMDataProviderInstanceLink dataProviderInstanceLink =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				fetchDataProviderInstanceLink(
					ddmDataProviderInstanceId, structure.getStructureId());

		Assert.assertNotNull(dataProviderInstanceLink);

		DDMStructureLocalServiceUtil.deleteStructure(structure);

		dataProviderInstanceLink =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				fetchDataProviderInstanceLink(
					ddmDataProviderInstanceId, structure.getStructureId());

		Assert.assertNull(dataProviderInstanceLink);
	}

	@Test
	public void testAddStructureWithReferencedDataProviderInstance2()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("Field", "select");

		ddmFormField.setDataType("string");

		long ddmDataProviderInstanceId = RandomTestUtil.randomLong();

		ddmFormField.setProperty("dataSourceType", "data-provider");

		JSONArray dataProviderInstanceIdJSONArray =
			_jsonFactory.createJSONArray();

		dataProviderInstanceIdJSONArray.put(ddmDataProviderInstanceId);

		ddmFormField.setProperty(
			"ddmDataProviderInstanceId", dataProviderInstanceIdJSONArray);

		ddmForm.addDDMFormField(ddmFormField);

		DDMStructure structure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		DDMDataProviderInstanceLink dataProviderInstanceLink =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				fetchDataProviderInstanceLink(
					ddmDataProviderInstanceId, structure.getStructureId());

		Assert.assertNotNull(dataProviderInstanceLink);

		DDMStructureLocalServiceUtil.deleteStructure(structure);

		dataProviderInstanceLink =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				fetchDataProviderInstanceLink(
					ddmDataProviderInstanceId, structure.getStructureId());

		Assert.assertNull(dataProviderInstanceLink);
	}

	@Test
	public void testAddStructureWithReferencedDataProviderInstance3()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Field1");

		DDMDataProviderInstance dataProviderInstance1 =
			createDDMDataProviderInstance();

		List<String> actions = new ArrayList<>();

		String action = String.format(
			"call('%s','','')", dataProviderInstance1.getUuid());

		actions.add(action);

		DDMFormRule ddmFormRule = new DDMFormRule("TRUE", actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		DDMStructure structure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		List<DDMDataProviderInstanceLink> dataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(structure.getStructureId());

		Assert.assertEquals(
			dataProviderInstanceLinks.toString(), 1,
			dataProviderInstanceLinks.size());
	}

	@Test
	public void testCopyStructure() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		DDMStructure copyStructure = copyStructure(structure);

		Assert.assertEquals(structure.getGroupId(), copyStructure.getGroupId());
		JSONAssert.assertEquals(
			structure.getDefinition(), copyStructure.getDefinition(), false);
		Assert.assertEquals(
			structure.getStorageType(), copyStructure.getStorageType());
		Assert.assertEquals(structure.getType(), copyStructure.getType());
	}

	@Test
	public void testDeleteStructure() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		DDMStructureLocalServiceUtil.deleteStructure(
			structure.getStructureId());

		Assert.assertNull(
			DDMStructureLocalServiceUtil.fetchDDMStructure(
				structure.getStructureId()));
	}

	@Test(
		expected = RequiredStructureException.MustNotDeleteStructureReferencedByTemplates.class
	)
	public void testDeleteStructureReferencedByTemplates() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		addDisplayTemplate(
			structure.getPrimaryKey(), "Test Display Template",
			WorkflowConstants.STATUS_APPROVED);
		addFormTemplate(
			structure.getPrimaryKey(), "Test Form Template",
			WorkflowConstants.STATUS_APPROVED);

		DDMStructureLocalServiceUtil.deleteStructure(
			structure.getStructureId());
	}

	@Test
	public void testFetchStructure() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		Assert.assertNotNull(
			DDMStructureLocalServiceUtil.fetchStructure(
				structure.getGroupId(), _classNameId,
				structure.getStructureKey()));
	}

	@Test
	public void testGetFullHierarchyDDMFormFieldsMap() throws Exception {
		DDMForm parentDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"Name", true, false, false);

		nameDDMFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField("Age", true, false, false));

		parentDDMForm.addDDMFormField(nameDDMFormField);

		DDMStructure parentStructure = ddmStructureTestHelper.addStructure(
			parentDDMForm, StorageType.JSON.toString());

		DDMForm childDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormField descriptionDDMFormField =
			DDMFormTestUtil.createTextDDMFormField(
				"Description", true, false, false);

		childDDMForm.addDDMFormField(descriptionDDMFormField);

		DDMStructure childStructure = ddmStructureTestHelper.addStructure(
			parentStructure.getStructureId(), parentStructure.getClassNameId(),
			null, "Child Structure", StringPool.BLANK, childDDMForm,
			DDMUtil.getDefaultDDMFormLayout(childDDMForm),
			StorageType.JSON.toString(), DDMStructureConstants.TYPE_DEFAULT);

		Map<String, DDMFormField> childFullHierarchyDDMFormFieldsMap =
			childStructure.getFullHierarchyDDMFormFieldsMap(true);

		Assert.assertTrue(
			childFullHierarchyDDMFormFieldsMap.containsKey("Name"));
		Assert.assertTrue(
			childFullHierarchyDDMFormFieldsMap.containsKey("Age"));
		Assert.assertTrue(
			childFullHierarchyDDMFormFieldsMap.containsKey("Description"));

		// Update parent DDM form to have just the Name field

		DDMForm parentDDMFormUpdated = DDMFormTestUtil.createDDMForm();

		parentDDMFormUpdated.addDDMFormField(nameDDMFormField);

		parentStructure.setDDMForm(parentDDMFormUpdated);

		DDMStructureLocalServiceUtil.updateDDMStructure(parentStructure);

		// Assert that the child DDM form has the full hierarchy updated

		childStructure = DDMStructureLocalServiceUtil.getStructure(
			childStructure.getStructureId());

		childFullHierarchyDDMFormFieldsMap =
			childStructure.getFullHierarchyDDMFormFieldsMap(true);

		Assert.assertTrue(
			childFullHierarchyDDMFormFieldsMap.containsKey("Name"));
		Assert.assertTrue(
			childFullHierarchyDDMFormFieldsMap.containsKey("Description"));
	}

	@Test
	public void testGetStructures() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getStructures(structure.getGroupId());

		Assert.assertTrue(
			structures.toString(), structures.contains(structure));
	}

	@Test
	public void testGetTemplates() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		addDisplayTemplate(
			structure.getStructureId(), "Test Display Template",
			WorkflowConstants.STATUS_APPROVED);
		addFormTemplate(
			structure.getStructureId(), "Test Form Template",
			WorkflowConstants.STATUS_APPROVED);

		List<DDMTemplate> templates = structure.getTemplates();

		Assert.assertEquals(templates.toString(), 2, templates.size());
	}

	@Test
	public void testSearchByAnyStatus() throws Exception {
		addStructure(
			0, _classNameId, null, StringUtil.randomString(), StringPool.BLANK,
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED);

		addStructure(
			0, _classNameId, null, StringUtil.randomString(), StringPool.BLANK,
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT, WorkflowConstants.STATUS_DRAFT);

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, null, DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_ANY, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(structures.toString(), 2, structures.size());
	}

	@Test
	public void testSearchByClassNameId() throws Exception {
		addStructure(_classNameId, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString());
		addStructure(_classNameId, StringUtil.randomString());

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, null, DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(structures.toString(), 3, structures.size());
	}

	@Test
	public void testSearchByDescription() throws Exception {
		addStructure(_classNameId, StringUtil.randomString(), "Contact");
		addStructure(_classNameId, StringUtil.randomString(), "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, "Contact", null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		DDMStructure structure = structures.get(0);

		Assert.assertEquals(
			"Contact", structure.getDescription(group.getDefaultLanguageId()));
	}

	@Test
	public void testSearchByDraftStatus() throws Exception {
		addStructure(
			0, _classNameId, null, StringUtil.randomString(), StringPool.BLANK,
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED);

		addStructure(
			0, _classNameId, null, StringUtil.randomString(), StringPool.BLANK,
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT, WorkflowConstants.STATUS_DRAFT);

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, null, DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_DRAFT, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(structures.toString(), 1, structures.size());
	}

	@Test
	public void testSearchByKeywords1() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "Events");

		addStructure(_classNameId, "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			structure.getCompanyId(), new long[] {structure.getGroupId()},
			structure.getClassNameId(), "Event",
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new StructureIdComparator(true));

		Assert.assertEquals("Events", getStructureName(structures.get(0)));
		Assert.assertEquals("Event", getStructureName(structures.get(1)));
	}

	@Test
	public void testSearchByKeywords2() throws Exception {
		DDMStructure structure = addStructure(_classNameId, "To Do");

		addStructure(_classNameId, "To Doing");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			structure.getCompanyId(), new long[] {structure.getGroupId()},
			structure.getClassNameId(), "To Do",
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new StructureIdComparator(true));

		Assert.assertEquals(structures.toString(), 2, structures.size());
		Assert.assertEquals("To Do", getStructureName(structures.get(0)));
		Assert.assertEquals("To Doing", getStructureName(structures.get(1)));
	}

	@Test
	public void testSearchByName() throws Exception {
		addStructure(_classNameId, "Contact");
		addStructure(_classNameId, "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, "Contact", null, null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals("Contact", getStructureName(structures.get(0)));
	}

	@Test
	public void testSearchByNameAndDescription() throws Exception {
		addStructure(_classNameId, "Contact", "Contact");
		addStructure(_classNameId, "Event", "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, "Contact", "Event", null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(structures.toString(), 2, structures.size());
	}

	@Test
	public void testSearchByNameAndDescriptionOrdered() throws Exception {
		addStructure(_classNameId, "Contact", "Contact");
		addStructure(_classNameId, "Event", "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, "Contact", "Event", null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new StructureIdComparator(true));

		Assert.assertEquals("Contact", getStructureName(structures.get(0)));
		Assert.assertEquals("Event", getStructureName(structures.get(1)));
	}

	@Test
	public void testSearchByNonexistingStorageType() throws Exception {
		addStructure(_classNameId, StringUtil.randomString());

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, "NonExistingStorageType",
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(structures.toString(), 0, structures.size());
	}

	@Test
	public void testSearchByStorageType() throws Exception {
		addStructure(_classNameId, StringUtil.randomString());

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, StorageType.JSON.toString(),
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(structures.toString(), 1, structures.size());
	}

	@Test
	public void testSearchByType() throws Exception {
		addStructure(
			0, _classNameId, null, StringUtil.randomString(), StringPool.BLANK,
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED);

		addStructure(
			0, _classNameId, null, StringUtil.randomString(), StringPool.BLANK,
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_FRAGMENT,
			WorkflowConstants.STATUS_APPROVED);

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, null, DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(structures.toString(), 1, structures.size());

		structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, null, DDMStructureConstants.TYPE_FRAGMENT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(structures.toString(), 1, structures.size());
	}

	@Test
	public void testSearchCount() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, "Test Structure", null, null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, false);

		addStructure(_classNameId, "Test Structure");

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, "Test Structure", null, null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, false);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testSearchCountByKeywords() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, WorkflowConstants.STATUS_APPROVED);

		addStructure(_classNameId, "Test Structure");

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testSearchCountByType() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, null, DDMStructureConstants.TYPE_FRAGMENT,
			WorkflowConstants.STATUS_ANY, true);

		addStructure(
			0, _classNameId, null, StringUtil.randomString(), StringPool.BLANK,
			read("test-structure.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_FRAGMENT,
			WorkflowConstants.STATUS_APPROVED);

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_classNameId, null, null, null, DDMStructureConstants.TYPE_FRAGMENT,
			WorkflowConstants.STATUS_ANY, true);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testUpdateStructureWithReferencedDataProviderInstance()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField ddmFormField1 = new DDMFormField("Field1", "select");

		ddmFormField1.setDataType("string");

		long ddmDataProviderInstanceId1 = RandomTestUtil.randomLong();

		ddmFormField1.setProperty("dataSourceType", "data-provider");

		ddmFormField1.setProperty(
			"ddmDataProviderInstanceId", ddmDataProviderInstanceId1);

		ddmFormFields.add(ddmFormField1);

		DDMFormField ddmFormField2 = new DDMFormField("Field2", "select");

		ddmFormField2.setDataType("string");

		long ddmDataProviderInstanceId2 = RandomTestUtil.randomLong();

		ddmFormField2.setProperty("dataSourceType", "data-provider");

		ddmFormField2.setProperty(
			"ddmDataProviderInstanceId", ddmDataProviderInstanceId2);

		ddmFormFields.add(ddmFormField2);

		ddmForm.setDDMFormFields(ddmFormFields);

		DDMStructure structure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		List<DDMDataProviderInstanceLink> dataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(structure.getStructureId());

		Assert.assertEquals(
			dataProviderInstanceLinks.toString(), 2,
			dataProviderInstanceLinks.size());

		// Remove one of the data provider instance links

		ddmFormFields.remove(ddmFormField2);

		ddmForm.setDDMFormFields(ddmFormFields);

		ddmStructureTestHelper.updateStructure(
			structure.getStructureId(), ddmForm);

		dataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(structure.getStructureId());

		Assert.assertEquals(
			dataProviderInstanceLinks.toString(), 1,
			dataProviderInstanceLinks.size());

		DDMStructureLocalServiceUtil.deleteStructure(structure);

		dataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(structure.getStructureId());

		Assert.assertEquals(
			dataProviderInstanceLinks.toString(), 0,
			dataProviderInstanceLinks.size());
	}

	@Test
	public void testUpdateStructureWithReferencedDataProviderInstance2()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Field1");

		DDMDataProviderInstance dataProviderInstance1 =
			createDDMDataProviderInstance();

		DDMDataProviderInstance dataProviderInstance2 =
			createDDMDataProviderInstance();

		List<String> actions = new ArrayList<>();

		String action1 = String.format(
			"call('%s','','')", dataProviderInstance1.getUuid());

		String action2 = String.format(
			"call('%s','','')", dataProviderInstance2.getUuid());

		actions.add(action1);
		actions.add(action2);

		DDMFormRule ddmFormRule1 = new DDMFormRule("TRUE", actions);

		ddmForm.addDDMFormRule(ddmFormRule1);

		actions = new ArrayList<>();

		actions.add(action1);

		DDMFormRule ddmFormRule2 = new DDMFormRule("FALSE", actions);

		ddmForm.addDDMFormRule(ddmFormRule2);

		DDMStructure structure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		List<DDMDataProviderInstanceLink> dataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(structure.getStructureId());

		Assert.assertEquals(
			dataProviderInstanceLinks.toString(), 2,
			dataProviderInstanceLinks.size());

		// Remove one of the data provider instance links

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		ddmFormRules.remove(ddmFormRule1);

		ddmForm.setDDMFormRules(ddmFormRules);

		ddmStructureTestHelper.updateStructure(
			structure.getStructureId(), ddmForm);

		dataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(structure.getStructureId());

		Assert.assertEquals(
			dataProviderInstanceLinks.toString(), 1,
			dataProviderInstanceLinks.size());

		DDMStructureLocalServiceUtil.deleteStructure(structure);

		dataProviderInstanceLinks =
			DDMDataProviderInstanceLinkLocalServiceUtil.
				getDataProviderInstanceLinks(structure.getStructureId());

		Assert.assertEquals(
			dataProviderInstanceLinks.toString(), 0,
			dataProviderInstanceLinks.size());
	}

	@Test
	public void testValidateIndexTypePropertyDefaultValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Field1");

		DDMStructure structure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		DDMStructure structureAfterUpdate = updateStructure(structure);

		DDMForm ddmFormAfterUpdate = structureAfterUpdate.getDDMForm();

		List<DDMFormField> ddmFormField = ddmFormAfterUpdate.getDDMFormFields();

		DDMFormField textField = ddmFormField.get(0);

		Assert.assertEquals(StringPool.BLANK, textField.getIndexType());
	}

	@Test
	public void testValidateIndexTypePropertyValue1() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Field1");

		DDMFormTestUtil.setIndexTypeProperty(ddmForm, "text");

		DDMStructure structure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		DDMStructure structureAfterUpdate = updateStructure(structure);

		DDMForm ddmFormAfterUpdate = structureAfterUpdate.getDDMForm();

		List<DDMFormField> ddmFormFieldAfterUpdate =
			ddmFormAfterUpdate.getDDMFormFields();

		DDMFormField textFieldAfterUpdate = ddmFormFieldAfterUpdate.get(0);

		Assert.assertEquals("text", textFieldAfterUpdate.getIndexType());
	}

	@Test
	public void testValidateIndexTypePropertyValue2() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Field1");

		DDMFormTestUtil.setIndexTypeProperty(ddmForm, "none");

		DDMStructure structure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		DDMStructure structureAfterUpdate = updateStructure(structure);

		DDMForm ddmFormAfterUpdate = structureAfterUpdate.getDDMForm();

		List<DDMFormField> ddmFormFieldAfterUpdate =
			ddmFormAfterUpdate.getDDMFormFields();

		DDMFormField textFieldAfterUpdate = ddmFormFieldAfterUpdate.get(0);

		Assert.assertEquals("none", textFieldAfterUpdate.getIndexType());
	}

	@Test(expected = InvalidParentStructureException.class)
	public void testValidateParentStructure() throws Exception {
		DDMStructure structure1 = addStructure(
			0, _classNameId, null, "Test Structure 1", null,
			read("ddm-structure-text-field.xsd"), StorageType.JSON.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);

		DDMStructure structure2 = addStructure(
			structure1.getStructureId(), _classNameId, null, "Test Structure 2",
			null, read("ddm-structure-radio-field.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMStructure structure3 = addStructure(
			structure2.getStructureId(), _classNameId, null, "Test Structure 3",
			null, read("ddm-structure-select-field.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		structure1.setParentStructureId(structure3.getStructureId());

		updateStructure(structure1);
	}

	protected DDMStructure copyStructure(DDMStructure structure)
		throws Exception {

		return DDMStructureLocalServiceUtil.copyStructure(
			structure.getUserId(), structure.getStructureId(),
			structure.getNameMap(), structure.getDescriptionMap(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	protected DDMDataProviderInstance createDDMDataProviderInstance()
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), StringUtil.randomString());

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("dataProviderName");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		return DDMDataProviderInstanceLocalServiceUtil.addDataProviderInstance(
			TestPropsValues.getUserId(), group.getGroupId(), nameMap, nameMap,
			ddmFormValues, "rest", serviceContext);
	}

	protected String getStructureName(DDMStructure structure) {
		return structure.getName(group.getDefaultLanguageId());
	}

	protected DDMStructure updateStructure(DDMStructure structure)
		throws Exception {

		return DDMStructureLocalServiceUtil.updateStructure(
			structure.getUserId(), structure.getStructureId(),
			structure.getParentStructureId(), structure.getNameMap(),
			structure.getDescriptionMap(), structure.getDDMForm(),
			structure.getDDMFormLayout(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private static long _classNameId;

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}