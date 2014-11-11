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

package com.liferay.portlet.dynamicdatalists.util.test;

import com.liferay.portal.kernel.test.DependenciesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
public class DDLRecordTestHelper {

	public static DDLRecord updateRecord(
			long recordId, DDMFormValues ddmFormValues, int workflowAction)
		throws Exception {

		ServiceContext serviceContext = DDLRecordTestUtil.getServiceContext(
			workflowAction);

		return DDLRecordLocalServiceUtil.updateRecord(
			TestPropsValues.getUserId(), recordId, false,
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
			serviceContext);
	}

	public DDLRecordTestHelper(Object testCase) throws Exception {
		this(testCase, new DDMStructureTestHelper());
	}

	public DDLRecordTestHelper(
			Object testCase, DDMStructureTestHelper ddmStructureTestHelper)
		throws Exception {

		_clazz = testCase.getClass();
		_ddmStructureTestHelper = ddmStructureTestHelper;
		_group = ddmStructureTestHelper.getGroup();
		_recordSet = addRecordSet();
	}

	public DDLRecord addRecord(
			String name, String description, int workflowAction)
		throws Exception {

		Fields fields = new Fields();

		Field nameField = new Field(
			_recordSet.getDDMStructureId(), "name", name);

		nameField.setDefaultLocale(LocaleUtil.ENGLISH);

		fields.put(nameField);

		Field descriptionField = new Field(
			_recordSet.getDDMStructureId(), "description", description);

		descriptionField.setDefaultLocale(LocaleUtil.ENGLISH);

		fields.put(descriptionField);

		return addRecord(_recordSet.getRecordSetId(), fields, workflowAction);
	}

	public Group getGroup() {
		return _group;
	}

	public DDLRecordSet getRecordSet() {
		return _recordSet;
	}

	protected DDLRecord addRecord(
			long recordSetId, Fields fields, int workflowAction)
		throws Exception {

		return DDLRecordTestUtil.addRecord(
			_group, recordSetId, fields, workflowAction);
	}

	protected DDLRecordSet addRecordSet() throws Exception {
		DDMStructure ddmStructure = _ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(DDLRecordSet.class), null,
			"Test Structure", readText("test-structure.xsd"),
			StorageType.XML.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		return addRecordSet(ddmStructure.getStructureId());
	}

	protected DDLRecordSet addRecordSet(long ddmStructureId) throws Exception {
		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.US, "Test Record Set");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDLRecordSetLocalServiceUtil.addRecordSet(
			TestPropsValues.getUserId(), _group.getGroupId(), ddmStructureId,
			null, nameMap, null, DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, serviceContext);
	}

	protected String readText(String fileName) throws Exception {
		return DependenciesTestUtil.readText(
			_clazz, DDLRecordTestUtil.getBasePath() + fileName);
	}

	private final Class<?> _clazz;
	private final DDMStructureTestHelper _ddmStructureTestHelper;
	private final Group _group;
	private final DDLRecordSet _recordSet;

}