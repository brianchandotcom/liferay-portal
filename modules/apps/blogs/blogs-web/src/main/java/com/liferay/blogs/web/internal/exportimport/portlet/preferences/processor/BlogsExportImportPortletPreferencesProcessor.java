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

package com.liferay.blogs.web.internal.exportimport.portlet.preferences.processor;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true, property = "javax.portlet.name=" + BlogsPortletKeys.BLOGS,
	service = ExportImportPortletPreferencesProcessor.class
)
public class BlogsExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return ListUtil.fromArray(_blogsPortletDisplayTemplateExportCapability);
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.fromArray(_blogsPortletDisplayTemplateImportCapability);
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		if (!_exportImportHelper.isExportPortletData(portletDataContext) ||
			!portletDataContext.getBooleanParameter(
				_blogsPortletDataHandler.getNamespace(), "entries")) {

			return portletPreferences;
		}

		try {
			portletDataContext.addPortletPermissions(
				BlogsConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(BlogsPortletKeys.BLOGS);
			pde.setType(PortletDataException.EXPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		String portletId = portletDataContext.getPortletId();

		ActionableDynamicQuery actionableDynamicQuery =
			_blogsEntryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.setPerformActionMethod(
			(BlogsEntry blogsEntry) ->
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, portletId, blogsEntry));

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(BlogsPortletKeys.BLOGS);
			pde.setType(PortletDataException.EXPORT_PORTLET_DATA);

			throw pde;
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		if (!portletDataContext.getBooleanParameter(
				_blogsPortletDataHandler.getNamespace(), "entries")) {

			return portletPreferences;
		}

		try {
			portletDataContext.importPortletPermissions(
				BlogsConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(BlogsPortletKeys.BLOGS);
			pde.setType(PortletDataException.IMPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		Element entriesElement = portletDataContext.getImportDataGroupElement(
			BlogsEntry.class);

		List<Element> entryElements = entriesElement.elements();

		for (Element entryElement : entryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, entryElement);
		}

		Element friendlyURLEntriesElement =
			portletDataContext.getImportDataGroupElement(
				FriendlyURLEntry.class);

		List<Element> friendlyURLEntryElements =
			friendlyURLEntriesElement.elements();

		for (Element friendlyURLEntryElement : friendlyURLEntryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, friendlyURLEntryElement);
		}

		return portletPreferences;
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference(target = "(javax.portlet.name=" + BlogsPortletKeys.BLOGS + ")")
	private PortletDataHandler _blogsPortletDataHandler;

	@Reference(target = "(name=BlogsExportCapability)")
	private Capability _blogsPortletDisplayTemplateExportCapability;

	@Reference(target = "(name=BlogsImportCapability)")
	private Capability _blogsPortletDisplayTemplateImportCapability;

	@Reference
	private ExportImportHelper _exportImportHelper;

}