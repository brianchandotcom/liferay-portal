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

package com.liferay.portlet.dynamicdatalists.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.asset.model.BaseDDMFieldReader;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesToFieldsConverterUtil;

/**
 * @author Adolfo Pérez
 */
public class DDLRecordDDMFieldReader extends BaseDDMFieldReader {

	public DDLRecordDDMFieldReader(DDLRecord ddlRecord) {
		_ddlRecord = ddlRecord;
	}

	@Override
	public Fields getFields() throws PortalException {
		DDLRecordSet ddlRecordSet = _ddlRecord.getRecordSet();

		DDMFormValues ddmFormValues = _ddlRecord.getDDMFormValues();

		return DDMFormValuesToFieldsConverterUtil.convert(
			ddlRecordSet.getDDMStructure(), ddmFormValues);
	}

	private final DDLRecord _ddlRecord;

}