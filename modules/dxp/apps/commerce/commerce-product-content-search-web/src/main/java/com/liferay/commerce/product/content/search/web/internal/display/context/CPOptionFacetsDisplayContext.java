/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.content.search.web.internal.display.context;

import com.liferay.commerce.product.content.search.web.internal.util.CPOptionFacetsUtil;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
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

	public String getCPOptionName(long groupId, String fieldName)
		throws PortalException {

		CPOption cpOption = getCPOption(groupId, fieldName);

		String name = StringPool.BLANK;

		if (cpOption != null) {
			name = cpOption.getName(_locale);
		}

		return name;
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