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

package com.liferay.fragment.item.selector.web.internal.frontend.taglib.clay.servlet;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;

/**
 * @author Víctor Galán
 */
public class FragmentEntryVerticalCard implements VerticalCard {

	public FragmentEntryVerticalCard(FragmentEntry fragmentEntry) {
		_fragmentEntry = fragmentEntry;
	}

	@Override
	public String getCssClass() {
		return "card-interactive card-interactive-secondary selector-button";
	}

	@Override
	public String getIcon() {
		return _fragmentEntry.getIcon();
	}

	@Override
	public String getStickerCssClass() {
		return "text-white bg-orange";
	}

	@Override
	public String getStickerIcon() {
		return "forms";
	}

	@Override
	public String getTitle() {
		return _fragmentEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final FragmentEntry _fragmentEntry;

}