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

package com.liferay.message.boards.web.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.model.MBThreadFlag;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Daniel Kocsis
 * @author Gergely Mathe
 */
@Component(
	property = "javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
	service = PortletDataHandler.class
)
public class MBPortletDataHandler extends BasePortletDataHandler {

	@Override
	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return _mbAdminPortletDataHandler.deleteData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return _mbAdminPortletDataHandler.exportData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	public String getNamespace() {
		return _mbAdminPortletDataHandler.getNamespace();
	}

	@Override
	public String getSchemaVersion() {
		return _mbAdminPortletDataHandler.getSchemaVersion();
	}

	@Override
	public String getServiceName() {
		return _mbAdminPortletDataHandler.getServiceName();
	}

	@Override
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		return _mbAdminPortletDataHandler.importData(
			portletDataContext, portletId, portletPreferences, data);
	}

	@Override
	public void prepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		_mbAdminPortletDataHandler.prepareManifestSummary(
			portletDataContext, portletPreferences);
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(MBBan.class),
			new StagedModelType(MBCategory.class),
			new StagedModelType(MBMessage.class),
			new StagedModelType(MBThread.class),
			new StagedModelType(MBThreadFlag.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				getNamespace(), "categories", true, false, null,
				MBCategory.class.getName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "messages", true, false, null,
				MBMessage.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL),
			new PortletDataHandlerBoolean(
				getNamespace(), "thread-flags", true, false, null,
				MBThreadFlag.class.getName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "user-bans", true, false, null,
				MBBan.class.getName()));
		setPublishToLiveByDefault(
			PropsValues.MESSAGE_BOARDS_PUBLISH_TO_LIVE_BY_DEFAULT);
		setStagingControls(getExportControls());
	}

	@Reference(
		target = "(javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN + ")"
	)
	private PortletDataHandler _mbAdminPortletDataHandler;

}