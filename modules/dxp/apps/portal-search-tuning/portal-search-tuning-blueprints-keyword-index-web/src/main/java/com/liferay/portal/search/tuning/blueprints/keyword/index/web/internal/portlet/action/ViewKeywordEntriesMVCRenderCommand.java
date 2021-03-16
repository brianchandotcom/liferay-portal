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

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexPortletKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexWebKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context.ViewActiveEntriesManagementToolbarDisplayContext;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context.ViewBlacklistedEntriesManagementToolbarDisplayContext;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context.ViewKeywordEntriesDisplayContext;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context.ViewReportedEntriesManagementToolbarDisplayContext;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.DocumentToKeywordEntryTranslator;

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
		"javax.portlet.name=" + KeywordIndexPortletKeys.KEYWORD_INDEX_ADMIN,
		"mvc.command.name=" + KeywordIndexMVCCommandNames.VIEW_KEYWORD_ENTRIES,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ViewKeywordEntriesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		KeywordEntryStatus keywordEntryStatus = _getStatusFromTab(
			renderRequest);

		ViewKeywordEntriesDisplayContext viewKeywordEntriesDisplayContext =
			_getViewKeywordEntriesDisplayContext(
				renderRequest, renderResponse, keywordEntryStatus);

		renderRequest.setAttribute(
			KeywordIndexWebKeys.VIEW_KEYWORD_ENTRIES_DISPLAY_CONTEXT,
			viewKeywordEntriesDisplayContext);

		if (keywordEntryStatus.equals(KeywordEntryStatus.ACTIVE)) {
			_setActiveEntriesContext(
				renderRequest, renderResponse,
				viewKeywordEntriesDisplayContext);
		}
		else if (keywordEntryStatus.equals(KeywordEntryStatus.BLACKLISTED)) {
			_setBlacklistedEntriesContext(
				renderRequest, renderResponse,
				viewKeywordEntriesDisplayContext);
		}
		else if (keywordEntryStatus.equals(KeywordEntryStatus.REPORTED)) {
			_setReportedEntriesContext(
				renderRequest, renderResponse,
				viewKeywordEntriesDisplayContext);
		}

		return "/view.jsp";
	}

	private KeywordEntryStatus _getStatusFromTab(RenderRequest renderRequest) {
		String status = ParamUtil.getString(
			renderRequest, "tabs", KeywordEntryStatus.REPORTED.name());

		try {
			return KeywordEntryStatus.valueOf(status);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(
				illegalArgumentException.getMessage(),
				illegalArgumentException);
		}

		return KeywordEntryStatus.REPORTED;
	}

	private ViewKeywordEntriesDisplayContext
		_getViewKeywordEntriesDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			KeywordEntryStatus status) {

		return new ViewKeywordEntriesDisplayContext(
			_portal.getLiferayPortletRequest(renderRequest),
			_portal.getLiferayPortletResponse(renderResponse),
			_documentToKeywordEntryTranslator, _portal, _queries,
			_keywordIndexNameBuilder, _searchEngineAdapter, _sorts, status);
	}

	private void _setActiveEntriesContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		ViewKeywordEntriesDisplayContext viewKeywordEntriesDisplayContext) {

		try {
			ViewActiveEntriesManagementToolbarDisplayContext
				viewActiveEntriesManagementToolbarDisplayContext =
					new ViewActiveEntriesManagementToolbarDisplayContext(
						_portal.getHttpServletRequest(renderRequest),
						_portal.getLiferayPortletRequest(renderRequest),
						_portal.getLiferayPortletResponse(renderResponse),
						viewKeywordEntriesDisplayContext.getSearchContainer(),
						viewKeywordEntriesDisplayContext.getDisplayStyle());

			renderRequest.setAttribute(
				KeywordIndexWebKeys.
					ACTIVE_ENTRIES_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
				viewActiveEntriesManagementToolbarDisplayContext);
		}
		catch (PortalException | PortletException exception) {
			_log.error(exception.getMessage(), exception);

			SessionErrors.add(
				renderRequest, KeywordIndexWebKeys.ERROR,
				exception.getMessage());
		}
	}

	private void _setBlacklistedEntriesContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		ViewKeywordEntriesDisplayContext viewKeywordEntriesDisplayContext) {

		try {
			ViewBlacklistedEntriesManagementToolbarDisplayContext
				viewBlacklistedEntriesManagementToolbarDisplayContext =
					new ViewBlacklistedEntriesManagementToolbarDisplayContext(
						_portal.getHttpServletRequest(renderRequest),
						_portal.getLiferayPortletRequest(renderRequest),
						_portal.getLiferayPortletResponse(renderResponse),
						viewKeywordEntriesDisplayContext.getSearchContainer(),
						viewKeywordEntriesDisplayContext.getDisplayStyle());

			renderRequest.setAttribute(
				KeywordIndexWebKeys.
					BLACKLISTED_ENTRIES_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
				viewBlacklistedEntriesManagementToolbarDisplayContext);
		}
		catch (PortalException | PortletException exception) {
			_log.error(exception.getMessage(), exception);

			SessionErrors.add(
				renderRequest, KeywordIndexWebKeys.ERROR,
				exception.getMessage());
		}
	}

	private void _setReportedEntriesContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		ViewKeywordEntriesDisplayContext viewKeywordEntriesDisplayContext) {

		try {
			ViewReportedEntriesManagementToolbarDisplayContext
				viewReportedEntriesManagementToolbarDisplayContext =
					new ViewReportedEntriesManagementToolbarDisplayContext(
						_portal.getHttpServletRequest(renderRequest),
						_portal.getLiferayPortletRequest(renderRequest),
						_portal.getLiferayPortletResponse(renderResponse),
						viewKeywordEntriesDisplayContext.getSearchContainer(),
						viewKeywordEntriesDisplayContext.getDisplayStyle());

			renderRequest.setAttribute(
				KeywordIndexWebKeys.
					REPORTED_ENTRIES_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
				viewReportedEntriesManagementToolbarDisplayContext);
		}
		catch (PortalException | PortletException exception) {
			_log.error(exception.getMessage(), exception);

			SessionErrors.add(
				renderRequest, KeywordIndexWebKeys.ERROR,
				exception.getMessage());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewKeywordEntriesMVCRenderCommand.class);

	@Reference
	private DocumentToKeywordEntryTranslator _documentToKeywordEntryTranslator;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private KeywordIndexNameBuilder _keywordIndexNameBuilder;

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

}