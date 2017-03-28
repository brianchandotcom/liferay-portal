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

package com.liferay.document.library.web.internal.upload;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorUploadResponseHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLUploadResponseHandler.class)
public class DLUploadResponseHandler implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException pe)
		throws PortalException {

		return _itemSelectorUploadResponseHandler.onFailure(portletRequest, pe);
	}

	@Override
	public JSONObject onSuccess(
			PortletRequest portletRequest, FileEntry fileEntry)
		throws PortalException {

		JSONObject jsonObject = _itemSelectorUploadResponseHandler.onSuccess(
			portletRequest, fileEntry);

		JSONObject fileJSONObject = jsonObject.getJSONObject("file");

		fileJSONObject.put("url", _getURL(portletRequest, fileEntry));

		return jsonObject;
	}

	private String _getURL(PortletRequest portletRequest, FileEntry fileEntry) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return DLUtil.getPreviewURL(
				fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
				StringPool.BLANK);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get URL for file entry " +
						fileEntry.getFileEntryId(),
					pe);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLUploadResponseHandler.class);

	@Reference
	private ItemSelectorUploadResponseHandler
		_itemSelectorUploadResponseHandler;

}