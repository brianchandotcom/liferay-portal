/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.util.Map;
import java.util.Objects;

import javax.portlet.RenderRequest;

/**
 * @author Víctor Galán
 */
public class SelectStylebookLayoutVerticalCard implements VerticalCard {

	public SelectStylebookLayoutVerticalCard(
		RenderRequest renderRequest, Layout selLayout,
		StyleBookEntry styleBookEntry) {

		_renderRequest = renderRequest;
		_selLayout = selLayout;
		_styleBookEntry = styleBookEntry;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getCssClass() {
		String cssClass =
			"select-style-book-option card-interactive " +
				"card-interactive-secondary";

		if (_isSelected()) {
			cssClass += " active";
		}

		return cssClass;
	}

	public StyleBookEntry getDefaultStyleBookEntry() {
		if (_defaultStyleBookEntry != null) {
			return _defaultStyleBookEntry;
		}

		_defaultStyleBookEntry =
			DefaultStyleBookEntryUtil.getDefaultMasterStyleBookEntry(
				_selLayout);

		return _defaultStyleBookEntry;
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		return HashMapBuilder.put(
			"role", "button"
		).put(
			"tabIndex", "0"
		).build();
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
	public String getStickerCssClass() {
		return "sticker-primary";
	}

	@Override
	public String getStickerIcon() {
		if (_styleBookEntry.isDefaultStyleBookEntry()) {
			return "check-circle";
		}

		return null;
	}

	@Override
	public String getSubtitle() {
		StyleBookEntry defaultStyleBookEntry = getDefaultStyleBookEntry();

		if ((_styleBookEntry.getStyleBookEntryId() <= 0) &&
			(defaultStyleBookEntry != null)) {

			return defaultStyleBookEntry.getName();
		}

		return StringPool.DASH;
	}

	@Override
	public String getTitle() {
		if (_styleBookEntry.getStyleBookEntryId() > 0) {
			return _styleBookEntry.getName();
		}

		StyleBookEntry defaultStyleBookEntry = getDefaultStyleBookEntry();

		if (defaultStyleBookEntry == null) {
			return LanguageUtil.get(
				_themeDisplay.getLocale(), "styles-from-theme");
		}

		if (_hasEditableMasterLayout() &&
			(_selLayout.getMasterLayoutPlid() > 0)) {

			return LanguageUtil.get(
				_themeDisplay.getLocale(), "styles-from-master");
		}

		return LanguageUtil.get(_themeDisplay.getLocale(), "styles-by-default");
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private boolean _hasEditableMasterLayout() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(_selLayout.getPlid());

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(_selLayout.getClassPK());
		}

		if ((layoutPageTemplateEntry == null) ||
			!Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			return true;
		}

		return false;
	}

	private boolean _isSelected() {
		long styleBookEntryId = ParamUtil.getLong(
			_renderRequest, "styleBookEntryId");

		if (Objects.equals(
				styleBookEntryId, _styleBookEntry.getStyleBookEntryId())) {

			return true;
		}

		return false;
	}

	private StyleBookEntry _defaultStyleBookEntry;
	private final RenderRequest _renderRequest;
	private final Layout _selLayout;
	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}