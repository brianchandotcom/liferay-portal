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
import com.liferay.commerce.product.content.search.web.internal.util.CPSpecificationOptionFacetsUtil;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.portlet.RenderRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionFacetsDisplayContext {

	public CPSpecificationOptionFacetsDisplayContext(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService,
		RenderRequest renderRequest, List<Facet> facets,
		String paginationStartParameterName,
		PortletSharedSearchResponse portletSharedSearchResponse) {

		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
		_renderRequest = renderRequest;
		_facets = facets;
		_paginationStartParameterName = paginationStartParameterName;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		_locale = _renderRequest.getLocale();
	}

	public CPSpecificationOption getCPSpecificationOption(String fieldName)
		throws PortalException {

		String key =
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionKeyFromIndexFieldName(fieldName);

		return _cpSpecificationOptionLocalService.fetchCPSpecificationOption(
			PortalUtil.getCompanyId(_renderRequest), key);
	}

	public String getCPSpecificationOptionKey(String fieldName)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		return cpSpecificationOption.getKey();
	}

	public String getCPSpecificationOptionTitle(String fieldName)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		return cpSpecificationOption.getTitle(_locale);
	}

	public List<Facet> getFacets() {
		return _facets;
	}

	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
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

	public boolean isCPDefinitionSpecificationOptionValueSelected(
			String fieldName, String fieldValue)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		Optional<String[]> parameterValuesOptional =
			_portletSharedSearchResponse.getParameterValues(
				cpSpecificationOption.getKey(), _renderRequest);

		if (parameterValuesOptional.isPresent()) {
			String[] parameterValues = parameterValuesOptional.get();

			return ArrayUtil.contains(parameterValues, fieldValue);
		}

		return false;
	}

	private final CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;
	private final List<Facet> _facets;
	private final Locale _locale;
	private final String _paginationStartParameterName;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;
	private final RenderRequest _renderRequest;

}