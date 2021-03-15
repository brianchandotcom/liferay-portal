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

package com.liferay.depot.web.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public class DepotEntryURLUtil {

	public static ActionURL getAddDepotEntryActionURL(
		String redirect, LiferayPortletResponse liferayPortletResponse) {

		ActionURL addDepotEntryURL = liferayPortletResponse.createActionURL();

		addDepotEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/depot/add_depot_entry");
		addDepotEntryURL.setParameter("redirect", redirect);

		return addDepotEntryURL;
	}

	public static ActionURL getDeleteDepotEntryActionURL(
		long depotEntryId, String redirect,
		LiferayPortletResponse liferayPortletResponse) {

		ActionURL deleteDepotEntryActionURL =
			liferayPortletResponse.createActionURL();

		deleteDepotEntryActionURL.setParameter(
			ActionRequest.ACTION_NAME, "/depot/delete_depot_entry");
		deleteDepotEntryActionURL.setParameter("redirect", redirect);
		deleteDepotEntryActionURL.setParameter(
			"depotEntryId", String.valueOf(depotEntryId));

		return deleteDepotEntryActionURL;
	}

	public static String getDepotEntryPermissionsURL(
			DepotEntry depotEntry, LiferayPortletRequest liferayPortletRequest)
		throws Exception {

		Group group = depotEntry.getGroup();

		return PermissionsURLTag.doTag(
			StringPool.BLANK, DepotEntry.class.getName(), group.getName(), null,
			String.valueOf(depotEntry.getDepotEntryId()),
			LiferayWindowState.POP_UP.toString(), null,
			liferayPortletRequest.getHttpServletRequest());
	}

	public static ActionURL getDisconnectSiteActionURL(
		long depotEntryGroupRelId, String redirect,
		LiferayPortletResponse liferayPortletResponse) {

		ActionURL disconnectSiteActionURL =
			liferayPortletResponse.createActionURL();

		disconnectSiteActionURL.setParameter(
			ActionRequest.ACTION_NAME, "/depot/disconnect_depot_entry");
		disconnectSiteActionURL.setParameter("redirect", redirect);
		disconnectSiteActionURL.setParameter(
			"depotEntryGroupRelId", String.valueOf(depotEntryGroupRelId));

		return disconnectSiteActionURL;
	}

	public static PortletURL getEditDepotEntryPortletURL(
		DepotEntry depotEntry, String redirect,
		LiferayPortletRequest liferayPortletRequest) {

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				liferayPortletRequest, DepotPortletKeys.DEPOT_ADMIN,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/depot/edit_depot_entry"
		).setRedirect(
			redirect
		).setParameter(
			"depotEntryId", depotEntry.getDepotEntryId()
		).build();
	}

	public static ActionURL getUpdateDDMStructuresAvailableActionURL(
		long depotEntryGroupRelId, boolean ddmStructuresAvailable,
		String redirect, LiferayPortletResponse liferayPortletResponse) {

		ActionURL updateDDMStructuresAvailableActionURL =
			liferayPortletResponse.createActionURL();

		updateDDMStructuresAvailableActionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/depot/update_ddm_structures_available");
		updateDDMStructuresAvailableActionURL.setParameter(
			"redirect", redirect);
		updateDDMStructuresAvailableActionURL.setParameter(
			"depotEntryGroupRelId", String.valueOf(depotEntryGroupRelId));
		updateDDMStructuresAvailableActionURL.setParameter(
			"ddmStructuresAvailable", String.valueOf(ddmStructuresAvailable));

		return updateDDMStructuresAvailableActionURL;
	}

	public static ActionURL getUpdateSearchableActionURL(
		long depotEntryGroupRelId, boolean searchable, String redirect,
		LiferayPortletResponse liferayPortletResponse) {

		ActionURL updateSearchableActionURL =
			liferayPortletResponse.createActionURL();

		updateSearchableActionURL.setParameter(
			ActionRequest.ACTION_NAME, "/depot/update_searchable");
		updateSearchableActionURL.setParameter("redirect", redirect);
		updateSearchableActionURL.setParameter(
			"depotEntryGroupRelId", String.valueOf(depotEntryGroupRelId));
		updateSearchableActionURL.setParameter(
			"searchable", String.valueOf(searchable));

		return updateSearchableActionURL;
	}

}