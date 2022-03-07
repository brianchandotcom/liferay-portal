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

package com.liferay.commerce.product.content.search.web.internal.display.context;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionFacetsDisplayContext implements Serializable {

	public CPSpecificationOptionFacetsDisplayContext(
			HttpServletRequest httpServletRequest)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;
	}

	public List<CPSpecificationOptionsSearchFacetDisplayContext>
		getCPSpecificationOptionsSearchFacetDisplayContexts() {

		return _cpSpecificationOptionsSearchFacetDisplayContexts;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		long displayStyleGroupId = _DISPLAY_STYLE_GROUP_ID;

		if (displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		_displayStyleGroupId = displayStyleGroupId;

		return _displayStyleGroupId;
	}

	public boolean hasCommerceChannel() throws PortalException {
		CommerceContext commerceContext =
			(CommerceContext)_httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceChannelId = commerceContext.getCommerceChannelId();

		if (commerceChannelId > 0) {
			return true;
		}

		return false;
	}

	public void setCPSpecificationOptionsSearchFacetDisplayContexts(
		List<CPSpecificationOptionsSearchFacetDisplayContext>
			cpSpecificationOptionsSearchFacetDisplayContexts) {

		_cpSpecificationOptionsSearchFacetDisplayContexts =
			cpSpecificationOptionsSearchFacetDisplayContexts;
	}

	private static final long _DISPLAY_STYLE_GROUP_ID = 0;

	private List<CPSpecificationOptionsSearchFacetDisplayContext>
		_cpSpecificationOptionsSearchFacetDisplayContexts;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;

}