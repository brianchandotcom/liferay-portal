/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.StructureXsdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DDMStructureServiceTest extends BaseDDMServiceTestCase {

	@Test
	public void testAddStructureWithDuplicatedKey() throws Exception {
		String structureKey = ServiceTestUtil.randomString();
		String storageType = StorageType.XML.getValue();
		String xsd = getTestStructureXsd(storageType);
		int type =  DDMStructureConstants.TYPE_DEFAULT;

		try {
			addStructure(
				_testClassNameId, structureKey, "Test Structure 1", xsd,
				storageType, type);

			addStructure(
				_testClassNameId, structureKey, "Test Structure 2", xsd,
				storageType, type);

			Assert.fail("Should not be able to add Structure because " +
					"structureKey is duplicated");
		}
		catch (StructureDuplicateStructureKeyException sdske) {
		}
	}

	@Test
	public void testAddStructureWithoutName() throws Exception {
		String storageType = StorageType.XML.getValue();
		String xsd = getTestStructureXsd(storageType);
		int type =  DDMStructureConstants.TYPE_DEFAULT;

		try {
			addStructure(
				_testClassNameId, null, StringPool.BLANK, xsd, storageType,
				type);

			Assert.fail("Should not be able to add Structure because " +
				"name is empty");
		}
		catch (StructureNameException sne) {
		}
	}

	@Test
	public void testAddStructureWithoutXsd() throws Exception {
		String storageType = StorageType.XML.getValue();
		int type =  DDMStructureConstants.TYPE_DEFAULT;

		try {
			addStructure(
				_testClassNameId, null, "Test Structure", StringPool.BLANK,
				storageType, type);

			Assert.fail("Should not be able to add Structure because " +
				"xsd is empty");
		}
		catch (StructureXsdException sxe) {
		}
	}

	@Test
	public void testCopyStructure() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure");

		DDMStructure copyStructure = copyStructure(structure);

		Assert.assertEquals(structure.getGroupId(), copyStructure.getGroupId());
		Assert.assertEquals(structure.getXsd(), copyStructure.getXsd());
		Assert.assertEquals(
			structure.getStorageType(), copyStructure.getStorageType());
		Assert.assertEquals(structure.getType(), copyStructure.getType());
	}

	@Test
	public void testDeleteStructure() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure");

		long structureId = structure.getStructureId();

		DDMStructureLocalServiceUtil.deleteStructure(structureId);

		DDMStructure fetchStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructure(structureId);

		Assert.assertNull(fetchStructure);
	}

	@Test
	public void testDeleteStructureReferencedByTemplates() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure");

		addDetailTemplate(structure.getPrimaryKey(), "Test List Template");
		addListTemplate(structure.getPrimaryKey(), "Test Detail Template");

		try {
			DDMStructureLocalServiceUtil.deleteStructure(
				structure.getStructureId());

			Assert.fail(
				"Should not be able to delete this structure because it is " +
				"referenced by templates");
		}
		catch (RequiredStructureException rse) {
		}
	}

	@Test
	public void testFetchStructure() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure");

		DDMStructure fetchStructure =
			DDMStructureLocalServiceUtil.fetchStructure(
				structure.getGroupId(), structure.getStructureKey());

		Assert.assertNotNull(fetchStructure);
	}

	@Test
	public void testGetStructures() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure");

		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getStructures(structure.getGroupId());

		Assert.assertTrue(structures.contains(structure));
	}

	@Test
	public void testGetTemplates() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure");

		addDetailTemplate(structure.getPrimaryKey(), "Test List Template");
		addListTemplate(structure.getPrimaryKey(), "Test Detail Template");

		List<DDMTemplate> templates = structure.getTemplates();

		Assert.assertEquals(2, templates.size());
	}

	@Test
	public void testSearch() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure 1");

		addStructure(_testClassNameId, "Test Structure 2");

		long[] groupIds = new long[] {structure.getGroupId()};
		long[] classNameIds = new long[] {structure.getClassNameId()};

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			structure.getCompanyId(), groupIds, classNameIds, null, null,
			structure.getStorageType(), structure.getType(), false, 0, 1, null);

		Assert.assertEquals(1, structures.size());
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure 1");

		addStructure(_testClassNameId, "Test Structure 2");

		long[] groupIds = new long[] {structure.getGroupId()};
		long[] classNameIds = new long[] {structure.getClassNameId()};

		List<DDMStructure> structures = DDMStructureLocalServiceUtil.search(
			structure.getCompanyId(), groupIds, classNameIds, null, 0, 1, null);

		Assert.assertEquals(1, structures.size());
	}

	@Test
	public void testSearchCount() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure");

		long[] groupIds = new long[] {structure.getGroupId()};
		long[] classNameIds = new long[] {structure.getClassNameId()};

		int count = DDMStructureLocalServiceUtil.searchCount(
			structure.getCompanyId(), groupIds, classNameIds,
			structure.getName(), structure.getDescription(),
			structure.getStorageType(), structure.getType(), false);

		Assert.assertTrue(count > 0);
	}

	@Test
	public void testSearchCountByKeywords() throws Exception {
		DDMStructure structure = addStructure(
			_testClassNameId, "Test Structure");

		long[] groupIds = new long[] {structure.getGroupId()};
		long[] classNameIds = new long[] {structure.getClassNameId()};

		int count = DDMStructureLocalServiceUtil.searchCount(
			structure.getCompanyId(), groupIds, classNameIds,
			structure.getName());

		Assert.assertTrue(count > 0);
	}

	protected DDMStructure copyStructure(DDMStructure structure)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return  DDMStructureLocalServiceUtil.copyStructure(
			structure.getUserId(), structure.getStructureId(),
			structure.getNameMap(), structure.getDescriptionMap(),
			serviceContext);
	}

	protected DDMStructure updateStructure(DDMStructure structure)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return DDMStructureLocalServiceUtil.updateStructure(
			structure.getStructureId(), structure.getNameMap(),
			structure.getDescriptionMap(), structure.getXsd(), serviceContext);
	}

	private long _testClassNameId = PortalUtil.getClassNameId(DDLRecord.class);

}