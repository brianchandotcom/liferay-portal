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

package com.liferay.adaptive.media.blogs.web.internal.exportimport.content.processor;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"model.class.name=com.liferay.blogs.model.BlogsEntry",
		"service.ranking:Integer=100"
	},
	service = ExportImportContentProcessor.class
)
public class AMBlogsEntryExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		String replacedContent =
			_blogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedModel, content,
					exportReferencedContent, escapeContent);

		return _htmlExportImportContentProcessor.replaceExportContentReferences(
			portletDataContext, stagedModel, replacedContent,
			exportReferencedContent, escapeContent);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		String replacedContent =
			_blogsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, stagedModel, content);

		return _htmlExportImportContentProcessor.replaceImportContentReferences(
			portletDataContext, stagedModel, replacedContent);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		_blogsEntryExportImportContentProcessor.validateContentReferences(
			groupId, content);

		_htmlExportImportContentProcessor.validateContentReferences(
			groupId, content);
	}

	@Reference(
		target = "(&(model.class.name=com.liferay.blogs.model.BlogsEntry)(!(component.name=com.liferay.adaptive.media.blogs.web.internal.exportimport.content.processor.AMBlogsEntryExportImportContentProcessor)))"
	)
	private ExportImportContentProcessor<String>
		_blogsEntryExportImportContentProcessor;

	@Reference(target = "(adaptive.media.format=html)")
	private ExportImportContentProcessor<String>
		_htmlExportImportContentProcessor;

}