/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.constants.LockedLayoutType;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.model.LockedLayout;
import com.liferay.layout.model.LockedLayoutOrder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class LockedLayoutsDisplayContext {

	public LockedLayoutsDisplayContext(
		Language language, LayoutLocalService layoutLocalService,
		LayoutLockManager layoutLockManager,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_language = language;
		_layoutLocalService = layoutLocalService;
		_layoutLockManager = layoutLockManager;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getLastAutoSave(LockedLayout lockedLayout) {
		Date lastAutoSaveDate = lockedLayout.getLastAutoSaveDate();

		return _language.format(
			_themeDisplay.getLocale(), "x-ago",
			_language.getTimeDescription(
				_themeDisplay.getLocale(),
				System.currentTimeMillis() - lastAutoSaveDate.getTime(), true));
	}

	public String getLayoutType(LockedLayout lockedLayout) {
		return _layoutLockManager.getLayoutType(
			lockedLayout.getClassPK(), _themeDisplay.getLocale(),
			lockedLayout.getType());
	}

	public String getLayoutURL(LockedLayout lockedLayout)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(lockedLayout.getPlid());

		return PortalUtil.getLayoutFullURL(layout, _themeDisplay);
	}

	public List<DropdownItem> getLockedLayoutDropdownItems(
		LockedLayout lockedLayout) {

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> dropdownGroupItem.setDropdownItems(
				DropdownItemListBuilder.add(
					dropdownItem -> {
						dropdownItem.putData("action", "unlockLockedLayout");
						dropdownItem.putData(
							"unlockLockedLayoutURL",
							PortletURLBuilder.createActionURL(
								_liferayPortletResponse
							).setActionName(
								"/layout_locked_layouts/unlock_layouts"
							).setRedirect(
								_themeDisplay.getURLCurrent()
							).setParameter(
								"plid", lockedLayout.getPlid()
							).buildString());
						dropdownItem.setIcon("unlock");
						dropdownItem.setLabel(
							_language.get(_themeDisplay.getLocale(), "unlock"));
					}
				).build())
		).build();
	}

	public LockedLayoutOrder getLockedLayoutOrder() {
		if (_lockedLayoutOrder != null) {
			return _lockedLayoutOrder;
		}

		_lockedLayoutOrder = new LockedLayoutOrder(
			Objects.equals(getOrderByType(), "desc"), _themeDisplay.getLocale(),
			LockedLayoutOrder.LockedLayoutOrderType.create(getOrderByCol()));

		return _lockedLayoutOrder;
	}

	public LockedLayoutType getLockedLayoutType() {
		if (_lockedLayoutType != null) {
			return _lockedLayoutType;
		}

		_lockedLayoutType = LockedLayoutType.create(
			ParamUtil.getString(_liferayPortletRequest, "type"));

		return _lockedLayoutType;
	}

	public String getName(LockedLayout lockedLayout) {
		return LocalizationUtil.getLocalization(
			lockedLayout.getName(), _themeDisplay.getLanguageId());
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_liferayPortletRequest, "orderByCol",
			LockedLayoutOrder.LockedLayoutOrderType.LAST_AUTOSAVE.getValue());

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "desc");

		return _orderByType;
	}

	public SearchContainer<LockedLayout> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<LockedLayout> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, _liferayPortletResponse.createRenderURL(),
			null, "no-locked-pages-were-found");

		searchContainer.setResultsAndTotal(_getFilteredLockedLayouts());

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public boolean hasLockedLayouts() {
		if (ListUtil.isEmpty(_getLockedLayouts())) {
			return false;
		}

		return true;
	}

	private List<LockedLayout> _getFilteredLockedLayouts() {
		if (_filteredLockedLayouts != null) {
			return _filteredLockedLayouts;
		}

		if (Validator.isNull(_getKeywords())) {
			_filteredLockedLayouts = _getLockedLayouts();

			return _filteredLockedLayouts;
		}

		_filteredLockedLayouts = ListUtil.filter(
			_getLockedLayouts(),
			lockedLayout -> _hasKeywords(_getKeywords(), lockedLayout));

		return _filteredLockedLayouts;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = StringUtil.toLowerCase(
			ParamUtil.getString(_liferayPortletRequest, "keywords"));

		return _keywords;
	}

	private List<LockedLayout> _getLockedLayouts() {
		if (_lockedLayouts != null) {
			return _lockedLayouts;
		}

		_lockedLayouts = _layoutLockManager.getLockedLayouts(
			_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
			getLockedLayoutOrder(), getLockedLayoutType());

		return _lockedLayouts;
	}

	private boolean _hasKeywords(String keywords, LockedLayout lockedLayout) {
		if (StringUtil.contains(
				StringUtil.toLowerCase(lockedLayout.getUserName()), keywords,
				StringPool.BLANK) ||
			StringUtil.contains(
				StringUtil.toLowerCase(getName(lockedLayout)), keywords,
				StringPool.BLANK)) {

			return true;
		}

		return false;
	}

	private List<LockedLayout> _filteredLockedLayouts;
	private String _keywords;
	private final Language _language;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutLockManager _layoutLockManager;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private LockedLayoutOrder _lockedLayoutOrder;
	private List<LockedLayout> _lockedLayouts;
	private LockedLayoutType _lockedLayoutType;
	private String _orderByCol;
	private String _orderByType;
	private SearchContainer<LockedLayout> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}