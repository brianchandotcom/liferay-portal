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
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetDisplayBuilder;
import com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetDisplayContext;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import java.util.Optional;

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
		"javax.portlet.security-role-ref=guest,power-user,user"
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
			buildDisplayContext(portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, modifiedFacetDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	protected ModifiedFacetDisplayContext buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		ModifiedFacetPortletPreferences modifiedFacetPortletPreferences =
			new ModifiedFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			new ModifiedFacetDisplayBuilder(
				getCalendarFactory(), getDateFormatFactory(), http);

		modifiedFacetDisplayBuilder.setCurrentURL(
			portal.getCurrentURL(renderRequest));

		Facet facet = portletSharedSearchResponse.getFacet(getFieldName());

		modifiedFacetDisplayBuilder.setFacet(facet);

		ThemeDisplay themeDisplay = getThemeDisplay(renderRequest);

		modifiedFacetDisplayBuilder.setLocale(themeDisplay.getLocale());
		modifiedFacetDisplayBuilder.setTimeZone(themeDisplay.getTimeZone());

		String parameterName =
			modifiedFacetPortletPreferences.getParameterName();

		modifiedFacetDisplayBuilder.setParameterName(parameterName);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest),
			modifiedFacetDisplayBuilder::setParameterValues);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameter(
				parameterName + "From", renderRequest),
			modifiedFacetDisplayBuilder::setFromParameterValue);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameter(
				parameterName + "To", renderRequest),
			modifiedFacetDisplayBuilder::setToParameterValue);

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		modifiedFacetDisplayBuilder.setTotalHits(searchResponse.getTotalHits());

		return modifiedFacetDisplayBuilder.build();
	}

	protected CalendarFactory getCalendarFactory() {

		// See LPS-72507 and LPS-76500

		if (calendarFactory != null) {
			return calendarFactory;
		}

		return CalendarFactoryUtil.getCalendarFactory();
	}

	protected DateFormatFactory getDateFormatFactory() {

		// See LPS-72507 and LPS-76500

		if (dateFormatFactory != null) {
			return dateFormatFactory;
		}

		return DateFormatFactoryUtil.getDateFormatFactory();
	}

	protected String getFieldName() {
		Facet facet = modifiedFacetFactory.newInstance(null);

		return facet.getFieldName();
	}

	protected ModifiedFacetPortletPreferencesImpl getPortletPreferences(
		RenderRequest renderRequest) {

		return new ModifiedFacetPortletPreferencesImpl(
			Optional.ofNullable(renderRequest.getPreferences()));
	}

	protected ThemeDisplay getThemeDisplay(RenderRequest renderRequest) {
		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		return themeDisplaySupplier.getThemeDisplay();
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

}