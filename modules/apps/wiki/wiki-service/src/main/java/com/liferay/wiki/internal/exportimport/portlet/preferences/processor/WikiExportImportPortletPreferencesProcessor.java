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

import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true, property = "javax.portlet.name=" + WikiPortletKeys.WIKI,
	service = ExportImportPortletPreferencesProcessor.class
)
public class WikiExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.fromArray(
			_portletDisplayTemplateExporter,
			_wikiCommentsAndRatingsExporterImporterCapability);
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.fromArray(
			_wikiCommentsAndRatingsExporterImporterCapability,
			_referencedStagedModelImporter, _portletDisplayTemplateImporter);
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		if (!_exportImportHelper.isExportPortletData(portletDataContext) ||
			!portletDataContext.getBooleanParameter(
				_wikiPortletDataHandler.getNamespace(), "wiki-pages")) {

			return portletPreferences;
		}

		try {
			portletDataContext.addPortletPermissions(
				WikiConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(WikiPortletKeys.WIKI);
			pde.setType(PortletDataException.EXPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		try {
			String portletId = portletDataContext.getPortletId();

			ActionableDynamicQuery nodeActionableDynamicQuery =
				_wikiNodeLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			nodeActionableDynamicQuery.setPerformActionMethod(
				(WikiNode wikiNode) ->
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletId, wikiNode));

			nodeActionableDynamicQuery.performActions();

			ActionableDynamicQuery pageActionableDynamicQuery =
				_wikiPageLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			pageActionableDynamicQuery.setPerformActionMethod(
				(WikiPage wikiPage) ->
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, portletId, wikiPage));

			pageActionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(WikiPortletKeys.WIKI);
			pde.setType(PortletDataException.EXPORT_PORTLET_DATA);

			throw pde;
		}

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

		if (!portletDataContext.getBooleanParameter(
				_wikiPortletDataHandler.getNamespace(), "wiki-pages")) {

			return portletPreferences;
		}

		try {
			portletDataContext.importPortletPermissions(
				WikiConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(WikiPortletKeys.WIKI);
			pde.setType(PortletDataException.IMPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		Element nodesElement = portletDataContext.getImportDataGroupElement(
			WikiNode.class);

		List<Element> nodeElements = nodesElement.elements();

		for (Element nodeElement : nodeElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, nodeElement);
		}

		Element pagesElement = portletDataContext.getImportDataGroupElement(
			WikiPage.class);

		List<Element> pageElements = pagesElement.elements();

		for (Element pageElement : pageElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, pageElement);
		}

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

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletDataContext.getPortletId(), node);
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

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiExportImportPortletPreferencesProcessor.class);

	@Reference
	private ExportImportHelper _exportImportHelper;

	private GroupLocalService _groupLocalService;

	@Reference(target = "(name=PortletDisplayTemplateExporter)")
	private Capability _portletDisplayTemplateExporter;

	@Reference(target = "(name=PortletDisplayTemplateImporter)")
	private Capability _portletDisplayTemplateImporter;

	@Reference(target = "(name=ReferencedStagedModelImporter)")
	private Capability _referencedStagedModelImporter;

	@Reference
	private WikiCommentsAndRatingsExporterImporterCapability
		_wikiCommentsAndRatingsExporterImporterCapability;

	private WikiNodeLocalService _wikiNodeLocalService;
	private WikiPageLocalService _wikiPageLocalService;

	@Reference(target = "(javax.portlet.name=" + WikiPortletKeys.WIKI + ")")
	private PortletDataHandler _wikiPortletDataHandler;

}