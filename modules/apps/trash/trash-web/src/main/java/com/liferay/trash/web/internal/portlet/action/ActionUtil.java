/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.web.internal.portlet.action;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.ResourceRequest;

import java.util.List;

/**
 * Provides a utility method to get Recycle Bin entries by row IDs from the
 * render request to render the Recycle Bin portlet's information panel.
 *
 * @author Jürgen Kappler
 */
public class ActionUtil {

	public static List<TrashEntry> getTrashEntries(ResourceRequest request)
		throws Exception {

		long[] trashEntryIds = ParamUtil.getLongValues(request, "rowIds");

		return TransformUtil.unsafeTransformToList(
			trashEntryIds,
			trashEntryId -> {
				TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
					trashEntryId);

				if (!hasViewPermission(
						trashEntry.getClassPK(), trashEntry.getGroupId(),
						request,
						TrashHandlerRegistryUtil.getTrashHandler(
							trashEntry.getClassName()))) {

					return null;
				}

				return trashEntry;
			});
	}

	public static boolean hasViewPermission(
			long classPK, long groupId, PortletRequest portletRequest,
			TrashHandler trashHandler)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if ((trashHandler == null) ||
			(groupId != themeDisplay.getScopeGroupId())) {

			return false;
		}

		return trashHandler.hasTrashPermission(
			themeDisplay.getPermissionChecker(), groupId, classPK,
			ActionKeys.VIEW);
	}

}