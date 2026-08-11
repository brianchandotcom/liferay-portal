/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.processes.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Jaime Leon
 */
public class PublishProcessesDisplayContext {

	public PublishProcessesDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, Group liveGroup) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_liveGroup = liveGroup;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(_getNewPublishURL());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "new"));
			}
		).build();
	}

	public List<NavigationItem> getNavigationItems() {
		String tabs1 = _getTabs1();

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(tabs1.equals("processes"));
				navigationItem.setHref(_getTabURL("processes"));
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "processes"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(tabs1.equals("scheduled"));
				navigationItem.setHref(_getTabURL("scheduled"));
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "scheduled"));
			}
		).build();
	}

	public List<FDSActionDropdownItem> getPublishFDSActionDropdownItems() {
		FDSActionDropdownItem deleteFDSActionDropdownItem =
			new FDSActionDropdownItem(
				_BASE_PATH + "/publish-processes/{id}", "trash", "delete",
				LanguageUtil.get(_httpServletRequest, "delete"), "delete", null,
				"async",
				HashMapBuilder.<String, Object>put(
					"status.code",
					Arrays.asList(
						BackgroundTaskConstants.STATUS_IN_PROGRESS,
						BackgroundTaskConstants.STATUS_NEW,
						BackgroundTaskConstants.STATUS_QUEUED)
				).build());

		deleteFDSActionDropdownItem.setConfirmationMessage(
			LanguageUtil.get(
				_httpServletRequest, "are-you-sure-you-want-to-delete-this"));

		FDSActionDropdownItem clearFDSActionDropdownItem =
			new FDSActionDropdownItem(
				_BASE_PATH + "/publish-processes/{id}", "trash", "clear",
				LanguageUtil.get(_httpServletRequest, "clear"), "delete", null,
				"async",
				HashMapBuilder.<String, Object>put(
					"status.code",
					Arrays.asList(
						BackgroundTaskConstants.STATUS_CANCELLED,
						BackgroundTaskConstants.STATUS_COMPLETED_WITH_ERRORS,
						BackgroundTaskConstants.STATUS_FAILED,
						BackgroundTaskConstants.STATUS_SUCCESSFUL)
				).build());

		clearFDSActionDropdownItem.setConfirmationMessage(
			LanguageUtil.get(
				_httpServletRequest, "are-you-sure-you-want-to-delete-this"));

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				_BASE_PATH + "/publish-processes/{id}/relaunch", "reload",
				"relaunch", LanguageUtil.get(_httpServletRequest, "relaunch"),
				"post", null, "async"),
			deleteFDSActionDropdownItem, clearFDSActionDropdownItem);
	}

	public String getPublishProcessesAPIURL() {
		return _getScopePath() + "/publish-processes";
	}

	public List<FDSActionDropdownItem>
		getScheduledPublishFDSActionDropdownItems() {

		FDSActionDropdownItem unscheduleFDSActionDropdownItem =
			new FDSActionDropdownItem(
				_getScopePath() + "/scheduled-publish-processes/{id}", "trash",
				"unschedule",
				LanguageUtil.get(_httpServletRequest, "unschedule"), "delete",
				null, "async");

		unscheduleFDSActionDropdownItem.setConfirmationMessage(
			LanguageUtil.get(
				_httpServletRequest,
				"are-you-sure-you-want-to-unschedule-this-publication"));

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCRenderCommandName(
					"/staging_processes/view_new_publish"
				).setBackURL(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"groupId", _liveGroup.getGroupId()
				).setParameter(
					"scheduledPublishProcessId", "{id}"
				).buildString(),
				"pencil", "edit", LanguageUtil.get(_httpServletRequest, "edit"),
				"get", null, "link"),
			unscheduleFDSActionDropdownItem);
	}

	public String getScheduledPublishProcessesAPIURL() {
		return _getScopePath() + "/scheduled-publish-processes";
	}

	private String _getNewPublishURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/staging_processes/view_new_publish"
		).setBackURL(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"groupId", _liveGroup.getGroupId()
		).setParameter(
			"scheduled", () -> Objects.equals(_getTabs1(), "scheduled")
		).buildString();
	}

	private String _getScopePath() {
		return StringBundler.concat(
			_BASE_PATH, "/sites/", _liveGroup.getExternalReferenceCode());
	}

	private String _getTabs1() {
		return ParamUtil.getString(_httpServletRequest, "tabs1", "processes");
	}

	private String _getTabURL(String tabs1) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setTabs1(
			tabs1
		).buildString();
	}

	private static final String _BASE_PATH = "/o/export-import/v1.0";

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Group _liveGroup;
	private final ThemeDisplay _themeDisplay;

}