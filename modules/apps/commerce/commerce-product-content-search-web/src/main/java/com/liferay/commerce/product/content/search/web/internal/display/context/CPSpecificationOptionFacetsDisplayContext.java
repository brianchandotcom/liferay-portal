/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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