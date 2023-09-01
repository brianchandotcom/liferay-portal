/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.rss.web.internal.configuration.RSSPortletInstanceConfiguration;
import com.liferay.rss.web.internal.configuration.RSSWebCacheConfiguration;
import com.liferay.rss.web.internal.util.RSSFeed;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class RSSDisplayContext {

	public RSSDisplayContext(
			HttpServletRequest httpServletRequest,
			RSSWebCacheConfiguration rssWebCacheConfiguration)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;
		_rssWebCacheConfiguration = rssWebCacheConfiguration;

		_rssPortletInstanceConfiguration =
			ConfigurationProviderUtil.getPortletInstanceConfiguration(
				RSSPortletInstanceConfiguration.class,
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY));
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_rssPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public List<RSSFeed> getRSSFeeds() {
		List<RSSFeed> rssFeeds = new ArrayList<>();

		String[] titles = _rssPortletInstanceConfiguration.titles();

		String[] urls = _rssPortletInstanceConfiguration.urls();

		for (int i = 0; i < urls.length; i++) {
			String url = urls[i];

			String title = StringPool.BLANK;

			if (i < titles.length) {
				title = titles[i];
			}

			rssFeeds.add(new RSSFeed(_rssWebCacheConfiguration, url, title));
		}

		return rssFeeds;
	}

	public RSSPortletInstanceConfiguration
		getRSSPortletInstanceConfiguration() {

		return _rssPortletInstanceConfiguration;
	}

	public List<TabsItem> getTabsItems() {
		return TabsItemListBuilder.add(
			tabsItem -> {
				tabsItem.setActive(true);
				tabsItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "feeds"));
			}
		).add(
			tabsItem -> tabsItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "display-settings"))
		).build();
	}

	public boolean isShowConfigurationLink() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			portletDisplay.getId(), ActionKeys.CONFIGURATION);
	}

	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final RSSPortletInstanceConfiguration
		_rssPortletInstanceConfiguration;
	private final RSSWebCacheConfiguration _rssWebCacheConfiguration;

}