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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDefinitionException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DDMStructureServiceTest extends BaseDDMServiceTestCase {

	@BeforeClass
	public static void setUpClass() {
		_CLASS_NAME_ID = PortalUtil.getClassNameId(DDLRecord.class);
	}

	@Test
	public void testAddStructureMissingRequiredElementAttribute()
		throws Exception {

		String storageType = StorageType.XML.getValue();

		try {
			addStructure(
				_CLASS_NAME_ID, null, "Test Structure",
				readText("ddm-structure-required-element-attribute.xsd"),
				storageType, DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDefinitionException sde) {
		}
	}

	@Test
	public void testAddStructureWithDuplicateElementName() throws Exception {
		String storageType = StorageType.XML.getValue();

		try {
			addStructure(
				_CLASS_NAME_ID, null, "Test Structure",
				readText("ddm-structure-duplicate-element-name.xsd"),
				storageType, DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDuplicateElementException sdee) {
		}
	}

	@Test
	public void testAddStructureWithDuplicateElementNameInParent()
		throws Exception {

		String storageType = StorageType.XML.getValue();

		try {
			DDMStructure parentStructure = addStructure(
				_CLASS_NAME_ID, null, "Test Parent Structure",
				readText("ddm-structure-duplicate-element-name.xsd"),
				storageType, DDMStructureConstants.TYPE_DEFAULT);

			addStructure(
				parentStructure.getStructureId(), _CLASS_NAME_ID, null,
				"Test Structure",
				readText("ddm-structure-duplicate-element-name.xsd"),
				storageType, DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDuplicateElementException sdee) {
		}
	}

	@Test
	public void testAddStructureWithDuplicateKey() throws Exception {
		String structureKey = RandomTestUtil.randomString();
		String storageType = StorageType.XML.getValue();

		try {
			addStructure(
				_CLASS_NAME_ID, structureKey, "Test Structure 1",
				getTestStructureDefinition(storageType), storageType,
				DDMStructureConstants.TYPE_DEFAULT);

			addStructure(
				_CLASS_NAME_ID, structureKey, "Test Structure 2",
				getTestStructureDefinition(storageType), storageType,
				DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDuplicateStructureKeyException sdske) {
		}
	}

	@Test
	public void testAddStructureWithInvalidElementAttribute() throws Exception {
		String storageType = StorageType.XML.getValue();

		try {
			addStructure(
				_CLASS_NAME_ID, null, "Test Structure",
				readText("ddm-structure-invalid-element-attribute.xsd"),
				storageType, DDMStructureConstants.TYPE_DEFAULT);

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
				StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

			Assert.fail();
		}
		catch (StructureDefinitionException sde) {
		}
	}

	@Test
	public void testAddStructureWithoutName() throws Exception {
		String storageType = StorageType.XML.getValue();

		try {
			addStructure(
				_CLASS_NAME_ID, null, StringPool.BLANK,
				getTestStructureDefinition(storageType), storageType,
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
	public void testSearch() throws Exception {
		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, "Test Structure 1");

		addStructure(_CLASS_NAME_ID, "Test Structure 2");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			structure.getCompanyId(), new long[] {structure.getGroupId()},
			new long[] {structure.getClassNameId()}, null, null,
			structure.getStorageType(), structure.getType(), false, 0, 1, null);

		Assert.assertEquals(1, structures.size());
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		DDMStructure structure = addStructure(
			_CLASS_NAME_ID, "Test Structure 1");

		addStructure(_CLASS_NAME_ID, "Test Structure 2");

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			structure.getCompanyId(), new long[] {structure.getGroupId()},
			new long[] {structure.getClassNameId()}, null, 0, 1, null);

		Assert.assertEquals(1, structures.size());
	}

	@Test
	public void testSearchCount() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			new long[] {_CLASS_NAME_ID}, "Test Structure", null, null,
			DDMStructureConstants.TYPE_DEFAULT, false);

		addStructure(_CLASS_NAME_ID, "Test Structure");

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			new long[] {_CLASS_NAME_ID}, "Test Structure", null, null,
			DDMStructureConstants.TYPE_DEFAULT, false);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testSearchCountByKeywords() throws Exception {
		int initialCount = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			new long[] {_CLASS_NAME_ID}, null);

		addStructure(_CLASS_NAME_ID, "Test Structure");

		int count = DDMStructureLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			new long[] {_CLASS_NAME_ID}, null);

		Assert.assertEquals(initialCount + 1, count);
	}

	protected DDMStructure copyStructure(DDMStructure structure)
		throws Exception {

		return DDMStructureLocalServiceUtil.copyStructure(
			structure.getUserId(), structure.getStructureId(),
			structure.getNameMap(), structure.getDescriptionMap(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	protected DDMStructure updateStructure(DDMStructure structure)
		throws Exception {

		return DDMStructureLocalServiceUtil.updateStructure(
			structure.getStructureId(), structure.getParentStructureId(),
			structure.getNameMap(), structure.getDescriptionMap(),
			structure.getDefinition(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private static long _CLASS_NAME_ID;

}