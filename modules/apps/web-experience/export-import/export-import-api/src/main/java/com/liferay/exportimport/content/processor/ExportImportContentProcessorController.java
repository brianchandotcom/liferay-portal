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

package com.liferay.exportimport.content.processor;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;

import java.io.Serializable;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true, service = ExportImportContentProcessorController.class
)
@ProviderType
public class ExportImportContentProcessorController {

	public <S extends StagedModel, T extends Serializable> T
			replaceExportContentReferences(
				PortletDataContext portletDataContext, S stagedModel, T content,
				boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		return doReplaceContentReferences(
			portletDataContext, stagedModel, content, exportReferencedContent,
			escapeContent, true);
	}

	public <S extends StagedModel, T extends Serializable> T
			replaceImportContentReferences(
				PortletDataContext portletDataContext, S stagedModel, T content)
		throws Exception {

		return doReplaceContentReferences(
			portletDataContext, stagedModel, content, false, false, false);
	}

	public <S extends StagedModel, T extends Serializable> boolean
		validateContentReferences(
			Class<S> stagedModelClass, long groupId, T content) {

		List<ExportImportContentProcessor<?, ?>> exportImportContentProcessors =
			ExportImportContentProcessorRegistryUtil.
				getExportImportContentProcessors(stagedModelClass.getName());

		return exportImportContentProcessors.stream()
			.map(this::_castExportImportContentProcessor)
			.filter(Objects::nonNull)
			.allMatch(eicp -> eicp.validateContentReferences(groupId, content));
	}

	protected <S extends StagedModel, T extends Serializable> T
			doReplaceContentReferences(
				PortletDataContext portletDataContext, S stagedModel, T content,
				boolean exportReferencedContent, boolean escapeContent,
				boolean export)
		throws Exception {

		String className = ExportImportClassedModelUtil.getClassName(
			stagedModel);

		List<ExportImportContentProcessor<?, ?>> exportImportContentProcessors =
			ExportImportContentProcessorRegistryUtil.
				getExportImportContentProcessors(className);

		T processedContent = null;

		for (ExportImportContentProcessor<?, ?>
				genericExportImportContentProcessor :
					exportImportContentProcessors) {

			ExportImportContentProcessor<S, T> exportImportContentProcessor =
				_castExportImportContentProcessor(
					genericExportImportContentProcessor);

			if (exportImportContentProcessor == null) {
				continue;
			}

			if (processedContent == null) {
				if (export) {
					processedContent =
						exportImportContentProcessor.
							replaceExportContentReferences(
								portletDataContext, stagedModel, content,
								exportReferencedContent, escapeContent);
				}
				else {
					processedContent =
						exportImportContentProcessor.
							replaceImportContentReferences(
								portletDataContext, stagedModel, content);
				}
			}
			else {
				if (export) {
					processedContent =
						exportImportContentProcessor.
							replaceExportContentReferences(
								portletDataContext, stagedModel,
								processedContent, exportReferencedContent,
								escapeContent);
				}
				else {
					processedContent =
						exportImportContentProcessor.
							replaceImportContentReferences(
								portletDataContext, stagedModel,
								processedContent);
				}
			}
		}

		return processedContent;
	}

	private <S extends StagedModel, T extends Serializable>
		ExportImportContentProcessor<S, T> _castExportImportContentProcessor(
			ExportImportContentProcessor<?, ?>
				genericExportImportContentProcessor) {

		if (genericExportImportContentProcessor == null) {
			return null;
		}

		ExportImportContentProcessor<S, T> exportImportContentProcessor = null;

		try {
			exportImportContentProcessor =
				(ExportImportContentProcessor<S, T>)
					genericExportImportContentProcessor;
		}
		catch (ClassCastException cce) {
			if (_log.isWarnEnabled()) {
				Class genericExportImportContentProcessorClass =
					genericExportImportContentProcessor.getClass();

				_log.warn(
					"Skipping " +
						genericExportImportContentProcessorClass.
							getSimpleName() +
							" due to unsupported content type");
			}
		}

		return exportImportContentProcessor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportContentProcessorController.class);

}