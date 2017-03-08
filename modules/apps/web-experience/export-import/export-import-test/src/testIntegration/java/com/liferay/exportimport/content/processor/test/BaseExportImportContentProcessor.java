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

package com.liferay.exportimport.content.processor.test;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.base.BaseTextExportImportContentProcessor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael Bowerman
 */
@Component(
	property = {
		"model.class.name=com.liferay.exportimport.content.processor.test.DummyStagedModel"
	},
	service = {ExportImportContentProcessor.class}
)
public class BaseExportImportContentProcessor
	extends BaseTextExportImportContentProcessor {
}