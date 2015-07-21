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

package com.liferay.item.selector.upload.web.display.context;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UploadableFileReturnType;
import com.liferay.item.selector.upload.web.ItemSelectorUploadView;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.util.PortletKeys;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

/**
 * @author Ambrín Chaudhary
 */
public class ItemSelectorUploadViewDisplayContext {

	public ItemSelectorUploadViewDisplayContext(
		ItemSelectorUploadView itemSelectorUploadView,
		String itemSelectedEventName) {

		_itemSelectorUploadView = itemSelectorUploadView;
		_itemSelectedEventName = itemSelectedEventName;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getTitle(Locale locale) {
		return _itemSelectorUploadView.getTitle(locale);
	}

	public ItemSelectorReturnType getUploadItemReturnType() {
		return new UploadableFileReturnType();
	}

	public PortletURL getUploadURL(
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			PortletKeys.BLOGS);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/blogs/upload_editor_image");

		return portletURL;
	}

	private final String _itemSelectedEventName;
	private final ItemSelectorUploadView _itemSelectorUploadView;

}