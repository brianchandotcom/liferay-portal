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

package com.liferay.item.selector.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Alejandro Tardín
 */
public class ItemDescriptorVerticalCard extends BaseVerticalCard {

	public ItemDescriptorVerticalCard(
		BaseModel<?> baseModel,
		ItemSelectorViewDescriptor.ItemDescriptor itemDescriptor,
		RenderRequest renderRequest, RowChecker rowChecker) {

		super(baseModel, renderRequest, rowChecker);

		_itemDescriptor = itemDescriptor;
	}

	@Override
	public String getCssClass() {
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
	public String getInputValue() {
		return null;
	}

	@Override
	public List<LabelItem> getLabels() {
		if (_itemDescriptor.getStatus() == null) {
			return Collections.emptyList();
		}

		return LabelItemListBuilder.add(
			labelItem -> labelItem.setStatus(_itemDescriptor.getStatus())
		).build();
	}

	@Override
	public String getSubtitle() {
		return _itemDescriptor.getSubtitle(themeDisplay.getLocale());
	}

	@Override
	public String getTitle() {
		return _itemDescriptor.getTitle(themeDisplay.getLocale());
	}

	@Override
	public Boolean isFlushHorizontal() {
		return true;
	}

	private final ItemSelectorViewDescriptor.ItemDescriptor _itemDescriptor;

}