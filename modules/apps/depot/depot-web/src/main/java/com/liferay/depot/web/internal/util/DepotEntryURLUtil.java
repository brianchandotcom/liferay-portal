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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public class DepotEntryURLUtil {

	public static ActionURL getAddDepotEntryActionURL(
		String redirect, LiferayPortletResponse liferayPortletResponse) {

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse
		).setActionName(
			"/depot/add_depot_entry"
		).setRedirect(
			redirect
		).buildActionURL();
	}

	public static ActionURL getDeleteDepotEntryActionURL(
		long depotEntryId, String redirect,
		LiferayPortletResponse liferayPortletResponse) {

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse
		).setActionName(
			"/depot/delete_depot_entry"
		).setRedirect(
			redirect
		).setParameter(
			"depotEntryId", depotEntryId
		).buildActionURL();
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

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse
		).setActionName(
			"/depot/disconnect_depot_entry"
		).setRedirect(
			redirect
		).setParameter(
			"depotEntryGroupRelId", depotEntryGroupRelId
		).buildActionURL();
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
		).buildPortletURL();
	}

	public static ActionURL getUpdateDDMStructuresAvailableActionURL(
		long depotEntryGroupRelId, boolean ddmStructuresAvailable,
		String redirect, LiferayPortletResponse liferayPortletResponse) {

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse
		).setActionName(
			"/depot/update_ddm_structures_available"
		).setRedirect(
			redirect
		).setParameter(
			"ddmStructuresAvailable", ddmStructuresAvailable
		).setParameter(
			"depotEntryGroupRelId", depotEntryGroupRelId
		).buildActionURL();
	}

	public static ActionURL getUpdateSearchableActionURL(
		long depotEntryGroupRelId, boolean searchable, String redirect,
		LiferayPortletResponse liferayPortletResponse) {

		return PortletURLBuilder.createActionURL(
			liferayPortletResponse
		).setActionName(
			"/depot/update_searchable"
		).setRedirect(
			redirect
		).setParameter(
			"depotEntryGroupRelId", depotEntryGroupRelId
		).setParameter(
			"searchable", searchable
		).buildActionURL();
	}

}