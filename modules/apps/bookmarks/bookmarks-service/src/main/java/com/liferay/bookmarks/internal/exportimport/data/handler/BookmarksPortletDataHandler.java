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

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 * @author Mate Thurzo
 * @author Daniel Kocsis
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS,
	service = PortletDataHandler.class
)
public class BookmarksPortletDataHandler extends BasePortletDataHandler {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             BookmarksAdminPortletDataHandler#NAMESPACE}
	 */
	@Deprecated
	public static final String NAMESPACE = "bookmarks";

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             BookmarksAdminPortletDataHandler#SCHEMA_VERSION}
	 */
	@Deprecated
	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getNamespace() {
		return _bookmarksAdminPortletDataHandler.getNamespace();
	}

	@Override
	public String getSchemaVersion() {
		return _bookmarksAdminPortletDataHandler.getSchemaVersion();
	}

	@Override
	public String getServiceName() {
		return _bookmarksAdminPortletDataHandler.getServiceName();
	}

	@Override
	public boolean validateSchemaVersion(String schemaVersion) {
		return _bookmarksAdminPortletDataHandler.validateSchemaVersion(
			schemaVersion);
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataPortletPreferences("rootFolderId");
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(BookmarksEntry.class),
			new StagedModelType(BookmarksFolder.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				getNamespace(), "folders", true, false, null,
				BookmarksFolder.class.getName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "entries", true, false, null,
				BookmarksEntry.class.getName()));
		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return _bookmarksAdminPortletDataHandler.doDeleteData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		return _bookmarksAdminPortletDataHandler.doExportData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		return _bookmarksAdminPortletDataHandler.doImportData(
			portletDataContext, portletId, portletPreferences, data);
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		_bookmarksAdminPortletDataHandler.doPrepareManifestSummary(
			portletDataContext, portletPreferences);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	private BookmarksAdminPortletDataHandler _bookmarksAdminPortletDataHandler;

}