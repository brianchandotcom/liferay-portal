/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.web.internal.upload.DLUploadFileEntryHandler;
import com.liferay.item.selector.ItemSelectorUploadResponseHandler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadHandler;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/image_editor",
		"mvc.command.name=/document_library/upload_file_entry"
	},
	service = MVCActionCommand.class
)
public class UploadFileEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		_uploadHandler.upload(
			_dlUploadFileEntryHandler, _dlUploadResponseHandler, actionRequest,
			actionResponse);
	}

	@Reference
	private DLUploadFileEntryHandler _dlUploadFileEntryHandler;

	private final DLUploadResponseHandler _dlUploadResponseHandler =
		new DLUploadResponseHandler();

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ItemSelectorUploadResponseHandler
		_itemSelectorUploadResponseHandler;

	@Reference
	private UploadHandler _uploadHandler;

	private class DLUploadResponseHandler implements UploadResponseHandler {

		@Override
		public JSONObject onFailure(
				PortletRequest portletRequest, PortalException portalException)
			throws PortalException {

			return _itemSelectorUploadResponseHandler.onFailure(
				portletRequest, portalException);
		}

		@Override
		public JSONObject onSuccess(
				UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
			throws PortalException {

			JSONObject jsonObject =
				_itemSelectorUploadResponseHandler.onSuccess(
					uploadPortletRequest, fileEntry);

			JSONObject fileJSONObject = jsonObject.getJSONObject("file");

			fileJSONObject.put("url", _getURL(uploadPortletRequest, fileEntry));

			return jsonObject;
		}

		private String _getURL(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry) {

			try {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)uploadPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return _dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
					StringPool.BLANK);
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get URL for file entry " +
							fileEntry.getFileEntryId(),
						portalException);
				}
			}

			return StringPool.BLANK;
		}

		private final Log _log = LogFactoryUtil.getLog(
			DLUploadResponseHandler.class);

	}

}