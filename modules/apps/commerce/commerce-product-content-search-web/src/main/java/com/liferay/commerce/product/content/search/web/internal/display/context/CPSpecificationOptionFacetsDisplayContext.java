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
import com.liferay.commerce.product.content.search.web.internal.configuration.CPSpecificationOptionFacetPortletInstanceConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionFacetsDisplayContext implements Serializable {

	public CPSpecificationOptionFacetsDisplayContext() {
	}

	public CPSpecificationOptionFacetsDisplayContext(
		CPSpecificationOptionFacetPortletInstanceConfiguration
			cpSpecificationOptionFacetPortletInstanceConfiguration) {

		_cpSpecificationOptionFacetPortletInstanceConfiguration =
			cpSpecificationOptionFacetPortletInstanceConfiguration;
	}

	public CPSpecificationOptionFacetPortletInstanceConfiguration
		getCPSpecificationOptionFacetPortletInstanceConfiguration() {

		return _cpSpecificationOptionFacetPortletInstanceConfiguration;
	}

	public List<CPSpecificationOptionsSearchFacetDisplayContext>
		getCpSpecificationOptionsSearchFacetDisplayContext() {

		return _cpSpecificationOptionsSearchFacetDisplayContext;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_cpSpecificationOptionFacetPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			_displayStyleGroupId = _themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public boolean hasCommerceChannel() throws PortalException {
		CommerceContext commerceContext =
			(CommerceContext)_renderRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceChannelId = commerceContext.getCommerceChannelId();

		if (commerceChannelId > 0) {
			return true;
		}

		return false;
	}

	public void setCpSpecificationOptionFacetPortletInstanceConfiguration(
		CPSpecificationOptionFacetPortletInstanceConfiguration
			cpSpecificationOptionFacetPortletInstanceConfiguration) {

		_cpSpecificationOptionFacetPortletInstanceConfiguration =
			cpSpecificationOptionFacetPortletInstanceConfiguration;
	}

	public void setCpSpecificationOptionsSearchFacetDisplayContext(
		List<CPSpecificationOptionsSearchFacetDisplayContext>
			cpSpecificationOptionsSearchFacetDisplayContext) {

		_cpSpecificationOptionsSearchFacetDisplayContext =
			cpSpecificationOptionsSearchFacetDisplayContext;
	}

	public void setRenderRequest(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	private CPSpecificationOptionFacetPortletInstanceConfiguration
		_cpSpecificationOptionFacetPortletInstanceConfiguration;
	private List<CPSpecificationOptionsSearchFacetDisplayContext>
		_cpSpecificationOptionsSearchFacetDisplayContext;
	private long _displayStyleGroupId;
	private RenderRequest _renderRequest;
	private ThemeDisplay _themeDisplay;

}