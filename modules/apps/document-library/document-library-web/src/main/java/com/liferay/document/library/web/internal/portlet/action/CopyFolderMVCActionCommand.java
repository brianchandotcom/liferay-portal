/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

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
		"mvc.command.name=/document_library/copy_folder"
	},
	service = MVCActionCommand.class
)
public class CopyFolderMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		try {
			_copyFolder(actionRequest, actionResponse);
		}
		catch (IOException ioException) {
			_log.error(ioException);

			throw new PortalException(ioException);
		}
	}

	private void _checkDestinationGroup(
			Group group, long[] groupIds, long sourceGroupId)
		throws PortalException {

		if (group.isStaged() && !group.isStagingGroup()) {
			throw new PortalException(
				"cannot-copy-folders-to-the-live-version-of-a-group");
		}

		Group sourceGroup = _groupLocalService.getGroup(sourceGroupId);

		if (group.isDepot() ^ sourceGroup.isDepot()) {
			long[] connectedGroupIds = groupIds;

			if (group.isDepot()) {
				connectedGroupIds =
					_siteConnectedGroupGroupProvider.
						getCurrentAndAncestorSiteAndDepotGroupIds(
							sourceGroup.getGroupId());
			}

			if (ArrayUtil.isEmpty(connectedGroupIds) ||
				!ArrayUtil.contains(connectedGroupIds, sourceGroupId)) {

				throw new PortalException(
					"the-item-is-not-copied-because-the-site-and-asset-" +
						"library-are-not-connected");
			}
		}
	}

	private void _copyFolder(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long sourceRepositoryId = ParamUtil.getLong(
			actionRequest, "sourceRepositoryId");
		long sourceFolderId = ParamUtil.getLong(
			actionRequest, "sourceFolderId");
		long destinationRepositoryId = ParamUtil.getLong(
			actionRequest, "destinationRepositoryId");
		long destinationParentFolderId = ParamUtil.getLong(
			actionRequest, "destinationParentFolderId");

		try {
			Group group = _groupLocalService.getGroup(destinationRepositoryId);

			long[] groupIds =
				_siteConnectedGroupGroupProvider.
					getCurrentAndAncestorSiteAndDepotGroupIds(
						group.getGroupId());

			_checkDestinationGroup(group, groupIds, sourceRepositoryId);

			_dlAppService.copyFolder(
				sourceRepositoryId, sourceFolderId, destinationRepositoryId,
				destinationParentFolderId,
				_getFileEntryTypeIds(group.getGroupId(), sourceFolderId),
				groupIds,
				ServiceContextFactory.getInstance(
					DLFolder.class.getName(), actionRequest));

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

	private Map<Long, Long> _getFileEntryTypeIds(long groupId, long folderId)
		throws PortalException {

		long[] groupIds =
			_siteConnectedGroupGroupProvider.
				getCurrentAndAncestorSiteAndDepotGroupIds(groupId, true);

		if (ArrayUtil.isEmpty(groupIds)) {
			return new HashMap<>();
		}

		DLFolder folder = _dlFolderService.getFolder(folderId);

		return _dlFileEntryLocalService.getFileEntryTypeIds(
			folder.getCompanyId(), groupIds, folder.getTreePath());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyFolderMVCActionCommand.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFolderService _dlFolderService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SiteConnectedGroupGroupProvider _siteConnectedGroupGroupProvider;

}