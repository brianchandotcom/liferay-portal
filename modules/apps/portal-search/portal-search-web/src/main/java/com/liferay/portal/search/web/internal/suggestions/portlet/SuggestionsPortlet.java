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

package com.liferay.portal.search.web.internal.suggestions.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.portlet.shared.task.helper.PortletSharedRequestHelper;
import com.liferay.portal.search.web.internal.suggestions.constants.SuggestionsPortletKeys;
import com.liferay.portal.search.web.internal.suggestions.display.context.SuggestionsPortletDisplayContext;
import com.liferay.portal.search.web.internal.suggestions.display.context.builder.SuggestionsPortletDisplayContextBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.io.IOException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-suggestions",
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
		"javax.portlet.display-name=Suggestions",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/suggestions/view.jsp",
		"javax.portlet.name=" + SuggestionsPortletKeys.SUGGESTIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class SuggestionsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		SuggestionsPortletPreferences suggestionsPortletPreferences =
			new SuggestionsPortletPreferencesImpl(
				Optional.ofNullable(renderRequest.getPreferences()));

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			_buildDisplayContext(
				suggestionsPortletPreferences, portletSharedSearchResponse,
				renderRequest);

		if (!suggestionsPortletDisplayContext.hasRelatedQueriesSuggestions() &&
			!suggestionsPortletDisplayContext.hasSpellCheckSuggestion()) {

			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, suggestionsPortletDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected Html html;

	@Reference
	protected Http http;

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedRequestHelper portletSharedRequestHelper;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private SuggestionsPortletDisplayContext _buildDisplayContext(
		SuggestionsPortletPreferences suggestionsPortletPreferences,
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		SuggestionsPortletDisplayContextBuilder
			suggestionsPortletDisplayContextBuilder =
				new SuggestionsPortletDisplayContextBuilder(html, http);

		_copy(
			portletSharedSearchResponse::getKeywordsOptional,
			suggestionsPortletDisplayContextBuilder::setKeywords);

		SearchSettings searchSettings =
			portletSharedSearchResponse.getSearchSettings();

		_copy(
			searchSettings::getKeywordsParameterName,
			suggestionsPortletDisplayContextBuilder::setKeywordsParameterName);

		suggestionsPortletDisplayContextBuilder.setRelatedQueriesSuggestions(
			portletSharedSearchResponse.getRelatedQueriesSuggestions());
		suggestionsPortletDisplayContextBuilder.
			setRelatedQueriesSuggestionsEnabled(
				suggestionsPortletPreferences.
					isRelatedQueriesSuggestionsEnabled());
		suggestionsPortletDisplayContextBuilder.setSearchURL(
			portletSharedRequestHelper.getCompleteURL(renderRequest));

		_copy(
			portletSharedSearchResponse::getSpellCheckSuggestionOptional,
			suggestionsPortletDisplayContextBuilder::setSpellCheckSuggestion);

		suggestionsPortletDisplayContextBuilder.setSpellCheckSuggestionEnabled(
			suggestionsPortletPreferences.isSpellCheckSuggestionEnabled());

		return suggestionsPortletDisplayContextBuilder.build();
	}

	private <T> void _copy(Supplier<Optional<T>> from, Consumer<T> to) {
		Optional<T> optional = from.get();

		optional.ifPresent(to);
	}

}