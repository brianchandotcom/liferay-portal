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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDefinitionException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.comparator.StructureIdComparator;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
public class DDMStructureServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		_CLASS_NAME_ID = PortalUtil.getClassNameId(DDLRecordSet.class);
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
		Assert.assertEquals(
			structure.getDefinition(), copyStructure.getDefinition());
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

	@Test
	public void testDeleteStructureReferencedByTemplates() throws Exception {
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Test Structure");

		addDisplayTemplate(structure.getPrimaryKey(), "Test Display Template");
		addFormTemplate(structure.getPrimaryKey(), "Test Form Template");

		try {
			DDMStructureLocalServiceUtil.deleteStructure(
				structure.getStructureId());

			Assert.fail();
		}
		catch (RequiredStructureException rse) {
		}
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
	public void testSearchByClassNameId() throws Exception {
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, null, null,
			DDMStructureConstants.TYPE_DEFAULT, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(3, structures.size());
	}

	@Test
	public void testSearchByDescription() throws Exception {
		addStructure(_CLASS_NAME_ID, StringUtil.randomString(),  "Contact");
		addStructure(_CLASS_NAME_ID, StringUtil.randomString(), "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, "Contact", null,
			DDMStructureConstants.TYPE_DEFAULT, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		DDMStructure structure = structures.get(0);

		Assert.assertEquals(
			"Contact", structure.getDescription(group.getDefaultLanguageId()));
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		DDMStructure structure = addStructure(_CLASS_NAME_ID, "Events");

		addStructure(_CLASS_NAME_ID, "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			structure.getCompanyId(), new long[] {structure.getGroupId()},
			structure.getClassNameId(), "Event", QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new StructureIdComparator(true));

		Assert.assertEquals("Events", getStructureName(structures.get(0)));
		Assert.assertEquals("Event", getStructureName(structures.get(1)));
	}

	@Test
	public void testSearchByName() throws Exception {
		addStructure(_CLASS_NAME_ID, "Contact");
		addStructure(_CLASS_NAME_ID, "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Contact", null, null,
			DDMStructureConstants.TYPE_DEFAULT, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals("Contact", getStructureName(structures.get(0)));
	}

	@Test
	public void testSearchByNameAndDescription() throws Exception {
		addStructure(_CLASS_NAME_ID, "Contact",  "Contact");
		addStructure(_CLASS_NAME_ID, "Event", "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Contact", "Event", null,
			DDMStructureConstants.TYPE_DEFAULT, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(0, structures.size());
	}

	@Test
	public void testSearchByNameOrDescription() throws Exception {
		addStructure(_CLASS_NAME_ID, "Contact",  "Contact");
		addStructure(_CLASS_NAME_ID, "Event", "Event");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Contact", "Event", null,
			DDMStructureConstants.TYPE_DEFAULT, false, QueryUtil.ALL_POS,
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
			DDMStructureConstants.TYPE_DEFAULT, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(0, structures.size());
	}

	@Test
	public void testSearchByStorageType() throws Exception {
		addStructure(_CLASS_NAME_ID, StringUtil.randomString());

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null, null, StorageType.JSON.toString(),
			DDMStructureConstants.TYPE_DEFAULT, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, structures.size());
	}

	@Test
	public void testSearchCount() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Test Structure", null, null,
			DDMStructureConstants.TYPE_DEFAULT, false);

		addStructure(_CLASS_NAME_ID, "Test Structure");

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, "Test Structure", null, null,
			DDMStructureConstants.TYPE_DEFAULT, false);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testSearchCountByKeywords() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null);

		addStructure(_CLASS_NAME_ID, "Test Structure");

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			_CLASS_NAME_ID, null);

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
			structure.getStructureId(), structure.getParentStructureId(),
			structure.getNameMap(), structure.getDescriptionMap(),
			structure.getDDMForm(), structure.getDDMFormLayout(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private static long _CLASS_NAME_ID;

}