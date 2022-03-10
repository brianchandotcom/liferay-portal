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

package com.liferay.portal.search.web.internal.folder.facet.portlet;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.facet.display.context.FolderSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.FolderSearcher;
import com.liferay.portal.search.web.internal.facet.display.context.FolderTitleLookup;
import com.liferay.portal.search.web.internal.facet.display.context.FolderTitleLookupImpl;
import com.liferay.portal.search.web.internal.facet.display.context.builder.FolderSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.folder.facet.constants.FolderFacetPortletKeys;
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
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-folder-facet",
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
		"javax.portlet.display-name=Folder Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/folder/facet/view.jsp",
		"javax.portlet.name=" + FolderFacetPortletKeys.FOLDER_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class FolderFacetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		FolderSearchFacetDisplayContext folderSearchFacetDisplayContext =
			_buildDisplayContext(portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, folderSearchFacetDisplayContext);

		if (folderSearchFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private FolderSearchFacetDisplayContext _buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Facet facet = portletSharedSearchResponse.getFacet(
			_getAggregationName(renderRequest));

		FolderTitleLookup folderTitleLookup = new FolderTitleLookupImpl(
			new FolderSearcher(), portal.getHttpServletRequest(renderRequest));

		FolderFacetConfiguration folderFacetConfiguration =
			new FolderFacetConfigurationImpl(facet.getFacetConfiguration());

		FolderFacetPortletPreferences folderFacetPortletPreferences =
			new FolderFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		FolderSearchFacetDisplayContextBuilder
			folderSearchFacetDisplayContextBuilder =
				_createFolderSearchFacetDisplayContextBuilder(renderRequest);

		folderSearchFacetDisplayContextBuilder.setFacet(facet);
		folderSearchFacetDisplayContextBuilder.setFolderTitleLookup(
			folderTitleLookup);
		folderSearchFacetDisplayContextBuilder.setFrequenciesVisible(
			folderFacetPortletPreferences.isFrequenciesVisible());
		folderSearchFacetDisplayContextBuilder.setFrequencyThreshold(
			folderFacetConfiguration.getFrequencyThreshold());
		folderSearchFacetDisplayContextBuilder.setMaxTerms(
			folderFacetConfiguration.getMaxTerms());
		folderSearchFacetDisplayContextBuilder.setPaginationStartParameterName(
			_getPaginationStartParameterName(portletSharedSearchResponse));

		String parameterName = folderFacetPortletPreferences.getParameterName();

		folderSearchFacetDisplayContextBuilder.setParameterName(parameterName);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest),
			folderSearchFacetDisplayContextBuilder::setParameterValues);

		return folderSearchFacetDisplayContextBuilder.build();
	}

	private FolderSearchFacetDisplayContextBuilder
		_createFolderSearchFacetDisplayContextBuilder(
			RenderRequest renderRequest) {

		try {
			return new FolderSearchFacetDisplayContextBuilder(renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private String _getAggregationName(RenderRequest renderRequest) {
		return portal.getPortletId(renderRequest);
	}

	private String _getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

}