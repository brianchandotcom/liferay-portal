/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.upload.DDMUserPersonalFolderUploadFileEntryHandler;
import com.liferay.item.selector.ItemSelectorUploadResponseHandler;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadHandler;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"mvc.command.name=/dynamic_data_mapping_form/upload_ddm_user_personal_folder"
	},
	service = MVCActionCommand.class
)
public class UploadDDMUserPersonalFolderMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_ddmUserPersonalFolderUploadFileEntryHandler,
			_ddmUserPersonalFolderUploadResponseHandler, actionRequest,
			actionResponse);
	}

	@Reference
	private DDMUserPersonalFolderUploadFileEntryHandler
		_ddmUserPersonalFolderUploadFileEntryHandler;

	private final DDMUserPersonalFolderUploadResponseHandler
		_ddmUserPersonalFolderUploadResponseHandler =
			new DDMUserPersonalFolderUploadResponseHandler();

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ItemSelectorUploadResponseHandler
		_itemSelectorUploadResponseHandler;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UploadHandler _uploadHandler;

	private class DDMUserPersonalFolderUploadResponseHandler
		implements UploadResponseHandler {

		@Override
		public JSONObject onFailure(
				PortletRequest portletRequest, PortalException portalException)
			throws PortalException {

			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			if (portalException instanceof
					ObjectEntryValuesException.InvalidFileExtension) {

				return JSONUtil.put(
					"error",
					() -> {
						ObjectFieldSetting objectFieldSetting =
							_objectFieldSettingLocalService.
								fetchObjectFieldSetting(
									ParamUtil.getLong(
										portletRequest, "objectFieldId"),
									"acceptedFileExtensions");

						return JSONUtil.put(
							"errorType",
							ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION
						).put(
							"message", objectFieldSetting.getValue()
						);
					}
				).put(
					"success", Boolean.FALSE
				);
			}

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

			if (SessionMessages.contains(
					uploadPortletRequest,
					_portal.getPortletId(uploadPortletRequest) +
						SessionMessages.
							KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE)) {

				SessionMessages.clear(uploadPortletRequest);
			}

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
			DDMUserPersonalFolderUploadResponseHandler.class);

	}

}