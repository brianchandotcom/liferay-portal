/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.layoutsadmin.util.SitemapUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 */
public class SitemapAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(request, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				request, "privateLayout");

			LayoutSet layoutSet = null;

			if (groupId > 0) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				if (group.isStagingGroup()) {
					groupId = group.getLiveGroupId();
				}

				layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					groupId, privateLayout);
			}
			else {
				String host = PortalUtil.getHost(request);

				host = host.trim().toLowerCase();

				VirtualHost virtualHost =
					VirtualHostLocalServiceUtil.getVirtualHost(host);

				if (virtualHost.getLayoutSetId() != 0) {
					layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
						virtualHost.getLayoutSetId());
				}
				else {
					String virtualHostSiteName = "Guest";

					String virtualHostDefaultSiteName = PropsUtil.get(
						PropsKeys.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

					if (Validator.isNotNull(virtualHostDefaultSiteName)) {
						virtualHostSiteName = virtualHostDefaultSiteName;
					}

					long companyId = PortalUtil.getCompanyId(request);

					Group group = GroupLocalServiceUtil.getGroup(
						companyId, virtualHostSiteName);

					layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
						group.getGroupId(), false);
				}
			}

			String sitemap = SitemapUtil.getSitemap(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				themeDisplay);

			ServletResponseUtil.sendFile(
				request, response, null, sitemap.getBytes(StringPool.UTF8),
				ContentTypes.TEXT_XML_UTF8);
		}
		catch (NoSuchLayoutSetException nslse) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nslse, request, response);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(SitemapAction.class);

}