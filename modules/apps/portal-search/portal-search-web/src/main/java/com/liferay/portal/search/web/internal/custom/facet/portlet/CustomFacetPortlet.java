/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.facet.portlet;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext;
import com.liferay.portal.search.web.internal.custom.facet.display.context.builder.CustomFacetDisplayContextBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-custom-facet",
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Custom Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/custom/facet/view.jsp",
		"javax.portlet.name=" + CustomFacetPortletKeys.CUSTOM_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class CustomFacetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		CustomFacetDisplayContext customFacetDisplayContext =
			_createCustomFacetDisplayContext(
				portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, customFacetDisplayContext);

		if (customFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private CustomFacetDisplayContext _buildDisplayContext(
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest)
		throws ConfigurationException {

		CustomFacetDisplayContextBuilder customFacetDisplayContextBuilder =
			new CustomFacetDisplayContextBuilder(
				_getHttpServletRequest(renderRequest));

		CustomFacetPortletPreferences customFacetPortletPreferences =
			new CustomFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		String parameterName = _getParameterName(customFacetPortletPreferences);

		return customFacetDisplayContextBuilder.setCustomDisplayCaption(
			customFacetPortletPreferences.getCustomHeading()
		).setFacet(
			_getFacet(
				portletSharedSearchResponse, customFacetPortletPreferences,
				renderRequest)
		).setFieldToAggregate(
			customFacetPortletPreferences.getAggregationField()
		).setFrequenciesVisible(
			customFacetPortletPreferences.isFrequenciesVisible()
		).setFrequencyThreshold(
			customFacetPortletPreferences.getFrequencyThreshold()
		).setMaxTerms(
			customFacetPortletPreferences.getMaxTerms()
		).setOrder(
			customFacetPortletPreferences.getOrder()
		).setPaginationStartParameterName(
			_getPaginationStartParameterName(portletSharedSearchResponse)
		).setParameterName(
			parameterName
		).setParameterValues(
			portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest)
		).build();
	}

	private CustomFacetDisplayContext _createCustomFacetDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		try {
			return _buildDisplayContext(
				portletSharedSearchResponse, renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private Facet _getFacet(
		PortletSharedSearchResponse portletSharedSearchResponse,
		CustomFacetPortletPreferences customFacetPortletPreferences,
		RenderRequest renderRequest) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getFederatedSearchResponse(
				customFacetPortletPreferences.getFederatedSearchKey());

		return searchResponse.withFacetContextGet(
			facetContext -> facetContext.getFacet(
				_getPortletId(renderRequest)));
	}

	private HttpServletRequest _getHttpServletRequest(
		RenderRequest renderRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	private String _getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

	private String _getParameterName(
		CustomFacetPortletPreferences customFacetPortletPreferences) {

		String parameterName = customFacetPortletPreferences.getParameterName();

		if (Validator.isNotNull(parameterName)) {
			return parameterName;
		}

		String aggregationField =
			customFacetPortletPreferences.getAggregationField();

		if (Validator.isNotNull(aggregationField)) {
			return aggregationField;
		}

		return "customfield";
	}

	private String _getPortletId(RenderRequest renderRequest) {
		return _portal.getPortletId(renderRequest);
	}

	@Reference
	private Portal _portal;

}