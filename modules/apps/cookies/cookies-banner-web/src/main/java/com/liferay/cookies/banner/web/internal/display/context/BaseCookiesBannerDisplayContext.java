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

package com.liferay.cookies.banner.web.internal.display.context;

import com.liferay.portal.kernel.cookies.constants.CookiesConstants;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jürgen Kappler
 */
public class BaseCookiesBannerDisplayContext {

	public BaseCookiesBannerDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
	}

	public String[] getOptionalCookieNames() {
		return new String[] {
			CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL,
			CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
			CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION
		};
	}

	public String[] getRequiredCookieNames() {
		return new String[] {CookiesConstants.NAME_CONSENT_TYPE_NECESSARY};
	}

	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;

}