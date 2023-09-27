/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.display.context;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalServiceUtil;
import com.liferay.announcements.web.internal.search.AnnouncementsEntryChecker;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.announcements.service.permission.AnnouncementsEntryPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class AnnouncementsAdminViewManagementToolbarDisplayContext {

	public AnnouncementsAdminViewManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_renderRequest = renderRequest;

		_announcementsAdminViewDisplayContext =
			new DefaultAnnouncementsAdminViewDisplayContext(
				_httpServletRequest);
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

	public List<String> getAvailableActions(
			AnnouncementsEntry announcementsEntry)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (AnnouncementsEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), announcementsEntry,
				ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		return availableActions;
	}

	public String getClearResultsURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setNavigation(
			_getNavigation()
		).buildString();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				String navigation = _getNavigation();

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
						"distributionScope", _getDistributionScope()
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
					LanguageUtil.get(
						_httpServletRequest, "filter-by-navigation"));
			}
		).build();
	}

	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			() -> Validator.isNotNull(_getDistributionScope()),
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

	public SearchContainer<AnnouncementsEntry> getSearchContainer()
		throws PortalException {

		SearchContainer<AnnouncementsEntry>
			announcementsEntriesSearchContainer = new SearchContainer(
				_renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
				SearchContainer.DEFAULT_DELTA, _currentURLObj, null,
				"no-entries-were-found");

		long classNameId = 0;
		long classPK = 0;

		String[] distributionScopeArray = StringUtil.split(
			_getDistributionScope());

		if (distributionScopeArray.length == 2) {
			classNameId = GetterUtil.getLong(distributionScopeArray[0]);
			classPK = GetterUtil.getLong(distributionScopeArray[1]);
		}

		long announcementsClassNameId = classNameId;
		long announcementsClassPK = classPK;

		announcementsEntriesSearchContainer.setResultsAndTotal(
			() -> AnnouncementsEntryLocalServiceUtil.getEntries(
				_themeDisplay.getCompanyId(), announcementsClassNameId,
				announcementsClassPK,
				Objects.equals(_getNavigation(), "alerts"),
				announcementsEntriesSearchContainer.getStart(),
				announcementsEntriesSearchContainer.getEnd()),
			AnnouncementsEntryLocalServiceUtil.getEntriesCount(
				_themeDisplay.getCompanyId(), announcementsClassNameId,
				announcementsClassPK,
				Objects.equals(_getNavigation(), "alerts")));

		announcementsEntriesSearchContainer.setRowChecker(
			new AnnouncementsEntryChecker(
				_liferayPortletRequest, _liferayPortletResponse));

		return announcementsEntriesSearchContainer;
	}

	public String getSearchContainerId() {
		if (Objects.equals(_getNavigation(), "alerts")) {
			return "alertsEntries";
		}

		return "announcementsEntries";
	}

	public boolean isDisabled() {
		return false;
	}

	private String _getDistributionScope() {
		return ParamUtil.getString(_httpServletRequest, "distributionScope");
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

	private String _getNavigation() {
		return ParamUtil.getString(
			_httpServletRequest, "navigation", "announcements");
	}

	private final AnnouncementsAdminViewDisplayContext
		_announcementsAdminViewDisplayContext;
	private final PortletURL _currentURLObj;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}