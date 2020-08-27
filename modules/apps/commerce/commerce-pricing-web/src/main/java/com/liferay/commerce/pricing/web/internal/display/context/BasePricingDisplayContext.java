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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.pricing.web.internal.display.context.util.CommercePricingRequestHelper;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Alberti
 */
public abstract class BasePricingDisplayContext {

	public BasePricingDisplayContext(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			this.httpServletRequest);

		commercePricingRequestHelper = new CommercePricingRequestHelper(
			httpServletRequest);

		liferayPortletRequest =
			commercePricingRequestHelper.getLiferayPortletRequest();
		liferayPortletResponse =
			commercePricingRequestHelper.getLiferayPortletResponse();
	}

	protected final CommercePricingRequestHelper commercePricingRequestHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final PortalPreferences portalPreferences;

}