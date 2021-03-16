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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsWebKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.display.context.ViewMisspellingSetsDisplayContext;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.display.context.ViewMisspellingSetsManagementToolbarDisplayContext;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.DocumentToMisspellingSetTranslator;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MisspellingsPortletKeys.MISSPELLINGS,
		"mvc.command.name=" + MisspellingsMVCCommandNames.VIEW_MISSPELLING_SETS,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ViewMisspellingSetsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ViewMisspellingSetsDisplayContext misspellingSetsDisplayContext =
			new ViewMisspellingSetsDisplayContext(
				_portal.getLiferayPortletRequest(renderRequest),
				_portal.getLiferayPortletResponse(renderResponse),
				_documentToMisspellingSetTranslator,
				_misspellingsIndexNameBuilder, _portal, _queries,
				_searchEngineAdapter, _sorts);

		renderRequest.setAttribute(
			MisspellingsWebKeys.VIEW_MISSPELLING_SETS_DISPLAY_CONTEXT,
			misspellingSetsDisplayContext);

		try {
			renderRequest.setAttribute(
				MisspellingsWebKeys.
					VIEW_MISSPELLING_SETS_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
				_getMisspellingSetsManagementToolbarDisplayContext(
					renderRequest, renderResponse,
					misspellingSetsDisplayContext.getSearchContainer(),
					misspellingSetsDisplayContext.getDisplayStyle()));
		}
		catch (PortalException | PortletException exception) {
			_log.error(exception.getMessage(), exception);

			SessionErrors.add(
				renderRequest, MisspellingsWebKeys.ERROR,
				exception.getMessage());
		}

		return "/view.jsp";
	}

	private ViewMisspellingSetsManagementToolbarDisplayContext
		_getMisspellingSetsManagementToolbarDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			SearchContainer<MisspellingSet> searchContainer,
			String displayStyle) {

		return new ViewMisspellingSetsManagementToolbarDisplayContext(
			_portal.getHttpServletRequest(renderRequest),
			_portal.getLiferayPortletRequest(renderRequest),
			_portal.getLiferayPortletResponse(renderResponse), searchContainer,
			displayStyle);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewMisspellingSetsMVCRenderCommand.class);

	@Reference
	private DocumentToMisspellingSetTranslator
		_documentToMisspellingSetTranslator;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Language _language;

	@Reference
	private MisspellingsIndexNameBuilder _misspellingsIndexNameBuilder;

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

}