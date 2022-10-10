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

package com.liferay.layout.admin.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.layout.admin.web.internal.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.admin.web.internal.servlet.taglib.util.LayoutUtilityPageEntryActionDropdownItemsProvider;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutUtilityPageEntryVerticalCard extends BaseVerticalCard {

	public LayoutUtilityPageEntryVerticalCard(
		LayoutUtilityPageEntry layoutUtilityPageEntry,
		RenderRequest renderRequest, RenderResponse renderResponse,
		RowChecker rowChecker) {

		super(layoutUtilityPageEntry, renderRequest, rowChecker);

		_layoutUtilityPageEntry = layoutUtilityPageEntry;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
			_layoutUtilityPageEntry.getPlid());
		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		LayoutUtilityPageEntryActionDropdownItemsProvider
			layoutUtilityPageEntryActionDropdownItemsProvider =
				new LayoutUtilityPageEntryActionDropdownItemsProvider(
					_layoutUtilityPageEntry, _renderRequest, _renderResponse);

		return layoutUtilityPageEntryActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	@Override
	public String getIcon() {
		return "list";
	}

	@Override
	public List<LabelItem> getLabels() {
		if (_draftLayout == null) {
			return Collections.emptyList();
		}

		return LabelItemListBuilder.add(
			labelItem -> labelItem.setStatus(_draftLayout.getStatus())
		).build();
	}

	@Override
	public String getStickerIcon() {
		if (_layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {
			return "check-circle";
		}

		return null;
	}

	@Override
	public String getStickerStyle() {
		return "primary";
	}

	@Override
	public String getSubtitle() {
		LayoutUtilityPageEntryConstants.Type type =
			LayoutUtilityPageEntryConstants.parse(
				_layoutUtilityPageEntry.getType());

		return LanguageUtil.get(_httpServletRequest, type.getLabel());
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_layoutUtilityPageEntry.getName());
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	private final Layout _draftLayout;
	private final HttpServletRequest _httpServletRequest;
	private final LayoutUtilityPageEntry _layoutUtilityPageEntry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}