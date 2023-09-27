/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalServiceUtil;

import java.text.DateFormat;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class RedirectEntryInfoPanelDisplayContext {

	public RedirectEntryInfoPanelDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		List<RedirectEntry> redirectEntries) {

		_liferayPortletRequest = liferayPortletRequest;
		_redirectEntries = redirectEntries;

		_dateFormat = DateFormat.getDateInstance(
			DateFormat.LONG, liferayPortletRequest.getLocale());
	}

	public String getFormattedRedirectEntryCreateDate() {
		RedirectEntry redirectEntry = _getRedirectEntry();

		return _dateFormat.format(redirectEntry.getCreateDate());
	}

	public String getFormattedRedirectEntryExpirationDate() {
		RedirectEntry redirectEntry = _getRedirectEntry();

		if (redirectEntry.getExpirationDate() == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "never");
		}

		return _dateFormat.format(redirectEntry.getExpirationDate());
	}

	public String getFormattedRedirectEntryLastOccurrenceDate() {
		RedirectEntry redirectEntry = _getRedirectEntry();

		if (redirectEntry.getLastOccurrenceDate() == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "never");
		}

		return _dateFormat.format(redirectEntry.getLastOccurrenceDate());
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(
					LanguageUtil.get(
						_liferayPortletRequest.getHttpServletRequest(),
						"details"));
			}
		).build();
	}

	public List<RedirectEntry> getRedirectEntries() {
		return _redirectEntries;
	}

	public int getRedirectEntriesCount() {
		if (isEmptyRedirectEntries()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return RedirectEntryLocalServiceUtil.getRedirectEntriesCount(
				themeDisplay.getScopeGroupId());
		}

		return _redirectEntries.size();
	}

	public String getRedirectEntryTypeLabel() {
		RedirectEntry redirectEntry = _getRedirectEntry();

		if (redirectEntry.isPermanent()) {
			return "permanent";
		}

		return "temporary";
	}

	public User getRedirectEntryUser() {
		if (_redirectEntryUser != null) {
			return _redirectEntryUser;
		}

		RedirectEntry redirectEntry = _getRedirectEntry();

		_redirectEntryUser = UserLocalServiceUtil.fetchUser(
			redirectEntry.getUserId());

		return _redirectEntryUser;
	}

	public String getRedirectEntryUserFullName() {
		User redirectEntryUser = getRedirectEntryUser();

		return redirectEntryUser.getFullName();
	}

	public boolean isEmptyRedirectEntries() {
		if (_redirectEntries.isEmpty()) {
			return true;
		}

		return false;
	}

	public boolean isSingletonRedirectEntry() {
		if (_redirectEntries.size() == 1) {
			return true;
		}

		return false;
	}

	private RedirectEntry _getRedirectEntry() {
		if (_redirectEntry != null) {
			return _redirectEntry;
		}

		_redirectEntry = _redirectEntries.get(0);

		return _redirectEntry;
	}

	private final DateFormat _dateFormat;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final List<RedirectEntry> _redirectEntries;
	private RedirectEntry _redirectEntry;
	private User _redirectEntryUser;

}