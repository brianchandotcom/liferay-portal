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

package com.liferay.mobile.device.rules.web.internal.rule.group.action;

import com.liferay.mobile.device.rules.action.ActionHandler;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward Han
 */
@Component(immediate = true, service = ActionHandler.class)
public class SiteRedirectActionHandler extends BaseRedirectActionHandler {

	public static String getHandlerType() {
		return SiteRedirectActionHandler.class.getName();
	}

	@Override
	public String getEditorJSP() {
		return "/action/site_url.jsp";
	}

	@Override
	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	@Override
	public String getType() {
		return getHandlerType();
	}

	@Override
	protected String getURL(
			MDRAction mdrAction, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		UnicodeProperties typeSettingsProperties =
			mdrAction.getTypeSettingsProperties();

		long plid = GetterUtil.getLong(
			typeSettingsProperties.getProperty("plid"));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout themeDisplayLayout = themeDisplay.getLayout();

		if (plid == themeDisplayLayout.getPlid()) {
			return null;
		}

		Layout layout = _layoutLocalService.fetchLayout(plid);

		long groupId = GetterUtil.getLong(
			typeSettingsProperties.getProperty("groupId"));

		if ((layout != null) && (layout.getGroupId() != groupId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Layout ", layout.getPlid(),
						" does not belong to group ", groupId));
			}

			layout = null;
		}

		if (layout == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Using default public layout");
			}

			Group group = null;

			if (groupId != themeDisplayLayout.getGroupId()) {
				group = _groupLocalService.fetchGroup(groupId);
			}

			if (group == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No group found with group ID " + groupId);
				}

				return null;
			}

			layout = _layoutLocalService.fetchLayout(
				group.getDefaultPublicPlid());
		}

		if (layout != null) {
			return _portal.getLayoutURL(layout, themeDisplay);
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unable to resolve default layout");
		}

		return null;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteRedirectActionHandler.class);

	private static final Collection<String> _propertyNames =
		Collections.unmodifiableCollection(Arrays.asList("groupId", "plid"));

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}