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

import com.liferay.fragment.constants.FragmentEntryTypeConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.servlet.taglib.util.FragmentEntryActionDropdownItems;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public FragmentEntryVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(baseModel, rowChecker);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_fragmentEntry = (FragmentEntry)baseModel;
		_request = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		FragmentEntryActionDropdownItems fragmentEntryActionDropdownItems =
			new FragmentEntryActionDropdownItems(
				_fragmentEntry, _renderRequest, _renderResponse);

		try {
			return fragmentEntryActionDropdownItems.getActionDropdownItems();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return FragmentWebKeys.FRAGMENT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		PortletURL editFragmentEntryURL = _renderResponse.createRenderURL();

		editFragmentEntryURL.setParameter(
			"mvcRenderCommandName", "/fragment/edit_fragment_entry");
		editFragmentEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		editFragmentEntryURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(_fragmentEntry.getFragmentCollectionId()));
		editFragmentEntryURL.setParameter(
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));

		return editFragmentEntryURL.toString();
	}

	@Override
	public String getIcon() {
		return "code";
	}

	@Override
	public String getImageSrc() {
		return _fragmentEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public List<LabelItem> getLabels() {
		return new LabelItemList() {
			{
				add(
					labelItem -> {
						labelItem.setStatus(_fragmentEntry.getStatus());
					});
			}
		};
	}

	@Override
	public String getStickerCssClass() {
		if (_fragmentEntry.getType() ==
				FragmentEntryTypeConstants.TYPE_ELEMENT) {

			return "file-icon-color-4";
		}

		return "file-icon-color-2";
	}

	@Override
	public String getStickerIcon() {
		return "cards";
	}

	@Override
	public String getSubtitle() {
		Date statusDate = _fragmentEntry.getStatusDate();

		String statusDateDescription = LanguageUtil.getTimeDescription(
			_request, System.currentTimeMillis() - statusDate.getTime(), true);

		return LanguageUtil.format(_request, "x-ago", statusDateDescription);
	}

	@Override
	public String getTitle() {
		return _fragmentEntry.getName();
	}

	private final FragmentEntry _fragmentEntry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}