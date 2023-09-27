/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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