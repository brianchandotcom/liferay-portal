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

package com.liferay.dynamic.data.lists.exportimport.content.processor;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.mapping.exportimport.content.processor.DDMFormValuesExportImportContentProcessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	property = {
		"model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord"
	},
	service = {ExportImportContentProcessor.class}
)
public class DDLRecordDDMFormValuesExportImportContentProcessor
	implements ExportImportContentProcessor<DDLRecord, DDMFormValues> {

	public DDMFormValues replaceExportContentReferences(
			PortletDataContext portletDataContext, DDLRecord stagedModel,
			DDMFormValues content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		return ddmFormValuesExportImportContentProcessor.
			replaceExportContentReferences(
				portletDataContext, stagedModel, content,
				exportReferencedContent, escapeContent);
	}

	public DDMFormValues replaceImportContentReferences(
			PortletDataContext portletDataContext, DDLRecord stagedModel,
			DDMFormValues content)
		throws Exception {

		return ddmFormValuesExportImportContentProcessor.
			replaceImportContentReferences(
				portletDataContext, stagedModel, content);
	}

	public boolean validateContentReferences(
		long groupId, DDMFormValues content) {

		return ddmFormValuesExportImportContentProcessor.
			validateContentReferences(groupId, content);
	}

	@Reference
	protected DDMFormValuesExportImportContentProcessor
		ddmFormValuesExportImportContentProcessor;

}