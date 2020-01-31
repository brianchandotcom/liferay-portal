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

package com.liferay.item.selector.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.item.selector.ItemSelectorViewDescriptor;

import javax.portlet.RenderRequest;

/**
 * @author Alejandro Tardín
 */
public class ItemDescriptorVerticalCard extends BaseVerticalCard {

	public ItemDescriptorVerticalCard(
		ItemSelectorViewDescriptor.ItemDescriptor itemDescriptor,
		RenderRequest renderRequest) {

		super(null, renderRequest, null);

		_itemDescriptor = itemDescriptor;
	}

	@Override
	public String getAspectRatioCssClasses() {
		return "aspect-ratio-item-center-middle " +
			"aspect-ratio-item-vertical-fluid";
	}

	@Override
	public String getElementClasses() {
		return "card-interactive card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		return _itemDescriptor.getIcon();
	}

	@Override
	public String getImageSrc() {
		return _itemDescriptor.getImageURL();
	}

	@Override
	public String getSubtitle() {
		return _itemDescriptor.getSubtitle(themeDisplay.getLocale());
	}

	@Override
	public String getTitle() {
		return _itemDescriptor.getTitle(themeDisplay.getLocale());
	}

	private final ItemSelectorViewDescriptor.ItemDescriptor _itemDescriptor;

}