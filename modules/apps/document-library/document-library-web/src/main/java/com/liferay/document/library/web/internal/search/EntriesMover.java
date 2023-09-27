/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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