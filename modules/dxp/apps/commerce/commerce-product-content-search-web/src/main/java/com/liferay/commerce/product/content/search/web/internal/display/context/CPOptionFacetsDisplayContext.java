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

import com.liferay.commerce.product.content.search.web.internal.util.CPOptionFacetsUtil;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
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
		CPOptionService cpOptionService, RenderRequest renderRequest,
		List<Facet> facets,
		PortletSharedSearchResponse portletSharedSearchResponse) {

		_cpOptionService = cpOptionService;
		_renderRequest = renderRequest;
		_facets = facets;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		_locale = _renderRequest.getLocale();
	}

	public CPOption getCPOption(long groupId, String fieldName)
		throws PortalException {

		String cpOptionKey =
			CPOptionFacetsUtil.getCPOptionKeyFromIndexFieldName(fieldName);

		CPOption cpOption = _cpOptionService.fetchCPOption(
			groupId, cpOptionKey);

		return cpOption;
	}

	public String getCPOptionKey(long groupId, String fieldName)
		throws PortalException {

		CPOption cpOption = getCPOption(groupId, fieldName);

		return cpOption.getKey();
	}

	public String getCPOptionTitle(long groupId, String fieldName)
		throws PortalException {

		CPOption cpOption = getCPOption(groupId, fieldName);

		String title = StringPool.BLANK;

		if (cpOption != null) {
			title = cpOption.getTitle(_locale);
		}

		return title;
	}

	public List<Facet> getFacets() {
		return _facets;
	}

	public boolean isCPOptionValueSelected(
			long groupId, String fieldName, String fieldValue)
		throws PortalException {

		CPOption cpOption = getCPOption(groupId, fieldName);

		Optional<String[]> parameterValuesOptional =
			_portletSharedSearchResponse.getParameterValues(
				cpOption.getKey(), _renderRequest);

		if (parameterValuesOptional.isPresent()) {
			String[] parameterValues = parameterValuesOptional.get();

			return ArrayUtil.contains(parameterValues, fieldValue);
		}

		return false;
	}

	private final CPOptionService _cpOptionService;
	private final List<Facet> _facets;
	private final Locale _locale;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;
	private final RenderRequest _renderRequest;

}