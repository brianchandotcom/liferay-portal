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

package com.liferay.dynamic.data.lists.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DDLRecordCTDisplayRenderer
	extends BaseCTDisplayRenderer<DDLRecord> {

	@Override
	public Class<DDLRecord> getModelClass() {
		return DDLRecord.class;
	}

	@Override
	public String getTitle(Locale locale, DDLRecord ddlRecord)
		throws PortalException {

		return String.valueOf(ddlRecord.getPrimaryKey());
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DDLRecord> displayBuilder)
		throws PortalException {

		DDLRecord ddlRecord = displayBuilder.getModel();

		displayBuilder.display(
			"created-by",
			() -> {
				String userName = ddlRecord.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", ddlRecord.getCreateDate()
		).display(
			"last-modified", ddlRecord.getModifiedDate()
		).display(
			"version", ddlRecord.getVersion()
		).display(
			"cached", ddlRecord.isCachedModel()
		).display(
			"ddl-record-content-json", _serializeJSONDDLRecord(ddlRecord), false
		);
	}

	/**
	 * Serializes a DDLRecord into a JSON String representation of its contents.
	 *
	 * @param ddlRecord the DDLRecord to be serialized
	 * @return JSON String of the given DDLRecord's contents
	 * @throws PortalException if the DDMFormValues couldn't be retrieved from the DDLRecord
	 */
	private String _serializeJSONDDLRecord(DDLRecord ddlRecord)
		throws PortalException {

		DDMFormValues ddmFormValues = ddlRecord.getDDMFormValues();

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues.getDDMForm());

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _ddmFormSerializer;

}