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

package com.liferay.digital.signature.web.internal.portlet.action;

import com.liferay.digital.signature.constants.DigitalSignatureConstants;
import com.liferay.digital.signature.constants.DigitalSignaturePortletKeys;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Keven Leone
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DigitalSignaturePortletKeys.COLLECT_DIGITAL_SIGNATURE,
		"mvc.command.name=/digital_signature/get_invalid_file_extensions"
	},
	service = MVCResourceCommand.class
)
public class GetInvalidFileExtensionsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_toJSONArray(
				ParamUtil.getLongValues(resourceRequest, "fileEntryIds")));
	}

	private JSONArray _toJSONArray(long[] fileEntryIds) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (long fileEntryId : fileEntryIds) {
			try {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
					fileEntryId);

				if (!ArrayUtil.contains(
						DigitalSignatureConstants.ALLOWED_FILE_EXTENSIONS,
						fileEntry.getExtension())) {

					jsonArray.put(
						JSONUtil.put(
							"fileEntryId", fileEntryId
						).put(
							"fileName", fileEntry.getTitle()
						));
				}
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetInvalidFileExtensionsMVCResourceCommand.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

}