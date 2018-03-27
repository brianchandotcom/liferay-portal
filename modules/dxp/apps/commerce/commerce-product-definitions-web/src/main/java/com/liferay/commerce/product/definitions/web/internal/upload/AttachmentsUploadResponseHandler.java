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

package com.liferay.commerce.product.definitions.web.internal.upload;

import com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration;
import com.liferay.commerce.product.exception.CPAttachmentFileEntryNameException;
import com.liferay.commerce.product.exception.CPAttachmentFileEntrySizeException;
import com.liferay.item.selector.ItemSelectorUploadResponseHandler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.upload.UploadResponseHandler;

import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = AttachmentsUploadResponseHandler.class
)
public class AttachmentsUploadResponseHandler implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException pe)
		throws PortalException {

		JSONObject jsonObject = _itemSelectorUploadResponseHandler.onFailure(
			portletRequest, pe);

		if (pe instanceof CPAttachmentFileEntryNameException ||
			pe instanceof CPAttachmentFileEntrySizeException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			if (pe instanceof CPAttachmentFileEntryNameException) {
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;

				errorMessage = StringUtil.merge(
					_attachmentsConfiguration.imageExtensions());
			}
			else if (pe instanceof CPAttachmentFileEntrySizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}

			JSONObject errorJSONObject = _jsonFactory.createJSONObject();

			errorJSONObject.put("errorType", errorType);
			errorJSONObject.put("message", errorMessage);

			jsonObject.put("error", errorJSONObject);
		}
		else {
			throw pe;
		}

		return jsonObject;
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		return _itemSelectorUploadResponseHandler.onSuccess(
			uploadPortletRequest, fileEntry);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_attachmentsConfiguration = ConfigurableUtil.createConfigurable(
			AttachmentsConfiguration.class, properties);
	}

	private volatile AttachmentsConfiguration _attachmentsConfiguration;

	@Reference
	private ItemSelectorUploadResponseHandler
		_itemSelectorUploadResponseHandler;

	@Reference
	private JSONFactory _jsonFactory;

}