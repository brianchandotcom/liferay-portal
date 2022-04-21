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

	public String[] getOptionalCookies() {
		return new String[] {
			"CONSENT_TYPE_FUNCTIONAL", "CONSENT_TYPE_PERFORMANCE",
			"CONSENT_TYPE_PERSONALIZATION"
		};
	}

	public String[] getRequiredCookies() {
		return new String[] {"CONSENT_TYPE_STRICTLY_NECESSARY"};
	}

	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;

}