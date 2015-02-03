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

package com.liferay.wiki.web.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.util.PortletKeys;
import com.liferay.wiki.settings.WikiSettings;
import com.liferay.wiki.web.settings.WikiPortletInstanceSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ivan Zaera
 */
public class WikiRequestHelper extends BaseRequestHelper {

	public WikiRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public WikiPortletInstanceSettings getWikiPortletInstanceSettings() {
		try {
			if (_wikiPortletInstanceSettings == null) {
				String portletId = getPortletId();

				if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
					_wikiPortletInstanceSettings =
						WikiPortletInstanceSettings.getInstance(
							getLayout(), getResourcePortletId(),
							getRequest().getParameterMap());
				}
				else {
					_wikiPortletInstanceSettings =
						WikiPortletInstanceSettings.getInstance(
							getLayout(), getPortletId());
				}
			}

			return _wikiPortletInstanceSettings;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public WikiSettings getWikiSettings() {
		try {
			if (_wikiSettings == null) {
				String portletId = getPortletId();

				if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
					_wikiSettings = WikiSettings.getInstance(
						getSiteGroupId(), getRequest().getParameterMap());
				}
				else {
					_wikiSettings = WikiSettings.getInstance(getSiteGroupId());
				}
			}

			return _wikiSettings;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private WikiPortletInstanceSettings _wikiPortletInstanceSettings;
	private WikiSettings _wikiSettings;

}