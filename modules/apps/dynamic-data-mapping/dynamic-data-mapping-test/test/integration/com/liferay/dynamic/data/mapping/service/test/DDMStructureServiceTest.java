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
import com.liferay.dynamic.data.mapping.exception.RequiredStructureException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateStructureKeyException;
import com.liferay.dynamic.data.mapping.exception.StructureNameException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.dynamic.data.mapping.util.comparator.StructureIdComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class DDMStructureServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_CLASS_NAME_ID = PortalUtil.getClassNameId(StringUtil.randomString());
	}

	@Test
	public void testAddStructureMissingRequiredElementAttribute()
		throws Exception {

		try {
			addStructure(
				_CLASS_NAME_ID, null, "Test Structure",
				read("ddm-structure-required-element-attribute.xsd"),
				StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDefinitionException sde) {
		}
	}

	@Test
	public void testAddStructureWithDuplicateElementName() throws Exception {
		try {
			addStructure(
				_CLASS_NAME_ID, null, "Test Structure",
				read("ddm-structure-duplicate-element-name.xsd"),
				StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDuplicateElementException sdee) {
		}
	}

	@Test
	public void testAddStructureWithDuplicateElementNameInParent()
		throws Exception {

		try {
			DDMStructure parentStructure = addStructure(
				_CLASS_NAME_ID, null, "Test Parent Structure",
				read("ddm-structure-duplicate-element-name.xsd"),
				StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			addStructure(
				parentStructure.getStructureId(), _CLASS_NAME_ID, null,
				"Test Structure",
				read("ddm-structure-duplicate-element-name.xsd"),
				StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDuplicateElementException sdee) {
		}
	}

	@Test
	public void testAddStructureWithDuplicateKey() throws Exception {
		String structureKey = RandomTestUtil.randomString();

		try {
			addStructure(
				_CLASS_NAME_ID, structureKey, "Test Structure 1",
				read("test-structure.xsd"), StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			addStructure(
				_CLASS_NAME_ID, structureKey, "Test Structure 2",
				read("test-structure.xsd"), StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDuplicateStructureKeyException sdske) {
		}
	}

	@Test
	public void testAddStructureWithInvalidElementAttribute() throws Exception {
		try {
			addStructure(
				_CLASS_NAME_ID, null, "Test Structure",
				read("ddm-structure-invalid-element-attribute.xsd"),
				StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDefinitionException sde) {
		}
	}

	@Test
	public void testAddStructureWithoutDefinition() throws Exception {
		try {
			addStructure(
				_CLASS_NAME_ID, null, "Test Structure", StringPool.BLANK,
				StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDefinitionException sde) {
		}
	}

	@Test
	public void testAddStructureWithoutName() throws Exception {
		try {
			addStructure(
				_CLASS_NAME_ID, null, StringPool.BLANK,
				read("test-structure.xsd"), StorageType.JSON.getValue(),
				DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureNameException sne) {
		}
	}

	@Test
	public void testCopyStructure() throws Exception {
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Test Structure");

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
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Test Structure");

		DDMStructureLocalServiceUtil.deleteStructure(
			structure.getStructureId());

		Assert.assertNull(
			DDMStructureLocalServiceUtil.fetchDDMStructure(
				structure.getStructureId()));
	}

	@Test(
		expected =
			RequiredStructureException.
				MustNotDeleteStructureReferencedByTemplates.class
	)
	public void testDeleteStructureReferencedByTemplates() throws Exception {
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Test Structure");

		addDisplayTemplate(structure.getPrimaryKey(), "Test Display Template");
		addFormTemplate(structure.getPrimaryKey(), "Test Form Template");

		DDMStructureLocalServiceUtil.deleteStructure(
			structure.getStructureId());
	}

	@Test
	public void testFetchStructure() throws Exception {
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Test Structure");

		Assert.assertNotNull(
			DDMStructureLocalServiceUtil.fetchStructure(
				structure.getGroupId(), _CLASS_NAME_ID,
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
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Test Structure");

		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getStructures(structure.getGroupId());

		Assert.assertTrue(structures.contains(structure));
	}

	@Test
	public void testGetTemplates() throws Exception {
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Test Structure");

		addDisplayTemplate(structure.getStructureId(), "Test Display Template");
		addFormTemplate(structure.getStructureId(), "Test Form Template");

		List<DDMTemplate> templates = structure.getTemplates();

		Assert.assertEquals(2, templates.size());
	}

	@Test
	public void testSearchByAnyStatus() throws Exception {
		addStructure(
			0, _CLASS_NAME_ID, null, StringUtil.randomString(),
			StringPool.BLANK, read("test-structure.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED);

		addStructure(
			0, _CLASS_NAME_ID, null, StringUtil.randomString(),
			StringPool.BLANK, read("test-structure.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_DRAFT);

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, null, null,
			DDMStructureConstants.TYPE_DEFAULT, WorkflowConstants.STATUS_ANY,
			true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(2, structures.size());
	}

	@Test
	public void testSearchByClassNameId() throws Exception {
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, null, null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(3, structures.size());
	}

	@Test
	public void testSearchByDescription() throws Exception {
		addStructure(_CLASS_NAME_ID, StringUtil.randomString(), "Contact");
		addStructure(_CLASS_NAME_ID, StringUtil.randomString(), "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, "Contact", null,
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
			0, _CLASS_NAME_ID, null, StringUtil.randomString(),
			StringPool.BLANK, read("test-structure.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED);

		addStructure(
			0, _CLASS_NAME_ID, null, StringUtil.randomString(),
			StringPool.BLANK, read("test-structure.xsd"),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_DRAFT);

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, null, null,
			DDMStructureConstants.TYPE_DEFAULT, WorkflowConstants.STATUS_DRAFT,
			true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, structures.size());
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Events");

		addStructure(_CLASS_NAME_ID, "Event");

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
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "To Do");

		addStructure(_CLASS_NAME_ID, "To Doing");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			structure.getCompanyId(), new long[] {structure.getGroupId()},
			structure.getClassNameId(), "To Do",
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new StructureIdComparator(true));

		Assert.assertEquals(2, structures.size());

		Assert.assertEquals("To Do", getStructureName(structures.get(0)));
		Assert.assertEquals("To Doing", getStructureName(structures.get(1)));
	}

	@Test
	public void testSearchByName() throws Exception {
		addStructure(_CLASS_NAME_ID, "Contact");
		addStructure(_CLASS_NAME_ID, "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Contact", null, null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals("Contact", getStructureName(structures.get(0)));
	}

	@Test
	public void testSearchByNameAndDescription() throws Exception {
		addStructure(_CLASS_NAME_ID, "Contact", "Contact");
		addStructure(_CLASS_NAME_ID, "Event", "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Contact", "Event", null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(0, structures.size());
	}

	@Test
	public void testSearchByNameOrDescription() throws Exception {
		addStructure(_CLASS_NAME_ID, "Contact", "Contact");
		addStructure(_CLASS_NAME_ID, "Event", "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Contact", "Event", null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new StructureIdComparator(true));

		Assert.assertEquals("Contact", getStructureName(structures.get(0)));
		Assert.assertEquals("Event", getStructureName(structures.get(1)));
	}

	@Test
	public void testSearchByNonExistingStorageType() throws Exception {
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, null, "NonExistingStorageType",
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(0, structures.size());
	}

	@Test
	public void testSearchByStorageType() throws Exception {
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, null, StorageType.JSON.toString(),
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, structures.size());
	}

	@Test
	public void testSearchCount() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Test Structure", null, null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, false);

		addStructure(_CLASS_NAME_ID, "Test Structure");

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Test Structure", null, null,
			DDMStructureConstants.TYPE_DEFAULT,
			WorkflowConstants.STATUS_APPROVED, false);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testSearchCountByKeywords() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, WorkflowConstants.STATUS_APPROVED);

		addStructure(_CLASS_NAME_ID, "Test Structure");

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(initialCount + 1, count);
	}

	protected DDMStructure copyStructure(DDMStructure structure)
		throws Exception {

		return DDMStructureLocalServiceUtil.copyStructure(
			structure.getUserId(), structure.getStructureId(),
			structure.getNameMap(), structure.getDescriptionMap(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
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

	private static long _CLASS_NAME_ID;

}