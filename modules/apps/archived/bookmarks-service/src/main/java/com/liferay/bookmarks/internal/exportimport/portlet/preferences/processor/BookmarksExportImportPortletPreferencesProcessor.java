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

package com.liferay.bookmarks.internal.exportimport.portlet.preferences.processor;

import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS,
	service = ExportImportPortletPreferencesProcessor.class
)
public class BookmarksExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return null;
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return null;
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		if (!_exportImportHelper.isExportPortletData(portletDataContext)) {
			return portletPreferences;
		}

		try {
			portletDataContext.addPortletPermissions(
				BookmarksConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(BookmarksPortletKeys.BOOKMARKS);
			pde.setType(PortletDataException.EXPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		try {
			String namespace = _bookmarksPortletDataHandler.getNamespace();

			String portletId = portletDataContext.getPortletId();

			if (portletDataContext.getBooleanParameter(namespace, "folders")) {
				ExportActionableDynamicQuery folderActionableDynamicQuery =
					_bookmarksFolderStagedModelRepository.
						getExportActionableDynamicQuery(portletDataContext);

				folderActionableDynamicQuery.setPerformActionMethod(
					(BookmarksFolder bookmarksFolder) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, bookmarksFolder));

				folderActionableDynamicQuery.performActions();
			}

			if (portletDataContext.getBooleanParameter(namespace, "entries")) {
				ActionableDynamicQuery entryActionableDynamicQuery =
					_bookmarksEntryStagedModelRepository.
						getExportActionableDynamicQuery(portletDataContext);

				entryActionableDynamicQuery.setPerformActionMethod(
					(BookmarksEntry bookmarksEntry) ->
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, portletId, bookmarksEntry));

				entryActionableDynamicQuery.performActions();
			}
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(BookmarksPortletKeys.BOOKMARKS);
			pde.setType(PortletDataException.EXPORT_PORTLET_DATA);

			throw pde;
		}

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return portletPreferences;
		}

		BookmarksFolder folder =
			_bookmarksFolderLocalService.fetchBookmarksFolder(rootFolderId);

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(),
			portletDataContext.getPortletId());

		portletDataContext.addReferenceElement(
			portlet, portletDataContext.getExportDataRootElement(), folder,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			!portletDataContext.getBooleanParameter(
				_bookmarksPortletDataHandler.getNamespace(), "entries"));

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.importPortletPermissions(
				BookmarksConstants.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			PortletDataException pde = new PortletDataException(pe);

			pde.setPortletId(BookmarksPortletKeys.BOOKMARKS);
			pde.setType(PortletDataException.IMPORT_PORTLET_PERMISSIONS);

			throw pde;
		}

		String namespace = _bookmarksPortletDataHandler.getNamespace();

		if (portletDataContext.getBooleanParameter(namespace, "folders")) {
			Element foldersElement =
				portletDataContext.getImportDataGroupElement(
					BookmarksFolder.class);

			List<Element> folderElements = foldersElement.elements();

			for (Element folderElement : folderElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, folderElement);
			}
		}

		if (portletDataContext.getBooleanParameter(namespace, "entries")) {
			Element entriesElement =
				portletDataContext.getImportDataGroupElement(
					BookmarksEntry.class);

			List<Element> entryElements = entriesElement.elements();

			for (Element entryElement : entryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, entryElement);
			}
		}

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId <= 0) {
			return portletPreferences;
		}

		String rootFolderPath = ExportImportPathUtil.getModelPath(
			portletDataContext, BookmarksFolder.class.getName(), rootFolderId);

		BookmarksFolder folder =
			(BookmarksFolder)portletDataContext.getZipEntryAsObject(
				rootFolderPath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, folder);

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		rootFolderId = MapUtil.getLong(folderIds, rootFolderId, rootFolderId);

		try {
			portletPreferences.setValue(
				"rootFolderId", String.valueOf(rootFolderId));
		}
		catch (ReadOnlyException roe) {
			throw new PortletDataException(
				"Unable to update preference \"rootFolderId\"", roe);
		}

		return portletPreferences;
	}

	@Reference(
		target = "(model.class.name=com.liferay.bookmarks.model.BookmarksEntry)"
	)
	private StagedModelRepository<BookmarksEntry>
		_bookmarksEntryStagedModelRepository;

	@Reference
	private BookmarksFolderLocalService _bookmarksFolderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.bookmarks.model.BookmarksFolder)"
	)
	private StagedModelRepository<BookmarksFolder>
		_bookmarksFolderStagedModelRepository;

	@Reference(
		target = "(javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS + ")"
	)
	private PortletDataHandler _bookmarksPortletDataHandler;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private PortletLocalService _portletLocalService;

}