/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/copy_file_shortcut"
	},
	service = MVCActionCommand.class
)
public class CopyFileShortcutMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		try {
			_copyFileShortcut(actionRequest, actionResponse);
		}
		catch (IOException ioException) {
			_log.error(ioException);

			throw new PortalException(ioException);
		}
	}

	private void _checkDestinationRepository(
			long repositoryId, long fileShortcutId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(repositoryId);

		if (group.isStaged() && !group.isStagingGroup()) {
			throw new PortalException(
				"cannot-copy-file-shortcuts-to-the-live-version-of-a-group");
		}

		FileShortcut fileShortcut = _dlAppService.getFileShortcut(
			fileShortcutId);

		long sourceGroupId = fileShortcut.getGroupId();

		Group sourceGroup = _groupLocalService.getGroup(sourceGroupId);

		if (group.isDepot() ^ sourceGroup.isDepot()) {
			long[] groupIds = null;

			if (group.isDepot()) {
				groupIds =
					_siteConnectedGroupGroupProvider.
						getCurrentAndAncestorSiteAndDepotGroupIds(
							sourceGroup.getGroupId());
			}
			else {
				groupIds =
					_siteConnectedGroupGroupProvider.
						getCurrentAndAncestorSiteAndDepotGroupIds(
							group.getGroupId());
			}

			if (ArrayUtil.isEmpty(groupIds) ||
				!ArrayUtil.contains(groupIds, sourceGroupId)) {

				throw new PortalException(
					"the-item-is-not-copied-because-the-site-and-asset-" +
						"library-are-not-connected");
			}
		}
	}

	private void _copyFileShortcut(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileShortcutId = ParamUtil.getLong(
			actionRequest, "fileShortcutId");
		long destinationFolderId = ParamUtil.getLong(
			actionRequest, "destinationFolderId");
		long destinationRepositoryId = ParamUtil.getLong(
			actionRequest, "destinationRepositoryId");

		try {
			_checkDestinationRepository(
				destinationRepositoryId, fileShortcutId);

			_dlAppService.copyFileShortcut(
				fileShortcutId, destinationFolderId, destinationRepositoryId,
				ServiceContextFactory.getInstance(
					DLFileShortcut.class.getName(), actionRequest));

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, _jsonFactory.createJSONObject());
		}
		catch (PortalException portalException) {
			String errorMessage = themeDisplay.translate(
				portalException.getMessage());

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("errorMessage", errorMessage));

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyFileShortcutMVCActionCommand.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SiteConnectedGroupGroupProvider _siteConnectedGroupGroupProvider;

}