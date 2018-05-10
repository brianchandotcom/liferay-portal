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

package com.liferay.portal.search.web.internal.custom.facet.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.facet.custom.CustomFacetFactory;
import com.liferay.portal.search.web.internal.custom.facet.builder.CustomFacetBuilder;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayBuilder;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-custom-facet",
		"com.liferay.portlet.display-category=category.search",
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
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/custom/facet/view.jsp",
		"javax.portlet.name=" + CustomFacetPortletKeys.CUSTOM_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class CustomFacetPortlet
	extends MVCPortlet implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		CustomFacetPortletPreferences customFacetPortletPreferences =
			new CustomFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		Optional<String> fieldToAggregateOptional =
			customFacetPortletPreferences.getAggregationFieldOptional();

		fieldToAggregateOptional.ifPresent(
			fieldToAggregate -> {
				Facet facet = buildFacet(
					fieldToAggregate, customFacetPortletPreferences,
					portletSharedSearchSettings);

				portletSharedSearchSettings.addFacet(facet);
			});
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		CustomFacetDisplayContext customFacetDisplayContext =
			buildDisplayContext(portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, customFacetDisplayContext);

		if (customFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	protected CustomFacetDisplayContext buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		CustomFacetPortletPreferences customFacetPortletPreferences =
			new CustomFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		CustomFacetDisplayBuilder customFacetDisplayBuilder =
			new CustomFacetDisplayBuilder();

		customFacetDisplayBuilder.setFacet(
			portletSharedSearchResponse.getFacet(
				getAggregationName(
					customFacetPortletPreferences,
					getPortletId(renderRequest))));
		customFacetDisplayBuilder.setFrequenciesVisible(
			customFacetPortletPreferences.isFrequenciesVisible());
		customFacetDisplayBuilder.setFrequencyThreshold(
			customFacetPortletPreferences.getFrequencyThreshold());
		customFacetDisplayBuilder.setMaxTerms(
			customFacetPortletPreferences.getMaxTerms());

		String parameterName = getParameterName(customFacetPortletPreferences);

		customFacetDisplayBuilder.setParameterName(parameterName);

		copy(
			customFacetPortletPreferences::getCustomHeadingOptional,
			customFacetDisplayBuilder::setCustomDisplayCaption);

		customFacetDisplayBuilder.setFieldToAggregate(
			customFacetPortletPreferences.getAggregationFieldString());

		copy(
			() -> getParameterValues(
				parameterName, portletSharedSearchResponse, renderRequest),
			customFacetDisplayBuilder::setParameterValues);

		return customFacetDisplayBuilder.build();
	}

	protected Facet buildFacet(
		String fieldToAggregate,
		CustomFacetPortletPreferences customFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		CustomFacetBuilder customFacetBuilder = new CustomFacetBuilder(
			customFacetFactory);

		customFacetBuilder.setAggregationName(
			getAggregationName(
				customFacetPortletPreferences,
				portletSharedSearchSettings.getPortletId()));
		customFacetBuilder.setFieldToAggregate(fieldToAggregate);
		customFacetBuilder.setSearchContext(
			portletSharedSearchSettings.getSearchContext());

		copy(
			() -> portletSharedSearchSettings.getParameterValues(
				getParameterName(customFacetPortletPreferences)),
			customFacetBuilder::setSelectedFields);

		return customFacetBuilder.build();
	}

	protected <T> void copy(Supplier<Optional<T>> from, Consumer<T> to) {
		Optional<T> optional = from.get();

		optional.ifPresent(to);
	}

	protected String getAggregationName(
		CustomFacetPortletPreferences customFacetPortletPreferences,
		String portletId) {

		return customFacetPortletPreferences.getAggregationFieldString() +
			StringPool.PERIOD + portletId;
	}

	protected String getParameterName(
		CustomFacetPortletPreferences customFacetPortletPreferences) {

		Optional<String> optional = Stream.of(
			customFacetPortletPreferences.getParameterNameOptional(),
			customFacetPortletPreferences.getAggregationFieldOptional()
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).findFirst();

		return optional.orElse("customfield");
	}

	protected Optional<List<String>> getParameterValues(
		String parameterName,
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Optional<String[]> parameterValuesOptional =
			portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest);

		return parameterValuesOptional.map(Arrays::asList);
	}

	protected String getPortletId(RenderRequest renderRequest) {
		return _portal.getPortletId(renderRequest);
	}

	@Reference
	protected CustomFacetFactory customFacetFactory;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	@Reference
	private Portal _portal;

}