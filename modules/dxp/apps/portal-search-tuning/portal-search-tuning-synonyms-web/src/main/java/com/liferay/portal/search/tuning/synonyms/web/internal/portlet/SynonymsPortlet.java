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

package com.liferay.portal.search.tuning.synonyms.web.internal.portlet;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.synonyms.web.internal.constants.SynonymsPortletKeys;
import com.liferay.portal.search.tuning.synonyms.web.internal.display.context.SynonymsDisplayBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.DocumentToSynonymSetTranslator;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexNameBuilder;

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
		"com.liferay.portlet.css-class-wrapper=portlet-search-tuning",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Synonyms",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SynonymsPortletKeys.SYNONYMS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class SynonymsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		SynonymsDisplayBuilder synonymsDisplayBuilder =
			new SynonymsDisplayBuilder(
				_documentToSynonymSetTranslator,
				_portal.getHttpServletRequest(renderRequest), _language,
				_portal, _queries, renderRequest, renderResponse,
				_searchEngineAdapter, _searchEngineInformation, _sorts,
				_synonymSetIndexNameBuilder);

		renderRequest.setAttribute(
			SynonymsPortletKeys.SYNONYMS_DISPLAY_CONTEXT,
			synonymsDisplayBuilder.build());

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private DocumentToSynonymSetTranslator _documentToSynonymSetTranslator;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	@Reference
	private Sorts _sorts;

	@Reference
	private SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder;

}