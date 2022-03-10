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

package com.liferay.portal.search.web.internal.modified.facet.portlet;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetDisplayContext;
import com.liferay.portal.search.web.internal.modified.facet.display.context.builder.ModifiedFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
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
 * @author Lino Alves
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-modified-facet",
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
		"javax.portlet.display-name=Modified Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/modified/facet/view.jsp",
		"javax.portlet.name=" + ModifiedFacetPortletKeys.MODIFIED_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class ModifiedFacetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			_buildDisplayContext(portletSharedSearchResponse, renderRequest);

		if (modifiedFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, modifiedFacetDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	protected CalendarFactory calendarFactory;
	protected DateFormatFactory dateFormatFactory;

	@Reference
	protected Http http;

	@Reference
	protected ModifiedFacetFactory modifiedFacetFactory;

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private ModifiedFacetDisplayContext _buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		ModifiedFacetPortletPreferences modifiedFacetPortletPreferences =
			new ModifiedFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		ModifiedFacetDisplayContextBuilder modifiedFacetDisplayContextBuilder =
			_createModifiedFacetDisplayContextBuilder(
				_getCalendarFactory(), _getDateFormatFactory(), http,
				renderRequest);

		modifiedFacetDisplayContextBuilder.setCurrentURL(
			portal.getCurrentURL(renderRequest));
		modifiedFacetDisplayContextBuilder.setFacet(
			portletSharedSearchResponse.getFacet(_getFieldName()));

		ThemeDisplay themeDisplay = _getThemeDisplay(renderRequest);

		modifiedFacetDisplayContextBuilder.setLocale(themeDisplay.getLocale());

		modifiedFacetDisplayContextBuilder.setPaginationStartParameterName(
			_getPaginationStartParameterName(portletSharedSearchResponse));

		String parameterName =
			modifiedFacetPortletPreferences.getParameterName();

		modifiedFacetDisplayContextBuilder.setParameterName(parameterName);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest),
			modifiedFacetDisplayContextBuilder::setParameterValues);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameter(
				parameterName + "From", renderRequest),
			modifiedFacetDisplayContextBuilder::setFromParameterValue);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameter(
				parameterName + "To", renderRequest),
			modifiedFacetDisplayContextBuilder::setToParameterValue);

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		modifiedFacetDisplayContextBuilder.setTimeZone(
			themeDisplay.getTimeZone());
		modifiedFacetDisplayContextBuilder.setTotalHits(
			searchResponse.getTotalHits());

		return modifiedFacetDisplayContextBuilder.build();
	}

	private ModifiedFacetDisplayContextBuilder
		_createModifiedFacetDisplayContextBuilder(
			CalendarFactory calendarFactory,
			DateFormatFactory dateFormatFactory, Http http,
			RenderRequest renderRequest) {

		try {
			return new ModifiedFacetDisplayContextBuilder(
				calendarFactory, dateFormatFactory, http, renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private CalendarFactory _getCalendarFactory() {

		// See LPS-72507 and LPS-76500

		if (calendarFactory != null) {
			return calendarFactory;
		}

		return CalendarFactoryUtil.getCalendarFactory();
	}

	private DateFormatFactory _getDateFormatFactory() {

		// See LPS-72507 and LPS-76500

		if (dateFormatFactory != null) {
			return dateFormatFactory;
		}

		return DateFormatFactoryUtil.getDateFormatFactory();
	}

	private String _getFieldName() {
		Facet facet = modifiedFacetFactory.newInstance(null);

		return facet.getFieldName();
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

}