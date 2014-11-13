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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
public class DDLRecordTestHelper {

	public DDLRecordTestHelper(DDLRecordSet ddlRecordSet, Group group)
		throws Exception {

		_ddlRecordSet = ddlRecordSet;
		_group = group;
	}

	public DDLRecord addRecord(
			String name, String description, int workflowAction)
		throws Exception {

		Field nameField = new Field(
			_ddlRecordSet.getDDMStructureId(), "name", name);

		nameField.setDefaultLocale(LocaleUtil.ENGLISH);

		Fields fields = new Fields();

		fields.put(nameField);

		Field descriptionField = new Field(
			_ddlRecordSet.getDDMStructureId(), "description", description);

		descriptionField.setDefaultLocale(LocaleUtil.ENGLISH);

		fields.put(descriptionField);

		return addRecord(
			_ddlRecordSet.getRecordSetId(), fields, workflowAction);
	}

	protected DDLRecord addRecord(
			long recordSetId, Fields fields, int workflowAction)
		throws Exception {

		ServiceContext serviceContext = DDLRecordTestUtil.getServiceContext(
			workflowAction);

		return DDLRecordLocalServiceUtil.addRecord(
			TestPropsValues.getUserId(), _group.getGroupId(), recordSetId,
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields, serviceContext);
	}

	private final DDLRecordSet _ddlRecordSet;
	private final Group _group;

}