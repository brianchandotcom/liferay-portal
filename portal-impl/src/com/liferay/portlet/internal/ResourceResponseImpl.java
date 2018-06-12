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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayResourceResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.extra.config.ExtraPortletAppConfig;
import com.liferay.portlet.extra.config.ExtraPortletAppConfigRegistry;

import java.util.Locale;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class ResourceResponseImpl
	extends MimeResponseImpl implements LiferayResourceResponse {

	@Override
	public void addDateHeader(String name, long date) {
		response.addDateHeader(name, date);
	}

	@Override
	public void addHeader(String name, String value) {
		response.addHeader(name, value);
	}

	@Override
	public void addIntHeader(String name, int value) {
		response.addIntHeader(name, value);
	}

	@Override
	public void addProperty(Cookie cookie) {
		if (!(isCalledFlushBuffer() || isCommitted())) {
			response.addCookie(cookie);
		}
	}

	@Override
	public LiferayPortletURL createLiferayPortletURL(
		long plid, String portletName, String lifecycle, MimeResponse.Copy copy,
		boolean includeLinkToLayoutUuid) {

		ResourceRequest resourceRequest = (ResourceRequest)getPortletRequest();

		String cacheability = resourceRequest.getCacheability();

		if (cacheability.equals(ResourceURL.PAGE)) {
		}
		else if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			throw new IllegalStateException(
				"Unable to create an action URL from a resource response " +
					"when the cacheability is not set to PAGE");
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			throw new IllegalStateException(
				"Unable to create a render URL from a resource response when " +
					"the cacheability is not set to PAGE");
		}

		return super.createLiferayPortletURL(
			plid, portletName, lifecycle, copy, includeLinkToLayoutUuid);
	}

	@Override
	public String getLifecycle() {
		return PortletRequest.RESOURCE_PHASE;
	}

	@Override
	public int getStatus() {
		return response.getStatus();
	}

	@Override
	public void setCharacterEncoding(String charset) {
		response.setCharacterEncoding(charset);

		_canSetLocaleEncoding = false;
	}

	@Override
	public void setContentLength(int length) {
		response.setContentLength(length);
	}

	@Override
	public void setContentLengthLong(long length) {
		response.setContentLengthLong(length);
	}

	@Override
	public void setDateHeader(String name, long date) {
		response.setDateHeader(name, date);
	}

	@Override
	public void setHeader(String name, String value) {
		response.setHeader(name, value);

		if (name.equals(ResourceResponse.HTTP_STATUS_CODE)) {
			int status = GetterUtil.getInteger(
				value, HttpServletResponse.SC_OK);

			response.setStatus(status);
		}
	}

	@Override
	public void setIntHeader(String name, int value) {
		response.setIntHeader(name, value);
	}

	@Override
	public void setLocale(Locale locale) {
		if (locale == null) {
			return;
		}

		response.setLocale(locale);

		if (_canSetLocaleEncoding) {
			Portlet portlet = getPortlet();

			PortletApp portletApp = portlet.getPortletApp();

			ExtraPortletAppConfig extraPortletAppConfig =
				ExtraPortletAppConfigRegistry.getExtraPortletAppConfig(
					portletApp.getServletContextName());

			String characterEncoding = extraPortletAppConfig.getEncoding(
				locale.toString());

			if (characterEncoding != null) {
				setCharacterEncoding(characterEncoding);

				_canSetLocaleEncoding = true;
			}
		}
	}

	@Override
	public void setStatus(int statusCode) {
		response.setStatus(statusCode);
	}

	private boolean _canSetLocaleEncoding = true;

}