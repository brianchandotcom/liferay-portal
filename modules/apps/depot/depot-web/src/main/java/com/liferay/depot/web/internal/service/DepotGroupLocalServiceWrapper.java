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

package com.liferay.depot.web.internal.service;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.GroupServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ServiceWrapper.class)
public class DepotGroupLocalServiceWrapper extends GroupServiceWrapper {

	public DepotGroupLocalServiceWrapper() {
		super(null);
	}

	public DepotGroupLocalServiceWrapper(GroupService groupService) {
		super(groupService);
	}

	@Override
	public String getGroupDisplayURL(
			long groupId, boolean privateLayout, boolean secureConnection)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		if (group.isDepot()) {
			GroupPermissionUtil.check(
				GuestOrUserUtil.getPermissionChecker(), group,
				ActionKeys.UPDATE);

			String controlPanelFullURL = _portal.getControlPanelFullURL(
				groupId, DepotPortletKeys.DEPOT_ADMIN, null);

			DepotEntry depotEntry = _depotEntryLocalService.getGroupDepotEntry(
				group.getGroupId());

			String namespace = _portal.getPortletNamespace(
				DepotPortletKeys.DEPOT_ADMIN);

			controlPanelFullURL = _http.addParameter(
				controlPanelFullURL, namespace + "mvcRenderCommandName",
				"/depot/view_depot_dashboard");
			controlPanelFullURL = _http.addParameter(
				controlPanelFullURL, namespace + "depotEntryId",
				String.valueOf(depotEntry.getDepotEntryId()));

			return controlPanelFullURL;
		}

		return super.getGroupDisplayURL(
			groupId, privateLayout, secureConnection);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}