/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.portlet;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.date.facet.constants.DateFacetPortletKeys;
import com.liferay.portal.search.web.internal.date.facet.display.context.DateFacetDisplayContext;
import com.liferay.portal.search.web.internal.date.facet.display.context.builder.DateFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-date-facet",
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
		"javax.portlet.display-name=Date Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/date/facet/view.jsp",
		"javax.portlet.name=" + DateFacetPortletKeys.DATE_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class DateFacetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(renderRequest);

		DateFacetDisplayContext dateFacetDisplayContext = _buildDisplayContext(
			portletSharedSearchResponse, renderRequest);

		if (dateFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, dateFacetDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	private DateFacetDisplayContext _buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			_createDateFacetDisplayContextBuilder(renderRequest);

		dateFacetDisplayContextBuilder.setCurrentURL(
			_portal.getCurrentURL(renderRequest));

		DateFacetPortletPreferences dateFacetPortletPreferences =
			new DateFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		dateFacetDisplayContextBuilder.setCustomDisplayCaption(
			dateFacetPortletPreferences.getCustomHeading());

		dateFacetDisplayContextBuilder.setFacet(
			_getFacet(
				dateFacetPortletPreferences, portletSharedSearchResponse,
				renderRequest));

		dateFacetDisplayContextBuilder.setFieldToAggregate(
			dateFacetPortletPreferences.getAggregationField());
		dateFacetDisplayContextBuilder.setFrequenciesVisible(
			dateFacetPortletPreferences.isFrequenciesVisible());
		dateFacetDisplayContextBuilder.setFrequencyThreshold(
			dateFacetPortletPreferences.getFrequencyThreshold());

		String parameterName = dateFacetPortletPreferences.getParameterName();

		dateFacetDisplayContextBuilder.setFromParameterValue(
			portletSharedSearchResponse.getParameter(
				parameterName + "From", renderRequest));

		ThemeDisplay themeDisplay = _getThemeDisplay(renderRequest);

		dateFacetDisplayContextBuilder.setLocale(themeDisplay.getLocale());

		dateFacetDisplayContextBuilder.setOrder(
			dateFacetPortletPreferences.getOrder());
		dateFacetDisplayContextBuilder.setPaginationStartParameterName(
			_getPaginationStartParameterName(portletSharedSearchResponse));
		dateFacetDisplayContextBuilder.setParameterName(parameterName);
		dateFacetDisplayContextBuilder.setParameterValues(
			portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest));
		dateFacetDisplayContextBuilder.setTimeZone(themeDisplay.getTimeZone());
		dateFacetDisplayContextBuilder.setToParameterValue(
			portletSharedSearchResponse.getParameter(
				parameterName + "To", renderRequest));

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		dateFacetDisplayContextBuilder.setTotalHits(
			searchResponse.getTotalHits());

		return dateFacetDisplayContextBuilder.build();
	}

	private DateFacetDisplayContextBuilder
		_createDateFacetDisplayContextBuilder(RenderRequest renderRequest) {

		try {
			return new DateFacetDisplayContextBuilder(renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private Facet _getFacet(
		DateFacetPortletPreferences dateFacetPortletPreferences,
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getFederatedSearchResponse(
				dateFacetPortletPreferences.getFederatedSearchKey());

		return searchResponse.withFacetContextGet(
			facetContext -> facetContext.getFacet(
				_portal.getPortletId(renderRequest)));
	}

	private String _getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

	private ThemeDisplay _getThemeDisplay(RenderRequest renderRequest) {
		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		return themeDisplaySupplier.getThemeDisplay();
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletSharedSearchRequest _portletSharedSearchRequest;

}