/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.item.selector;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.criteria.AssetEntryItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.util.comparator.StyleBookEntryNameComparator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class StyleBookEntryItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<StyleBookEntry> {

	public StyleBookEntryItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		StyleBookEntryItemSelectorCriterion
			styleBookEntryItemSelectorCriterion) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_styleBookEntryItemSelectorCriterion =
			styleBookEntryItemSelectorCriterion;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "icon";
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"icon"};
	}

	@Override
	public ItemDescriptor getItemDescriptor(StyleBookEntry styleBookEntry) {
		return new StyleBookEntryItemDescriptor(
			_getSelLayout(), styleBookEntry);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new AssetEntryItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"name", "create-date"};
	}

	@Override
	public SearchContainer<StyleBookEntry> getSearchContainer()
		throws PortalException {

		SearchContainer<StyleBookEntry> styleBookEntrySearchContainer =
			new SearchContainer<>(
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST),
				_portletURL, null, "there-are-no-style-books");

		List<StyleBookEntry> styleBookEntries = new ArrayList<>();

		StyleBookEntry styleFromThemeStyleBookEntry =
			StyleBookEntryLocalServiceUtil.create();

		styleFromThemeStyleBookEntry.setStyleBookEntryId(0);
		styleFromThemeStyleBookEntry.setName(
			LanguageUtil.get(_httpServletRequest, "styles-from-theme"));

		StyleBookEntry defaultStyleBookEntry =
			StyleBookEntryLocalServiceUtil.fetchDefaultStyleBookEntry(
				_themeDisplay.getScopeGroupId());

		if (defaultStyleBookEntry == null) {
			styleFromThemeStyleBookEntry.setDefaultStyleBookEntry(true);
		}

		styleBookEntries.add(styleFromThemeStyleBookEntry);

		styleBookEntries.addAll(
			StyleBookEntryLocalServiceUtil.getStyleBookEntries(
				StagingUtil.getLiveGroupId(_themeDisplay.getScopeGroupId()),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StyleBookEntryNameComparator(true)));

		styleBookEntrySearchContainer.setResultsAndTotal(styleBookEntries);

		return styleBookEntrySearchContainer;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowManagementToolbar() {
		return false;
	}

	private Layout _getSelLayout() {
		if (_selLayout != null) {
			return _selLayout;
		}

		if (_styleBookEntryItemSelectorCriterion.getSelPlid() !=
				LayoutConstants.DEFAULT_PLID) {

			_selLayout = LayoutLocalServiceUtil.fetchLayout(
				_styleBookEntryItemSelectorCriterion.getSelPlid());
		}

		return _selLayout;
	}

	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;
	private Layout _selLayout;
	private final StyleBookEntryItemSelectorCriterion
		_styleBookEntryItemSelectorCriterion;
	private final ThemeDisplay _themeDisplay;

}