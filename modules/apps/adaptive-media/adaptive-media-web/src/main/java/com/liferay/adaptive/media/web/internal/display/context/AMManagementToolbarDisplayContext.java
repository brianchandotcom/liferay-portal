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

package com.liferay.adaptive.media.web.internal.display.context;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.web.internal.constants.AMWebKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class AMManagementToolbarDisplayContext {

	public AMManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest, PortletURL currentURLObj) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_httpServletRequest = httpServletRequest;
		_currentURLObj = currentURLObj;
	}

	public CreationMenu getCreationMenu() {
		CreationMenu creationMenu = new CreationMenu();

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_liferayPortletResponse.createRenderURL(),
					"mvcRenderCommandName",
					"/adaptive_media/edit_image_configuration_entry",
					"redirect", _currentURLObj.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "add-image-resolution"));
			});

		return creationMenu;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "filter-by-state"));
					});
			}
		};
	}

	public List<LabelItem> getFilterLabelItems() {
		final String entriesNavigation = _getEntriesNavigation();

		return new LabelItemList() {
			{
				if (entriesNavigation.equals("enabled") ||
					entriesNavigation.equals("disabled")) {

					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								_currentURLObj, _liferayPortletResponse);

							removeLabelURL.setParameter(
								"entriesNavigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);
							labelItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, entriesNavigation));
						});
				}
			}
		};
	}

	public List<AMImageConfigurationEntry> getSelectedConfigurationEntries() {
		return (List)_httpServletRequest.getAttribute(
			AMWebKeys.CONFIGURATION_ENTRIES_LIST);
	}

	public int getTotalItems() {
		List<AMImageConfigurationEntry> selectedConfigurationEntries =
			getSelectedConfigurationEntries();

		return selectedConfigurationEntries.size();
	}

	public boolean isDisabled() {
		List<AMImageConfigurationEntry> selectedConfigurationEntries =
			getSelectedConfigurationEntries();

		String entriesNavigation = _getEntriesNavigation();

		if ((selectedConfigurationEntries.size() <= 0) &&
			entriesNavigation.equals("all")) {

			return true;
		}

		return false;
	}

	private String _getEntriesNavigation() {
		return ParamUtil.getString(
			_httpServletRequest, "entriesNavigation", "all");
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		final String entriesNavigation = _getEntriesNavigation();

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(entriesNavigation.equals("all"));

						PortletURL allImageConfigurationEntriesURL =
							PortletURLUtil.clone(
								_currentURLObj, _liferayPortletResponse);

						dropdownItem.setHref(
							allImageConfigurationEntriesURL,
							"entriesNavigation", "all");

						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							entriesNavigation.equals("enabled"));

						PortletURL enabledImageConfigurationEntriesURL =
							PortletURLUtil.clone(
								_currentURLObj, _liferayPortletResponse);

						dropdownItem.setHref(
							enabledImageConfigurationEntriesURL,
							"entriesNavigation", "enabled");

						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "enabled"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							entriesNavigation.equals("disabled"));

						PortletURL disabledImageConfigurationEntriesURL =
							PortletURLUtil.clone(
								_currentURLObj, _liferayPortletResponse);

						dropdownItem.setHref(
							disabledImageConfigurationEntriesURL,
							"entriesNavigation", "disabled");

						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "disabled"));
					});
			}
		};
	}

	private final PortletURL _currentURLObj;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}