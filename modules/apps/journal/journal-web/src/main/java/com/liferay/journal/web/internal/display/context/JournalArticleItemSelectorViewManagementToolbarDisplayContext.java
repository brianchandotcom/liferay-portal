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

package com.liferay.journal.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleItemSelectorViewManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public JournalArticleItemSelectorViewManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			JournalArticleItemSelectorViewDisplayContext
				journalArticleItemSelectorViewDisplayContext)
		throws Exception {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			journalArticleItemSelectorViewDisplayContext.getSearchContainer());

		_journalArticleItemSelectorViewDisplayContext =
			journalArticleItemSelectorViewDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).setParameter(
			"scope", StringPool.BLANK
		).buildString();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		DropdownItemList dropdownItemList = DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.setActive(_isEverywhereScopeFilter());
							dropdownItem.setHref(
								getPortletURL(), "scope", "everywhere");
							dropdownItem.setLabel(
								LanguageUtil.get(
									httpServletRequest, "everywhere"));
						}
					).add(
						dropdownItem -> {
							dropdownItem.setActive(!_isEverywhereScopeFilter());
							dropdownItem.setHref(
								getPortletURL(), "scope", "current");
							dropdownItem.setLabel(_getCurrentScopeLabel());
						}
					).build());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter-by-location"));
			}
		).build();

		dropdownItemList.addAll(super.getFilterDropdownItems());

		return dropdownItemList;
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		String scope = ParamUtil.getString(httpServletRequest, "scope");

		if (Validator.isNull(scope) || scope.equals("current")) {
			return null;
		}

		return LabelItemListBuilder.add(
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							getPortletURL(), liferayPortletResponse)
					).setParameter(
						"scope", (String)null
					).buildString());

				labelItem.setCloseable(true);
				labelItem.setLabel(
					String.format(
						"%s: %s", LanguageUtil.get(httpServletRequest, "scope"),
						_getScopeLabel(scope)));
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSortingOrder() {
		if (Objects.equals(getOrderByCol(), "relevance")) {
			return null;
		}

		return super.getSortingOrder();
	}

	@Override
	public Boolean isDisabled() {
		return false;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "descriptive";
	}

	@Override
	protected String getDisplayStyle() {
		return _journalArticleItemSelectorViewDisplayContext.getDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive", "icon"};
	}

	@Override
	protected List<DropdownItem> getDropdownItems(
		Map<String, String> entriesMap, PortletURL entryURL,
		String parameterName, String parameterValue) {

		if ((entriesMap == null) || entriesMap.isEmpty()) {
			return null;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _themeDisplay.getLocale(), getClass());

		return new DropdownItemList() {
			{
				for (Map.Entry<String, String> entry : entriesMap.entrySet()) {
					add(
						dropdownItem -> {
							if (parameterValue != null) {
								dropdownItem.setActive(
									parameterValue.equals(entry.getValue()));
							}

							dropdownItem.setHref(
								entryURL, parameterName, entry.getValue());
							dropdownItem.setLabel(
								LanguageUtil.get(
									resourceBundle, entry.getKey()));
						});
				}
			}
		};
	}

	@Override
	protected String[] getOrderByKeys() {
		String[] orderColumns = {"modified-date", "title"};

		if (_journalArticleItemSelectorViewDisplayContext.isSearch()) {
			orderColumns = ArrayUtil.append(orderColumns, "relevance");
		}

		if (_journalArticleItemSelectorViewDisplayContext.showArticleId()) {
			orderColumns = ArrayUtil.append(orderColumns, "id");
		}

		return orderColumns;
	}

	private String _getCurrentScopeLabel() {
		Group group = _themeDisplay.getScopeGroup();

		if (group.isSite()) {
			return LanguageUtil.get(httpServletRequest, "current-site");
		}

		if (group.isOrganization()) {
			return LanguageUtil.get(httpServletRequest, "current-organization");
		}

		if (group.isDepot()) {
			return LanguageUtil.get(
				httpServletRequest, "current-asset-library");
		}

		return LanguageUtil.get(httpServletRequest, "current-scope");
	}

	private String _getScopeLabel(String scope) {
		if (scope.equals("everywhere")) {
			return LanguageUtil.get(httpServletRequest, "everywhere");
		}

		return _getCurrentScopeLabel();
	}

	private boolean _isEverywhereScopeFilter() {
		if (Objects.equals(
				ParamUtil.getString(httpServletRequest, "scope"),
				"everywhere")) {

			return true;
		}

		return false;
	}

	private final JournalArticleItemSelectorViewDisplayContext
		_journalArticleItemSelectorViewDisplayContext;
	private final ThemeDisplay _themeDisplay;

}