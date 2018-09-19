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

package com.liferay.item.selector.web.internal.upload;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.ItemSelectorUploadResponseHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ItemSelectorUploadResponseHandler.class)
public class ItemSelectorUploadResponseHandlerImpl
	implements ItemSelectorUploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException pe)
		throws PortalException {

		return _defaultUploadResponseHandler.onFailure(portletRequest, pe);
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		JSONObject jsonObject = _defaultUploadResponseHandler.onSuccess(
			uploadPortletRequest, fileEntry);

		return _resolveValue(uploadPortletRequest, fileEntry, jsonObject);
	}

	private JSONObject _resolveValue(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry,
			JSONObject jsonObject)
		throws PortalException {

		String returnType = ParamUtil.getString(
			uploadPortletRequest, "returnType");

		ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver =
			_itemSelectorReturnTypeResolverHandler.
				getItemSelectorReturnTypeResolver(
					returnType, FileEntry.class.getName());

		if (itemSelectorReturnTypeResolver != null) {
			try {
				JSONObject fileJSONObject = jsonObject.getJSONObject("file");

				ThemeDisplay themeDisplay =
					(ThemeDisplay)uploadPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				String resolvedValue = itemSelectorReturnTypeResolver.getValue(
					fileEntry, themeDisplay);

				fileJSONObject.put("resolvedValue", resolvedValue);
			}
			catch (Exception e) {
				throw new PortalException(e);
			}
		}

		return jsonObject;
	}

	@Reference(target = "(upload.response.handler.system.default=true)")
	private UploadResponseHandler _defaultUploadResponseHandler;

	@Reference
	private ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;

}