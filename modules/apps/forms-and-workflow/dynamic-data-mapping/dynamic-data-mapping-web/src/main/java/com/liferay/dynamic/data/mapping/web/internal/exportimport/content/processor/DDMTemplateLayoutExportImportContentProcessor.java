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

package com.liferay.dynamic.data.mapping.web.internal.exportimport.content.processor;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.base.BaseLayoutExportImportContentProcessor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(
	property = {
		"content.processor.order=2",
		"model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate"
	},
	service = {ExportImportContentProcessor.class}
)
public class DDMTemplateLayoutExportImportContentProcessor
	extends BaseLayoutExportImportContentProcessor<DDMTemplate> {
}