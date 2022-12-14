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

package com.liferay.site.internal.struts;

import com.liferay.layout.admin.kernel.util.SitemapUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(property = "path=/portal/sitemap", service = StrutsAction.class)
public class SitemapStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			String layoutUuid = ParamUtil.getString(
				httpServletRequest, "layoutUuid");
			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

			LayoutSet layoutSet = null;

			if (groupId > 0) {
				Group group = _groupLocalService.getGroup(groupId);

				if (group.isStagingGroup()) {
					groupId = group.getLiveGroupId();
				}

				boolean privateLayout = ParamUtil.getBoolean(
					httpServletRequest, "privateLayout");

				layoutSet = _layoutSetLocalService.getLayoutSet(
					groupId, privateLayout);
			}
			else {
				String host = _portal.getHost(httpServletRequest);

				host = StringUtil.toLowerCase(host);
				host = host.trim();

				VirtualHost virtualHost =
					_virtualHostLocalService.fetchVirtualHost(host);

				if ((virtualHost != null) &&
					(virtualHost.getLayoutSetId() != 0)) {

					layoutSet = _layoutSetLocalService.getLayoutSet(
						virtualHost.getLayoutSetId());

					Group group = layoutSet.getGroup();

					if (group.isStagingGroup()) {
						_groupPermission.check(
							themeDisplay.getPermissionChecker(),
							group.getGroupId(), ActionKeys.VIEW_STAGING);
					}
				}
				else {
					String groupName =
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME;

					if (Validator.isNull(groupName)) {
						groupName = GroupConstants.GUEST;
					}

					Group group = _groupLocalService.getGroup(
						themeDisplay.getCompanyId(), groupName);

					layoutSet = _layoutSetLocalService.getLayoutSet(
						group.getGroupId(), false);
				}
			}

			String sitemap = SitemapUtil.getSitemap(
				layoutUuid, layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				themeDisplay);

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse, null,
				sitemap.getBytes(StringPool.UTF8), ContentTypes.TEXT_XML_UTF8);
		}
		catch (NoSuchLayoutSetException noSuchLayoutSetException) {
			_portal.sendError(
				HttpServletResponse.SC_NOT_FOUND, noSuchLayoutSetException,
				httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			_portal.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception,
				httpServletRequest, httpServletResponse);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SitemapStrutsAction.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private VirtualHostLocalService _virtualHostLocalService;

}