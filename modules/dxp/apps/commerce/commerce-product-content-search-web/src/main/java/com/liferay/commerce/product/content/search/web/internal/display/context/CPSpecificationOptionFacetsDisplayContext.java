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

import com.liferay.commerce.product.content.search.web.internal.util.CPSpecificationOptionFacetsUtil;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.ArrayUtil;
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
		CPDefinitionSpecificationOptionValueService
			cpDefinitionSpecificationOptionValueService,
		CPSpecificationOptionService cpSpecificationOptionService,
		RenderRequest renderRequest, List<Facet> facets,
		PortletSharedSearchResponse portletSharedSearchResponse) {

		_cpDefinitionSpecificationOptionValueService =
			cpDefinitionSpecificationOptionValueService;
		_cpSpecificationOptionService = cpSpecificationOptionService;
		_renderRequest = renderRequest;
		_facets = facets;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		_locale = _renderRequest.getLocale();
	}

	public CPSpecificationOption getCPSpecificationOption(String fieldName)
		throws PortalException {

		String cpSpecificationOptionId =
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionIdFromIndexFieldName(fieldName);

		return _cpSpecificationOptionService.fetchCPSpecificationOption(
			Long.parseLong(cpSpecificationOptionId));
	}

	public long getCPSpecificationOptionKey(String fieldName)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		return cpSpecificationOption.getCPSpecificationOptionId();
	}

	public String getCPSpecificationOptionTitle(String fieldName)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		return cpSpecificationOption.getTitle(_locale);
	}

	public String getDisplayName(Locale locale, String key)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				_cpDefinitionSpecificationOptionValueService.
					fetchCPDefinitionSpecificationOptionValue(
						Long.parseLong(key));

		return cpDefinitionSpecificationOptionValue.getValue(locale);
	}

	public List<Facet> getFacets() {
		return _facets;
	}

	public boolean isCPDefinitionSpecificationOptionValueSelected(
			String fieldName, String fieldValue)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		Optional<String[]> parameterValuesOptional =
			_portletSharedSearchResponse.getParameterValues(
				String.valueOf(
					cpSpecificationOption.getCPSpecificationOptionId()),
				_renderRequest);

		if (parameterValuesOptional.isPresent()) {
			String[] parameterValues = parameterValuesOptional.get();

			return ArrayUtil.contains(parameterValues, fieldValue);
		}

		return false;
	}

	private final CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;
	private final CPSpecificationOptionService _cpSpecificationOptionService;
	private final List<Facet> _facets;
	private final Locale _locale;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;
	private final RenderRequest _renderRequest;

}