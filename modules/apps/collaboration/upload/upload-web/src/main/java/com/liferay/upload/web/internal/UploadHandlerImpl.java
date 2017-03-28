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

package com.liferay.upload.web.internal;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.upload.UploadHandler;
import com.liferay.upload.UploadResponseHandler;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 * @author Adolfo Pérez
 * @author Roberto Díaz
 * @author Alejandro Tardín
 */
@Component
public class UploadHandlerImpl implements UploadHandler {

	@Override
	public void upload(
			UploadFileEntryHandler uploadFileEntryHandler,
			UploadResponseHandler uploadResponseHandler,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		try {
			JSONObject responseJSONObject = _generateResponse(
				uploadFileEntryHandler, uploadResponseHandler, portletRequest);

			JSONPortletResponseUtil.writeJSON(
				portletRequest, portletResponse, responseJSONObject);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private JSONObject _generateResponse(
			UploadFileEntryHandler uploadFileEntryHandler,
			UploadResponseHandler uploadResponseHandler,
			PortletRequest portletRequest)
		throws IOException, PortalException {

		try {
			UploadPortletRequest uploadPortletRequest =
				_getUploadPortletRequest(portletRequest);

			FileEntry fileEntry = uploadFileEntryHandler.upload(
				uploadPortletRequest);

			return uploadResponseHandler.onSuccess(portletRequest, fileEntry);
		}
		catch (PortalException pe) {
			return uploadResponseHandler.onFailure(portletRequest, pe);
		}
	}

	private UploadPortletRequest _getUploadPortletRequest(
			PortletRequest portletRequest)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		UploadException uploadException =
			(UploadException)portletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable cause = uploadException.getCause();

			if (uploadException.isExceededFileSizeLimit()) {
				throw new FileSizeException(cause);
			}

			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException(cause);
			}

			if (uploadException.isExceededUploadRequestSizeLimit()) {
				throw new UploadRequestSizeException(cause);
			}

			throw new PortalException(cause);
		}

		return uploadPortletRequest;
	}

}