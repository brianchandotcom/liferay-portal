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

package com.liferay.commerce.product.content.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_CONTENT_WEB,
		"mvc.command.name=viewCPAttachments"
	},
	service = MVCResourceCommand.class
)
public class ViewCPAttachmentsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	public void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		int type = ParamUtil.getInteger(
			resourceRequest, "type", CPAttachmentFileEntryConstants.TYPE_IMAGE);

		String ddmFormValues = ParamUtil.getString(
			resourceRequest, "ddmFormValues");

		long cpDefinitionId = ParamUtil.getLong(
			resourceRequest, "cpDefinitionId");

		try {
			List<CPAttachmentFileEntry> cpAttachmentFileEntries =
				_cpInstanceHelper.getCPAttachmentFileEntries(
					cpDefinitionId, ddmFormValues, type);

			for (CPAttachmentFileEntry cpAttachmentFileEntry :
					cpAttachmentFileEntries) {

				JSONObject jsonObject = _jsonFactory.createJSONObject();

				jsonObject.put(
					"cpAttachmentFileEntryId",
					cpAttachmentFileEntry.getCPAttachmentFileEntryId());

				FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

				String attachmentURL = DLUtil.getDownloadURL(
					fileEntry, fileEntry.getFileVersion(), themeDisplay,
					StringPool.BLANK);

				jsonObject.put("url", attachmentURL);

				jsonArray.put(jsonObject);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonArray);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewCPAttachmentsMVCResourceCommand.class);

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}