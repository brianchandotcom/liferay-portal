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

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate",
	service = {
		DDMTemplateExportImportContentProcessor.class,
		ExportImportContentProcessor.class
	}
)
public class DDMTemplateExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		return _defaultTextExportImportContentProcessor.
			replaceExportContentReferences(
				portletDataContext, stagedModel, content,
				exportReferencedContent, escapeContent);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return _defaultTextExportImportContentProcessor.
			replaceImportContentReferences(
				portletDataContext, stagedModel, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		_defaultTextExportImportContentProcessor.validateContentReferences(
			groupId, content);
	}

	@Reference(target = "(model.class.name=java.lang.String)")
	private ExportImportContentProcessor<String>
		_defaultTextExportImportContentProcessor;

}