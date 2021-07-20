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

package com.liferay.fragment.web.internal.servlet.taglib.clay;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseFragmentEntryVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public BaseFragmentEntryVerticalCard(
		FragmentEntry fragmentEntry, RenderRequest renderRequest,
		RowChecker rowChecker) {

		super(fragmentEntry, rowChecker);

		this.fragmentEntry = fragmentEntry;
		themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getIcon() {
		return fragmentEntry.getIcon();
	}

	@Override
	public String getImageSrc() {
		return fragmentEntry.getImagePreviewURL(themeDisplay);
	}

	@Override
	public String getInputName() {
		return rowChecker.getRowIds() + FragmentEntry.class.getSimpleName();
	}

	@Override
	public String getInputValue() {
		return String.valueOf(fragmentEntry.getFragmentEntryId());
	}

	@Override
	public String getStickerCssClass() {
		return "fragment-entry-sticker";
	}

	@Override
	public String getStickerIcon() {
		return getIcon();
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(fragmentEntry.getName());
	}

	protected final FragmentEntry fragmentEntry;
	protected final ThemeDisplay themeDisplay;

}