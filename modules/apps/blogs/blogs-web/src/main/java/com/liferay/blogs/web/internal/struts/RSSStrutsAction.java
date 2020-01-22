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

package com.liferay.blogs.web.internal.struts;

import com.liferay.blogs.configuration.BlogsGroupServiceOverriddenConfiguration;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.rss.util.RSSUtil;

import java.nio.charset.StandardCharsets;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "path=/blogs/rss", service = StrutsAction.class
)
public class RSSStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (!_isRSSFeedsEnabled(httpServletRequest) ||
			!_hasGroupViewPermission(httpServletRequest)) {

			_portal.sendRSSFeedsDisabledError(
				httpServletRequest, httpServletResponse);

			return null;
		}

		try {
			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse, null,
				_getRSS(httpServletRequest), ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception exception) {
			_portal.sendError(
				exception, httpServletRequest, httpServletResponse);

			return null;
		}
	}

	private String _getFindEntryURL(long plid, ThemeDisplay themeDisplay) {
		if (plid == 0) {
			return themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/blogs/find_entry?";
		}

		return StringBundler.concat(
			themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
			"/blogs/find_entry?p_l_id=", plid);
	}

	private String _getFindEntryURL(ThemeDisplay themeDisplay) {
		return _getFindEntryURL(0, themeDisplay);
	}

	private byte[] _getRSS(HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long companyId = ParamUtil.getLong(httpServletRequest, "companyId");
		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");
		int status = WorkflowConstants.STATUS_APPROVED;
		int max = ParamUtil.getInteger(
			httpServletRequest, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			httpServletRequest, "type", RSSUtil.FORMAT_DEFAULT);
		double version = ParamUtil.getDouble(
			httpServletRequest, "version", RSSUtil.VERSION_DEFAULT);
		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);

		String rss = StringPool.BLANK;

		if (companyId > 0) {
			String entryURL = _getFindEntryURL(themeDisplay);

			rss = _blogsEntryService.getCompanyEntriesRSS(
				companyId, new Date(), status, max, type, version, displayStyle,
				StringPool.BLANK, entryURL, themeDisplay);
		}
		else if (groupId > 0) {
			long plid = ParamUtil.getLong(
				httpServletRequest, "plid", themeDisplay.getPlid());

			String feedURL = _getFindEntryURL(plid, themeDisplay);

			rss = _blogsEntryService.getGroupEntriesRSS(
				groupId, new Date(), status, max, type, version, displayStyle,
				feedURL, feedURL, themeDisplay);
		}
		else if (organizationId > 0) {
			String entryURL = _getFindEntryURL(themeDisplay);

			rss = _blogsEntryService.getOrganizationEntriesRSS(
				organizationId, new Date(), status, max, type, version,
				displayStyle, StringPool.BLANK, entryURL, themeDisplay);
		}
		else if (themeDisplay.getLayout() != null) {
			String feedURL = themeDisplay.getPathMain() + "/blogs/rss";

			rss = _blogsEntryService.getGroupEntriesRSS(
				themeDisplay.getScopeGroupId(), new Date(), status, max, type,
				version, displayStyle, feedURL, feedURL, themeDisplay);
		}

		return rss.getBytes(StandardCharsets.UTF_8);
	}

	private boolean _hasGroupViewPermission(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

		if ((groupId == 0) ||
			GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), groupId,
				ActionKeys.VIEW)) {

			return true;
		}

		return false;
	}

	private boolean _isRSSFeedsEnabled(HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		BlogsGroupServiceOverriddenConfiguration
			blogsGroupServiceOverriddenConfiguration =
				_configurationProvider.getConfiguration(
					BlogsGroupServiceOverriddenConfiguration.class,
					new GroupServiceSettingsLocator(
						themeDisplay.getSiteGroupId(),
						BlogsConstants.SERVICE_NAME));

		return blogsGroupServiceOverriddenConfiguration.enableRss();
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

}