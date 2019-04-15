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

package com.liferay.document.library.web.internal.lar;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletPreferences;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Sampsa Sohlman
 * @author Máté Thurzó
 * @author Zsolt Berentey
 * @author Gergely Mathe
 */
@Component(
	property = "javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
	service = PortletDataHandler.class
)
public class DLPortletDataHandler extends BasePortletDataHandler {

	@Override
	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return _dlAdminPortletDataHandler.deleteData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return _dlAdminPortletDataHandler.exportData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	public String getNamespace() {
		return _dlAdminPortletDataHandler.getNamespace();
	}

	@Override
	public String getSchemaVersion() {
		return _dlAdminPortletDataHandler.getSchemaVersion();
	}

	@Override
	public String getServiceName() {
		return _dlAdminPortletDataHandler.getServiceName();
	}

	@Override
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		return _dlAdminPortletDataHandler.importData(
			portletDataContext, portletId, portletPreferences, data);
	}

	@Override
	public boolean isSupportsDataStrategyMirrorWithOverwriting() {
		return _dlAdminPortletDataHandler.
			isSupportsDataStrategyMirrorWithOverwriting();
	}

	@Override
	public void prepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		_dlAdminPortletDataHandler.prepareManifestSummary(
			portletDataContext, portletPreferences);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataLocalized(true);
		setDataPortletPreferences("rootFolderId");
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DLFileEntryType.class),
			new StagedModelType(DLFileShortcut.class),
			new StagedModelType(DLFileEntryConstants.getClassName()),
			new StagedModelType(DLFolderConstants.getClassName()),
			new StagedModelType(
				Repository.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL));
		setExportControls(
			new PortletDataHandlerBoolean(
				getNamespace(), "repositories", true, false, null,
				Repository.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL),
			new PortletDataHandlerBoolean(
				getNamespace(), "folders", true, false, null,
				DLFolderConstants.getClassName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "documents", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						getNamespace(), "previews-and-thumbnails")
				},
				DLFileEntryConstants.getClassName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "document-types", true, false, null,
				DLFileEntryType.class.getName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "shortcuts", true, false, null,
				DLFileShortcutConstants.getClassName()));
		setPublishToLiveByDefault(PropsValues.DL_PUBLISH_TO_LIVE_BY_DEFAULT);
		setRank(90);
		setStagingControls(getExportControls());
	}

	@Reference(
		target = "(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN + ")"
	)
	private PortletDataHandler _dlAdminPortletDataHandler;

}