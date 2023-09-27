/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
		"mvc.command.name=/document_library/copy_file_entry"
	},
	service = MVCActionCommand.class
)
public class CopyFileEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		try {
			_copyFileEntry(actionRequest, actionResponse);
		}
		catch (IOException ioException) {
			_log.error(ioException);

			throw new PortalException(ioException);
		}
	}

	private void _checkDestinationGroup(
			long fileEntryId, Group group, long[] groupIds)
		throws PortalException {

		if (group.isStaged() && !group.isStagingGroup()) {
			throw new PortalException(
				"cannot-copy-file-entries-to-the-live-version-of-a-group");
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		long sourceGroupId = fileEntry.getGroupId();

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

	private void _copyFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		long destinationFolderId = ParamUtil.getLong(
			actionRequest, "destinationFolderId");
		long destinationRepositoryId = ParamUtil.getLong(
			actionRequest, "destinationRepositoryId");

		try {
			Group group = _groupLocalService.getGroup(destinationRepositoryId);

			long[] groupIds =
				_siteConnectedGroupGroupProvider.
					getCurrentAndAncestorSiteAndDepotGroupIds(
						group.getGroupId());

			_checkDestinationGroup(fileEntryId, group, groupIds);

			_dlAppService.copyFileEntry(
				fileEntryId, destinationFolderId, destinationRepositoryId,
				_getFileEntryTypeId(destinationRepositoryId, fileEntryId),
				groupIds,
				ServiceContextFactory.getInstance(
					DLFileEntry.class.getName(), actionRequest));

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

	private long _getFileEntryTypeId(long groupId, long fileEntryId)
		throws PortalException {

		long[] groupIds =
			_siteConnectedGroupGroupProvider.
				getCurrentAndAncestorSiteAndDepotGroupIds(groupId, true);

		if (ArrayUtil.isEmpty(groupIds)) {
			return DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT;
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (ArrayUtil.contains(groupIds, fileEntry.getGroupId())) {
			return dlFileEntry.getFileEntryTypeId();
		}

		DLFileEntryType fileEntryType =
			_dlFileEntryTypeService.getFileEntryType(
				dlFileEntry.getFileEntryTypeId());

		if (ArrayUtil.contains(groupIds, fileEntryType.getGroupId())) {
			return dlFileEntry.getFileEntryTypeId();
		}

		return DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyFileEntryMVCActionCommand.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryTypeService _dlFileEntryTypeService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SiteConnectedGroupGroupProvider _siteConnectedGroupGroupProvider;

}