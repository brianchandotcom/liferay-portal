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

package com.liferay.bookmarks.internal.exportimport.data.handler;

import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.portlet.data.handler.helper.PortletDataHandlerHelper;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 * @author Máté Thurzó
 * @author Daniel Kocsis
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS_ADMIN,
	service = PortletDataHandler.class
)
public class BookmarksAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String[] CLASS_NAMES = {
		BookmarksEntry.class.getName(), BookmarksFolder.class.getName()
	};

	public static final String NAMESPACE = "bookmarks";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public String getResourceName() {
		return BookmarksConstants.RESOURCE_NAME;
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getServiceName() {
		return BookmarksConstants.SERVICE_NAME;
	}

	@Override
	public boolean validateSchemaVersion(String schemaVersion) {
		return _portletDataHandlerHelper.validateSchemaVersion(
			schemaVersion, getSchemaVersion());
	}

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(BookmarksEntry.class),
			new StagedModelType(BookmarksFolder.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "folders", true, false, null,
				BookmarksFolder.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "entries", true, false, null,
				BookmarksEntry.class.getName()));
		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				BookmarksPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_bookmarksEntryStagedModelRepository.deleteStagedModels(
			portletDataContext);
		_bookmarksFolderStagedModelRepository.deleteStagedModels(
			portletDataContext);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(
			BookmarksConstants.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "folders")) {
			ExportActionableDynamicQuery folderActionableDynamicQuery =
				_bookmarksFolderStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			folderActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "entries")) {
			ActionableDynamicQuery entryActionableDynamicQuery =
				_bookmarksEntryStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			entryActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			BookmarksConstants.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "folders")) {
			Element foldersElement =
				portletDataContext.getImportDataGroupElement(
					BookmarksFolder.class);

			List<Element> folderElements = foldersElement.elements();

			for (Element folderElement : folderElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, folderElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "entries")) {
			Element entriesElement =
				portletDataContext.getImportDataGroupElement(
					BookmarksEntry.class);

			List<Element> entryElements = entriesElement.elements();

			for (Element entryElement : entryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, entryElement);
			}
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		if (ExportImportDateUtil.isRangeFromLastPublishDate(
				portletDataContext)) {

			_staging.populateLastPublishDateCounts(
				portletDataContext,
				new StagedModelType[] {
					new StagedModelType(BookmarksEntry.class.getName()),
					new StagedModelType(BookmarksFolder.class.getName())
				});

			return;
		}

		ActionableDynamicQuery entryExportActionableDynamicQuery =
			_bookmarksEntryStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		entryExportActionableDynamicQuery.performCount();

		ActionableDynamicQuery folderExportActionableDynamicQuery =
			_bookmarksFolderStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		folderExportActionableDynamicQuery.performCount();
	}

	@Reference(
		target = "(model.class.name=com.liferay.bookmarks.model.BookmarksEntry)"
	)
	private StagedModelRepository<BookmarksEntry>
		_bookmarksEntryStagedModelRepository;

	@Reference(
		target = "(model.class.name=com.liferay.bookmarks.model.BookmarksFolder)"
	)
	private StagedModelRepository<BookmarksFolder>
		_bookmarksFolderStagedModelRepository;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortletDataHandlerHelper _portletDataHandlerHelper;

	@Reference
	private Staging _staging;

}