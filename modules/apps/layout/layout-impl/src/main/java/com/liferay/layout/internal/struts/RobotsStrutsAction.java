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

package com.liferay.layout.internal.struts;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.RobotsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(property = "path=/portal/robots", service = StrutsAction.class)
public class RobotsStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			String host = GetterUtil.getString(
				_portal.getForwardedHost(httpServletRequest));

			LayoutSet layoutSet = null;

			VirtualHost virtualHost = _virtualHostLocalService.fetchVirtualHost(
				host);

			if ((virtualHost != null) && (virtualHost.getLayoutSetId() > 0)) {
				layoutSet = _layoutSetLocalService.fetchLayoutSet(host);
			}
			else {
				Company company = _portal.getCompany(httpServletRequest);

				if (host.equals(company.getVirtualHostname()) &&
					Validator.isNotNull(
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME)) {

					Group defaultGroup = _groupLocalService.getGroup(
						company.getCompanyId(),
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

					layoutSet = defaultGroup.getPublicLayoutSet();
				}
			}

			String robots = RobotsUtil.getRobots(
				layoutSet, httpServletRequest.isSecure());

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse, null,
				robots.getBytes(StringPool.UTF8), ContentTypes.TEXT_PLAIN_UTF8);
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
		RobotsStrutsAction.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private VirtualHostLocalService _virtualHostLocalService;

}