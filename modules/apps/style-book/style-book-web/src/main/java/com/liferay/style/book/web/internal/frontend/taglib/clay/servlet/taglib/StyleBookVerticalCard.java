/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.style.book.constants.StyleBookActionKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.web.internal.security.permissions.resource.StyleBookPermission;
import com.liferay.style.book.web.internal.servlet.taglib.util.StyleBookEntryActionDropdownItemsProvider;

import java.util.Collections;
import java.util.List;

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
	public String getAriaLabel() {
		if (_styleBookEntry.isDefaultStyleBookEntry()) {
			return LanguageUtil.format(
				_themeDisplay.getLocale(), "x-marked-as-default",
				_styleBookEntry.getName());
		}

		return null;
	}

	@Override
	public String getHref() {
		if (!StyleBookPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				StyleBookActionKeys.MANAGE_STYLE_BOOK_ENTRIES) ||
			(_styleBookEntry.getStyleBookEntryId() <= 0)) {

			return null;
		}

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/style_book/edit_style_book_entry"
		).setParameter(
			"styleBookEntryId", _styleBookEntry.getStyleBookEntryId()
		).buildString();
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
	public String getStickerTitle() {
		if (_styleBookEntry.isDefaultStyleBookEntry()) {
			return LanguageUtil.get(
				_themeDisplay.getLocale(), "marked-as-default");
		}

		return null;
	}

	@Override
	public String getTitle() {
		return _styleBookEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		if (_styleBookEntry.getStyleBookEntryId() > 0) {
			return true;
		}

		return false;
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}