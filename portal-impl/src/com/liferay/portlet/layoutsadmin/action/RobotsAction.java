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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.RobotsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author David Truong
 */
public class RobotsAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			String host = GetterUtil.getString(PortalUtil.getHost(request));

			LayoutSet layoutSet = null;

			VirtualHost virtualHost =
				VirtualHostLocalServiceUtil.fetchVirtualHost(host);

			if ((virtualHost != null) && (virtualHost.getLayoutSetId() > 0)) {
				layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(host);
			}
			else {
				Company company = PortalUtil.getCompany(request);

				if (host.equals(company.getVirtualHostname()) &&
					Validator.isNotNull(
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME)) {

					Group defaultGroup = GroupLocalServiceUtil.getGroup(
						company.getCompanyId(),
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

					layoutSet = defaultGroup.getLayoutSet();
				}
			}

			String robots = RobotsUtil.getRobots(layoutSet);

			ServletResponseUtil.sendFile(
				request, response, null, robots.getBytes(StringPool.UTF8),
				ContentTypes.TEXT_PLAIN_UTF8);
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

	private static final Log _log = LogFactoryUtil.getLog(RobotsAction.class);

}