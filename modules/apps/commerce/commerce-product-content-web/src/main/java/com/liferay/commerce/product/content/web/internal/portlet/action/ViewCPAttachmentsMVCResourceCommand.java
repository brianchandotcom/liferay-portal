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

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_CONTENT_WEB,
		"mvc.command.name=/cp_content_web/view_cp_attachments"
	},
	service = MVCResourceCommand.class
)
public class ViewCPAttachmentsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	public void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		int type = ParamUtil.getInteger(
			resourceRequest, "type", CPAttachmentFileEntryConstants.TYPE_IMAGE);

		String ddmFormValues = ParamUtil.getString(
			resourceRequest, "ddmFormValues");

		long cpDefinitionId = ParamUtil.getLong(
			resourceRequest, "cpDefinitionId");

		CommerceContext commerceContext =
			(CommerceContext)resourceRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		try {
			long commerceAccountId = CommerceUtil.getCommerceAccountId(
				commerceContext);

			List<CPAttachmentFileEntry> cpAttachmentFileEntries =
				_cpInstanceHelper.getCPAttachmentFileEntries(
					commerceAccountId,
					commerceContext.getCommerceChannelGroupId(), cpDefinitionId,
					ddmFormValues, type);

			for (CPAttachmentFileEntry cpAttachmentFileEntry :
					cpAttachmentFileEntries) {

				JSONObject jsonObject = _jsonFactory.createJSONObject();

				jsonObject.put(
					"cpAttachmentFileEntryId",
					cpAttachmentFileEntry.getCPAttachmentFileEntryId());

				String attachmentURL = _commerceMediaResolver.getDownloadURL(
					commerceAccountId,
					cpAttachmentFileEntry.getCPAttachmentFileEntryId());

				jsonObject.put("url", attachmentURL);

				jsonArray.put(jsonObject);
			}

			if (cpAttachmentFileEntries.isEmpty()) {
				Company company = _portal.getCompany(resourceRequest);

				JSONObject jsonObject = _jsonFactory.createJSONObject();

				String attachmentURL = _commerceMediaResolver.getDefaultURL(
					company.getGroupId());

				jsonObject.put("url", attachmentURL);

				jsonArray.put(jsonObject);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonArray);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewCPAttachmentsMVCResourceCommand.class);

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}