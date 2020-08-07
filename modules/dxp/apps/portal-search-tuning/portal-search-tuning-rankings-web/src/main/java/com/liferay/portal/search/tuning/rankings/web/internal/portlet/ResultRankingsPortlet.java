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

package com.liferay.portal.search.tuning.rankings.web.internal.portlet;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;
import com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingPortletDisplayBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingPortletDisplayContext;
import com.liferay.portal.search.tuning.rankings.web.internal.index.DocumentToRankingTranslator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Filipe Oshiro
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-result-rankings",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Result Rankings",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ResultRankingsPortletKeys.RESULT_RANKINGS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class ResultRankingsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		RankingPortletDisplayContext rankingPortletDisplayContext =
			new RankingPortletDisplayBuilder(
				documentToRankingTranslator,
				portal.getHttpServletRequest(renderRequest), language, portal,
				queries, rankingIndexNameBuilder, sorts, renderRequest,
				renderResponse, searchEngineAdapter, _searchEngineInformation
			).build();

		renderRequest.setAttribute(
			ResultRankingsPortletKeys.RESULT_RANKINGS_DISPLAY_CONTEXT,
			rankingPortletDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected DocumentToRankingTranslator documentToRankingTranslator;

	@Reference
	protected IndexNameBuilder indexNameBuilder;

	@Reference
	protected Language language;

	@Reference
	protected Portal portal;

	@Reference
	protected Queries queries;

	@Reference
	protected RankingIndexNameBuilder rankingIndexNameBuilder;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	@Reference
	protected Sorts sorts;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

}