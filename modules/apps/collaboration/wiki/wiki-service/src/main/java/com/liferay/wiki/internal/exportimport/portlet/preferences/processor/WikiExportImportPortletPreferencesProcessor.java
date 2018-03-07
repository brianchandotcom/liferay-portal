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

package com.liferay.wiki.internal.exportimport.portlet.preferences.processor;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
		return ListUtil.toList(new Capability[] {_exportCapability});
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.toList(
			new Capability[] {_importCapability, _capability});
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		Group group = _groupLocalService.fetchGroup(
			portletDataContext.getGroupId());

		String hiddenNodeNames = portletPreferences.getValue(
			"hiddenNodes", null);

		for (String hiddenNodeName : StringUtil.split(hiddenNodeNames)) {
			exportNode(portletDataContext, group, hiddenNodeName);
		}

		String visibleNodeNames = portletPreferences.getValue(
			"visibleNodes", null);

		for (String visibleNodeName : StringUtil.split(visibleNodeNames)) {
			exportNode(portletDataContext, group, visibleNodeName);
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

	protected void exportNode(
			PortletDataContext portletDataContext, Group group, String nodeName)
		throws PortletDataException {

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!group.isStagedPortlet(portletDataContext.getPortletId())) {

			return;
		}

		WikiNode node = _wikiNodeLocalService.fetchNode(
			portletDataContext.getScopeGroupId(), nodeName);

		if (node == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to export referenced wiki node " + nodeName);
			}

			return;
		}

		String portletId = portletDataContext.getPortletId();

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, node);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiExportImportPortletPreferencesProcessor.class);

	@Reference(target = "(name=ReferencedStagedModelImporter)")
	private Capability _capability;

	@Reference(target = "(name=PortletDisplayTemplateExporter)")
	private Capability _exportCapability;

	private GroupLocalService _groupLocalService;

	@Reference(target = "(name=PortletDisplayTemplateImporter)")
	private Capability _importCapability;

	private WikiNodeLocalService _wikiNodeLocalService;

}