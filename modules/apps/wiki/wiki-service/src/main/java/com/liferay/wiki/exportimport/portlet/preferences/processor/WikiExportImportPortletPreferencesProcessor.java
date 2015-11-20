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

package com.liferay.wiki.exportimport.portlet.preferences.processor;

import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.capability.ReferencedStagedModelImporterCapability;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.display.template.exportimport.portlet.preferences.processor.PortletDisplayTemplateExportCapability;
import com.liferay.portlet.display.template.exportimport.portlet.preferences.processor.PortletDisplayTemplateImportCapability;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true, property = {"javax.portlet.name=" + WikiPortletKeys.WIKI},
	service = ExportImportPortletPreferencesProcessor.class
)
public class WikiExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.toList(
			new Capability[] {_portletDisplayTemplateExportCapability});
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.toList(
			new Capability[] {
				_portletDisplayTemplateImportCapability,
				_referencedStagedModelImporterCapability
			});
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		String portletId = portletDataContext.getPortletId();

		String hiddenNodeNames = portletPreferences.getValue(
			"hiddenNodes", null);

		for (String hiddenNodeName : StringUtil.split(hiddenNodeNames)) {
			WikiNode wikiNode = _wikiNodeLocalService.fetchNode(
				portletDataContext.getScopeGroupId(), hiddenNodeName);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, wikiNode);
		}

		String visibleNodeNames = portletPreferences.getValue(
			"visibleNodes", null);

		for (String visibleNodeName : StringUtil.split(visibleNodeNames)) {
			WikiNode wikiNode = _wikiNodeLocalService.fetchNode(
				portletDataContext.getScopeGroupId(), visibleNodeName);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, wikiNode);
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setPortletDisplayTemplateExportCapability(
		PortletDisplayTemplateExportCapability
			portletDisplayTemplateExportCapability) {

		_portletDisplayTemplateExportCapability =
			portletDisplayTemplateExportCapability;
	}

	@Reference(unbind = "-")
	protected void setPortletDisplayTemplateImportCapability(
		PortletDisplayTemplateImportCapability
			portletDisplayTemplateImportCapability) {

		_portletDisplayTemplateImportCapability =
			portletDisplayTemplateImportCapability;
	}

	@Reference(unbind = "-")
	protected void setReferencedStagedModelImporterCapability(
		ReferencedStagedModelImporterCapability
			referencedStagedModelImporterCapability) {

		_referencedStagedModelImporterCapability =
			referencedStagedModelImporterCapability;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	private volatile PortletDisplayTemplateExportCapability
		_portletDisplayTemplateExportCapability;
	private volatile PortletDisplayTemplateImportCapability
		_portletDisplayTemplateImportCapability;
	private volatile ReferencedStagedModelImporterCapability
		_referencedStagedModelImporterCapability;
	private volatile WikiNodeLocalService _wikiNodeLocalService;

}