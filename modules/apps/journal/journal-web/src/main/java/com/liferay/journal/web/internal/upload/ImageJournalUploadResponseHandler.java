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

package com.liferay.journal.web.internal.upload;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 * @author Alejandro Tardín
 */
@Component(service = ImageJournalUploadResponseHandler.class)
public class ImageJournalUploadResponseHandler
	implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException pe)
		throws PortalException {

		JSONObject jsonObject = _defaultUploadResponseHandler.onFailure(
			portletRequest, pe);

		if (pe instanceof ImageTypeException) {
			JSONObject errorJSONObject = JSONUtil.put(
				"errorType",
				ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION
			).put(
				"message", StringPool.BLANK
			);

			jsonObject.put("error", errorJSONObject);
		}

		return jsonObject;
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		JSONObject jsonObject = _defaultUploadResponseHandler.onSuccess(
			uploadPortletRequest, fileEntry);

		JSONObject fileJSONObject = jsonObject.getJSONObject("file");

		fileJSONObject.put("type", "journal");

		return jsonObject;
	}

	@Reference(target = "(upload.response.handler.system.default=true)")
	private UploadResponseHandler _defaultUploadResponseHandler;

}