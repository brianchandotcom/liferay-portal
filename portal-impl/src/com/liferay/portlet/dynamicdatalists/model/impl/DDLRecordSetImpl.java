/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordSetImpl extends DDLRecordSetBaseImpl {

	public DDLRecordSetImpl() {
	}

	public DDMStructure getDDMStructure()
		throws PortalException, SystemException {

		return DDMStructureLocalServiceUtil.getStructure(getDDMStructureId());
	}

	public DDMStructure getDDMStructure(long detailDDMTemplateId)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = getDDMStructure();

		if (detailDDMTemplateId > 0) {
			try {
				DDMTemplate ddmTemplate =
					DDMTemplateLocalServiceUtil.getTemplate(
						detailDDMTemplateId);

				ddmStructure.setXsd(ddmTemplate.getScript());
			}
			catch (NoSuchTemplateException nste) {
			}
		}

		return ddmStructure;
	}

	public List<DDLRecord> getRecords() throws SystemException {
		return DDLRecordLocalServiceUtil.getRecords(getRecordSetId());
	}

	public List<Fields> getRecordsFieldsList() throws Exception {
		List<Fields> fieldsList = new ArrayList<Fields>();

		for (DDLRecord record : getRecords()) {
			fieldsList.add(record.getFields());
		}

		return fieldsList;
	}

}