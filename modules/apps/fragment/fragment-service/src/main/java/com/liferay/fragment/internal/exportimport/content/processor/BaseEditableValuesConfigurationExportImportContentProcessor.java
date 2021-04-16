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

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Víctor Galán
 */
public abstract class
	BaseEditableValuesConfigurationExportImportContentProcessor
		implements ExportImportContentProcessor<JSONObject> {

	@Override
	public JSONObject replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableValuesJSONObject,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_getFragmentConfigurationFields((FragmentEntryLink)stagedModel);

		if (ListUtil.isEmpty(fragmentConfigurationFields)) {
			return editableValuesJSONObject;
		}

		JSONObject editableProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (editableProcessorJSONObject == null) {
			return editableValuesJSONObject;
		}

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			JSONObject jsonObject = editableProcessorJSONObject.getJSONObject(
				fragmentConfigurationField.getName());

			if (jsonObject != null) {
				replaceExportContentReferences(
					portletDataContext, stagedModel, jsonObject,
					exportReferencedContent);
			}
		}

		return editableValuesJSONObject;
	}

	@Override
	public JSONObject replaceImportContentReferences(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		JSONObject editableValuesJSONObject) {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_getFragmentConfigurationFields((FragmentEntryLink)stagedModel);

		if (ListUtil.isEmpty(fragmentConfigurationFields)) {
			return editableValuesJSONObject;
		}

		JSONObject editableProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (editableProcessorJSONObject == null) {
			return editableValuesJSONObject;
		}

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			JSONObject jsonObject = editableProcessorJSONObject.getJSONObject(
				fragmentConfigurationField.getName());

			if (jsonObject != null) {
				replaceImportContentReferences(portletDataContext, jsonObject);
			}
		}

		return editableValuesJSONObject;
	}

	@Override
	public void validateContentReferences(long groupId, JSONObject jsonObject)
		throws PortalException {
	}

	protected abstract String getConfigurationType();

	protected abstract FragmentEntryConfigurationParser
		getFragmentEntryConfigurationParser();

	protected abstract void replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject configurationValueJSONObject,
			boolean exportReferencedContent)
		throws Exception;

	protected abstract void replaceImportContentReferences(
		PortletDataContext portletDataContext,
		JSONObject configurationValueJSONObject);

	private List<FragmentConfigurationField> _getFragmentConfigurationFields(
		FragmentEntryLink fragmentEntryLink) {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			getFragmentEntryConfigurationParser();

		return Stream.of(
			fragmentEntryConfigurationParser.getFragmentConfigurationFields(
				fragmentEntryLink.getConfiguration())
		).flatMap(
			Collection::stream
		).filter(
			fragmentConfigurationField -> Objects.equals(
				fragmentConfigurationField.getType(), getConfigurationType())
		).collect(
			Collectors.toList()
		);
	}

	private static final String _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

}