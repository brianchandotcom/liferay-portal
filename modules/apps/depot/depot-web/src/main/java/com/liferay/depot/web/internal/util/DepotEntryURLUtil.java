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

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.RenderURL;

/**
 * @author Alejandro Tardín
 */
public class DepotEntryURLUtil {

	public static ActionURL getAddDepotEntryActionURL(
		String redirect, LiferayPortletResponse liferayPortletResponse) {

		ActionURL addDepotURL = liferayPortletResponse.createActionURL();

		addDepotURL.setParameter(ActionRequest.ACTION_NAME, "/depot_entry/add");
		addDepotURL.setParameter("redirect", redirect);

		return addDepotURL;
	}

	public static ActionURL getEditDepotEntryActionURL(
		LiferayPortletResponse liferayPortletResponse) {

		ActionURL editDepotEntryActionURL =
			liferayPortletResponse.createActionURL();

		editDepotEntryActionURL.setParameter(
			ActionRequest.ACTION_NAME, "/depot_entry/edit");

		return editDepotEntryActionURL;
	}

	public static RenderURL getEditDepotEntryRenderURL(
		long depotEntryId, String redirect,
		LiferayPortletResponse liferayPortletResponse) {

		RenderURL editDepotEntryURL = liferayPortletResponse.createRenderURL();

		editDepotEntryURL.setParameter(
			"mvcRenderCommandName", "/depot_entry/edit");
		editDepotEntryURL.setParameter(
			"depotEntryId", String.valueOf(depotEntryId));

		editDepotEntryURL.setParameter("redirect", redirect);

		return editDepotEntryURL;
	}

}