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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.BaseDDMServiceTestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class StorageAdapterTest extends BaseDDMServiceTestCase {

	public StorageAdapterTest() {
		_classNameId = PortalUtil.getClassNameId(DDLRecordSet.class);

		_expandoStorageAdapater = new ExpandoStorageAdapter();
		_xmlStorageAdapater = new XMLStorageAdapter();
	}

	@Test
	public void testCreateRepeatableField() throws Exception {
		String xsd = readText("text-repeatable-structure.xsd");

		DDMStructure ddmStructure = addStructure(
			_classNameId, null, "Test Structure", xsd,
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		Fields fields = new Fields();

		Field field = new Field(
			ddmStructure.getStructureId(), "name", new String[] {"1", "2"});

		fields.put(field);

		// XML

		long classPK = create(
			_xmlStorageAdapater, ddmStructure.getStructureId(), fields);

		Fields actualFields = _xmlStorageAdapater.getFields(classPK);

		Assert.assertEquals(fields, actualFields);

		// Expando

		classPK = create(
			_expandoStorageAdapater, ddmStructure.getStructureId(), fields);

		actualFields = _expandoStorageAdapater.getFields(classPK);

		Assert.assertEquals(fields, actualFields);
	}

	protected long create(
			StorageAdapter storageAdapter, long ddmStructureId, Fields fields)
		throws Exception {

		return storageAdapter.create(
			TestPropsValues.getCompanyId(), ddmStructureId, fields,
			ServiceTestUtil.getServiceContext(group.getGroupId()));
	}

	private long _classNameId;

	private StorageAdapter _expandoStorageAdapater;
	private StorageAdapter _xmlStorageAdapater;

}