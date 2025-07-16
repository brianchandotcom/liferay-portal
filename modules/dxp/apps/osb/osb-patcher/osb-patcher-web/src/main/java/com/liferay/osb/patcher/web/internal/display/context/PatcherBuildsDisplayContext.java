/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.display.context;

import com.liferay.osb.patcher.constants.PatcherBuildConstants;
import com.liferay.osb.patcher.constants.WorkflowConstants;
import com.liferay.osb.patcher.model.PatcherBuild;
import com.liferay.osb.patcher.service.PatcherBuildLocalServiceUtil;
import com.liferay.osb.patcher.util.PatcherUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PatcherBuildsDisplayContext {

	public PatcherBuildsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public SearchContainer<PatcherBuild> getSearchContainer() throws Exception {
		if (_patcherBuildSearchContainer != null) {
			return _patcherBuildSearchContainer;
		}

		SearchContainer<PatcherBuild> patcherBuildSearchContainer =
			new SearchContainer<>(
				_renderRequest, _getPortletURL(), null, "there-are-no-builds");

		Indexer<PatcherBuild> indexer = IndexerRegistryUtil.getIndexer(
			PatcherBuild.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setAttribute(
			"patcherBuildAccountEntryCode", _getPatcherBuildAccountEntryCode());
		searchContext.setAttribute(
			"patcherProjectVersionId", _getPatcherProjectVersionId());
		searchContext.setAttribute("qaStatus", _getQAStatus());
		searchContext.setAttribute("status", _getStatus());
		searchContext.setAttribute("type", _getType());
		searchContext.setEnd(patcherBuildSearchContainer.getEnd());
		searchContext.setGroupIds(null);

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		String patcherBuildName = ParamUtil.getString(
			_httpServletRequest, "patcherBuildName");

		if ((!PatcherUtil.isPatcherTickets(keywords) ||
			 PatcherUtil.isPatcherProjectVersionName(keywords)) &&
			!PatcherUtil.isPatcherTickets(patcherBuildName)) {

			searchContext.setSorts(
				new Sort("statusDate", Sort.LONG_TYPE, true));
		}

		searchContext.setStart(patcherBuildSearchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		patcherBuildSearchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				SearchResultUtil.getSearchResults(
					hits, LocaleUtil.getDefault()),
				searchResult -> PatcherBuildLocalServiceUtil.fetchPatcherBuild(
					searchResult.getClassPK())),
			hits.getLength());

		_patcherBuildSearchContainer = patcherBuildSearchContainer;

		return _patcherBuildSearchContainer;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getPatcherBuildAccountEntryCode() {
		if (_patcherBuildAccountEntryCode != null) {
			return _patcherBuildAccountEntryCode;
		}

		_patcherBuildAccountEntryCode = ParamUtil.getString(
			_httpServletRequest, "patcherBuildAccountEntryCode");

		return _patcherBuildAccountEntryCode;
	}

	private long _getPatcherProjectVersionId() {
		if (_patcherProjectVersionId != null) {
			return _patcherProjectVersionId;
		}

		_patcherProjectVersionId = ParamUtil.getLong(
			_httpServletRequest, "patcherProjectVersionId");

		return _patcherProjectVersionId;
	}

	private PortletURL _getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/patcher/index_builds"
		).setKeywords(
			_getKeywords()
		).setTabs1(
			"builds"
		).setParameter(
			"patcherProjectVersionId", _getPatcherProjectVersionId()
		).setParameter(
			"qaStatus", _getQAStatus()
		).setParameter(
			"status", _getStatus()
		).setParameter(
			"type", _getType()
		).buildPortletURL();

		return _portletURL;
	}

	private int _getQAStatus() {
		if (_qaStatus != null) {
			return _qaStatus;
		}

		_qaStatus = ParamUtil.getInteger(
			_httpServletRequest, "qaStatus", WorkflowConstants.STATUS_ANY);

		return _qaStatus;
	}

	private int _getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			_httpServletRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	private int _getType() {
		if (_type != null) {
			return _type;
		}

		_type = ParamUtil.getInteger(
			_httpServletRequest, "type", PatcherBuildConstants.TYPE_ANY);

		return _type;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _patcherBuildAccountEntryCode;
	private SearchContainer<PatcherBuild> _patcherBuildSearchContainer;
	private Long _patcherProjectVersionId;
	private PortletURL _portletURL;
	private Integer _qaStatus;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Integer _status;
	private Integer _type;

}