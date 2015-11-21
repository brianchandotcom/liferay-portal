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

package com.liferay.document.library.web.search;

import com.liferay.portal.kernel.dao.search.RowMover;
import com.liferay.portal.kernel.dao.search.RowMoverDropTarget;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portlet.admin.util.PortalProductMenuApplicationType;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author Chema Balsas
 */
public class EntriesMover extends RowMover {

	public EntriesMover(long scopeGroupId) throws PortalException {
		RowMoverDropTarget moveToFolderRowMoverDropTarget = new RowMoverDropTarget();

		moveToFolderRowMoverDropTarget.setAction("move-to-folder");
		moveToFolderRowMoverDropTarget.setActiveCssClass("active");
		moveToFolderRowMoverDropTarget.setSelector("[data-folder=\"true\"]");

		addRowMoverDropTarget(moveToFolderRowMoverDropTarget);

		if (TrashUtil.isTrashEnabled(scopeGroupId)) {
			RowMoverDropTarget moveToTrashRowMoverDropTarget = new RowMoverDropTarget();

			moveToTrashRowMoverDropTarget.setAction("move-to-trash");
			moveToTrashRowMoverDropTarget.setActiveCssClass("active");
			moveToTrashRowMoverDropTarget.setContainer("body");
			moveToTrashRowMoverDropTarget.setInfoCssClass("active");

			String productMenuPortletId = PortletProviderUtil.getPortletId(
				PortalProductMenuApplicationType.ProductMenu.CLASS_NAME,
				PortletProvider.Action.VIEW);

			String trashPortletId = PortletProviderUtil.getPortletId(
				TrashEntry.class.getName(), PortletProvider.Action.VIEW);

			moveToTrashRowMoverDropTarget.setSelector(
				"#_" + productMenuPortletId + "_portlet_" + trashPortletId);

			addRowMoverDropTarget(moveToTrashRowMoverDropTarget);
		}
	}

}