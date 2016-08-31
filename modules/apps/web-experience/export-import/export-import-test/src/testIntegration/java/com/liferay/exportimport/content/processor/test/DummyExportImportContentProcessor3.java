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
import com.liferay.exportimport.kernel.lar.PortletDataContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	property = {
		"content.processor.order=3",
		"model.class.name=com.liferay.exportimport.content.processor.test.DummyStagedModel"
	},
	service = {ExportImportContentProcessor.class}
)
public class DummyExportImportContentProcessor3
	implements ExportImportContentProcessor<DummyStagedModel, String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, DummyStagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		return content + 3;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, DummyStagedModel stagedModel,
			String content)
		throws Exception {

		return content + 3;
	}

	@Override
	public boolean validateContentReferences(long groupId, String content) {
		return false;
	}

}