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

package com.liferay.style.book.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.style.book.constants.StyleBookActionKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.web.internal.constants.StyleBookWebKeys;
import com.liferay.style.book.web.internal.security.permissions.resource.StyleBookPermission;
import com.liferay.style.book.web.internal.servlet.taglib.util.StyleBookEntryActionDropdownItemsProvider;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class StyleBookVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public StyleBookVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(baseModel, rowChecker);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_styleBookEntry = (StyleBookEntry)baseModel;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		if (!StyleBookPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				StyleBookActionKeys.MANAGE_STYLE_BOOK_ENTRIES)) {

			return Collections.emptyList();
		}

		StyleBookEntryActionDropdownItemsProvider
			styleBookEntryActionDropdownItemsProvider =
				new StyleBookEntryActionDropdownItemsProvider(
					_styleBookEntry, _renderRequest, _renderResponse);

		return styleBookEntryActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	@Override
	public String getDefaultEventHandler() {
		return StyleBookWebKeys.STYLE_BOOK_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		if (!StyleBookPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				StyleBookActionKeys.MANAGE_STYLE_BOOK_ENTRIES)) {

			return null;
		}

		PortletURL editStyleBookEntryURL = _renderResponse.createRenderURL();

		editStyleBookEntryURL.setParameter(
			"mvcRenderCommandName", "/style_book/edit_style_book_entry");
		editStyleBookEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		editStyleBookEntryURL.setParameter(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));

		return editStyleBookEntryURL.toString();
	}

	@Override
	public String getIcon() {
		return "magic";
	}

	@Override
	public String getImageSrc() {
		return _styleBookEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public List<LabelItem> getLabels() {
		StyleBookEntry draftStyleBookEntry =
			StyleBookEntryLocalServiceUtil.fetchDraft(_styleBookEntry);

		if (_styleBookEntry.isHead() && (draftStyleBookEntry != null)) {
			return LabelItemListBuilder.add(
				labelItem -> labelItem.setStatus(
					WorkflowConstants.STATUS_APPROVED)
			).add(
				labelItem -> labelItem.setStatus(WorkflowConstants.STATUS_DRAFT)
			).build();
		}

		return LabelItemListBuilder.add(
			labelItem -> {
				int status = WorkflowConstants.STATUS_APPROVED;

				if (!_styleBookEntry.isHead()) {
					status = WorkflowConstants.STATUS_DRAFT;
				}

				labelItem.setStatus(status);
			}
		).build();
	}

	@Override
	public String getStickerIcon() {
		if (_styleBookEntry.isDefaultStyleBookEntry()) {
			return "check-circle";
		}

		return null;
	}

	@Override
	public String getStickerStyle() {
		return "primary";
	}

	@Override
	public String getTitle() {
		return _styleBookEntry.getName();
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}