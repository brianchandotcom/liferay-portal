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
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "content.processor.type=FragmentEntryLinkEditableValues",
	service = ExportImportContentProcessor.class
)
public class EditableValuesNavigationMenuSelectorExportImportContentProcessor
	implements ExportImportContentProcessor<JSONObject> {

	@Override
	public JSONObject replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableValuesJSONObject,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_getNavigationMenuSelectorFragmentConfigurationFields(
				(FragmentEntryLink)stagedModel);

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

			_exportSiteNavigationMenu(
				jsonObject, exportReferencedContent, portletDataContext,
				stagedModel);
		}

		return editableValuesJSONObject;
	}

	@Override
	public JSONObject replaceImportContentReferences(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		JSONObject editableValuesJSONObject) {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_getNavigationMenuSelectorFragmentConfigurationFields(
				(FragmentEntryLink)stagedModel);

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

			_replaceSiteNavigationMenuIds(jsonObject, portletDataContext);
		}

		return editableValuesJSONObject;
	}

	@Override
	public void validateContentReferences(long groupId, JSONObject jsonObject) {
	}

	private void _exportSiteNavigationMenu(
			JSONObject configurationValueJSONObject,
			boolean exportReferencedContent,
			PortletDataContext portletDataContext,
			StagedModel referrerStagedModel)
		throws Exception {

		long siteNavigationMenuId = configurationValueJSONObject.getLong(
			"siteNavigationMenuId");

		StagedModel stagedModel = null;

		if (siteNavigationMenuId > 0) {
			stagedModel =
				_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
					siteNavigationMenuId);
		}
		else {
			stagedModel = _layoutLocalService.fetchLayout(
				configurationValueJSONObject.getLong(
					"parentSiteNavigationMenuItemId"));
		}

		if (stagedModel == null) {
			return;
		}

		if (exportReferencedContent) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, referrerStagedModel, stagedModel,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
		else {
			Element entityElement = portletDataContext.getExportDataElement(
				referrerStagedModel);

			portletDataContext.addReferenceElement(
				referrerStagedModel, entityElement, stagedModel,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}
	}

	private List<FragmentConfigurationField>
		_getNavigationMenuSelectorFragmentConfigurationFields(
			FragmentEntryLink fragmentEntryLink) {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_fragmentEntryConfigurationParser.getFragmentConfigurationFields(
				fragmentEntryLink.getConfiguration());

		Stream<FragmentConfigurationField> stream =
			fragmentConfigurationFields.stream();

		return stream.filter(
			fragmentConfigurationField -> Objects.equals(
				fragmentConfigurationField.getType(), "navigationMenuSelector")
		).collect(
			Collectors.toList()
		);
	}

	private void _replaceSiteNavigationMenuIds(
		JSONObject configurationValueJSONObject,
		PortletDataContext portletDataContext) {

		long siteNavigationMenuId = configurationValueJSONObject.getLong(
			"siteNavigationMenuId");

		long parentSiteNavigationMenuItemId =
			configurationValueJSONObject.getLong(
				"parentSiteNavigationMenuItemId");

		if (siteNavigationMenuId == 0) {
			Map<Long, Long> layoutNewPrimaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class.getName());

			configurationValueJSONObject.put(
				"parentSiteNavigationMenuItemId",
				layoutNewPrimaryKeys.getOrDefault(
					parentSiteNavigationMenuItemId, 0L));
		}
		else {
			Map<Long, Long> siteNavigationMenuNewPrimaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					SiteNavigationMenu.class.getName());

			configurationValueJSONObject.put(
				"siteNavigationMenuId",
				siteNavigationMenuNewPrimaryKeys.getOrDefault(
					siteNavigationMenuId, 0L));

			Map<Long, Long> siteNavigationMenuItemNewPrimaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					SiteNavigationMenuItem.class.getName());

			configurationValueJSONObject.put(
				"parentSiteNavigationMenuItemId",
				siteNavigationMenuItemNewPrimaryKeys.getOrDefault(
					parentSiteNavigationMenuItemId, 0L));
		}
	}

	private static final String _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}