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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryResponseCustomizer;

import java.io.IOException;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(property = "source=itemselector")
public class ItemSelectorUploadFileEntryResponseCustomizerImpl
	implements UploadFileEntryResponseCustomizer {

	@Override
	public void customize(
			FileEntry fileEntry, JSONObject jsonObject, PortletRequest request)
		throws IOException {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String returnType = ParamUtil.getString(request, "returnType");

			ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver =
				_itemSelectorReturnTypeResolverHandler.
					getItemSelectorReturnTypeResolver(
						returnType, FileEntry.class);

			if (itemSelectorReturnTypeResolver != null) {
				String resolvedValue = itemSelectorReturnTypeResolver.getValue(
					fileEntry, themeDisplay);

				jsonObject.put("resolvedValue", resolvedValue);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Reference
	private ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;

}