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
import com.liferay.commerce.product.content.search.web.internal.util.CPOptionFacetsUtil;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.portlet.RenderRequest;

/**
 * @author Marco Leo
 */
public class CPOptionFacetsDisplayContext {

	public CPOptionFacetsDisplayContext(
		CPOptionLocalService cpOptionLocalService, RenderRequest renderRequest,
		List<Facet> facets, String paginationStartParameterName,
		PortletSharedSearchResponse portletSharedSearchResponse) {

		_cpOptionLocalService = cpOptionLocalService;
		_renderRequest = renderRequest;
		_facets = facets;
		_paginationStartParameterName = paginationStartParameterName;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		_locale = _renderRequest.getLocale();
	}

	public CPOption getCPOption(long companyId, String fieldName)
		throws PortalException {

		String cpOptionKey =
			CPOptionFacetsUtil.getCPOptionKeyFromIndexFieldName(fieldName);

		return _cpOptionLocalService.fetchCPOption(companyId, cpOptionKey);
	}

	public String getCPOptionKey(long companyId, String fieldName)
		throws PortalException {

		CPOption cpOption = getCPOption(companyId, fieldName);

		return cpOption.getKey();
	}

	public String getCPOptionName(long companyId, String fieldName)
		throws PortalException {

		CPOption cpOption = getCPOption(companyId, fieldName);

		String name = StringPool.BLANK;

		if (cpOption != null) {
			name = cpOption.getName(_locale);
		}

		return name;
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

	public boolean isCPOptionValueSelected(
			long companyId, String fieldName, String fieldValue)
		throws PortalException {

		CPOption cpOption = getCPOption(companyId, fieldName);

		Optional<String[]> parameterValuesOptional =
			_portletSharedSearchResponse.getParameterValues(
				cpOption.getKey(), _renderRequest);

		if (parameterValuesOptional.isPresent()) {
			String[] parameterValues = parameterValuesOptional.get();

			return ArrayUtil.contains(parameterValues, fieldValue);
		}

		return false;
	}

	private final CPOptionLocalService _cpOptionLocalService;
	private final List<Facet> _facets;
	private final Locale _locale;
	private final String _paginationStartParameterName;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;
	private final RenderRequest _renderRequest;

}