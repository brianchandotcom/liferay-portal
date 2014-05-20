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

package com.liferay.portlet.blogs.pingback;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
public class PingbackServiceContextFunction
	implements Function<String, ServiceContext> {

	public PingbackServiceContextFunction(
		long companyId, long groupId, String urlTitle) {

		_companyId = companyId;
		_groupId = groupId;
		_urlTitle = urlTitle;
	}

	@Override
	public ServiceContext apply(String className) {
		try {
			return buildServiceContext();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
		catch (SystemException se) {
			throw new RuntimeException(se);
		}
	}

	protected String buildRedirect(String layoutFullURL)
		throws SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			_companyId, PortletKeys.BLOGS);

		StringBundler sb = new StringBundler(5);

		sb.append(layoutFullURL);
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append(portlet.getFriendlyURLMapping());
		sb.append(StringPool.SLASH);
		sb.append(_urlTitle);

		return sb.toString();
	}

	protected ServiceContext buildServiceContext()
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		String pingbackUserName = LanguageUtil.get(
			LocaleUtil.getSiteDefault(), "pingback");

		serviceContext.setAttribute("pingbackUserName", pingbackUserName);

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			_groupId, PortletKeys.BLOGS);

		String redirect = buildRedirect(layoutFullURL);
		serviceContext.setAttribute("redirect", redirect);

		serviceContext.setLayoutFullURL(layoutFullURL);

		return serviceContext;
	}

	private long _companyId;
	private long _groupId;
	private String _urlTitle;

}