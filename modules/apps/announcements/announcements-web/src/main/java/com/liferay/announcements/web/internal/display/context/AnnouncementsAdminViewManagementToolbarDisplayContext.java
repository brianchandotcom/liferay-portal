/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class AnnouncementsAdminViewManagementToolbarDisplayContext {

	public AnnouncementsAdminViewManagementToolbarDisplayContext(
		AnnouncementsAdminViewDisplayContext
			announcementsAdminViewDisplayContext,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_announcementsAdminViewDisplayContext =
			announcementsAdminViewDisplayContext;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getClearResultsURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setNavigation(
			_announcementsAdminViewDisplayContext.getNavigation()
		).buildString();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				String navigation =
					_announcementsAdminViewDisplayContext.getNavigation();

				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCRenderCommandName(
						"/announcements/edit_entry"
					).setRedirect(
						PortalUtil.getCurrentURL(_httpServletRequest)
					).setParameter(
						"alert", navigation.equals("alerts")
					).setParameter(
						"distributionScope",
						_announcementsAdminViewDisplayContext.
							getDistributionScope()
					).buildPortletURL());

				String label = null;

				if (navigation.equals("alerts")) {
					label = "add-alert";
				}
				else {
					label = "add-announcement";
				}

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, label));
			}
		).build();
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "filter-by"));
			}
		).build();
	}

	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			() -> Validator.isNotNull(
				_announcementsAdminViewDisplayContext.getDistributionScope()),
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse)
					).setParameter(
						"distributionScope", (String)null
					).buildString());

				labelItem.setCloseable(true);
				labelItem.setLabel(
					_announcementsAdminViewDisplayContext.
						getCurrentDistributionScopeLabel());
			}
		).build();
	}

	public boolean isDisabled() {
		return false;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems()
		throws Exception {

		return new DropdownItemList() {
			{
				PortletURL navigationURL = PortletURLUtil.clone(
					_currentURLObj, _liferayPortletResponse);

				String currentDistributionScopeLabel =
					_announcementsAdminViewDisplayContext.
						getCurrentDistributionScopeLabel();

				Map<String, String> distributionScopes =
					_announcementsAdminViewDisplayContext.
						getDistributionScopes();

				for (Map.Entry<String, String> distributionScopeEntry :
						distributionScopes.entrySet()) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								currentDistributionScopeLabel.equals(
									distributionScopeEntry.getKey()));
							dropdownItem.setHref(
								navigationURL, "distributionScope",
								distributionScopeEntry.getValue());
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									distributionScopeEntry.getKey()));
						});
				}
			}
		};
	}

	private final AnnouncementsAdminViewDisplayContext
		_announcementsAdminViewDisplayContext;
	private final PortletURL _currentURLObj;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}