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

package com.liferay.portal.security.auth;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.BaseAuthTokenWhitelist;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public class StrutsPortletAuthTokenWhitelist extends BaseAuthTokenWhitelist {

	public StrutsPortletAuthTokenWhitelist() {
		trackWhitelistServices(
			PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS, _portletCSRFWhitelist);

		registerPortalProperty(PropsKeys.AUTH_TOKEN_IGNORE_ACTIONS);

		trackWhitelistServices(
			PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS,
			_portletInvocationWhitelist);

		registerPortalProperty(
			PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS);
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		String portletId = portlet.getPortletId();

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String strutsAction = httpServletRequest.getParameter(
			namespace.concat("struts_action"));

		if (Validator.isNotNull(strutsAction) &&
			_portletCSRFWhitelist.contains(strutsAction) &&
			isValidStrutsAction(
				portlet.getCompanyId(),
				PortletIdCodec.decodePortletName(portletId), strutsAction)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		String portletId = portlet.getPortletId();

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String strutsAction = httpServletRequest.getParameter(
			namespace.concat("struts_action"));

		if (Validator.isNull(strutsAction)) {
			strutsAction = httpServletRequest.getParameter("struts_action");
		}

		if (Validator.isNotNull(strutsAction) &&
			_portletInvocationWhitelist.contains(strutsAction) &&
			isValidStrutsAction(
				portlet.getCompanyId(), portletId, strutsAction)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String strutsAction = liferayPortletURL.getParameter("struts_action");

		if (Validator.isBlank(strutsAction)) {
			return false;
		}

		if (_portletCSRFWhitelist.contains(strutsAction)) {
			long plid = liferayPortletURL.getPlid();

			Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

			if (layout == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to load layout " + plid);
				}

				return false;
			}

			String rootPortletId = PortletIdCodec.decodePortletName(
				liferayPortletURL.getPortletId());

			if (isValidStrutsAction(0, rootPortletId, strutsAction)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String strutsAction = liferayPortletURL.getParameter("struts_action");

		if (Validator.isBlank(strutsAction)) {
			return false;
		}

		if (_portletInvocationWhitelist.contains(strutsAction)) {
			long plid = liferayPortletURL.getPlid();

			Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

			if (layout == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to load layout " + plid);
				}

				return false;
			}

			if (isValidStrutsAction(
					0, liferayPortletURL.getPortletId(), strutsAction)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isValidStrutsAction(
		long companyId, String portletId, String strutsAction) {

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletId);

			if (portlet == null) {
				return false;
			}

			String strutsPath = strutsAction.substring(
				1, strutsAction.lastIndexOf(CharPool.SLASH));

			if (strutsPath.equals(portlet.getStrutsPath()) ||
				strutsPath.equals(portlet.getParentStrutsPath())) {

				return true;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StrutsPortletAuthTokenWhitelist.class);

	private final Set<String> _portletCSRFWhitelist = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private final Set<String> _portletInvocationWhitelist =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

}