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

package com.liferay.portal.language.override.web.internal.display;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class ViewManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		ViewDisplayContext viewDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			viewDisplayContext.getSearchContainer());

		_viewDisplayContext = viewDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).setNavigation(
			(String)null
		).buildString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					getPortletURL(), "mvcPath", "/edit.jsp", "backURL",
					String.valueOf(getPortletURL()), "key", StringPool.BLANK);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-language-key"));
			}
		).build();
	}

	public List<DropdownItem> getDropdownItems() {
		DropdownItemList dropdownItemList = new DropdownItemList();

		String selectedLanguage = _viewDisplayContext.getSelectedLanguage();

		for (Locale locale : _viewDisplayContext.getAvailableLocales()) {
			String languageId = LanguageUtil.getLanguageId(locale);

			String icon = StringUtil.toLowerCase(
				TextFormatter.format(languageId, TextFormatter.O));

			dropdownItemList.add(
				dropdownItem -> {
					dropdownItem.put("symbolLeft", icon);
					dropdownItem.setActive(
						Objects.equals(selectedLanguage, languageId));
					dropdownItem.setHref(
						HttpUtil.setParameter(
							String.valueOf(getPortletURL()),
							liferayPortletResponse.getNamespace() +
								"selectedLanguage",
							languageId));
					dropdownItem.setIcon(icon);
					dropdownItem.setLabel(
						TextFormatter.format(languageId, TextFormatter.O));
				});
		}

		return dropdownItemList;
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			() -> Objects.equals(getNavigation(), "override"),
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						getPortletURL()
					).setNavigation(
						(String)null
					).buildString());
				labelItem.setDismissible(true);
				labelItem.setLabel(
					LanguageUtil.get(httpServletRequest, "override"));
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		return String.valueOf(searchContainer.getIteratorURL());
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
		return _viewDisplayContext.getDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive"};
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"override"};
	}

	private final ViewDisplayContext _viewDisplayContext;

}