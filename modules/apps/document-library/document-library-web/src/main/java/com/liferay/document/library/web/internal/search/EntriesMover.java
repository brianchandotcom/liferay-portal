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

package com.liferay.document.library.web.internal.search;

import com.liferay.admin.kernel.util.PortalProductMenuApplicationType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.RowMover;
import com.liferay.portal.kernel.dao.search.RowMoverDropTarget;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.trash.model.TrashEntry;

/**
 * @author Chema Balsas
 */
public class EntriesMover extends RowMover {

	public EntriesMover(boolean trashEnabled) throws PortalException {
		RowMoverDropTarget moveToFolderRowMoverDropTarget =
			new RowMoverDropTarget();

		moveToFolderRowMoverDropTarget.setAction("move-to-folder");
		moveToFolderRowMoverDropTarget.setActiveCssClass("active");
		moveToFolderRowMoverDropTarget.setSelector("[data-folder=\"true\"]");

		addRowMoverDropTarget(moveToFolderRowMoverDropTarget);

		if (trashEnabled) {
			RowMoverDropTarget moveToTrashRowMoverDropTarget =
				new RowMoverDropTarget();

			moveToTrashRowMoverDropTarget.setAction("move-to-trash");
			moveToTrashRowMoverDropTarget.setActiveCssClass("bg-info");
			moveToTrashRowMoverDropTarget.setContainer("body");
			moveToTrashRowMoverDropTarget.setInfoCssClass("bg-primary");

			String productMenuPortletId = PortletProviderUtil.getPortletId(
				PortalProductMenuApplicationType.ProductMenu.CLASS_NAME,
				PortletProvider.Action.VIEW);

			String trashPortletId = PortletProviderUtil.getPortletId(
				TrashEntry.class.getName(), PortletProvider.Action.VIEW);

			moveToTrashRowMoverDropTarget.setSelector(
				StringBundler.concat(
					"#_", productMenuPortletId, "_portlet_", trashPortletId));

			addRowMoverDropTarget(moveToTrashRowMoverDropTarget);
		}
	}

}