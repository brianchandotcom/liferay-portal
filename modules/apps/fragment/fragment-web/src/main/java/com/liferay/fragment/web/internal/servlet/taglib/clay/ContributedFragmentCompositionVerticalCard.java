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

import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.servlet.taglib.util.ContributedFragmentCompositionActionDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class ContributedFragmentCompositionVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public ContributedFragmentCompositionVerticalCard(
		FragmentComposition fragmentComposition, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(fragmentComposition, rowChecker);

		_fragmentComposition = fragmentComposition;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ContributedFragmentCompositionActionDropdownItemsProvider
			contributedFragmentEntryActionDropdownItemsProvider =
				new ContributedFragmentCompositionActionDropdownItemsProvider(
					_fragmentComposition, _renderRequest, _renderResponse);

		try {
			return contributedFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return FragmentWebKeys.FRAGMENT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getImageSrc() {
		return _fragmentComposition.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public String getInputValue() {
		return _fragmentComposition.getFragmentCompositionKey();
	}

	@Override
	public String getStickerCssClass() {
		return "fragment-composition-sticker";
	}

	@Override
	public String getStickerIcon() {
		return "edit-layout";
	}

	@Override
	public String getTitle() {
		return _fragmentComposition.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContributedFragmentCompositionVerticalCard.class);

	private final FragmentComposition _fragmentComposition;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}