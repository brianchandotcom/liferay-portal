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

package com.liferay.commerce.product.content.search.web.internal.portlet.shared.search;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPPriceRangeFacetsPortletInstanceConfiguration;
import com.liferay.commerce.search.facet.SerializableFacet;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Optional;

import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	enabled = false,
	property = "javax.portlet.name=" + CPPortletKeys.CP_PRICE_RANGE_FACETS,
	service = PortletSharedSearchContributor.class
)
public class CPPriceRangeFacetsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		RenderRequest renderRequest =
			portletSharedSearchSettings.getRenderRequest();

		try {
			SearchContext searchContext =
				portletSharedSearchSettings.getSearchContext();

			Facet facet = _getFacet(renderRequest, searchContext);

			Optional<String[]> parameterValuesOptional =
				portletSharedSearchSettings.getParameterValues71(
					facet.getFieldName());

			SerializableFacet serializableFacet = new SerializableFacet(
				facet.getFieldName(), searchContext);

			if (parameterValuesOptional.isPresent()) {
				serializableFacet.select(parameterValuesOptional.get());

				searchContext.setAttribute(
					facet.getFieldName(), parameterValuesOptional.get());
			}

			portletSharedSearchSettings.addFacet(facet);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private Facet _getFacet(
			RenderRequest renderRequest, SearchContext searchContext)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		CPPriceRangeFacetsPortletInstanceConfiguration
			cpPriceRangeFacetsPortletInstanceConfiguration =
				portletDisplay.getPortletInstanceConfiguration(
					CPPriceRangeFacetsPortletInstanceConfiguration.class);

		Facet facet = new RangeFacet(searchContext);

		FacetConfiguration facetConfiguration = new FacetConfiguration();

		JSONObject jsonObject = new JSONObjectImpl();

		String rangesJSONArrayString =
			cpPriceRangeFacetsPortletInstanceConfiguration.
				rangesJSONArrayString();

		rangesJSONArrayString = StringUtil.replace(
			rangesJSONArrayString, new String[] {"\\,", StringPool.STAR},
			new String[] {StringPool.COMMA, String.valueOf(Double.MAX_VALUE)});

		jsonObject.put(
			"ranges", JSONFactoryUtil.createJSONArray(rangesJSONArrayString));

		facetConfiguration.setDataJSONObject(jsonObject);

		facet.setFacetConfiguration(facetConfiguration);

		facet.setFieldName(CPField.BASE_PRICE);

		return facet;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPPriceRangeFacetsPortletSharedSearchContributor.class);

}