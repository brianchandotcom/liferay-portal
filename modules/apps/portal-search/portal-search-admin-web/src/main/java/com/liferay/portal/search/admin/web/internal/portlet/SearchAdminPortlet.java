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

package com.liferay.portal.search.admin.web.internal.portlet;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.admin.web.internal.constants.SearchAdminPortletKeys;
import com.liferay.portal.search.admin.web.internal.constants.SearchAdminWebKeys;
import com.liferay.portal.search.admin.web.internal.display.context.FieldMappingsDisplayBuilder;
import com.liferay.portal.search.admin.web.internal.display.context.SearchAdminDisplayBuilder;
import com.liferay.portal.search.admin.web.internal.display.context.SearchAdminDisplayContext;
import com.liferay.portal.search.admin.web.internal.display.context.SearchEngineDisplayBuilder;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.index.IndexInformation;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-search-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Search Administration",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SearchAdminPortletKeys.SEARCH_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SearchAdminPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal, renderRequest, renderResponse);

		searchAdminDisplayBuilder.setIndexInformation(indexInformation);

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, searchAdminDisplayContext);

		String tab = searchAdminDisplayContext.getSelectedTab();

		if (tab.equals("connections")) {
			SearchEngineDisplayBuilder searchEngineDisplayBuilder =
				new SearchEngineDisplayBuilder();

			searchEngineDisplayBuilder.setSearchEngineInformation(
				searchEngineInformation);

			renderRequest.setAttribute(
				SearchAdminWebKeys.SEARCH_ENGINE_DISPLAY_CONTEXT,
				searchEngineDisplayBuilder.build());
		}
		else if (tab.equals("field-mappings")) {
			FieldMappingsDisplayBuilder fieldMappingsDisplayBuilder =
				new FieldMappingsDisplayBuilder(_http);

			fieldMappingsDisplayBuilder.setCompanyId(
				_portal.getCompanyId(renderRequest));
			fieldMappingsDisplayBuilder.setCurrentURL(
				_portal.getCurrentURL(renderRequest));
			fieldMappingsDisplayBuilder.setIndexInformation(indexInformation);
			fieldMappingsDisplayBuilder.setNamespace(
				renderResponse.getNamespace());

			String selectedIndexName = ParamUtil.getString(
				renderRequest, "selectedIndexName");

			fieldMappingsDisplayBuilder.setSelectedIndexName(selectedIndexName);

			renderRequest.setAttribute(
				SearchAdminWebKeys.FIELD_MAPPINGS_DISPLAY_CONTEXT,
				fieldMappingsDisplayBuilder.build());
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile IndexInformation indexInformation;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile SearchEngineInformation searchEngineInformation;

	@Reference
	private Http _http;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}